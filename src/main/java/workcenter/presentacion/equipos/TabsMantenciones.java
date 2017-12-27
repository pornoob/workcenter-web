package workcenter.presentacion.equipos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.equipos.LogicaEquipos;
import workcenter.negocio.equipos.LogicaMantenciones;
import workcenter.negocio.equipos.LogicaProveedorPetroleo;
import workcenter.util.components.Constantes;
import workcenter.util.components.FacesUtil;
import workcenter.util.dto.Dia;
import workcenter.util.dto.Mes;
import workcenter.util.dto.Semana;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Claudio Olivares
 */
@Component
@Scope("flow")
public class TabsMantenciones implements Serializable {

    private static final long serialVersionUID = -8603051539908186924L;

    private List<MmeMantencionTracto> mantencionesTractos;
    private List<MmeMantencionSemirremolque> mantencionesSemiremolque;
    private Set<MmeMantencionMaquina> mantencionesMaquina;
    private List<Equipo> equipos;
    private Map<Equipo, MmeMantencionTracto> pannes;
    private Map<Equipo, Vuelta> vueltas;
    private Map<Equipo, RendimientoCopec> rendimientosCopec;

    private Equipo equipo;

    private Mes mesData;

    private Integer mes;
    private Integer anio;

    private Boolean isListadoVisible;
    private Boolean isCalendarioVisible;

    @Autowired
    private LogicaMantenciones logicaMantenciones;

    @Autowired
    private LogicaEquipos logicaEquipos;

    @Autowired
    private LogicaProveedorPetroleo logicaProveedorPetroleo;

    @Autowired
    private Constantes constantes;

    public void cargarMantencionesSemiremolques() {
        mantencionesSemiremolque = logicaMantenciones.obtenerUltimasMantencionesSemiremolques();
    }

    public void cargarCalendarioTracto() throws ParseException {
        initMesData();
        isCalendarioVisible = Boolean.TRUE;
        isListadoVisible = Boolean.FALSE;
        equipos = logicaEquipos.obtenerTractosConMantenimientos(mes, anio);
        rendimientosCopec = new HashMap<>();
        List<RendimientoCopec> ultimosRendimientos = logicaProveedorPetroleo.obtenerUltimosRendimientos();
        for (RendimientoCopec r : ultimosRendimientos) {
            rendimientosCopec.put(new Equipo(r.getPatente()), r);
        }
        List<Vuelta> ultimasVueltas = logicaEquipos.obtenerUltimasVueltasTracto();
        vueltas = new HashMap<>();
        for (Vuelta v : ultimasVueltas) {
            vueltas.put(v.getTracto(), v);
        }
    }

