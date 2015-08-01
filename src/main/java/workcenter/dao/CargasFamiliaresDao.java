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
	public List<ValoresCargasFamiliares> obtenerValoresCargasFamiliares() {
        Query q = em.createNamedQuery("ValoresCargasFamiliare.findAll" );     
        return q.getResultList();
    }

	public void guardarValoresCargasFamiliares(
			ValoresCargasFamiliares oBjValoresCargasFamiliares) {
		
		if (oBjValoresCargasFamiliares.getId() == 0) {
            em.persist(oBjValoresCargasFamiliares);
        } else {
            em.merge(oBjValoresCargasFamiliares);
        }	
		
	}

	public void eliminarValorCargaFamiliar(ValoresCargasFamiliares vCF) {
		ValoresCargasFamiliares valorEliminar = em.find(ValoresCargasFamiliares.class, vCF.getId());
			em.remove(valorEliminar);
	}

}
