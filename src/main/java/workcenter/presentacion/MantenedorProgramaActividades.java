package workcenter.presentacion;

import java.io.File;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Documento;
import workcenter.entidades.MpaActividad;
import workcenter.entidades.MpaEjecucionPlan;
import workcenter.entidades.MpaPlanPrograma;
import workcenter.entidades.MpaPrograma;
import workcenter.entidades.MpaValorPlanPrograma;
import workcenter.entidades.Personal;
import workcenter.negocio.LogicaDocumentos;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.negocio.LogicaProgramaActividades;
import workcenter.presentacion.includes.FicheroUploader;
import workcenter.util.WorkcenterFileListener;
import workcenter.util.components.Constantes;
import workcenter.util.components.SesionCliente;
import workcenter.util.dto.Mes;
import workcenter.util.pojo.Descargable;
import workcenter.util.components.FacesUtil;

/**
 *
 * @author colivares
 */
@Component
@Scope("flow")
public class MantenedorProgramaActividades implements Serializable, WorkcenterFileListener {

    private Map<Mes, Integer> cantActividades;
    private List<String> filasPlan;
    private List<Personal> responsables;
    private MpaPrograma programa;
    private MpaActividad actividad;
    private MpaPlanPrograma planSeleccionado;
    private Mes mesSeleccionado;
    private Personal responsable;
    private List<MpaPrograma> programas;
    private List<MpaActividad> actividades;
    private List<MpaPlanPrograma> planes;
    private boolean ordenarPorPrograma;
    private boolean haConsultado;
    private Integer anioSeleccionado;

    @Autowired
    LogicaPersonal logicaPersonal;

    @Autowired
    SesionCliente sesionCliente;

    @Autowired
    Constantes constantes;

    @Autowired
    LogicaProgramaActividades logicaProgramaActividades;

    @Autowired
    FicheroUploader ficheroUploader;

    @Autowired
    LogicaDocumentos logicaDocumentos;

    public String inicio() {
        filasPlan = new ArrayList<String>();
        filasPlan.add("");
        obtenerResponsables();
        obtenerProgramas();
        obtenerActividades();
        anioSeleccionado = Calendar.getInstance().get(Calendar.YEAR);
        return irMostrarPlan();
    }

    public String irGenerarPlan() {
        if (!programas.isEmpty()) {
            if (programa == null || programa.getId() == null) {
                programa = programas.get(0);
            }
        }
        cantActividades = new HashMap<Mes, Integer>();
        for (Mes m : constantes.getMeses()) {
            cantActividades.put(m, 0);
        }
        obtenerActividades();
        haConsultado = false;
        planSeleccionado = new MpaPlanPrograma();
        planSeleccionado.setAnioVigencia(Calendar.getInstance().get(Calendar.YEAR));
        return "flowCrearPlan";
    }

    public String irMostrarPlan() {
        haConsultado = false;
        return "flowMostrarPlan";
    }

    public String irAgregarPrograma() {
        programa = new MpaPrograma();
        haConsultado = false;

        return "flowAgregarPrograma";
    }

    public String irAgregarActividad() {
        actividad = new MpaActividad();
        if (programas.isEmpty()) {
            FacesUtil.mostrarMensajeInformativo("La actividad debe estar asociada a un programa", "No se han creado programas");
            return null;
        } else {
            if (programa == null || programa.getId() == null) {
                programa = programas.get(0);
            }
        }
        actividad.setIdPrograma(programa);
        haConsultado = false;

        return "flowAgregarActividad";
    }

    public String ingresarEjecuciones(MpaPlanPrograma plan, Mes mes) {
        planSeleccionado = plan;
        programa = plan.getIdPrograma();
        actividad = plan.getIdActividad();
        mesSeleccionado = mes;
        return "flowRealizarActividad";
    }

    public List<Descargable> obtenerDescargables() {
        List<Descargable> descargables = new ArrayList<Descargable>();
        List<MpaEjecucionPlan> ejecuciones = logicaProgramaActividades.obtenerEjecuciones(planSeleccionado, mesSeleccionado);
        for (MpaEjecucionPlan e : ejecuciones) {
            List<Documento> docs = logicaDocumentos.obtenerDocumentosAsociados(e);
            for (Documento doc : docs) {
                Descargable d = new Descargable(new File(constantes.getPathArchivos() + doc.getId()));
                d.setNombre(doc.getNombreOriginal());
                descargables.add(d);
            }
        }
        return descargables;
    }

