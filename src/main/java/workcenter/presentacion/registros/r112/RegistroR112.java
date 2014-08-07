package workcenter.presentacion.registros.r112;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.MarActividad;
import workcenter.entidades.MarParticipantesAct;
import workcenter.entidades.MarTipoActividad;
import workcenter.entidades.Personal;
import workcenter.negocio.LogicaPersonal;
import workcenter.negocio.LogicaRegistros;
import workcenter.util.components.Constantes;
import workcenter.util.pojo.FacesUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author colivares
 */
@Component
@Scope("flow")
public class RegistroR112 implements Serializable {
    private List<MarActividad> actividades;
    private MarActividad actividad;
    private List<Personal> personal;
    private Personal participante;
    private List<MarParticipantesAct> participantes;
    private List<MarTipoActividad> tipoActividades;
    private MarTipoActividad tipoActividad;
    private Integer hrInicio;
    private Integer minInicio;
    private Integer hrFin;
    private Integer minFin;
    private Integer hrDuracion;
    private Integer minDuracion;
    private boolean inicio = false;

    @Autowired
    LogicaRegistros logicaRegistros;

    @Autowired
    Constantes constantes;

    @Autowired
    LogicaPersonal logicaPersonal;

    public String inicio() {
        actividades = logicaRegistros.obtenerActividadesSegunRegistro(constantes.getRegistroR112());
        return irListarR112();
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
        System.err.println("PARTICIPANTES A GUARDAR: "+participantes);
        actividad.setParticipantes(participantes);
        actividad.setHoraInicio((hrInicio < 10 ? "0" + hrInicio : hrInicio) + ":" + (minInicio < 10 ? "0" + minInicio : minInicio));
        actividad.setHoraFin((hrFin < 10 ? "0" + hrFin : hrFin) + ":" + (minFin < 10 ? "0" + minFin : minFin));
        actividad.setTipoActividad(tipoActividad);
        logicaRegistros.guardarActividad(actividad);
        actividades.add(actividad);
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Se ha creado la actividad \"" + actividad.getNombre() + "\"");
    }

    public String verParticipantes(MarActividad a) {
        a.setParticipantes(logicaRegistros.obtenerParticipantes(a));
        actividad = a;
        return "flowVerParticipantes";
    }

    public String preparaCreacionR112() {
        actividad = new MarActividad();
        Calendar fechaActual = Calendar.getInstance();
        actividad.setFecha(fechaActual.getTime());

        hrInicio = fechaActual.get(Calendar.HOUR_OF_DAY);
        hrFin = hrInicio.intValue();
        minInicio = fechaActual.get(Calendar.MINUTE);
        minFin = minInicio.intValue();

        tipoActividades = logicaRegistros.obtenerTiposActividades(constantes.getRegistroR112());
        personal = logicaPersonal.obtenerTodos();
        participantes = new ArrayList<MarParticipantesAct>();
        participante = null;
        return irNuevoR112();
    }

    public String preparaCreacionTipoActividad() {
        tipoActividad = new MarTipoActividad();
        tipoActividad.setMarRegistrosByIdRegistro(logicaRegistros.obtenerRegistro(constantes.getRegistroR112()));
        hrDuracion = null;
        minDuracion = null;
        return irNuevoTipoActividad();
    }

    public void guardarTipoActividad() {
        hrDuracion = hrDuracion == null ? 0 : hrDuracion;
        minDuracion = minDuracion == null ? 0 : minDuracion;
        tipoActividad.setDuracion((hrDuracion < 10 ? "0" + hrDuracion : hrDuracion) + ":" + (minDuracion < 10 ? "0" + minDuracion : minDuracion));
        logicaRegistros.guardarTipoActividad(tipoActividad);
        tipoActividades.add(tipoActividad);
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Se ha creado el tipo de actividad \"" + tipoActividad.getNombre() + "\"");
    }

    public void agregarParticipante() {
        participante = null;
//        System.out.println("Rut: " + FacesUtil.obtenerHttpServletRequest().getParameter("formR112:sNuevoParticipante_input"));
        Integer rut = Integer.valueOf(FacesUtil.obtenerHttpServletRequest().getParameter("formR112:sNuevoParticipante_input"));
        for (Personal p : personal) {
            if (rut != null && p.getRut().intValue() == rut.intValue()) {
                participante = p;
                break;
            }
        }
        if (participante == null) return;
        if (participantes.contains(participante)) {
            FacesUtil.mostrarMensajeError("Operación Fallida", "Esta persona ya fue agregada como participante");
            return;
        }
        MarParticipantesAct p = new MarParticipantesAct();
        p.setParticipante(participante);
        p.setMarActividadByIdActividad(actividad);
        participantes.add(p);
    }

    public void quitarParticipante() {
        participantes.remove(participante);
    }

    public Integer obtenerCantAsistentes(MarActividad a) {
        return logicaRegistros.obtenerAsistentesSegunActividad(a);
    }

    public String irListarR112() {
        return "flowListarR112";
    }

    public String irListarRegistros() {
        return "flowListarRegistros";
    }

    public String irNuevoTipoActividad() {
        return "flowNuevoTipoActividad";
    }

    public String irNuevoR112() {
        return "flowNuevoR112";
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

    public Personal getParticipante() {
        return participante;
    }

    public void setParticipante(Personal participante) {
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
}
