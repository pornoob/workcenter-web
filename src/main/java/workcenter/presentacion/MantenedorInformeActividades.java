package workcenter.presentacion;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.ActividadDiaria;
import workcenter.entidades.Documento;
import workcenter.entidades.Servicio;
import workcenter.entidades.TipoActividadDiaria;
import workcenter.negocio.LogicaDocumentos;
import workcenter.negocio.LogicaInformeActividades;
import workcenter.presentacion.includes.FicheroUploader;
import workcenter.util.WorkcenterFileListener;
import workcenter.util.components.SesionCliente;
import workcenter.util.dto.Horario;
import workcenter.util.dto.Semana;
import workcenter.util.pojo.Descargable;
import workcenter.util.pojo.FacesUtil;

/**
 *
 * @author colivares
 */
@Component
@Scope("flow")
public class MantenedorInformeActividades implements Serializable, WorkcenterFileListener {

    @Autowired
    LogicaInformeActividades logicaInformeActividades;

    @Autowired
    SesionCliente sesionCliente;

    @Autowired
    FicheroUploader ficheroUploader;

    @Autowired
    LogicaDocumentos logicaDocumentos;

    private Semana semana;
    private String linkAnterior;
    private String linkSiguiente;
    private Date fechaSeleccionada;
    private List<Servicio> servicios;
    private List<TipoActividadDiaria> tipoActividades;
    private Servicio servicioSeleccionado;
    private TipoActividadDiaria tipoActividadSeleccionada;
    private ActividadDiaria actividadDiaria;
    private Map<String, List<Documento>> documentosSubidos;
    private List<ActividadDiaria> actividades;

    public String inicio() {
        linkAnterior = "<< Anterior";
        linkSiguiente = "Siguiente >>";
        fechaSeleccionada = new Date();
        obtenerServicios();
        obtenerSemana();
        obtenerTiposActividades();
        try {
            servicioSeleccionado = servicios.get(0);
        } catch (NullPointerException e) {
            servicioSeleccionado = null;
        }
        obtenerActividades();
        return "flowMostrarCalendario";
    }

    public boolean horaMenorDiez(Integer hora) {
        if (hora == null) {
            return false;
        }
        return hora.intValue() < 10;
    }

    public List<Descargable> obtenerDescargables(ActividadDiaria ad) {
        List<Documento> docs = logicaDocumentos.obtenerDocumentosAsociados(ad);
        List<Descargable> descargables = new ArrayList<Descargable>();
        for (Documento d : docs) {
            Descargable descargable = new Descargable(new File(System.getProperty("catalina.base") + "/static/workcenter/" + d.getId()));
            descargable.setNombre(d.getNombreOriginal());
            descargables.add(descargable);
        }
        return descargables;
    }

