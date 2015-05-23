package workcenter.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import workcenter.entidades.ViajesTortola;

@Repository
public class FichaDao {
	
	 @PersistenceContext
	    private EntityManager em;
	 
	    public void crearFicha(ViajesTortola v) {
	        em.persist(v);
	    }

}
