package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.FactFactura;
import workcenter.entidades.Stock;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

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

    public List<FactFactura> findAll() {
        Query query = em.createNamedQuery("FactFactura.findAllWithFactory", FactFactura.class);
        return query.getResultList();
    }
}
