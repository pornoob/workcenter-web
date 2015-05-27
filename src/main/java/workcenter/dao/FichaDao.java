package workcenter.dao;

import java.util.List;

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
	    
	    public List<ViajesTortola> obtenerTodasFichaViajes(){
	    	return em.createNamedQuery("ViajesTortola.findAll" ).getResultList();
	    }
	    
	    public ViajesTortola obtenerGuia(Integer num_guia){
	    	try {
	    		return (ViajesTortola) em.createNamedQuery("ViajesTortola.findByGuia" ).setParameter("numGuia", num_guia).getSingleResult();	
			} catch (Exception e) {
				return null;
			}
	    	
	    }

}
