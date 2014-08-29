package workcenter.presentacion.registros.r112;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.LogicaDocumentos;
import workcenter.negocio.LogicaPersonal;
import workcenter.negocio.LogicaRegistroActividades;
import workcenter.presentacion.includes.FicheroUploader;
import workcenter.util.WorkcenterFileListener;
import workcenter.util.components.Constantes;
import workcenter.util.components.SesionCliente;
import workcenter.util.pojo.Descargable;
import workcenter.util.pojo.FacesUtil;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * @author colivares
 */
@Component
@Scope("flow")
public class RegistroR112 implements Serializable, WorkcenterFileListener {
    private List<MarActividad> actividades;
    private List<MarActividad> actividadesFiltradas;
    private MarActividad actividad;
    private List<Personal> personal;
    private MarParticipantesAct participante;
    private List<MarParticipantesAct> participantes;
    private List<MarTipoActividad> tipoActividades;
    private List<MarTipoActividad> subTipoActividades;
    private MarTipoActividad tipoActividad;
    private MarTipoActividad subTipoActividad;
    private MarRegistro formulario;
    private Integer hrInicio;
    private Integer minInicio;
    private Integer hrFin;
    private Integer minFin;
    private Integer hrDuracion;
    private Integer minDuracion;
    private String nota;

    private int logicaUpload;
    private Map<String, List<Documento>> comprobantesActividad;
    private Map<String, List<Documento>> materialesApoyo;
    private List<Descargable> materiales;

    @Autowired
    LogicaRegistroActividades logicaRegistroActividades;

    @Autowired
    Constantes constantes;

    @Autowired
    LogicaPersonal logicaPersonal;

    @Autowired
    FicheroUploader ficheroUploader;

    @Autowired
    LogicaDocumentos logicaDocumentos;

    @Autowired
    SesionCliente sesionCliente;

    public String inicio() {
        actividades = logicaRegistroActividades.obtenerActividadesSegunRegistro(constantes.getRegistroR112());
        formulario = logicaRegistroActividades.obtenerRegistro(constantes.getRegistroR112());
        System.err.println("APP PATH "+this.getClass().getResource(""));
        return irListarR112();
    }

    public StreamedContent obtenerFormulario() {
        Descargable d = new Descargable(new File(constantes.getPathArchivos()+formulario.getPathFormulario()));
        return d.getStreamedContent();
    }

    public StreamedContent obtenerFormulario(MarActividad a) {
        List<Documento> docs = logicaDocumentos.obtenerDocumentosAsociados(a);
        Descargable d = new Descargable(new File(constantes.getPathArchivos()+docs.get(0).getId()));
        d.setNombre(docs.get(0).getNombreOriginal());
        return d.getStreamedContent();
    }

    public void cargaMaterialesApoyo() {
        List<Documento> docs = logicaDocumentos.obtenerDocumentosAsociados(subTipoActividad);
        materiales = new ArrayList<Descargable>();
        for (Documento d : docs) {
            Descargable descargable = new Descargable(new File(constantes.getPathArchivos() + d.getId()));
            descargable.setNombre(d.getNombreOriginal());
            materiales.add(descargable);
        }
    }

    public void guardarActividad() {
//        boolean error = false;
//
//        if (hrInicio.intValue() > hrFin.intValue()) error = true;
//        else if (hrInicio.intValue() == hrFin.intValue() && minInicio > minFin) error = true;
//
//        if (error) {
//            FacesUtil.mostrarMensajeError("Operación Fallida", "La hora de inicio debe ser menor que la hora de termino");
//            return;
//        }
        boolean esEdicion = actividad.getId() != null;
        actividad.setParticipantes(participantes);
        actividad.setHoraInicio((hrInicio < 10 ? "0" + hrInicio : hrInicio) + ":" + (minInicio < 10 ? "0" + minInicio : minInicio));
        actividad.setHoraFin((hrFin < 10 ? "0" + hrFin : hrFin) + ":" + (minFin < 10 ? "0" + minFin : minFin));
        actividad.setTipoActividad(subTipoActividad);
        logicaRegistroActividades.guardarActividad(actividad);

        if (comprobantesActividad != null) {
            List<Documento> docs = comprobantesActividad.get(sesionCliente.getUsuario().getRut() + "|actividad");
            if (docs != null && !docs.isEmpty()) {
                logicaDocumentos.asociarDocumentos(docs, actividad);
            }
        }
        if (!esEdicion) {
            actividades.add(actividad);
            FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Se ha creado la actividad [ID: " + actividad.getId() + "]");
        } else {
            FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Se ha editado la actividad [ID: " + actividad.getId() + "]");
        }
    }

    public String verParticipantes(MarActividad a) {
        a.setParticipantes(logicaRegistroActividades.obtenerParticipantes(a));
        actividad = a;
        return "flowVerParticipantes";
    }

