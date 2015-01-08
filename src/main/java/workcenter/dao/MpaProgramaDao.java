package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author colivares
 */
@Repository
public class MpaProgramaDao {

    @PersistenceContext
    EntityManager em;
    
    public void guardar(MpaPrograma p) {
        if (p.getId() == null) {
            em.persist(p);
        } else {
            em.merge(p);
        }
    }

    public List<MpaPrograma> obtenerProgramas() {
        return em.createNamedQuery("MpaPrograma.findAll").getResultList();
    }

    public void guardarPlan(MpaPlanPrograma plan) {
        if (plan.getId() == null) {
            em.persist(plan);
        } else {
            em.merge(plan);
        }
    }

    public List<MpaPlanPrograma> obtenerPlanes(MpaPrograma programa, MpaActividad actividad, Personal responsable, MpaContrato contrato, Integer anioSeleccionado) {
        // aca
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MpaPlanPrograma> cq = cb.createQuery(MpaPlanPrograma.class);
        Root<MpaPlanPrograma> plan = cq.from(MpaPlanPrograma.class);
        cq.select(plan);
        List<Predicate> filtros = new ArrayList<Predicate>();

        if (programa != null) {
            filtros.add(cb.equal(plan.get(MpaPlanPrograma_.idPrograma), programa));
        }
        if (actividad != null) {
            filtros.add(cb.equal(plan.get(MpaPlanPrograma_.idActividad), actividad));
        }
        if (responsable != null) {
            filtros.add(cb.equal(plan.get(MpaPlanPrograma_.rutResponsable), responsable));
        }

        if (contrato != null) {
            filtros.add(cb.equal(plan.get(MpaPlanPrograma_.contrato), contrato));
        }
        filtros.add(cb.equal(plan.get(MpaPlanPrograma_.anioVigencia), anioSeleccionado));

        cq.where(filtros.toArray(new Predicate[] {}));

        cq.orderBy(cb.asc(plan.get(MpaPlanPrograma_.fecha)));
        return em.createQuery(cq).getResultList();
    }
}
