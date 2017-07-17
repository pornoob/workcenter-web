package workcenter.negocio.facturas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.FacturaDao;
import workcenter.entidades.FactDetalleFactura;
import workcenter.entidades.FactFactura;
import workcenter.entidades.Stock;

/**
 *
 * @author Claudio Olivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaFactura {
    @Autowired
    private FacturaDao facturaDao;

    @Transactional(readOnly = false)
    public void save(FactFactura factura) {
        facturaDao.save(factura);
        for (FactDetalleFactura item : factura.getItems()) {
            Stock s = new Stock();
            s.setDetalle(item);
            s.setProducto(item.getProducto());
            s.setCantidad(item.getCantidad());
            facturaDao.save(s);
        }
        
    }
}
