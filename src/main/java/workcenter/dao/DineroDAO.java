package workcenter.dao;

import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import workcenter.entidades.*;

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
        if (personal != null){
            cqDinero.where(cb.equal(dinero.get(Dinero_.receptor).get(Personal_.rut),personal.getRut()));
        }

        cqDinero.select(dinero);
        TypedQuery tq = em.createQuery(cqDinero);
        return tq.getResultList();
    }
}