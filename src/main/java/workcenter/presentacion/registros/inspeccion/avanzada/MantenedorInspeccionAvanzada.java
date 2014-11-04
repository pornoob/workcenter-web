package workcenter.presentacion.registros.inspeccion.avanzada;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.*;
import workcenter.negocio.equipos.LogicaEquipos;
import workcenter.negocio.registros.LogicaInspeccionAvanzada;
import workcenter.negocio.registros.LogicaRegistroActividades;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.presentacion.includes.FicheroUploader;
import workcenter.util.WorkcenterFileListener;
import workcenter.util.components.Constantes;
import workcenter.util.components.SesionCliente;
import workcenter.util.pojo.Descargable;
import workcenter.util.pojo.DynamicColumnDataTable;
import workcenter.util.components.FacesUtil;

import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by colivares on 18-08-14.
 */
@Component
@Scope("flow")
public class MantenedorInspeccionAvanzada implements Serializable, WorkcenterFileListener {
    @Autowired
    private LogicaPersonal logicaPersonal;

    @Autowired
    private LogicaEquipos logicaEquipos;

    @Autowired
    private LogicaInspeccionAvanzada logicaInspeccionAvanzada;

    @Autowired
    private FicheroUploader ficheroUploader;

    @Autowired
    private SesionCliente sesionCliente;

    @Autowired
    private LogicaDocumentos logicaDocumentos;

    @Autowired
    private Constantes constantes;

    @Autowired
    private LogicaRegistroActividades logicaRegistroActividades;

    private List<Personal> conductores;
    private List<Personal> encargados;
    private List<Equipo> tractos;
    private List<Equipo> bateas;
    private Integer mes;
    private Integer anio;
    private List<MiaInspeccionAvanzada> inspeccionesAvanzadas;
    private List<MiaInspeccionAvanzada> inspeccionesAvanzadasFiltradas;
    private MiaInspeccionAvanzada inspeccionAvanzada;
    private List<MiaPregunta> preguntas;
    private Map<String, List<Documento>> comprobantesInspeccion;
    private MarRegistro formulario;
    private enum Formulario {
        CAMION_BATEA,
        DETENCION_GESTION
    };
    private Formulario formularioEnPantalla;

    // Zona de "caché"
    private List<MiaRespuesta> ultimasRespuestas;
    private MiaRespuesta cacheRespuesta;
    private List<MiaInspeccionAvanzada> cacheInspecciones;

    public String inicio() {
        inspeccionesAvanzadas = logicaInspeccionAvanzada.obtenerTodas();
        formulario = logicaRegistroActividades.obtenerRegistro(constantes.getRegistroInspeccionAvanzada());
        obtenerConductores();
        obtenerEncargados();
        obtenerEquipos();
        mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
        anio = Calendar.getInstance().get(Calendar.YEAR);
        return "flowListar";
    }

    public String irListarRegistros() {
        return "flowListarRegistros";
    }

    public String irAgregar() {
        preguntas = logicaInspeccionAvanzada.obtenerPreguntas();
        inspeccionAvanzada = new MiaInspeccionAvanzada();
        formularioEnPantalla = Formulario.DETENCION_GESTION;
        return "flowAgregar";
    }

    public String irEditar(MiaInspeccionAvanzada i) {
        preguntas = logicaInspeccionAvanzada.obtenerPreguntas();
        inspeccionAvanzada = i;
        ultimasRespuestas = logicaInspeccionAvanzada.obtenerRespuestas(inspeccionAvanzada);
        if (!ultimasRespuestas.isEmpty() && ultimasRespuestas.get(0).getMiaPreguntasByIdPregunta().getNumero() < 31)
            formularioEnPantalla = Formulario.CAMION_BATEA;
        else
            formularioEnPantalla = Formulario.DETENCION_GESTION;
        comprobantesInspeccion = new HashMap<String, List<Documento>>();
        return "flowAgregar";
    }

    public MiaRespuesta obtenerRespuesta(MiaPregunta p) {
        if (inspeccionAvanzada.getId() != null)
            for (MiaRespuesta r : ultimasRespuestas) {
                if (r.getMiaPreguntasByIdPregunta().equals(p)) {
                    return r;
                }
            }
        if (cacheRespuesta == null) cacheRespuesta = new MiaRespuesta();
        cacheRespuesta.setCumple(false);
        return cacheRespuesta;
    }

