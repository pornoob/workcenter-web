package workcenter.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import workcenter.entidades.ValoresCargasFamiliares;

@Repository
public class CargasFamiliaresDao {
	
	@PersistenceContext
    private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<ValoresCargasFamiliares> obtenerCargasFamiliares() {
        Query q = em.createNamedQuery("ValoresCargasFamiliare.findAll" );     
        return q.getResultList();
    }

}
