package workcenter.negocio.facturas;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.FactProductoBodega;
import workcenter.dao.StockDao;
import workcenter.entidades.FactProducto;

/**
 *
 * @author Claudio Olivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaStock {
    
    @Autowired
    private StockDao stockDao;

    @Transactional(readOnly = true)
    public List<FactProducto> findAll() {
        return stockDao.findAll();
    }

    @Transactional(readOnly = true)
    public FactProducto findByName(String name) {
        return stockDao.findByName(name);
    }

    @Transactional(readOnly = false)
    public void save(FactProducto product) {
        stockDao.save(product);
    }

    @Transactional(readOnly = true)
    public List<FactProductoBodega> findProductosBodega() {
        return stockDao.findProductosBodega();
    }
 
}
