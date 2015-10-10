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
    
    public void guardarDatosDineros(Dinero d) {
            em.persist(d);
    }

    public List<Dinero> obtenerDineros() {
        return em.createNamedQuery("Dinero.findAll").getResultList();
    }
}