    public Integer obtenerCantEjecuciones(MpaPlanPrograma plan, Mes mes) {
        return logicaProgramaActividades.obtenerCantEjecuciones(plan, mes);
    }

    public String obtenerCumplimientoMesResponsable(MpaPlanPrograma plan, Mes mes) {
        float cumplimiento = logicaProgramaActividades.obtenerCumplimientoResponsable(plan, mes);
        NumberFormat nf = NumberFormat.getPercentInstance();
        return nf.format(cumplimiento);
    }

    public String obtenerCumplimientoResponsable(MpaPlanPrograma plan) {
        float cumplimiento = 0;
        int contador = 0;
        int planeado = 0;
        Calendar c = Calendar.getInstance();

        for (Mes mes : constantes.getMeses()) {
            if (Integer.parseInt(mes.getId()) >= c.get(Calendar.MONTH)) {
                break;
            }
            planeado = plan.obtenerPorMes(mes);
            if (planeado > 0) {
                cumplimiento += logicaProgramaActividades.obtenerCumplimientoResponsable(plan, mes);
                contador++;
            }
        }
        NumberFormat nf = NumberFormat.getPercentInstance();
        if (contador > 0)
            return nf.format(cumplimiento / contador);
        return "0";
    }

    public String obtenerCumplimientoGlobal(MpaPrograma programa) {
        float cumplimientoPersonal;
        int contadorPersonal;

        float cumplimientoGlobal = 0;
        int contadorGlobal = 0;

        Calendar c = Calendar.getInstance();
        for (MpaPlanPrograma p : planes) {
            if (!p.getIdPrograma().equals(programa)) {
                continue;
            }
            contadorPersonal = 0;
            cumplimientoPersonal = 0;
            for (Mes mes : constantes.getMeses()) {
                if (Integer.parseInt(mes.getId()) >= c.get(Calendar.MONTH)) {
                    break;
                }
                cumplimientoPersonal += logicaProgramaActividades.obtenerCumplimientoResponsable(p, mes);
                contadorPersonal++;
            }
            if (contadorPersonal > 0) {
                cumplimientoGlobal += (cumplimientoPersonal / contadorPersonal);
                contadorGlobal++;
            }
        }
        NumberFormat nf = NumberFormat.getPercentInstance();
        if (contadorGlobal > 0)
            return nf.format(cumplimientoGlobal / contadorGlobal);
        return "0";
    }

    public boolean correspondeDibujar(MpaPrograma p) {
        if (programa != null && !programa.equals(p)) {
            return false;
        }
        if (obtenerPlanes(p).isEmpty()) {
            return false;
        }
        return true;
    }

    public List<MpaPlanPrograma> obtenerPlanes(MpaPrograma p) {
        List<MpaPlanPrograma> planesFiltrados = new ArrayList<MpaPlanPrograma>();
        if (planes != null) {
            for (MpaPlanPrograma plan : planes) {
                if (plan.getIdPrograma().equals(p)) {
                    planesFiltrados.add(plan);
                }
            }
        }
        return planesFiltrados;
    }

    public void consultar() {
        if (!sesionCliente.esEditor(constantes.getModuloProgramaActividades())) {
            responsable = logicaPersonal.obtener(sesionCliente.getUsuario().getRut());
        }
        
        if (programa != null && responsable != null) {
            ordenarPorPrograma = false;
            planes = logicaProgramaActividades.obtenerPlanes(programa, responsable, anioSeleccionado);
        } else if (programa != null) {
            ordenarPorPrograma = true;
            planes = logicaProgramaActividades.obtenerPlanes(programa, anioSeleccionado);
        } else if (responsable != null) {
            ordenarPorPrograma = false;
            planes = logicaProgramaActividades.obtenerPlanes(responsable, anioSeleccionado);
        } else {
            ordenarPorPrograma = true;
            planes = logicaProgramaActividades.obtenerPlanes(anioSeleccionado);
        }
        haConsultado = true;
    }

    public String obtenerColor(String s) {
        NumberFormat nf = NumberFormat.getPercentInstance();
        float porcentaje;
        try {
            porcentaje = ((Number) nf.parse(s)).floatValue();
        } catch (Exception e) {
//            e.printStackTrace();
            porcentaje = 0;
        }

        if (porcentaje >= 1) {
            return "green";
        } else {
            return "red";
        }
    }