    public String preparaEdicionR112(MarActividad a) {
        actividad = a;
        tipoActividad = a.getTipoActividad().getMarTipoActividadByIdTipoPadre();
        subTipoActividad = a.getTipoActividad();
        participantes = a.getParticipantes();
        hrInicio = a.getHoraInicio() != null ? Integer.valueOf(a.getHoraInicio().split(":")[0]) : 0;
        hrFin = a.getHoraFin() != null ? Integer.valueOf(a.getHoraFin().split(":")[0]) : 0;
        minInicio = a.getHoraInicio() != null ? Integer.valueOf(a.getHoraInicio().split(":")[1]) : 0;
        minFin = a.getHoraFin() != null ? Integer.valueOf(a.getHoraFin().split(":")[1]) : 0;

        tipoActividades = logicaRegistroActividades.obtenerTiposActividades(constantes.getRegistroR112());
        subTipoActividades = logicaRegistroActividades.obtenerSubTiposActividades(tipoActividad);
        personal = logicaPersonal.obtenerTodos();
        cargaMaterialesApoyo();
        return irNuevoR112();
    }

    public String preparaCreacionR112() {
        actividad = new MarActividad();
        Calendar fechaActual = Calendar.getInstance();
        actividad.setFecha(fechaActual.getTime());

        hrInicio = fechaActual.get(Calendar.HOUR_OF_DAY);
        hrFin = hrInicio.intValue();
        minInicio = fechaActual.get(Calendar.MINUTE);
        minFin = minInicio.intValue();

        tipoActividades = logicaRegistroActividades.obtenerTiposActividades(constantes.getRegistroR112());
        tipoActividad = null;
        subTipoActividades = null;
        subTipoActividad = null;
        personal = logicaPersonal.obtenerTodos();
        participantes = new ArrayList<MarParticipantesAct>();
        participante = null;
        return irNuevoR112();
    }

    public void cargaSubTipoActividades() {
        subTipoActividades = logicaRegistroActividades.obtenerSubTiposActividades(tipoActividad);
        subTipoActividad = null;
    }

    public String preparaCreacionTipoActividad() {
        subTipoActividad = new MarTipoActividad();
        subTipoActividad.setMarRegistrosByIdRegistro(formulario);
        hrDuracion = null;
        minDuracion = null;
        materialesApoyo = new HashMap<String, List<Documento>>();
        return irNuevoTipoActividad();
    }

