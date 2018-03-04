package workcenter.presentacion.admin_empresas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.OrigenDestino;
import workcenter.entidades.TipoProducto;
import workcenter.negocio.LogicaEmpresas;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

@Component
@Scope("view")
public class EditarTramoView implements Serializable {
    private List<OrigenDestino> origenDestinoList;
    private List<TipoProducto> productoList;

    @Autowired
    private LogicaEmpresas logicaEmpresas;

    @PostConstruct
    private void init() {
        origenDestinoList = logicaEmpresas.obtenerOrigenesDestinos();
        productoList = logicaEmpresas.obtenerProductos();
    }

    public List<OrigenDestino> getOrigenDestinoList() {
        return origenDestinoList;
    }

    public void setOrigenDestinoList(List<OrigenDestino> origenDestinoList) {
        this.origenDestinoList = origenDestinoList;
    }

    public List<TipoProducto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<TipoProducto> productoList) {
        this.productoList = productoList;
    }
}
