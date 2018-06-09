package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.Personal;
import workcenter.entidades.Vuelta;
import workcenter.entidades.Vuelta_;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT DISTINCT v FROM Vuelta v ")
                .append("INNER JOIN FETCH v.conductor ")
                .append("INNER JOIN FETCH v.tracto t ")
                .append("INNER JOIN FETCH t.duenio ")
                .append("INNER JOIN FETCH v.batea ")
                .append("INNER JOIN FETCH v.productosList pl ")
                .append("INNER JOIN FETCH pl.tramo tp ")
                .append("INNER JOIN FETCH tp.tipoProducto tp ")
                .append("WHERE v.id = :ordenDeCarga");
        Query q = em.createQuery(jpql.toString(), Vuelta.class);
        q.setParameter("ordenDeCarga", ordenConsulta);
        return (Vuelta) q.getSingleResult();
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

    public List<Vuelta> buscarConProductos(Date fechaDesde, Date fechaHasta, Personal conductor) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Vuelta.class);
        Root<Vuelta> vuelta = cq.from(Vuelta.class);
        vuelta.fetch(Vuelta_.productosList, JoinType.LEFT);

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
        cq.distinct(true);
        TypedQuery<Vuelta> query = em.createQuery(cq);
        return query.getResultList();
    }
}
