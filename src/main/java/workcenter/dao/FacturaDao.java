package workcenter.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import workcenter.entidades.FactFactura;
import workcenter.entidades.Stock;

/**
 *
 * @author Claudio Olivares
 */
@Repository
public class FacturaDao {
    @PersistenceContext
    private EntityManager em;

    public void save(FactFactura factura) {
        if (factura.getFacturaId() == null) {
            em.persist(factura);
        } else {
            em.merge(factura);
        }
    }

    public void save(Stock s) {
        if (s.getStockId() == null) {
            em.persist(s);
        } else {
            em.merge(s);
        }
    }
}
