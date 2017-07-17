package workcenter.dao;

import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import workcenter.entidades.FactProducto;

/**
 *
 * @author Claudio Olivares
 */
@Repository
public class StockDao {
    private static final Logger LOG = Logger.getLogger(StockDao.class.getName());

    @PersistenceContext
    private EntityManager em;

    public List<FactProducto> findAll() {
        Query q = em.createNamedQuery("FactProducto.findAll", FactProducto.class);
        return q.getResultList();
    }

    public FactProducto findByName(String name) {
        Query q = em.createNamedQuery("FactProducto.findByName", FactProducto.class);
        q.setParameter("name", name);
        q.setMaxResults(1);
        try {
            return (FactProducto) q.getSingleResult();
        } catch (NoResultException e) {
            LOG.finest(e.getMessage());
            return null;
        }
    }

    public void save(FactProducto product) {
        if (product.getProductoId() == null) {
            em.persist(product);
        } else {
            em.merge(product);
        }
    }

    public List<FactProductoBodega> findProductosBodega() {
        Query q = em.createNamedQuery("FactProductoBodega.findAll", FactProductoBodega.class);
        return q.getResultList();
    }

}