    public void obtenerConductores() {
        conductores = logicaPersonal.obtenerConductores();
    }

    public void obtenerEncargados() {
        encargados = logicaPersonal.obtenerTodos();
    }

    public void obtenerEquipos() {
        tractos = logicaEquipos.obtenerTractos();
        bateas = logicaEquipos.obtenerBateas();
    }

    public void guardar() {
        String valor;
        if (comprobantesInspeccion == null) {
            comprobantesInspeccion = new HashMap<String, List<Documento>>();
        }
        boolean editar = inspeccionAvanzada.getId() != null;
        List<Documento> docs = comprobantesInspeccion.get(sesionCliente.getUsuario().getRut() + "|inspeccion");
        if ((docs == null || docs.isEmpty()) && !editar) {
            FacesUtil.mostrarMensajeError("Operación Fallida", "Debes Adjuntar al menos un documento");
            return;
        }
        List<MiaRespuesta> respuestas = new ArrayList<MiaRespuesta>();
        for (MiaPregunta pregunta : preguntas) {
            valor = FacesUtil.obtenerParametroRequest("formFormularios:pregunta" + pregunta.getId() + "_input");
            MiaRespuesta respuesta = new MiaRespuesta();
            respuesta.setCumple(valor != null);
            respuesta.setMiaInspeccionAvanzadaByIdInspeccion(inspeccionAvanzada);
            respuesta.setMiaPreguntasByIdPregunta(pregunta);
            respuestas.add(respuesta);
        }
        logicaInspeccionAvanzada.guardar(inspeccionAvanzada, respuestas);
        if (!editar) logicaDocumentos.asociarDocumentos(docs, inspeccionAvanzada);
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Se ha guardado la inspección avanzada [ID: " + inspeccionAvanzada.getId() + "]");
        comprobantesInspeccion = null;
        inspeccionAvanzada = new MiaInspeccionAvanzada();
    }

    public List<DynamicColumnDataTable> obtenerCalendario() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        Date fechaSeleccionada = null;
        try {
            fechaSeleccionada = sdf.parse((mes.intValue() > 9 ? String.valueOf(mes) : "0" + mes) + "-" + String.valueOf(anio));
        } catch (ParseException e) {
            System.err.println("PROBLEMAS AL CASTEAR FECHA");
            fechaSeleccionada = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(fechaSeleccionada);
        int cantDiasMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<DynamicColumnDataTable> retorno = new ArrayList<DynamicColumnDataTable>();
        for (int i = 1; i <= cantDiasMes; i++) {
            retorno.add(new DynamicColumnDataTable(String.valueOf(i), String.valueOf(i), "40"));
        }
        retorno.add(new DynamicColumnDataTable("-1", "Total", "40"));
        return retorno;
    }

    public Integer obtenerValorCalendario(String idColumna, Equipo e) {
        if (idColumna == null || "".equals(idColumna)) return 0;
        int id = Integer.parseInt(idColumna);
        String strFecha;
        cacheInspecciones = new ArrayList<MiaInspeccionAvanzada>();
        try {
            int contador = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            switch (id) {
                case -1:
                    strFecha = "01/" + (mes.intValue() < 10 ? "0" + mes.intValue() : mes.intValue()) + "/" + anio;
                    Date fechaInf = sdf.parse(strFecha);
                    Calendar c = Calendar.getInstance();
                    c.setTime(fechaInf);
                    strFecha = c.getActualMaximum(Calendar.DAY_OF_MONTH) + "/" + (mes.intValue() < 10 ? "0" + mes.intValue() : mes.intValue()) + "/" + anio;
                    Date fechaSup = sdf.parse(strFecha);
                    for (MiaInspeccionAvanzada i : inspeccionesAvanzadas) {
                        Equipo revisado = i.getTracto() != null ? i.getTracto() : i.getBatea();
                        Date fechaInspeccion = sdf.parse(sdf.format(i.getFecha()));
                        if (revisado.equals(e) &&
                                (fechaInspeccion.equals(fechaInf) || fechaInspeccion.after(fechaInf)) &&
                                (fechaInspeccion.before(fechaSup) || fechaInspeccion.equals(fechaSup))) {
                            contador++;
                            cacheInspecciones.add(i);
                        }
                    }
                    return contador;
                default:
                    strFecha = (id < 10 ? "0" + id : id) + "/" + (mes.intValue() < 10 ? "0" + mes.intValue() : mes.intValue()) + "/" + anio;
                    Date fecha = sdf.parse(strFecha);
                    for (MiaInspeccionAvanzada i : inspeccionesAvanzadas) {
                        Equipo revisado = i.getTracto() != null ? i.getTracto() : i.getBatea();
                        Date fechaInspeccion = sdf.parse(sdf.format(i.getFecha()));
                        if (revisado.equals(e) && fechaInspeccion.equals(fecha)) {
                            contador++;
                            cacheInspecciones.add(i);
                        }
                    }
                    return contador;

            }
        } catch (ParseException e1) {
            System.err.println("FALLA FECHA");
            return 0;
        }
    }

