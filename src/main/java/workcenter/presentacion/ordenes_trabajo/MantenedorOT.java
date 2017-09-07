package workcenter.presentacion.ordenes_trabajo;

import workcenter.negocio.taller.LogicaOt;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.faces.event.AjaxBehaviorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Equipo;
import workcenter.entidades.MmeMantencionMaquina;
import workcenter.entidades.MmeMantencionSemirremolque;
import workcenter.entidades.MmeMantencionTracto;
import workcenter.entidades.MmeTipoMantencion;
import workcenter.entidades.OrdenTrabajo;
import workcenter.entidades.Personal;
import workcenter.entidades.SolicitanteOt;
import workcenter.entidades.TrazabilidadOt;
import workcenter.negocio.equipos.LogicaEquipos;
import workcenter.negocio.equipos.LogicaMantenciones;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.util.components.Constantes;
import workcenter.util.components.FacesUtil;
import workcenter.util.components.SesionCliente;

/**
 *
 * @author Claudio Olivares
 */
@Component
@Scope("flow")
public class MantenedorOT implements Serializable {

    private static final long serialVersionUID = -4399796587348042523L;

    private Personal ejecutor;
    private OrdenTrabajo ot;
    private List<String> tiposTrabajo;
    private Boolean renderTractoBatea;
    private Boolean renderMaquinaria;
    private MmeMantencionTracto mantencionTracto;
    private MmeMantencionSemirremolque mantencionSemirremolque;
    private MmeMantencionMaquina mantencionMaquina;

    private List<OrdenTrabajo> ots_esperando;
    private List<OrdenTrabajo> ots_ejecutando;
    private List<OrdenTrabajo> ots_resueltas;
    private List<SolicitanteOt> solicitantes;

    private List<MmeTipoMantencion> tiposMantencion;
    private List<Equipo> tractos;
    private List<Equipo> bateas;
    private List<Equipo> maquinas;
    private List<Personal> mecanicos;

    @Autowired
    private LogicaOt logicaOt;
    @Autowired
    private LogicaMantenciones logicaMantenciones;
    @Autowired
    private LogicaEquipos logicaEquipos;
    @Autowired
    private LogicaPersonal logicaPersonal;
    @Autowired
    private Constantes constantes;
    @Autowired
    private SesionCliente sesionCliente;

    public void init() {
        ot = new OrdenTrabajo();
        solicitantes = logicaOt.findApplicants();
        ots_esperando = logicaOt.findByStatus(constantes.getESTADO_OT_CREADA());
        ots_ejecutando = logicaOt.findByStatus(constantes.getESTADO_OT_ASIGNADA());
        ots_resueltas = logicaOt.findByStatus(constantes.getESTADO_OT_FINALIZADA());
        mantencionMaquina = null;
        mantencionSemirremolque = null;
        mantencionTracto = null;
    }

