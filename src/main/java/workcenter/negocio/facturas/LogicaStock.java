package workcenter.negocio.facturas;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
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

    public List<FactProducto> findAll() {
        return stockDao.findAll();
    }

    public FactProducto findByName(String name) {
        return stockDao.findByName(name);
    }

    public void save(FactProducto product) {
        stockDao.save(product);
    }
 
}