    public boolean noEsCero(String idCol, Equipo e) {
        return obtenerValorCalendario(idCol, e).intValue() > 0;
    }

    public List<MiaPregunta> getPreguntasDetencionInmediata() {
        int[] preguntasCamion = {31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42};
        List<MiaPregunta> retorno = new ArrayList<MiaPregunta>();
        for (int i = 0; i < preguntasCamion.length; i++) {
            for (MiaPregunta p : preguntas) {
                if (p.getNumero().intValue() == preguntasCamion[i]) {
                    retorno.add(p);
                    break;
                }
            }
        }
        return retorno;
    }

    public List<MiaPregunta> getPreguntasGestionBrevedad() {
        int[] preguntasBatea = {43, 56, 44, 57, 45, 58, 46, 59, 47, 60, 48, 61, 49, 62, 50, 63, 51, 64, 52, 65, 53, 66, 54, 67, 55, 68};
        List<MiaPregunta> retorno = new ArrayList<MiaPregunta>();
        for (int i = 0; i < preguntasBatea.length; i++) {
            for (MiaPregunta p : preguntas) {
                if (p.getNumero().intValue() == preguntasBatea[i]) {
                    retorno.add(p);
                    break;
                }
            }
        }
        return retorno;
    }

    public List<MiaPregunta> getPreguntasCamion() {
        int[] preguntasCamion = {1, 11, 2, 12, 3, 13, 4, 14, 5, 15, 6, 16, 7, 17, 8, 18, 9, 19, 10};
        List<MiaPregunta> retorno = new ArrayList<MiaPregunta>();
        for (int i = 0; i < preguntasCamion.length; i++) {
            for (MiaPregunta p : preguntas) {
                if (p.getNumero().intValue() == preguntasCamion[i]) {
                    retorno.add(p);
                    break;
                }
            }
        }
        return retorno;
    }

    public List<MiaPregunta> getPreguntasBatea() {
        int[] preguntasBatea = {20, 26, 21, 27, 22, 28, 23, 29, 24, 30, 25};
        List<MiaPregunta> retorno = new ArrayList<MiaPregunta>();
        for (int i = 0; i < preguntasBatea.length; i++) {
            for (MiaPregunta p : preguntas) {
                if (p.getNumero().intValue() == preguntasBatea[i]) {
                    retorno.add(p);
                    break;
                }
            }
        }
        return retorno;
    }

    public StreamedContent obtenerFormulario() {
        Descargable d = new Descargable(new File(constantes.getPathArchivos() + formulario.getPathFormulario()));
        return d.getStreamedContent();
    }

    public StreamedContent obtenerFormulario(MiaInspeccionAvanzada i) {
        List<Documento> docs = logicaDocumentos.obtenerDocumentosAsociados(i);
        Descargable d = new Descargable(new File(constantes.getPathArchivos() + docs.get(0).getId()));
        d.setNombre(docs.get(docs.size() - 1).getNombreOriginal());
        return d.getStreamedContent();
    }

    public boolean pasaInspeccion(MiaInspeccionAvanzada i) {
        ultimasRespuestas = logicaInspeccionAvanzada.obtenerRespuestas(i);
        for (MiaRespuesta r : ultimasRespuestas) {
            if (!r.getCumple()) return false;
        }
        return true;
    }

    public boolean pasaInspecciones() {
        for (MiaInspeccionAvanzada i : cacheInspecciones) {
            ultimasRespuestas = logicaInspeccionAvanzada.obtenerRespuestas(i);
            for (MiaRespuesta r : ultimasRespuestas) {
                if (!r.getCumple()) return false;
            }
        }
        return true;
    }

