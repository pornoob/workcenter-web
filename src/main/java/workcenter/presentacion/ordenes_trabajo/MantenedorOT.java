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
import workcenter.entidades.MmeMantencionSemiremolque;
import workcenter.entidades.MmeMantencionTracto;
import workcenter.entidades.OrdenTrabajo;
import workcenter.entidades.Personal;
import workcenter.entidades.SolicitanteOt;
import workcenter.entidades.TrazabilidadOt;
import workcenter.util.components.Constantes;
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
    private MmeMantencionSemiremolque mantencionSemirremolque;

    private List<OrdenTrabajo> ots_esperando;
    private List<OrdenTrabajo> ots_ejecutando;
    private List<OrdenTrabajo> ots_resueltas;
    private List<SolicitanteOt> solicitantes;

    @Autowired
    private LogicaOt logicaOt;
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
        if (tiposTrabajo == null) {
            return;
        }
        for (String tipoTrabajo : tiposTrabajo) {
            if (tipoTrabajo.equals(constantes.getPAUTA_TRACTO()) || tipoTrabajo.equals(constantes.getPAUTA_SEMIREMOLQUE())) {
                renderMaquinaria = Boolean.FALSE;
                renderTractoBatea = Boolean.TRUE;
            } else {
                renderMaquinaria = Boolean.TRUE;
                renderTractoBatea = Boolean.FALSE;
            }
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
        logicaOt.create(ot);
    }

    public String prepareToAssign(OrdenTrabajo ot) {
        this.ot = ot;
        TrazabilidadOt state = new TrazabilidadOt();
        state.setAutor(sesionCliente.getUsuario().getRut());
        state.setOtId(this.ot);
        this.ot.getTrazabilidad().add(state);
        return "assign";
    }

    public void toAssign(OrdenTrabajo ot) {
        this.ot.getTrazabilidad().last().setFecha(new Date());
        this.ot.getTrazabilidad().last().setEstadoId(constantes.getESTADO_OT_ASIGNADA());
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

    public MmeMantencionSemiremolque getMantencionSemirremolque() {
        return mantencionSemirremolque;
    }

    public void setMantencionSemirremolque(MmeMantencionSemiremolque mantencionSemirremolque) {
        this.mantencionSemirremolque = mantencionSemirremolque;
    }
}
