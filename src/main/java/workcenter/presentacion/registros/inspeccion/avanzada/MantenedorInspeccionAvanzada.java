package workcenter.presentacion.registros.inspeccion.avanzada;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.*;
import workcenter.presentacion.includes.FicheroUploader;
import workcenter.util.WorkcenterFileListener;
import workcenter.util.components.Constantes;
import workcenter.util.components.SesionCliente;
import workcenter.util.pojo.Descargable;
import workcenter.util.pojo.DynamicColumnDataTable;
import workcenter.util.pojo.FacesUtil;

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
    private MiaInspeccionAvanzada inspeccionAvanzada;
    private List<MiaPregunta> preguntas;
    private Map<String, List<Documento>> comprobantesInspeccion;
    private MarRegistro formulario;

    // Zona de "caché"
    private List<MiaRespuesta> ultimasRespuestas;

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
        return "flowAgregar";
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
        List<Documento> docs = comprobantesInspeccion.get(sesionCliente.getUsuario().getRut() + "|inspeccion");
        if (docs == null || docs.isEmpty()) {
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
        logicaDocumentos.asociarDocumentos(docs, inspeccionAvanzada);
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
        retorno.add(new DynamicColumnDataTable("-1", "Total", "100"));
        return retorno;
    }

    public String obtenerValorCalendario(String idColumna, Equipo e) {
        int id = Integer.parseInt(idColumna);
        switch (id) {
            case -1:
                return "0";
            default:
                return "0";
        }
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
        int[] preguntasBatea = {20, 27, 21, 28, 22, 29, 23, 30, 24, 31, 25};
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
        Descargable d = new Descargable(new File(constantes.getPathArchivos()+formulario.getPathFormulario()));
        d.setNombre("InspeccionAvanzada.pdf");
        return d.getStreamedContent();
    }

    public boolean pasaInspeccion(MiaInspeccionAvanzada i) {
        ultimasRespuestas = logicaInspeccionAvanzada.obtenerRespuestas(i);
        for (MiaRespuesta r : ultimasRespuestas) {
            if (!r.getCumple()) return false;
        }
        return true;
    }

    public boolean esCausaBloqueante(MiaInspeccionAvanzada i) {
        for (MiaRespuesta r : ultimasRespuestas) {
            if (!r.getCumple() && r.getMiaPreguntasByIdPregunta().getBloqueante()) return true;
        }
        return false;
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

    public List<MiaPregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<MiaPregunta> preguntas) {
        this.preguntas = preguntas;
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