package workcenter.presentacion.facturas;

import java.io.Serializable;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Empresa;
import workcenter.entidades.FactDetalleFactura;
import workcenter.entidades.FactFactura;

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

    public void init() {
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
}
