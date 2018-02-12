package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.*;

import javax.persistence.*;
import java.util.List;

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

    public OrdenTrabajo findById(Long otId) {
        return em.find(OrdenTrabajo.class, otId);
    }

    public OrdenTrabajo findByIdAndStatus(Long id, Integer status) {
        Query q = em.createNamedQuery("OrdenTrabajo.findByIdAndStatus", OrdenTrabajo.class);
        q.setParameter("id", id);
        q.setParameter("status", status);
        try {
            return (OrdenTrabajo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public OrdenTrabajo findWithMantenimientos(Long id) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT ot FROM OrdenTrabajo ot ")
                .append("LEFT JOIN FETCH ot.solicitante ")
                .append("LEFT JOIN FETCH ot.repuestos ")
                .append("LEFT JOIN FETCH ot.asistentes ")
                .append("LEFT JOIN FETCH ot.trazabilidad ")
                .append("LEFT JOIN FETCH ot.mantencionTracto mt ")
                .append("LEFT JOIN FETCH ot.mantencionMaquina mm ")
                .append("LEFT JOIN FETCH ot.mantencionSemirremolque ms ")
                .append("LEFT JOIN FETCH mt.tracto t ")
                .append("LEFT JOIN FETCH mt.tipo ")
                .append("LEFT JOIN FETCH mt.mecanicoResponsable ")
                .append("LEFT JOIN FETCH mm.maquina m ")
                .append("LEFT JOIN FETCH mm.mecanicoResponsable ")
                .append("LEFT JOIN FETCH ms.semiRremolque s ")
                .append("LEFT JOIN FETCH ms.mecanicoResponsable ")
                .append("LEFT JOIN FETCH m.modelo mod ")
                .append("WHERE ot.id = :id");
        Query q = em.createQuery(jpql.toString(), OrdenTrabajo.class);
        q.setParameter("id", id);
        q.setMaxResults(1);

        try {
            OrdenTrabajo ot = (OrdenTrabajo) q.getSingleResult();
            return ot;
        } catch (Exception e) {
            return null;
        }
    }
}
