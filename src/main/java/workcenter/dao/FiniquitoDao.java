package workcenter.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import workcenter.entidades.Empresa;
import workcenter.entidades.Finiquito;
import workcenter.entidades.Personal;

/**
 *
 * @author Claudio Olivares
 */
@Repository
public class FiniquitoDao {

    @PersistenceContext
    private EntityManager em;
    
    public void guardar(Finiquito finiquito) {
        if (finiquito.getId() != null) {
            em.persist(finiquito);
        } else {
            em.merge(finiquito);    
        }
    }

    public List<Finiquito> obtenerFiniquitosTrabajador(Personal p, Integer anio) {
        Query q = em.createNamedQuery("Finiquito.findByPersonAndYear", Finiquito.class);
        q.setParameter("person", p);
        q.setParameter("year", anio);
        
        return q.getResultList();
    }
    
    public List<Finiquito> obtenerFiniquitosTrabajador(Personal p, Integer anio, Integer mes) {
        Query q = em.createNamedQuery("Finiquito.findByPersonMonthYear", Finiquito.class);
        q.setParameter("person", p);
        q.setParameter("year", anio);
        q.setParameter("month", mes);
        
        return q.getResultList();
    }

    public List<Finiquito> obtenerFiniquitosTrabajador(Empresa empleador, Integer anio) {
        Query q = em.createNamedQuery("Finiquito.findByFactoryAndYear", Finiquito.class);
        q.setParameter("factory", empleador);
        q.setParameter("year", anio);
        
        return q.getResultList();
    }

    public List<Finiquito> obtenerFiniquitosTrabajador(Integer mes, Integer anio) {
        Query q = em.createNamedQuery("Finiquito.findByMonthAndYear", Finiquito.class);
        q.setParameter("month", mes);
        q.setParameter("year", anio);
        
        return q.getResultList();
    }

    public void eliminar(Finiquito finiquito) {
        Finiquito finiquitoToRemove = em.find(Finiquito.class, finiquito.getId());
        em.remove(finiquitoToRemove);
    }
}
