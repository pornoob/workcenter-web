package workcenter.presentacion.bodega_taller;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.FactProducto;
import workcenter.negocio.facturas.LogicaStock;
import workcenter.util.components.FacesUtil;

/**
 *
 * @author Claudio Olivares
 */
@Component
@Scope("flow")
public class MantenedorBodegaTaller implements Serializable {

    private List<FactProducto> productos;
    private FactProducto producto;

    @Autowired
    private LogicaStock logicaStock;

    public void init() {
        productos = logicaStock.findAll();
    }
    
    public void addProducto() {
        FactProducto previous = logicaStock.findByName(producto.getNombre());
        if (previous != null) {
            FacesUtil.mostrarMensajeError("Error", "Ya se ha especificado un producto con este nombre");
            return;
        }
        logicaStock.save(producto);
    }

    public List<FactProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<FactProducto> productos) {
        this.productos = productos;
    }

    public FactProducto getProducto() {
        return producto;
    }

    public void setProducto(FactProducto producto) {
        this.producto = producto;
    }
}
