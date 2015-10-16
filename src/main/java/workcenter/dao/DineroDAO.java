package workcenter.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import workcenter.entidades.Dinero;

import java.util.List;

@Repository
public class DineroDAO {
    @PersistenceContext
    EntityManager em;
    
    public Boolean guardarDatosDineros(Dinero d) {
            try {
                em.persist(d);
                return true;
            }catch (Exception ex){
                ex.printStackTrace();
                return false;
            }
    }

    public List<Dinero> obtenerDineros() {
        return em.createNamedQuery("Dinero.findAll").getResultList();
    }
}