    public void guardarTipoActividad() {
        hrDuracion = hrDuracion == null ? 0 : hrDuracion;
        minDuracion = minDuracion == null ? 0 : minDuracion;
        subTipoActividad.setMarTipoActividadByIdTipoPadre(tipoActividad);
        subTipoActividad.setDuracion((hrDuracion < 10 ? "0" + hrDuracion : hrDuracion) + ":" + (minDuracion < 10 ? "0" + minDuracion : minDuracion));
        logicaRegistroActividades.guardarTipoActividad(subTipoActividad);

        List<Documento> docs = materialesApoyo.get(sesionCliente.getUsuario().getRut() + "|tipo_actividad");
        if (docs != null && !docs.isEmpty()) {
            logicaDocumentos.asociarDocumentos(docs, subTipoActividad);
        }

        if (subTipoActividades == null) {
            subTipoActividades = new ArrayList<MarTipoActividad>();
        }
        subTipoActividades.add(subTipoActividad);
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Se ha creado el tipo de actividad \"" + subTipoActividad.getNombre() + "\"");
    }

    public void agregarParticipante() {
//        System.out.println("Rut: " + FacesUtil.obtenerHttpServletRequest().getParameter("formR112:sNuevoParticipante_input"));
        Integer rut = Integer.valueOf(FacesUtil.obtenerHttpServletRequest().getParameter("formR112:sNuevoParticipante_input"));
        Personal seleccionado = null;
        for (Personal p : personal) {
            if (rut != null && p.getRut().intValue() == rut.intValue()) {
                seleccionado = p;
                break;
            }
        }
        if (seleccionado == null) return;
        for (MarParticipantesAct p : participantes) {
            if (p.getParticipante().equals(seleccionado)) {
                FacesUtil.mostrarMensajeError("Operación Fallida", "Esta persona ya fue agregada como participante");
                return;
            }

        }

        if (subTipoActividad.getRequiereNota() && (nota == null || nota.trim().equals(""))) {
            FacesUtil.mostrarMensajeError("Operación Fallida", "Debes especificar la nota del participante");
            return;
        } else {
            nota = null;
        }

        MarParticipantesAct p = new MarParticipantesAct();
        p.setParticipante(seleccionado);
        p.setMarActividadByIdActividad(actividad);
        if (nota != null) p.setNota(Float.valueOf(nota));
        participantes.add(p);
        nota = null;
    }

    public void quitarParticipante() {
        participantes.remove(participante);
    }

    public Integer obtenerCantAsistentes(MarActividad a) {
        return logicaRegistroActividades.obtenerAsistentesSegunActividad(a);
    }

    public String irListarR112() {
        return "flowListarR112";
    }

    public String irListarRegistros() {
        return "flowListarRegistros";
    }

    public String irNuevoTipoActividad() {
        logicaUpload = 2;
        return "flowNuevoTipoActividad";
    }

    public String irNuevoR112() {
        logicaUpload = 1;
        return "flowNuevoR112";
    }

    @Override
    public void subir(FileUploadEvent fue) {
        Documento d = ficheroUploader.subir(fue);
        if (d == null) {
            return;
        }
        switch (logicaUpload) {
            case 1:
                subirComprobanteActividad(d);
                break;
            case 2:
                subirMaterialApoyoTipoActividad(d);
                break;
        }
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Se ha subido el fichero");
    }

    @Override
    public void enlazar(Descargable descargable) {
        Documento d = logicaDocumentos.obtenerPorCodigo(descargable.getArchivo().getName());
        switch (logicaUpload) {
            case 1:
                subirComprobanteActividad(d);
                break;
            case 2:
                subirMaterialApoyoTipoActividad(d);
                break;
        }
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Se ha enlazado el fichero");
    }

    public void subirComprobanteActividad(Documento d) {
        if (comprobantesActividad == null) comprobantesActividad = new HashMap<String, List<Documento>>();
        if (comprobantesActividad.get(sesionCliente.getUsuario().getRut() + "|actividad") == null) {
            List<Documento> docs = new ArrayList<Documento>();
            docs.add(d);
            comprobantesActividad.put(sesionCliente.getUsuario().getRut() + "|actividad", docs);
        } else {
            List<Documento> docs = comprobantesActividad.get(sesionCliente.getUsuario().getRut() + "|actividad");
            docs.add(d);
            comprobantesActividad.put(sesionCliente.getUsuario().getRut() + "|actividad", docs);
        }
    }

    public void subirMaterialApoyoTipoActividad(Documento d) {
        if (materialesApoyo == null) materialesApoyo = new HashMap<String, List<Documento>>();
        if (materialesApoyo.get(sesionCliente.getUsuario().getRut() + "|tipo_actividad") == null) {
            List<Documento> docs = new ArrayList<Documento>();
            docs.add(d);
            materialesApoyo.put(sesionCliente.getUsuario().getRut() + "|tipo_actividad", docs);
        } else {
            List<Documento> docs = materialesApoyo.get(sesionCliente.getUsuario().getRut() + "|tipo_actividad");
            docs.add(d);
            materialesApoyo.put(sesionCliente.getUsuario().getRut() + "|tipo_actividad", docs);
        }
    }

    public List<MarActividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<MarActividad> actividades) {
        this.actividades = actividades;
    }

    public MarActividad getActividad() {
        return actividad;
    }

    public void setActividad(MarActividad actividad) {
        this.actividad = actividad;
    }

    public List<Personal> getPersonal() {
        return personal;
    }

    public void setPersonal(List<Personal> personal) {
        this.personal = personal;
    }

    public MarParticipantesAct getParticipante() {
        return participante;
    }

    public void setParticipante(MarParticipantesAct participante) {
        this.participante = participante;
    }

    public List<MarTipoActividad> getTipoActividades() {
        return tipoActividades;
    }

    public void setTipoActividades(List<MarTipoActividad> tipoActividades) {
        this.tipoActividades = tipoActividades;
    }

    public MarTipoActividad getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(MarTipoActividad tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public List<MarParticipantesAct> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<MarParticipantesAct> participantes) {
        this.participantes = participantes;
    }

    public Integer getHrInicio() {
        return hrInicio;
    }

    public void setHrInicio(Integer hrInicio) {
        this.hrInicio = hrInicio;
    }

    public Integer getMinInicio() {
        return minInicio;
    }

    public void setMinInicio(Integer minInicio) {
        this.minInicio = minInicio;
    }

    public Integer getHrFin() {
        return hrFin;
    }

    public void setHrFin(Integer hrFin) {
        this.hrFin = hrFin;
    }

    public Integer getMinFin() {
        return minFin;
    }

    public void setMinFin(Integer minFin) {
        this.minFin = minFin;
    }

    public Integer getHrDuracion() {
        return hrDuracion;
    }

    public void setHrDuracion(Integer hrDuracion) {
        this.hrDuracion = hrDuracion;
    }

    public Integer getMinDuracion() {
        return minDuracion;
    }

    public void setMinDuracion(Integer minDuracion) {
        this.minDuracion = minDuracion;
    }

    public List<MarTipoActividad> getSubTipoActividades() {
        return subTipoActividades;
    }

    public void setSubTipoActividades(List<MarTipoActividad> subTipoActividades) {
        this.subTipoActividades = subTipoActividades;
    }

    public MarTipoActividad getSubTipoActividad() {
        return subTipoActividad;
    }

    public void setSubTipoActividad(MarTipoActividad subTipoActividad) {
        this.subTipoActividad = subTipoActividad;
    }

    public List<Descargable> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<Descargable> materiales) {
        this.materiales = materiales;
    }

    public List<MarActividad> getActividadesFiltradas() {
        return actividadesFiltradas;
    }

    public void setActividadesFiltradas(List<MarActividad> actividadesFiltradas) {
        this.actividadesFiltradas = actividadesFiltradas;
    }

    public MarRegistro getFormulario() {
        return formulario;
    }

    public void setFormulario(MarRegistro formulario) {
        this.formulario = formulario;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