    public boolean esCausaBloqueante(MiaInspeccionAvanzada i) {
        for (MiaRespuesta r : ultimasRespuestas) {
            if (!r.getCumple() && r.getMiaPreguntasByIdPregunta().getBloqueante()) return true;
        }
        return false;
    }

    public boolean esCausaBloqueante() {
        for (MiaInspeccionAvanzada i : cacheInspecciones) {
            ultimasRespuestas = logicaInspeccionAvanzada.obtenerRespuestas(i);
            for (MiaRespuesta r : ultimasRespuestas) {
                if (!r.getCumple() && r.getMiaPreguntasByIdPregunta().getBloqueante()) return true;
            }
        }
        return false;
    }

    public boolean esCamionBatea() {
        return formularioEnPantalla == Formulario.CAMION_BATEA;
    }

    public boolean esDetencionGestion() {
        return formularioEnPantalla == Formulario.DETENCION_GESTION;
    }

    public void dummy() {
    }

    public List<Personal> getConductores() {
        return conductores;
    }

    public void setConductores(List<Personal> conductores) {
        this.conductores = conductores;
    }

    public List<Personal> getEncargados() {
        return encargados;
    }

    public void setEncargados(List<Personal> encargados) {
        this.encargados = encargados;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public List<Equipo> getTractos() {
        return tractos;
    }

    public void setTractos(List<Equipo> tractos) {
        this.tractos = tractos;
    }

    public List<MiaInspeccionAvanzada> getInspeccionesAvanzadas() {
        return inspeccionesAvanzadas;
    }

    public void setInspeccionesAvanzadas(List<MiaInspeccionAvanzada> inspeccionesAvanzadas) {
        this.inspeccionesAvanzadas = inspeccionesAvanzadas;
    }

    public MiaInspeccionAvanzada getInspeccionAvanzada() {
        return inspeccionAvanzada;
    }

    public void setInspeccionAvanzada(MiaInspeccionAvanzada inspeccionAvanzada) {
        this.inspeccionAvanzada = inspeccionAvanzada;
    }

    public List<MiaInspeccionAvanzada> getInspeccionesAvanzadasFiltradas() {
        return inspeccionesAvanzadasFiltradas;
    }

    public void setInspeccionesAvanzadasFiltradas(List<MiaInspeccionAvanzada> inspeccionesAvanzadasFiltradas) {
        this.inspeccionesAvanzadasFiltradas = inspeccionesAvanzadasFiltradas;
    }

    public List<Equipo> getBateas() {
        return bateas;
    }

    public void setBateas(List<Equipo> bateas) {
        this.bateas = bateas;
    }

    public List<Equipo> getEquipos() {
        List<Equipo> retorno = new ArrayList<Equipo>(tractos);
        retorno.addAll(bateas);
        return retorno;
    }

    @Override
    public void subir(FileUploadEvent fue) {
        Documento d = ficheroUploader.subir(fue);
        if (comprobantesInspeccion == null) comprobantesInspeccion = new HashMap<String, List<Documento>>();
        if (comprobantesInspeccion.get(sesionCliente.getUsuario().getRut() + "|inspeccion") == null) {
            List<Documento> docs = new ArrayList<Documento>();
            docs.add(d);
            comprobantesInspeccion.put(sesionCliente.getUsuario().getRut() + "|inspeccion", docs);
        } else {
            List<Documento> docs = comprobantesInspeccion.get(sesionCliente.getUsuario().getRut() + "|inspeccion");
            docs.add(d);
            comprobantesInspeccion.put(sesionCliente.getUsuario().getRut() + "|inspeccion", docs);
        }
    }

    @Override
    public void enlazar(Descargable descargable) {
        Documento d = logicaDocumentos.obtenerPorCodigo(descargable.getArchivo().getName());
        if (comprobantesInspeccion == null) comprobantesInspeccion = new HashMap<String, List<Documento>>();
        if (comprobantesInspeccion.get(sesionCliente.getUsuario().getRut() + "|inspeccion") == null) {
            List<Documento> docs = new ArrayList<Documento>();
            docs.add(d);
            comprobantesInspeccion.put(sesionCliente.getUsuario().getRut() + "|inspeccion", docs);
        } else {
            List<Documento> docs = comprobantesInspeccion.get(sesionCliente.getUsuario().getRut() + "|inspeccion");
            docs.add(d);
            comprobantesInspeccion.put(sesionCliente.getUsuario().getRut() + "|inspeccion", docs);
        }
    }
}