    public void guardarPlan() {
        MpaPlanPrograma plan = planSeleccionado;
        plan.setIdPrograma(programa);
        plan.setIdActividad(actividad);
        plan.setRutCreador(logicaPersonal.obtener(sesionCliente.getUsuario().getRut()));
        plan.setRutResponsable(responsable);
        plan.setMpaValorPlanProgramaCollection(new ArrayList<MpaValorPlanPrograma>());

        MpaValorPlanPrograma valoresPlan = new MpaValorPlanPrograma();
        valoresPlan.setear(cantActividades);
        valoresPlan.setIdPlan(plan);

        plan.getMpaValorPlanProgramaCollection().add(valoresPlan);
        plan.setFecha(new Date());
        logicaProgramaActividades.guardarPlan(plan);
        FacesUtil.mostrarMensajeInformativo("Se ha guardado correctamente el plan", "Plan guardado con ID: " + plan.getId());
        irGenerarPlan();
    }

    private void obtenerProgramas() {
        programas = logicaProgramaActividades.obtenerProgramas();
    }

    public void obtenerActividades() {
        if (programa == null || programa.getId() == null) {
            actividades = logicaProgramaActividades.obtenerActividades();
        } else {
            actividades = logicaProgramaActividades.obtenerActividades(programa);
        }
    }

    public void guardarPrograma() {
        logicaProgramaActividades.guardarPrograma(programa);
        obtenerProgramas();
        FacesUtil.mostrarMensajeInformativo("Se ha creado el programa " + programa.getNombre(), "Programa creado con ID: " + programa.getId());
    }

    public void guardarActividad() {
        logicaProgramaActividades.guardarActividad(actividad);
        obtenerActividades();
        FacesUtil.mostrarMensajeInformativo("Se ha creado la actividad " + actividad.getNombre(), "Actividad creada con ID: " + actividad.getId());
    }

    private void obtenerResponsables() {
        responsables = logicaPersonal.obtenerPersonalPorPermiso(constantes.getModuloProgramaActividades());
    }

    public List<String> getFilasPlan() {
        return filasPlan;
    }

    public void setFilasPlan(List<String> filasPlan) {
        this.filasPlan = filasPlan;
    }

