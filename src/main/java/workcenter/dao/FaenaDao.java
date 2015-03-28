package workcenter.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import workcenter.entidades.Faena;


@Repository
public class FaenaDao {
	@PersistenceContext
    private EntityManager em;

    public List<Faena> obtenerTodos() {
        Query q = em.createNamedQuery("Faena.findAll" );     
        return q.getResultList(); }

	public Faena obtenerFaena(String filtro) {
		
		Query q = em.createNamedQuery("Faena.findByFaena" );  
		q.setParameter("nombre", "%"+filtro.trim()+"%");
        return (Faena)q.getSingleResult();
        		}
		
	}
    
   


