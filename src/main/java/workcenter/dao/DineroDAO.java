package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class DineroDAO {
    @PersistenceContext
    EntityManager em;

    public Boolean guardarDatosDineros(Dinero d) {
        try {
            em.merge(d);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public List<Dinero> obtenerDineros() {
        return em.createNamedQuery("Dinero.findAll").getResultList();
    }

    public List<Dinero> obtenerDinerosConDescuentos() {
        return em.createNamedQuery("Dinero.findDineroWithDescuento").getResultList();
    }

    public List<Dinero> obtenerDinerosFiltros(Personal personal, Concepto concepto, Date fechaDesde, Date fechaHasta) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Dinero> cqDinero = cb.createQuery(Dinero.class);
        Root<Dinero> dinero = cqDinero.from(Dinero.class);
        dinero.fetch(Dinero_.receptor);
        dinero.fetch(Dinero_.concepto);
        dinero.fetch(Dinero_.ordendecarga, JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (personal != null) {
            predicates.add(cb.equal(dinero.get(Dinero_.receptor), personal));
        }

        if (concepto != null) {
            predicates.add(cb.equal(dinero.get(Dinero_.concepto), concepto));
        }

        if (fechaDesde != null && fechaHasta == null) {
            predicates.add(cb.greaterThanOrEqualTo(dinero.get(Dinero_.fechaactivo), fechaDesde));
        }
        if (fechaDesde == null && fechaHasta != null) {
            predicates.add(cb.lessThanOrEqualTo(dinero.get(Dinero_.fechaactivo), fechaHasta));
        }
        if (fechaDesde != null && fechaHasta != null) {
            predicates.add(cb.between(dinero.get(Dinero_.fechaactivo), fechaDesde, fechaHasta));
        }

        cqDinero.select(dinero).where(predicates.toArray(new Predicate[]{})).orderBy(cb.asc(dinero.get(Dinero_.id)));
        TypedQuery tq = em.createQuery(cqDinero);
        return tq.getResultList();
    }

    public List<Dinero> obtenerDinerosNoCancelados(Concepto conceptoParam) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT d FROM Dinero d ")
                .append("INNER JOIN FETCH d.receptor ")
                .append("WHERE NOT EXISTS ")
                .append("(SELECT p FROM PrestamoCancelado p WHERE p.prestamo = d.id) ")
                .append("AND d.concepto = :concepto ");
        Query query = em.createQuery(jpql.toString(), Dinero.class);
        query.setParameter("concepto", conceptoParam);
        return query.getResultList();
    }
}