    public List<Personal> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<Personal> reponsables) {
        this.responsables = reponsables;
    }

    public MpaPrograma getPrograma() {
        return programa;
    }

    public void setPrograma(MpaPrograma programa) {
        this.programa = programa;
    }

    public MpaActividad getActividad() {
        return actividad;
    }

    public void setActividad(MpaActividad actividad) {
        this.actividad = actividad;
    }

    public List<MpaPrograma> getProgramas() {
        return programas;
    }

    public void setProgramas(List<MpaPrograma> programas) {
        this.programas = programas;
    }

    public List<MpaActividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<MpaActividad> actividades) {
        this.actividades = actividades;
    }

    public Personal getResponsable() {
        return responsable;
    }

    public void setResponsable(Personal responsable) {
        this.responsable = responsable;
    }

    public boolean isHaConsultado() {
        return haConsultado;
    }

    public void setHaConsultado(boolean haConsultado) {
        this.haConsultado = haConsultado;
    }

    public Integer getEnero() {
        return cantActividades.get(constantes.getMeses().get(0));
    }

    public void setEnero(Integer valor) {
        cantActividades.put(constantes.getMeses().get(0), valor);
    }

    public Integer getFebrero() {
        return cantActividades.get(constantes.getMeses().get(1));
    }

    public void setFebrero(Integer valor) {
        cantActividades.put(constantes.getMeses().get(1), valor);
    }

    public Integer getMarzo() {
        return cantActividades.get(constantes.getMeses().get(2));
    }

    public void setMarzo(Integer valor) {
        cantActividades.put(constantes.getMeses().get(2), valor);
    }

    public Integer getAbril() {
        return cantActividades.get(constantes.getMeses().get(3));
    }

    public void setAbril(Integer valor) {
        cantActividades.put(constantes.getMeses().get(3), valor);
    }

    public Integer getMayo() {
        return cantActividades.get(constantes.getMeses().get(4));
    }

    public void setMayo(Integer valor) {
        cantActividades.put(constantes.getMeses().get(4), valor);
    }

    public Integer getJunio() {
        return cantActividades.get(constantes.getMeses().get(5));
    }

    public void setJunio(Integer valor) {
        cantActividades.put(constantes.getMeses().get(5), valor);
    }

    public Integer getJulio() {
        return cantActividades.get(constantes.getMeses().get(6));
    }

    public void setJulio(Integer valor) {
        cantActividades.put(constantes.getMeses().get(6), valor);
    }

    public Integer getAgosto() {
        return cantActividades.get(constantes.getMeses().get(7));
    }

    public void setAgosto(Integer valor) {
        cantActividades.put(constantes.getMeses().get(7), valor);
    }

    public Integer getSeptiembre() {
        return cantActividades.get(constantes.getMeses().get(8));
    }

    public void setSeptiembre(Integer valor) {
        cantActividades.put(constantes.getMeses().get(8), valor);
    }

    public Integer getOctubre() {
        return cantActividades.get(constantes.getMeses().get(9));
    }

    public void setOctubre(Integer valor) {
        cantActividades.put(constantes.getMeses().get(9), valor);
    }

    public Integer getNoviembre() {
        return cantActividades.get(constantes.getMeses().get(10));
    }

    public void setNoviembre(Integer valor) {
        cantActividades.put(constantes.getMeses().get(10), valor);
    }

    public Integer getDiciembre() {
        return cantActividades.get(constantes.getMeses().get(11));
    }

    public void setDiciembre(Integer valor) {
        cantActividades.put(constantes.getMeses().get(11), valor);
    }

    public boolean isOrdenarPorPrograma() {
        return ordenarPorPrograma;
    }

    public void setOrdenarPorPrograma(boolean ordenarPorPrograma) {
        this.ordenarPorPrograma = ordenarPorPrograma;
    }

    public MpaPlanPrograma getPlanSeleccionado() {
        return planSeleccionado;
    }

    public void setPlanSeleccionado(MpaPlanPrograma planSeleccionado) {
        this.planSeleccionado = planSeleccionado;
    }

    public Mes getMesSeleccionado() {
        return mesSeleccionado;
    }

    public void setMesSeleccionado(Mes mesSeleccionado) {
        this.mesSeleccionado = mesSeleccionado;
    }

    public Integer getAnioSeleccionado() {
        return anioSeleccionado;
    }

    public void setAnioSeleccionado(Integer anioSeleccionado) {
        this.anioSeleccionado = anioSeleccionado;
    }

    public void subir(FileUploadEvent fue) {
        Documento d = ficheroUploader.subir(fue);
        if (d == null) {
            return;
        }
        MpaEjecucionPlan ejecucion = new MpaEjecucionPlan();
        ejecucion.setIdActividad(planSeleccionado.getIdActividad());
        ejecucion.setIdPrograma(planSeleccionado.getIdPrograma());
        ejecucion.setRutResponsable(logicaPersonal.obtener(sesionCliente.getUsuario().getRut()));
        ejecucion.setIdMes(Integer.parseInt(mesSeleccionado.getId()));
        logicaProgramaActividades.guardarEjecucion(ejecucion);
        logicaDocumentos.asociarDocumento(d, ejecucion);
        FacesUtil.mostrarMensajeInformativo("Archivo subido", "Se ha subido el documento " + d.getId() + " a la ejecución del plan " + ejecucion.getId());
    }

    public void enlazar(Descargable descargable) {
        Documento d = logicaDocumentos.obtenerPorCodigo(descargable.getArchivo().getName());
        if (d == null) {
            return;
        }
        MpaEjecucionPlan ejecucion = new MpaEjecucionPlan();
        ejecucion.setIdActividad(planSeleccionado.getIdActividad());
        ejecucion.setIdPrograma(planSeleccionado.getIdPrograma());
        ejecucion.setRutResponsable(logicaPersonal.obtener(sesionCliente.getUsuario().getRut()));
        ejecucion.setIdMes(Integer.parseInt(mesSeleccionado.getId()));
        logicaProgramaActividades.guardarEjecucion(ejecucion);
        logicaDocumentos.asociarDocumento(d, ejecucion);
        FacesUtil.mostrarMensajeInformativo("Archivo enlazado", "Se ha enlazado el documento " + d.getId() + " a la ejecución del plan");
    }
}