    public boolean hayActividadDelTipo(TipoActividadDiaria ta, Date fecha, Horario horario) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        for (ActividadDiaria ad : actividades) {
            if (ta.equals(ad.getIdTipoActividad()) && sdf.format(fecha).equals(sdf.format(ad.getFecha()))
                    && Integer.valueOf(horario.getHora().split(":")[0]) == ad.getHora().intValue()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hayActividadDelTipo(Date fecha, Horario horario) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        for (ActividadDiaria ad : actividades) {
            if (sdf.format(fecha).equals(sdf.format(ad.getFecha()))
                    && Integer.valueOf(horario.getHora().split(":")[0]) == ad.getHora().intValue()) {
                return true;
            }
        }
        return false;
    }

    public void obtenerActividades() {
        actividades = logicaInformeActividades.obtenerActividades(servicioSeleccionado, sesionCliente.getUsuario().getRut(), semana);
    }

    public String volver() {
        return "flowMostrarCalendario";
    }

    public void guardar() {
        HttpServletRequest request = FacesUtil.obtenerHttpServletRequest();
        String screenFlow = request.getParameter("javax.faces.ViewState");
        logicaInformeActividades.guardar(actividadDiaria);
        if (documentosSubidos == null) {
            documentosSubidos = new HashMap<String, List<Documento>>();
        }
        List<Documento> docs = documentosSubidos.get(sesionCliente.getUsuario().getRut() + "|actividad_diaria" + screenFlow);
        if (docs != null && !docs.isEmpty()) {
            logicaDocumentos.asociarDocumentos(docs, actividadDiaria);
        }
        FacesUtil.mostrarMensajeInformativo("Actividad guardada exitosamente", "ID de actividad " + actividadDiaria.getId());
        actividadDiaria = new ActividadDiaria();
    }

    private void obtenerTiposActividades() {
        tipoActividades = logicaInformeActividades.obtenerTiposActividades();
    }

    public String irAgregarActividad() {
        actividadDiaria = new ActividadDiaria();
        actividadDiaria.setIdUsuario(sesionCliente.getUsuario().getRut());
        actividadDiaria.setIdServicio(servicioSeleccionado);
        return "flowAgregarActividad";
    }

    public String irDetalleActividades() {
        return "flowDetalleActividades";
    }

    private void obtenerServicios() {
        servicios = logicaInformeActividades.obtenerServicios(sesionCliente.getUsuario().getRut());
    }

    public void obtenerSemanaAnterior() {
        List<Date> fechasSemana = new ArrayList<Date>();
        Calendar c = Calendar.getInstance();
        c.setTime(fechaSeleccionada);
        c.add(Calendar.MINUTE, 60 * 24 * -8);

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        fechasSemana.add(c.getTime());

        semana = new Semana();
        for (int i = 0; i < semana.getDias().length; i++) {
            semana.getDias()[i].setFecha(fechasSemana.get(i));
        }
        fechaSeleccionada = c.getTime();
    }

    public void obtenerSemanaSiguiente() {
        List<Date> fechasSemana = new ArrayList<Date>();
        Calendar c = Calendar.getInstance();
        c.setTime(fechaSeleccionada);
        c.add(Calendar.MINUTE, 60 * 24);

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        fechasSemana.add(c.getTime());

        semana = new Semana();
        for (int i = 0; i < semana.getDias().length; i++) {
            semana.getDias()[i].setFecha(fechasSemana.get(i));
        }
        fechaSeleccionada = c.getTime();
    }

    public void obtenerSemana() {
        List<Date> fechasSemana = new ArrayList<Date>();
        Calendar c = Calendar.getInstance();
        c.setTime(fechaSeleccionada);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        fechasSemana.add(c.getTime());

        semana = new Semana();
        for (int i = 0; i < semana.getDias().length; i++) {
            semana.getDias()[i].setFecha(fechasSemana.get(i));
        }
        fechaSeleccionada = c.getTime();
    }

    public Semana getSemana() {
        return semana;
    }

    public void setSemana(Semana semana) {
        this.semana = semana;
    }

    public String getLinkAnterior() {
        return linkAnterior;
    }

    public void setLinkAnterior(String linkAnterior) {
        this.linkAnterior = linkAnterior;
    }

    public String getLinkSiguiente() {
        return linkSiguiente;
    }

    public void setLinkSiguiente(String linkSiguiente) {
        this.linkSiguiente = linkSiguiente;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public Servicio getServicioSeleccionado() {
        return servicioSeleccionado;
    }

    public void setServicioSeleccionado(Servicio servicioSeleccionado) {
        this.servicioSeleccionado = servicioSeleccionado;
    }

    public List<TipoActividadDiaria> getTipoActividades() {
        return tipoActividades;
    }

    public void setTipoActividades(List<TipoActividadDiaria> tipoActividades) {
        this.tipoActividades = tipoActividades;
    }

    public TipoActividadDiaria getTipoActividadSeleccionada() {
        return tipoActividadSeleccionada;
    }

    public void setTipoActividadSeleccionada(TipoActividadDiaria tipoActividadSeleccionada) {
        this.tipoActividadSeleccionada = tipoActividadSeleccionada;
    }

    public ActividadDiaria getActividadDiaria() {
        return actividadDiaria;
    }

    public void setActividadDiaria(ActividadDiaria actividadDiaria) {
        this.actividadDiaria = actividadDiaria;
    }

    public List<ActividadDiaria> getActividades() {
        return actividades;
    }

    public void setActividades(List<ActividadDiaria> actividades) {
        this.actividades = actividades;
    }

    public void subir(FileUploadEvent fue) {
        Documento d = ficheroUploader.subir(fue);

        if (d == null) {
            return;
        }
        HttpServletRequest request = FacesUtil.obtenerHttpServletRequest();
        String screenFlow = request.getParameter("javax.faces.ViewState");
        if (documentosSubidos == null) {
            documentosSubidos = new HashMap<String, List<Documento>>();
        }
        if (documentosSubidos.get(sesionCliente.getUsuario().getRut() + "|actividad_diaria" + screenFlow) == null) {
            List<Documento> docs = new ArrayList<Documento>();
            docs.add(d);
            documentosSubidos.put(sesionCliente.getUsuario().getRut() + "|actividad_diaria" + screenFlow, docs);
        } else {
            List<Documento> docs = documentosSubidos.get(sesionCliente.getUsuario().getRut() + "|actividad_diaria" + screenFlow);
            docs.add(d);
            documentosSubidos.put(sesionCliente.getUsuario().getRut() + "|actividad_diaria" + screenFlow, docs);
        }

    }

    public void enlazar(Descargable descargable) {
        Documento d = logicaDocumentos.obtenerPorCodigo(descargable.getArchivo().getName());
        HttpServletRequest request = FacesUtil.obtenerHttpServletRequest();
        String screenFlow = request.getParameter("javax.faces.ViewState");
        if (documentosSubidos == null) {
            documentosSubidos = new HashMap<String, List<Documento>>();
        }
        if (documentosSubidos.get(sesionCliente.getUsuario().getRut() + "|actividad_diaria" + screenFlow) == null) {
            List<Documento> docs = new ArrayList<Documento>();
            docs.add(d);
            documentosSubidos.put(sesionCliente.getUsuario().getRut() + "|actividad_diaria" + screenFlow, docs);
        } else {
            List<Documento> docs = documentosSubidos.get(sesionCliente.getUsuario().getRut() + "|actividad_diaria" + screenFlow);
            docs.add(d);
            documentosSubidos.put(sesionCliente.getUsuario().getRut() + "|actividad_diaria" + screenFlow, docs);
        }
        FacesUtil.mostrarMensajeInformativo("Archivo enlazado", "Se ha enlazado el documento " + d.getId() + " a la gestión");
    }
}
