package workcenter.presentacion.bodega_taller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.dao.FactProductoBodega;
import workcenter.entidades.FactProducto;
import workcenter.negocio.facturas.LogicaStock;
import workcenter.util.components.FacesUtil;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Claudio Olivares
 */
@Component
@Scope("flow")
public class MantenedorBodegaTaller implements Serializable {

    private static final long serialVersionUID = -7124335107068287607L;

    private List<FactProductoBodega> productos;
    private FactProducto producto;

    @Autowired
    private LogicaStock logicaStock;

    public void init() {
        productos = logicaStock.findProductosBodega();
    }

    public String prepareAddProducto () {
        producto = new FactProducto();
        return "add";
    }

    public void addProducto() {
        FactProducto previous = logicaStock.findByName(producto.getNombre());
        if (previous != null) {
            FacesUtil.mostrarMensajeError("Error", "Ya se ha especificado un producto con este nombre");
            return;
        }
        logicaStock.save(producto);
        producto = new FactProducto();
        productos = logicaStock.findProductosBodega();
        FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Producto agregado con éxito");
    }

    public List<FactProductoBodega> getProductos() {
        return productos;
    }

    public void setProductos(List<FactProductoBodega> productos) {
        this.productos = productos;
    }

    public FactProducto getProducto() {
        return producto;
    }

    public void setProducto(FactProducto producto) {
        this.producto = producto;
    }
}
