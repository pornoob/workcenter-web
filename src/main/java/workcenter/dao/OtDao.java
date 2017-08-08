package workcenter.dao;

import java.util.List;
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
public class OtDao {

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
}
