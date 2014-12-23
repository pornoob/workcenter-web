package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
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
        EntityType<MpaPlanPrograma> plan_ = em.getMetamodel().entity(MpaPlanPrograma.class);
        Root<MpaPlanPrograma> plan = cq.from(MpaPlanPrograma.class);
        cq.select(plan);

        if (programa != null) cq.where(cb.equal(plan.get((javax.persistence.metamodel.SingularAttribute<? super MpaPlanPrograma, Object>) plan_.getAttribute("idPrograma")), programa));
        if (actividad != null) cq.where(cb.equal(plan.get((javax.persistence.metamodel.SingularAttribute<? super MpaPlanPrograma, Object>) plan_.getAttribute("actividad")), actividad));
        if (responsable != null) cq.where(cb.equal(plan.get((javax.persistence.metamodel.SingularAttribute<? super MpaPlanPrograma, Object>) plan_.getAttribute("responsable")), responsable));
        if (contrato != null) cq.where(cb.equal(plan.get((javax.persistence.metamodel.SingularAttribute<? super MpaPlanPrograma, Object>) plan_.getAttribute("contrato")), contrato));
        cq.where(cb.equal(plan.get((javax.persistence.metamodel.SingularAttribute<? super MpaPlanPrograma, Object>) plan_.getAttribute("anioVigencia")), anioSeleccionado));
        cq.orderBy(cb.asc(plan.get((javax.persistence.metamodel.SingularAttribute<? super MpaPlanPrograma, Object>) plan_.getAttribute("fecha"))));
        return em.createQuery(cq).getResultList();
    }
}
