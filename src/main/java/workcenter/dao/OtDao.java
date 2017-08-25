package workcenter.dao;

import java.util.List;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import workcenter.entidades.OrdenTrabajo;
import workcenter.entidades.SolicitanteOt;

/**
 *
 * @author Claudio Olivares
 */
@Repository
public class OtDao extends MyDao {

    @PersistenceContext
    private EntityManager em;

    public void save(OrdenTrabajo ot) {
        if (ot.getId() == null) {
            em.persist(ot);
        } else {
            em.merge(ot);
        }
    }

    public List<OrdenTrabajo> findByStatus(Integer status) {
        Query q = em.createNamedQuery("OrdenTrabajo.findByStatus", OrdenTrabajo.class);
        q.setParameter("status", status);
        return q.getResultList();
    }

    public List<SolicitanteOt> findApplicants() {
        Query q = em.createNamedQuery("SolicitanteOt.findAll", SolicitanteOt.class);
        return q.getResultList();
    }

    public OrdenTrabajo findById(Integer otId) {
        return em.find(OrdenTrabajo.class, otId);
    }

    public OrdenTrabajo findByIdAndStatus(Integer id, Integer status) {
        Query q = em.createNamedQuery("OrdenTrabajo.findByIdAndStatus", OrdenTrabajo.class);
        q.setParameter("id", q);
        q.setParameter("status", status);
        try {
            return (OrdenTrabajo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public OrdenTrabajo findWithMantenimientos(Integer id) {
        Query q = em.createNamedQuery("OrdenTrabajo.findById", OrdenTrabajo.class);
        q.setParameter("id", id);
        
        EntityGraph<OrdenTrabajo> graph = em.createEntityGraph(OrdenTrabajo.class);
        graph.addAttributeNodes("mantencionTracto");
        graph.addAttributeNodes("mantencionSemirremolque");
        graph.addAttributeNodes("mantencionMaquina");
        q.setHint(ENTITY_GRAPH_OVERRIDE_HINT, graph);
        
        try {
            return (OrdenTrabajo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
