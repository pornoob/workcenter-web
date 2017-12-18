package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.FactProducto;

import javax.persistence.*;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Claudio Olivares
 */
@Repository
public class StockDao extends MyDao {
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
        Query q = em.createQuery("SELECT fp FROM FactProductoBodega fp LEFT JOIN FETCH fp.producto ");
        return q.getResultList();
    }

}
