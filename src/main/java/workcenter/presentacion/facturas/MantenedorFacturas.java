package workcenter.presentacion.facturas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Empresa;
import workcenter.entidades.FactDetalleFactura;
import workcenter.entidades.FactFactura;
import workcenter.entidades.FactProducto;
import workcenter.negocio.LogicaEmpresas;
import workcenter.negocio.facturas.LogicaFactura;
import workcenter.negocio.facturas.LogicaStock;
import workcenter.util.components.FacesUtil;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Claudio Olivares
 */
@Component
@Scope("flow")
public class MantenedorFacturas implements Serializable {

    private static final long serialVersionUID = -5016398205437195408L;

    private FactFactura factura;
    private Integer totalNetoCalculado;
    private Integer totalNeto;

    private List<Empresa> empresas;
    private List<FactProducto> productos;

    @Autowired
    private LogicaEmpresas logicaEmpresas;
    @Autowired
    private LogicaStock logicaStock;
    @Autowired
    private LogicaFactura logicaFactura;

    public void init() {
        empresas = logicaEmpresas.obtenerEmpresas();
        productos = logicaStock.findAll();
        factura = new FactFactura();
        totalNetoCalculado = 0;
    }

    public void reloadProducts() {
        productos = logicaStock.findAll();
    }

    public void addItem() {
        FactDetalleFactura detalleFactura = new FactDetalleFactura();
        detalleFactura.setFactura(factura);
        detalleFactura.setCantidad(0);
        detalleFactura.setPrecioUnitario(0);
        detalleFactura.setProducto(null);
        factura.addItem(detalleFactura);
    }

    public void updateNeto() {
        totalNetoCalculado = 0;
        Iterator<FactDetalleFactura> iterator = factura.getItems().iterator();
        while (iterator.hasNext()) {
            FactDetalleFactura current = iterator.next();
            totalNetoCalculado += current.getCantidad() * current.getPrecioUnitario();
        }
        if (factura.getDescuento() != null && factura.getDescuento() != 0)
            totalNetoCalculado = (int) (totalNetoCalculado - totalNetoCalculado * factura.getDescuento()/100);
    }

    public void removeItem(FactDetalleFactura item) {
        Set<FactDetalleFactura> oldItems = factura.getItems();
        Set<FactDetalleFactura> newItems = new HashSet<>();
        for (FactDetalleFactura oldItem : oldItems) {
            if (!oldItem.getRowKey().equals(item.getRowKey())) {
                newItems.add(oldItem);
            }
        }
        factura.setItems(newItems);
        updateNeto();
    }

    public void save() {
        Iterator<FactDetalleFactura> iterator = factura.getItems().iterator();
        while (iterator.hasNext()) {
            FactDetalleFactura item = iterator.next();
            if (item.getCantidad() == null || item.getCantidad() == 0 ||
                    item.getPrecioUnitario() == null ||
                    item.getProducto() == null) {
                iterator.remove();
            }
        }
        if (!totalNeto.equals(totalNetoCalculado)) {
            FacesUtil.mostrarMensajeError("Operación fallida", "no coincide el monto total ingresado con el total calculado");
            return;
        }
        logicaFactura.save(factura);
        FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Factura guardada con éxito");
        factura = new FactFactura();
    }

    // getters and setters
    public FactFactura getFactura() {
        return factura;
    }

    public void setFactura(FactFactura factura) {
        this.factura = factura;
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

    public Integer getTotalNetoCalculado() {
        return totalNetoCalculado;
    }

    public void setTotalNetoCalculado(Integer totalNetoCalculado) {
        this.totalNetoCalculado = totalNetoCalculado;
    }

    public Integer getTotalNeto() {
        return totalNeto;
    }

    public void setTotalNeto(Integer totalNeto) {
        this.totalNeto = totalNeto;
    }
}
