package workcenter.presentacion.ordenes_trabajo;

import java.io.Serializable;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Empresa;
import workcenter.entidades.OrdenTrabajo;

/**
 *
 * @author Claudio Olivares
 */
@Component
@Scope("flow")
public class MantenedorOT implements Serializable {

    private static final long serialVersionUID = -4399796587348042523L;
    
    private OrdenTrabajo ot;
    
    private List<OrdenTrabajo> ots_esperando;
    private List<OrdenTrabajo> ots_ejecutando;
    private List<OrdenTrabajo> ots_resueltas;
    private List<Empresa> empresas;
    
    public void init() {
    }
    
    public void create() {
    }
    
    public void toAssign(OrdenTrabajo ot) {
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

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
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
}
