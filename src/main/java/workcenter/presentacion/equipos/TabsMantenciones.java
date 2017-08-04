package workcenter.presentacion.equipos;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Equipo;
import workcenter.entidades.MmeMantencionMaquina;
import workcenter.entidades.MmeMantencionSemiremolque;
import workcenter.entidades.MmeMantencionTracto;
import workcenter.negocio.equipos.LogicaMantenciones;
import workcenter.util.components.FacesUtil;
import workcenter.util.dto.Dia;
import workcenter.util.dto.Mes;
import workcenter.util.dto.Semana;

/**
 *
 * @author Claudio Olivares
 */
@Component
@Scope("flow")
public class TabsMantenciones implements Serializable {

    private static final long serialVersionUID = -8603051539908186924L;

    private List<MmeMantencionTracto> mantencionesTractos;
    private List<MmeMantencionSemiremolque> mantencionesSemiremolque;
    private Set<MmeMantencionMaquina> mantencionesMaquina;
    
    private Equipo equipo;
    
    private Mes mesData;

    private Integer mes;
    private Integer anio;

    @Autowired
    private LogicaMantenciones logicaMantenciones;
    
    public void cargarMantencionesSemiremolques() {
        mantencionesSemiremolque = logicaMantenciones.obtenerUltimasMantencionesSemiremolques();
    }
    
    public void cargarMantencionesTracto() {
        mantencionesTractos = logicaMantenciones.obtenerUltimasMantenciones();
    }
    
    public void cargarMantencionesMaquinas() throws ParseException {
        initMesData();
        mantencionesMaquina = logicaMantenciones.obtenerUltimasMantencionesMaquina(mes, anio);
    }
    
    private void initMesData() throws ParseException {
        mesData = new Mes();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        mes = (Integer) FacesUtil.obtenerVariableFlow("mes");
        anio = (Integer) FacesUtil.obtenerVariableFlow("anio");
        Date fecha = sdf.parse(
                mes
                + "-"
                + anio
        );

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        
        mesData.setSemanas(new ArrayList<Semana>());
        int primerDia = (calendar.getFirstDayOfWeek() + 5) % 7 + 1;
        int dias = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int semanaInicial = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.set(Calendar.DAY_OF_MONTH, dias);
        int semanaFinal = calendar.get(Calendar.WEEK_OF_YEAR);

        for (int i = 0; i < semanaFinal - semanaInicial + 1; i++) {
            mesData.getSemanas().add(new Semana());
            if (i == 0) {
                for (Dia dia : mesData.getSemanas().get(i).getDias()) {
                    if (dia.getId() == primerDia) {
                        calendar.set(Calendar.DAY_OF_MONTH, 1);
                        dia.setFecha(calendar.getTime());
                    } else if (dia.getId() > primerDia) {
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        dia.setFecha(calendar.getTime());
                    }
                }
            } else {
                for (Dia dia : mesData.getSemanas().get(i).getDias()) {
                    if (calendar.get(Calendar.DAY_OF_MONTH) < dias) {
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        dia.setFecha(calendar.getTime());
                    }
                }
            }
        }
    }
    
    public MmeMantencionMaquina obtenerMantencion(Equipo e, Date dia) throws ParseException {
        for (MmeMantencionMaquina mantencion : mantencionesMaquina) {
            if (mantencion.getFecha().equals(dia) && e.equals(mantencion.getMaquina())) {
                return mantencion;
            }
        }
        return null;
    }

    public boolean dibujarSemaforoMaquina(Equipo e, Date dia) throws ParseException {
        for (MmeMantencionMaquina mantencion : mantencionesMaquina) {
            if (mantencion.getFecha().equals(dia) && e.equals(mantencion.getMaquina())) {
                return true;
            }
        }
        return false;
    }

    public String irHistorial(Equipo e) {
        equipo = e;
        mantencionesTractos = logicaMantenciones.obtenerMantenciones(e);
        return "flowHistorial";
    }

    public String irHistorialSemiremolque(Equipo e) {
        equipo = e;
        mantencionesSemiremolque = logicaMantenciones.obtenerMantencionesSemiremolques(e);
        return "flowHistorial";
    }
    
    public String irHistorialMaquina(Equipo e) {
        equipo = e;
        mantencionesMaquina = logicaMantenciones.obtenerMantencionesMaquina(e);
        return "flowHistorialMaquina";
    }

    // getter and setter
    public List<MmeMantencionTracto> getMantencionesTractos() {
        return mantencionesTractos;
    }

    public void setMantencionesTractos(List<MmeMantencionTracto> mantencionesTractos) {
        this.mantencionesTractos = mantencionesTractos;
    }

    public List<MmeMantencionSemiremolque> getMantencionesSemiremolque() {
        return mantencionesSemiremolque;
    }

    public void setMantencionesSemiremolque(List<MmeMantencionSemiremolque> mantencionesSemiremolque) {
        this.mantencionesSemiremolque = mantencionesSemiremolque;
    }

    public Set<MmeMantencionMaquina> getMantencionesMaquina() {
        return mantencionesMaquina;
    }

    public void setMantencionesMaquina(Set<MmeMantencionMaquina> mantencionesMaquina) {
        this.mantencionesMaquina = mantencionesMaquina;
    }

    public Mes getMesData() {
        return mesData;
    }

    public void setMesData(Mes mesData) {
        this.mesData = mesData;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}
