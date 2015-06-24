package workcenter.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import workcenter.entidades.ContratoPersonal;
import workcenter.entidades.Empresa;
import workcenter.entidades.Personal;

@Repository
public class LiquidacionDao {

	 @PersistenceContext
	    private EntityManager em;
	 
	 public ContratoPersonal obtenerDatosContrato(Personal p){
		 StringBuilder sb = new StringBuilder();
	        sb.append("select cp.* from contratospersonal cp ");
	        sb.append("where numero = ");
	        sb.append("(select MAX(numero) from contratospersonal"
	        		+ " where rut=:rut)");
	        Query q = em.createNativeQuery(sb.toString(), ContratoPersonal.class);
	        q.setParameter("rut", p.getRut());
	        try {
	            return (ContratoPersonal) q.getSingleResult();
	        } catch (Exception e) {
	            return null;
	        }		 
	 }
}
