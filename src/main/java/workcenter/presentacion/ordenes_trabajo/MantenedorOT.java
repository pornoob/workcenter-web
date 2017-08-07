package workcenter.presentacion.ordenes_trabajo;

import workcenter.negocio.taller.LogicaOt;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
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
        ots_esperando = logicaOt.findByStatus(constantes.getESTADO_OT_CREADA());
        ots_ejecutando = logicaOt.findByStatus(constantes.getESTADO_OT_ASIGNADA());
        ots_resueltas = logicaOt.findByStatus(constantes.getESTADO_OT_FINALIZADA());
    }
    
    public void create() {
        TrazabilidadOt state = new TrazabilidadOt();
        state.setAutor(sesionCliente.getUsuario().getRut());
        state.setFecha(new Date());
        state.setEstadoId(constantes.getESTADO_OT_CREADA());
        state.setOtId(ot);
        SortedSet<TrazabilidadOt> history = new TreeSet<>();
        history.add(state);
        ot.setTrazabilidad(history);
        logicaOt.create(ot);
    }
    
    public void toAssign(OrdenTrabajo ot) {
        TrazabilidadOt state = new TrazabilidadOt();
        state.setAutor(sesionCliente.getUsuario().getRut());
        state.setFecha(new Date());
        state.setEstadoId(constantes.getESTADO_OT_ASIGNADA());
        state.setOtId(ot);
        ot.getTrazabilidad().add(state);
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
}