    public void cargarMantencionesTracto() {
        equipos = logicaEquipos.obtenerTractosConMantenimientos();
        mantencionesTractos = new ArrayList<>();
        pannes = new HashMap<>();
        for (Equipo equipo : equipos) {
            if (equipo.getMantenimientos().isEmpty()) continue;
            for (MmeMantencionTracto mantenimiento : equipo.getMantenimientos()) {
                if (mantenimiento.getTipo() == null) continue;
                mantencionesTractos.add(mantenimiento);
                break;
            }
            for (MmeMantencionTracto mantenimiento : equipo.getMantenimientos()) {
                if (mantenimiento.getTipo() != null) continue;
                pannes.put(equipo, mantenimiento);
                break;
            }
        }
        List<Vuelta> ultimasVueltas = logicaEquipos.obtenerUltimasVueltasTracto();
        vueltas = new HashMap<>();
        for (Vuelta v : ultimasVueltas) {
            vueltas.put(v.getTracto(), v);
        }

        rendimientosCopec = new HashMap<>();
        List<RendimientoCopec> ultimosRendimientos = logicaProveedorPetroleo.obtenerUltimosRendimientos();
        for (RendimientoCopec r : ultimosRendimientos) {
            rendimientosCopec.put(new Equipo(r.getPatente()), r);
        }
        isListadoVisible = Boolean.TRUE;
        isCalendarioVisible = Boolean.FALSE;
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

    public Object obtenerMantencion(Equipo e, Date dia) {
        if (e.getTipo().getId().equals(constantes.getEquipoTipoTracto())) {
            for (MmeMantencionTracto mantenimiento : e.getMantenimientos()) {
                if (mantenimiento.getFecha().equals(dia)) {
                    return mantenimiento;
                }
            }
        } else if (e.getTipo().getId().equals(constantes.getEquipoTipoMaquina())) {
            for (MmeMantencionMaquina mantencion : mantencionesMaquina) {
                if (mantencion.getFecha().equals(dia) && e.equals(mantencion.getMaquina())) {
                    return mantencion;
                }
            }
        }
        return null;
    }

    public boolean dibujarSemaforoMaquina(Equipo e, Date dia) {
        if (mantencionesMaquina == null) return false;
        for (MmeMantencionMaquina mantencion : mantencionesMaquina) {
            if (mantencion.getFecha() != null && mantencion.getFecha().equals(dia) && e.equals(mantencion.getMaquina())) {
                return true;
            }
        }
        return false;
    }

    public Integer obtenerKmsFaltante(MmeMantencionTracto mt) {
        Equipo e = mt.getTracto();
        Integer kmCopec = rendimientosCopec.get(e) != null ? rendimientosCopec.get(e).getOdometro() : null;
        Integer kmGuia = vueltas.get(e) != null ? vueltas.get(e).getKmInicial() > vueltas.get(e).getKmFinal() ? vueltas.get(e).getKmInicial() : vueltas.get(e).getKmFinal() : null;
        int kms = 0;
        if (kmCopec != null && kmGuia != null) {
            kms = kmCopec > kmGuia ? kmCopec : kmGuia;
        } else if (kmCopec == null && kmGuia != null) {
            kms = kmGuia;
        } else if (kmCopec != null && kmGuia == null) {
            kms = kmCopec;
        }
        return obtenerKmSiguienteMantencion(mt) - kms;
    }

    public Integer obtenerKmSiguienteMantencion(MmeMantencionTracto mt) {
        MantenedorMantenciones flow = FacesUtil.getBeanInCurrentFlow(MantenedorMantenciones.class, "mantenedorMantenciones");
        return mt.getKilometraje() + (mt.getCiclo() == 0 || mt.getCiclo() < flow.getCiclos() ? flow.getTiposMantencion().get(0).getCotaKilometraje() : flow.getTiposMantencion().get(1).getCotaKilometraje());
    }

    public boolean filtroEstado(Object valor, Object filtro, Locale idioma) {
        if (filtro == null) {
            return true;
        }
        if (filtro.equals("proximas")) {
            return dibujarSemaforoAmarillo(obtenerKmsFaltante((MmeMantencionTracto) valor));
        } else if (filtro.equals("lejanas")) {
            return dibujarSemaforoVerde(obtenerKmsFaltante((MmeMantencionTracto) valor));
        } else if (filtro.equals("atrasadas")) {
            return dibujarSemaforoRojo(obtenerKmsFaltante((MmeMantencionTracto) valor));
        }
        return false;
    }

    public Date obtenerFechaSiguienteMantencion(MmeMantencionTracto mt) {
        Calendar c = Calendar.getInstance();
        int dias = obtenerKmsFaltante(mt) / 500;
        c.add(Calendar.DAY_OF_MONTH, dias);
        return c.getTime();
    }

    public boolean dibujarSemaforoRojo(Integer valor) {
        return valor <= 0;
    }

    public boolean dibujarSemaforoAmarillo(Integer valor) {
        return valor > 0 && valor <= constantes.getAlarmaProximaMantencion();
    }

    public boolean dibujarSemaforoVerde(Integer valor) {
        return valor > constantes.getAlarmaProximaMantencion();
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

    public List<MmeMantencionSemirremolque> getMantencionesSemiremolque() {
        return mantencionesSemiremolque;
    }

    public void setMantencionesSemiremolque(List<MmeMantencionSemirremolque> mantencionesSemiremolque) {
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

    public Map<Equipo, MmeMantencionTracto> getPannes() {
        return pannes;
    }

    public void setPannes(Map<Equipo, MmeMantencionTracto> pannes) {
        this.pannes = pannes;
    }

    public Map<Equipo, Vuelta> getVueltas() {
        return vueltas;
    }

    public void setVueltas(Map<Equipo, Vuelta> vueltas) {
        this.vueltas = vueltas;
    }

    public Map<Equipo, RendimientoCopec> getRendimientosCopec() {
        return rendimientosCopec;
    }

    public void setRendimientosCopec(Map<Equipo, RendimientoCopec> rendimientosCopec) {
        this.rendimientosCopec = rendimientosCopec;
    }

    public Boolean getListadoVisible() {
        return isListadoVisible;
    }

    public void setListadoVisible(Boolean listadoVisible) {
        isListadoVisible = listadoVisible;
    }

    public Boolean getCalendarioVisible() {
        return isCalendarioVisible;
    }

    public void setCalendarioVisible(Boolean calendarioVisible) {
        isCalendarioVisible = calendarioVisible;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }
}
