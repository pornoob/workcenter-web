package workcenter.dao;

import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import org.springframework.stereotype.Repository;

import workcenter.entidades.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class DineroDAO {
    @PersistenceContext
    EntityManager em;
    
    public Boolean guardarDatosDineros(Dinero d) {
        if (d.getId() == null){
            try {
                em.persist(d);
                return true;
            }catch (Exception ex){
                ex.printStackTrace();
                return false;
            }
        }else{
            try {
                em.merge(d);
                return true;
            }catch (Exception ex){
                ex.printStackTrace();
                return false;
            }
        }

    }

    public List<Dinero> obtenerDineros() {
        return em.createNamedQuery("Dinero.findAll").getResultList();
    }

    public List<Dinero> obtenerDinerosConDescuentos() {
        return em.createNamedQuery("Dinero.findDineroWithDescuento").getResultList();
    }

    public List<Dinero> obtenerDinerosFiltros(Personal personal, Concepto concepto, Date fechaDesde, Date fechaHasta){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Dinero> cqDinero = cb.createQuery(Dinero.class);
        Root<Dinero> dinero = cqDinero.from(Dinero.class);
        Join<Dinero,Personal> receptor = dinero.join(Dinero_.receptor);

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (personal != null){
            predicates.add(cb.equal(dinero.get(Dinero_.receptor),personal));
        }

        if (concepto != null){
            predicates.add(cb.equal(dinero.get(Dinero_.concepto),concepto));
        }

        if (fechaDesde != null && fechaHasta == null){
            predicates.add(cb.greaterThanOrEqualTo(dinero.get(Dinero_.fechaactivo),fechaDesde));
        }
        if (fechaDesde == null && fechaHasta != null){
            predicates.add(cb.lessThanOrEqualTo(dinero.get(Dinero_.fechaactivo), fechaHasta));
        }
        if (fechaDesde != null && fechaHasta != null){
            predicates.add(cb.between(dinero.get(Dinero_.fechaactivo),fechaDesde,fechaHasta));
        }

        cqDinero.select(dinero).where(predicates.toArray(new Predicate[]{})).orderBy(cb.asc(dinero.get(Dinero_.id)));
        TypedQuery tq = em.createQuery(cqDinero);
        return tq.getResultList();
    }
}