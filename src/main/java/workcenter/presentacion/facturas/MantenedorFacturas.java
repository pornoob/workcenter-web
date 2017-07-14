package workcenter.presentacion.facturas;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Empresa;
import workcenter.entidades.FactDetalleFactura;
import workcenter.entidades.FactFactura;
import workcenter.entidades.FactProducto;
import workcenter.negocio.LogicaEmpresas;
import workcenter.negocio.facturas.LogicaStock;

/**
 *
 * @author Claudio Olivares
 */
@Component
@Scope("flow")
public class MantenedorFacturas implements Serializable {

    private static final long serialVersionUID = -5016398205437195408L;
    
    private FactFactura factura;
    private FactDetalleFactura detalleFactura;
    
    private List<Empresa> empresas;
    private List<FactProducto> productos;
    
    @Autowired
    private LogicaEmpresas logicaEmpresas;
    @Autowired
    private LogicaStock logicaStock;

    public void init() {
        empresas = logicaEmpresas.obtenerEmpresas();
        productos =logicaStock.findAll();
        factura = new FactFactura();
    }
    
    public void reloadProducts() {
        productos =logicaStock.findAll();
    }
    
    public void addItem() {
        detalleFactura = new FactDetalleFactura();
        detalleFactura.setFactura(factura);
        detalleFactura.setCantidad(0);
        detalleFactura.setPrecioUnitario(0);
        factura.addItem(detalleFactura);
    }

    public FactFactura getFactura() {
        return factura;
    }

    public void setFactura(FactFactura factura) {
        this.factura = factura;
    }

    public FactDetalleFactura getDetalleFactura() {
        return detalleFactura;
    }

    public void setDetalleFactura(FactDetalleFactura detalleFactura) {
        this.detalleFactura = detalleFactura;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public List<FactProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<FactProducto> productos) {
        this.productos = productos;
    }
}