    private String listTipoTrabajoToString() {
        StringBuilder sb = new StringBuilder();
        for (String tipoTrabajo : tiposTrabajo) {
            sb.append(tipoTrabajo).append(',');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public String tipoTrabajoToPrettyName(String tipoTrabajo) {
        if (tipoTrabajo == null) {
            return null;
        }
        String[] tipos = tipoTrabajo.split(",");
        StringBuilder sb = new StringBuilder();
        for (String tipo : tipos) {
            if (tipo.isEmpty()) {
                continue;
            }
            if (Integer.valueOf(tipo) == constantes.getPAUTA_TRACTO()) {
                sb.append("Mantenimiento Tracto - ");
            } else if (Integer.valueOf(tipo) == constantes.getPAUTA_SEMIREMOLQUE()) {
                sb.append("Mantenimiento Semirremolque - ");
            } else if (Integer.valueOf(tipo) == constantes.getPAUTA_MAQUINARIA()) {
                sb.append("Mantenimiento Maquinaria - ");
            } else if (Integer.valueOf(tipo) == constantes.getPAUTA_VENTA_REPUESTO()) {
                sb.append("Venta Repuesto - ");
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 3);
        }
        return sb.toString();
    }

    public void updateRequestedFields(AjaxBehaviorEvent event) {
        renderMaquinaria = Boolean.FALSE;
        renderTractoBatea = Boolean.FALSE;
        if (tiposTrabajo == null || tiposTrabajo.isEmpty()) {
            return;
        }

        for (String tipoTrabajo : tiposTrabajo) {
            if (Integer.valueOf(tipoTrabajo).equals(constantes.getPAUTA_TRACTO()) || Integer.valueOf(tipoTrabajo).equals(constantes.getPAUTA_SEMIREMOLQUE())) {
                renderTractoBatea = Boolean.TRUE;

                tiposMantencion = logicaMantenciones.obtenerTiposMantencion();
                mecanicos = logicaPersonal.obtenerMecanicos();
                bateas = logicaEquipos.obtenerBateas();
                tractos = logicaEquipos.obtenerTractos();
                mantencionTracto = new MmeMantencionTracto();
                mantencionSemirremolque = new MmeMantencionSemirremolque();
                
                mantencionTracto.setOt(ot);
                mantencionSemirremolque.setOt(ot);
                
                ot.setMantencionTracto(mantencionTracto);
                ot.setMantencionSemirremolque(mantencionSemirremolque);
            } else {
                renderMaquinaria = Boolean.TRUE;
                mecanicos = logicaPersonal.obtenerMecanicos();
                maquinas = logicaEquipos.obtenerMaquinas();
                mantencionMaquina = new MmeMantencionMaquina();
                mantencionMaquina.setOt(ot);
                ot.setMantencionMaquina(mantencionMaquina);
            }
        }

        if (Boolean.TRUE.equals(renderMaquinaria) && Boolean.TRUE.equals(renderTractoBatea)) {
            tiposTrabajo = null;
            renderMaquinaria = Boolean.FALSE;
            renderTractoBatea = Boolean.FALSE;
            FacesUtil.mostrarMensajeError("Error", "No se puede seleccionar mantenciones multiples para una OT, a excepción de Tracto/Semirremolque");
        }
    }

    public void create() {
        TrazabilidadOt state = new TrazabilidadOt();
        state.setAutor(sesionCliente.getUsuario().getRut());
        state.setFecha(new Date());
        state.setEstadoId(constantes.getESTADO_OT_CREADA());
        state.setOtId(ot);
        SortedSet<TrazabilidadOt> history = new TreeSet<>();
        history.add(state);
        ot.setTipoTrabajo(listTipoTrabajoToString());
        ot.setTrazabilidad(history);
        if (Boolean.TRUE.equals(renderMaquinaria)) {
            mantencionMaquina.setOt(ot);
            logicaOt.create(ot, mantencionMaquina);
        } else {
            if (mantencionTracto.getTracto() == null && mantencionSemirremolque.getSemiRremolque() == null) {
                FacesUtil.mostrarMensajeError("Error", "Debes especificar al menos un Tracto y/o Semirremolque");
                return;
            }

            if (mantencionTracto.getTracto() != null && mantencionSemirremolque.getSemiRremolque() != null) {
                int ciclos = (tiposMantencion.get(1).getCotaKilometraje() / tiposMantencion.get(0).getCotaKilometraje()) - 1;
                int cicloActual = (logicaMantenciones.obtenerUltimoCiclo(mantencionTracto.getTracto()) + 2) % ciclos;
                mantencionTracto.setCiclo(cicloActual);
                mantencionSemirremolque.setCriterioSiguiente(30);
                
                mantencionSemirremolque.setOt(ot);
                mantencionTracto.setOt(ot);
                
                ot.setMantencionSemirremolque(mantencionSemirremolque);
                ot.setMantencionTracto(mantencionTracto);
                ot.setMantencionMaquina(null);
                
                logicaOt.create(ot, mantencionTracto, mantencionSemirremolque);
            } else if (mantencionTracto.getTracto() != null) {
                int ciclos = (tiposMantencion.get(1).getCotaKilometraje() / tiposMantencion.get(0).getCotaKilometraje()) - 1;
                int cicloActual = (logicaMantenciones.obtenerUltimoCiclo(mantencionTracto.getTracto()) + 2) % ciclos;
                mantencionTracto.setCiclo(cicloActual);
                mantencionTracto.setOt(ot);
                ot.setMantencionTracto(mantencionTracto);
                ot.setMantencionSemirremolque(null);
                ot.setMantencionMaquina(null);
                logicaOt.create(ot, mantencionTracto);
            } else {
                mantencionSemirremolque.setOt(ot);
                ot.setMantencionSemirremolque(mantencionSemirremolque);
                ot.setMantencionTracto(null);
                ot.setMantencionMaquina(null);
                logicaOt.create(ot, mantencionSemirremolque);
            }
        }
        init();
        FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Se ha generado una nueva orden de trabajo");
    }

    public String prepareToAssign(OrdenTrabajo ot) {
        this.ot = ot;
        Boolean isSelling = Boolean.FALSE;
        for (String tipoTrabajo : this.ot.getTipoTrabajo().split(",")) {
            if (constantes.getPAUTA_VENTA_REPUESTO() == Integer.valueOf(tipoTrabajo)) {
                isSelling = Boolean.TRUE;
                break;
            }
        }
        if (Boolean.TRUE.equals(isSelling)) {
            mecanicos = logicaPersonal.findAll();
        } else {
            mecanicos = logicaPersonal.obtenerMecanicos();
        }
        return "assign";
    }

    public void toAssign() {
        TrazabilidadOt state = new TrazabilidadOt();
        state.setAutor(sesionCliente.getUsuario().getRut());
        state.setOtId(this.ot);
        state.setEjecutor(ejecutor);
        state.setFecha(new Date());
        state.setEstadoId(constantes.getESTADO_OT_ASIGNADA());
        
        if (constantes.getPAUTA_VENTA_REPUESTO() != Integer.valueOf(this.ot.getTipoTrabajo())) {
            if (ot.getMantencionTracto() != null) {
                ot.getMantencionTracto().setMecanicoResponsable(ejecutor);
            }
            if (ot.getMantencionSemirremolque()!= null) {
                ot.getMantencionSemirremolque().setMecanicoResponsable(ejecutor);
            }
            if (ot.getMantencionMaquina() != null) {
                ot.getMantencionMaquina().setMecanicoResponsable(ejecutor);
            }
        }
        
        this.ot.getTrazabilidad().add(state);
        logicaOt.updateOt(this.ot);
        FacesUtil.mostrarMensajeInformativo("Operación exitosa", "La OT ha sido asignada correctamente");
        init();
    }

    public void toCancel(OrdenTrabajo ot) {
    }

    // getters and setters
    public OrdenTrabajo getOt() {
        return ot;
    }

    public void setOt(OrdenTrabajo ot) {
        this.ot = ot;
    }

    public List<OrdenTrabajo> getOts_esperando() {
        return ots_esperando;
    }

    public void setOts_esperando(List<OrdenTrabajo> ots_esperando) {
        this.ots_esperando = ots_esperando;
    }

    public List<OrdenTrabajo> getOts_resueltas() {
        return ots_resueltas;
    }

    public void setOts_resueltas(List<OrdenTrabajo> ots_resueltas) {
        this.ots_resueltas = ots_resueltas;
    }

    public List<OrdenTrabajo> getOts_ejecutando() {
        return ots_ejecutando;
    }

    public void setOts_ejecutando(List<OrdenTrabajo> ots_ejecutando) {
        this.ots_ejecutando = ots_ejecutando;
    }

    public List<SolicitanteOt> getSolicitantes() {
        return solicitantes;
    }

    public void setSolicitantes(List<SolicitanteOt> solicitantes) {
        this.solicitantes = solicitantes;
    }

    public Personal getEjecutor() {
        return ejecutor;
    }

    public void setEjecutor(Personal ejecutor) {
        this.ejecutor = ejecutor;
    }

    public List<String> getTiposTrabajo() {
        return tiposTrabajo;
    }

    public void setTiposTrabajo(List<String> tiposTrabajo) {
        this.tiposTrabajo = tiposTrabajo;
    }

    public Boolean getRenderTractoBatea() {
        return renderTractoBatea;
    }

    public void setRenderTractoBatea(Boolean renderTractoBatea) {
        this.renderTractoBatea = renderTractoBatea;
    }

    public Boolean getRenderMaquinaria() {
        return renderMaquinaria;
    }

    public void setRenderMaquinaria(Boolean renderMaquinaria) {
        this.renderMaquinaria = renderMaquinaria;
    }

    public MmeMantencionTracto getMantencionTracto() {
        return mantencionTracto;
    }

    public void setMantencionTracto(MmeMantencionTracto mantencionTracto) {
        this.mantencionTracto = mantencionTracto;
    }

    public MmeMantencionSemirremolque getMantencionSemirremolque() {
        return mantencionSemirremolque;
    }

    public void setMantencionSemirremolque(MmeMantencionSemirremolque mantencionSemirremolque) {
        this.mantencionSemirremolque = mantencionSemirremolque;
    }

    public List<MmeTipoMantencion> getTiposMantencion() {
        return tiposMantencion;
    }

    public void setTiposMantencion(List<MmeTipoMantencion> tiposMantencion) {
        this.tiposMantencion = tiposMantencion;
    }

    public List<Equipo> getTractos() {
        return tractos;
    }

    public void setTractos(List<Equipo> tractos) {
        this.tractos = tractos;
    }

    public List<Equipo> getBateas() {
        return bateas;
    }

    public void setBateas(List<Equipo> bateas) {
        this.bateas = bateas;
    }

    public List<Personal> getMecanicos() {
        return mecanicos;
    }

    public void setMecanicos(List<Personal> mecanicos) {
        this.mecanicos = mecanicos;
    }

    public MmeMantencionMaquina getMantencionMaquina() {
        return mantencionMaquina;
    }

    public void setMantencionMaquina(MmeMantencionMaquina mantencionMaquina) {
        this.mantencionMaquina = mantencionMaquina;
    }

    public List<Equipo> getMaquinas() {
        return maquinas;
    }

    public void setMaquinas(List<Equipo> maquinas) {
        this.maquinas = maquinas;
    }
}
