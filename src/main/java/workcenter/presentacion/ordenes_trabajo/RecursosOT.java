package workcenter.presentacion.ordenes_trabajo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.RequestContextHolder;
import workcenter.entidades.*;
import workcenter.negocio.facturas.LogicaStock;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.negocio.taller.LogicaOt;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Component
@Scope("view")
public class RecursosOT {
    private OrdenTrabajo ordenTrabajo;
    private RepuestoOt repuestoOt;
    private AsistenteOt asistenteOt;
    private List<RepuestoOt> repuestoOtList;
    private List<AsistenteOt> asistenteOtList;

    private List<FactProducto> productos;
    private List<Personal> personal;

    @Autowired
    private LogicaOt logicaOt;
    @Autowired
    private LogicaPersonal logicaPersonal;
    @Autowired
    private LogicaStock logicaStock;

    @PostConstruct
    public void init() {
        productos = logicaStock.findAll();
        personal = logicaPersonal.obtenerMecanicos();

        RequestContext requestContext = RequestContextHolder.getRequestContext();
        ordenTrabajo = (OrdenTrabajo) requestContext.getFlowScope().get("ordenTrabajo");
        repuestoOtList = new ArrayList<>(ordenTrabajo.getRepuestos());
        asistenteOtList = new ArrayList<>(ordenTrabajo.getAsistentes());
    }

    public void initRepuesto() {
        repuestoOt = new RepuestoOt();
        repuestoOtList.add(repuestoOt);
    }

    public void initAsistente() {
        asistenteOt = new AsistenteOt();
        asistenteOtList.add(asistenteOt);
    }

    public void removeRepuesto(RepuestoOt repuestoOt) {
        repuestoOtList.remove(repuestoOt);
    }

    public void removeAsistente(AsistenteOt asistenteOt) {
        asistenteOtList.remove(asistenteOt);
    }

    public String save() {
        ordenTrabajo.setRepuestos(new HashSet<>(repuestoOtList));
        ordenTrabajo.setAsistentes(new HashSet<>(asistenteOtList));

        for (RepuestoOt repuesto : ordenTrabajo.getRepuestos()) {
            repuesto.setOt(ordenTrabajo);
        }

        for (AsistenteOt asistente : ordenTrabajo.getAsistentes()) {
            asistente.setOt(ordenTrabajo);
        }

        logicaOt.updateOt(ordenTrabajo);
        return "back";
    }

    // getters and setters
    public OrdenTrabajo getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public RepuestoOt getRepuestoOt() {
        return repuestoOt;
    }

    public void setRepuestoOt(RepuestoOt repuestoOt) {
        this.repuestoOt = repuestoOt;
    }

    public AsistenteOt getAsistenteOt() {
        return asistenteOt;
    }

    public void setAsistenteOt(AsistenteOt asistenteOt) {
        this.asistenteOt = asistenteOt;
    }

    public List<RepuestoOt> getRepuestoOtList() {
        return repuestoOtList;
    }

    public void setRepuestoOtList(List<RepuestoOt> repuestoOtList) {
        this.repuestoOtList = repuestoOtList;
    }

    public List<AsistenteOt> getAsistenteOtList() {
        return asistenteOtList;
    }

    public void setAsistenteOtList(List<AsistenteOt> asistenteOtList) {
        this.asistenteOtList = asistenteOtList;
    }

    public List<FactProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<FactProducto> productos) {
        this.productos = productos;
    }

    public List<Personal> getPersonal() {
        return personal;
    }

    public void setPersonal(List<Personal> personal) {
        this.personal = personal;
    }
}
