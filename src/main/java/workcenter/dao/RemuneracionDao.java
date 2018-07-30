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
import java.util.Map;

/**
 * @author colivares
 */
@Repository
public class RemuneracionDao {

    @PersistenceContext
    EntityManager em;

    public List<Remuneracion> obtener(Personal c, Integer mes, Integer anio) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Remuneracion.class);
        Root<Remuneracion> remuneracionRoot = cq.from(Remuneracion.class);
        remuneracionRoot.fetch(Remuneracion_.contrato, JoinType.LEFT);

        List<Predicate> condiciones = new ArrayList<>();

        if (c != null)
            condiciones.add(cb.equal(remuneracionRoot.get(Remuneracion_.idPersonal), c.getRut()));

        if (mes != null)
            condiciones.add(cb.equal(cb.function("MONTH", Integer.class, remuneracionRoot.get(Remuneracion_.fechaLiquidacion)), mes));

        if (anio != null)
            condiciones.add(cb.equal(cb.function("YEAR", Integer.class, remuneracionRoot.get(Remuneracion_.fechaLiquidacion)), anio));

        if (!condiciones.isEmpty())
            cq.where(condiciones.toArray(new Predicate[condiciones.size()]));

        TypedQuery<Remuneracion> query = em.createQuery(cq);
        return query.getResultList();
    }

    public List<Remuneracion> obtener(Empresa e, Integer mes, Integer anio) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Remuneracion.class);
        Root<Remuneracion> remuneracionRoot = cq.from(Remuneracion.class);
        remuneracionRoot.fetch(Remuneracion_.contrato, JoinType.LEFT);

        List<Predicate> condiciones = new ArrayList<>();

        if (e != null)
            condiciones.add(cb.equal(remuneracionRoot.get(Remuneracion_.rutEmpleador), e.getRut()));

        if (mes != null)
            condiciones.add(cb.equal(cb.function("MONTH", Integer.class, remuneracionRoot.get(Remuneracion_.fechaLiquidacion)), mes));

        if (anio != null)
            condiciones.add(cb.equal(cb.function("YEAR", Integer.class, remuneracionRoot.get(Remuneracion_.fechaLiquidacion)), anio));

        if (!condiciones.isEmpty())
            cq.where(condiciones.toArray(new Predicate[condiciones.size()]));

        TypedQuery<Remuneracion> query = em.createQuery(cq);
        return query.getResultList();
    }

    public List<Remuneracion> obtenerTodas() {
        return em.createNamedQuery("Remuneracion.findAll").getResultList();
    }
}
