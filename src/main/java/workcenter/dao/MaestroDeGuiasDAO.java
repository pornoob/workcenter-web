package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.Personal;
import workcenter.entidades.Vuelta;
import workcenter.entidades.Vuelta_;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by renholders on 23-11-2015.
 */
@Repository
public class MaestroDeGuiasDAO {

    @PersistenceContext
    private EntityManager em;

    public Vuelta obtenerOrdendeCarga(Integer ordenConsulta) {
        return (Vuelta) em.createNamedQuery("Vuelta.findByOrdenDeCarga")
                .setParameter("ordenDeCarga", ordenConsulta)
                .getSingleResult();
    }

    public void guardarOrdenDeCarga(Vuelta ordenDeCarga) {
        if (ordenDeCarga.getId() == null) {
            em.persist(ordenDeCarga);
        } else {
            em.merge(ordenDeCarga);
        }
    }

    public List<Vuelta> buscar(Date fechaDesde, Date fechaHasta, Personal conductor) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Vuelta.class);
        Root<Vuelta> vuelta = cq.from(Vuelta.class);

        List<Predicate> condiciones = new ArrayList<>();

        if (fechaDesde != null) {
            condiciones.add(cb.greaterThanOrEqualTo(vuelta.get(Vuelta_.fecha), fechaDesde));
        }
        if (fechaHasta != null) {
            condiciones.add(cb.lessThanOrEqualTo(vuelta.get(Vuelta_.fecha), fechaHasta));
        }
        if (conductor != null) {
            condiciones.add(cb.equal(vuelta.get(Vuelta_.conductor), conductor));
        }
        if (!condiciones.isEmpty()) {
            cq.where(condiciones.toArray(new Predicate[condiciones.size()]));
        }
        TypedQuery<Vuelta> query = em.createQuery(cq);
        return query.getResultList();
    }
}
