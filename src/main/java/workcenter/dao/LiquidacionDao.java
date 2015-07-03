package workcenter.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import workcenter.entidades.*;
import workcenter.util.components.Constantes;

@Repository
public class LiquidacionDao {

    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private Constantes c;
    
    public ContratoPersonal obtenerDatosContrato(Personal p) {
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

    @SuppressWarnings("unchecked")
    public List<ValorPrevisionPersonal> obtenerDatosPrevision(Integer numeroContrato) {
        StringBuilder sb = new StringBuilder();
        sb.append("select vpp.* from valoresprevisionpersonal vpp ");
        sb.append("where contrato = :contrato ");
        Query q = em.createNativeQuery(sb.toString(), ValorPrevisionPersonal.class);
        q.setParameter("contrato", numeroContrato);
        try {
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public Variable obtenerValorUtm(Integer mes, Integer anio) {
        Query q = em.createNamedQuery("Variable.findByLlaveMesAnio");
        q.setParameter("mes", mes);
        q.setParameter("anio", anio);
        q.setParameter("llave", "utm");

        try {
            return (Variable) q.getSingleResult();
        } catch (Exception e) {
            Variable v = new Variable();
            v.setValor("0");
            return v;
        }
    }

    public List<ValorImpuestoUnico> obtenerValoresVigentesImpUnico() {
        return em.createNamedQuery("ValorImpuestoUnico.findVigentes").getResultList();
    }
    
    public void guardarDatosLiquidacion(Remuneracion liquidacion){
    	try {
            em.persist(liquidacion);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

	public List<BonoDescuentoPersonal> obtenerBonosDescuentos() {
		return em.createNamedQuery("BonoDescuentoPersonal.findAll").getResultList();		
	}

	@SuppressWarnings("unchecked")
	public Integer obtenerAnticipoSueldo(Integer idPers, String mes,
			Integer anio) {
		
			Query q = em.createNamedQuery("Dinero.findByConceptoFecha");
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd");
			try {
				q.setParameter("fechareal",  formatoDeFecha.parse((anio.toString()+"-"+mes+"-"+c.getDiasAnticipo()).trim()));
				} catch (ParseException ex) {
				ex.printStackTrace();
				}			
			q.setParameter("receptor", idPers);
			
            for (Dinero listaDineros :(List<Dinero>) q.getResultList()) {
				if(listaDineros.getConcepto().getId() == c.getConceptoAnticipo()){
					return listaDineros.getMonto();
				}
			}
            Dinero d = new Dinero();
            d.setMonto(0);
            return d.getMonto();
	}

	public List<Remuneracion> obtenerListaRemuneraciones() {
		  Query q = em.createNamedQuery("Remuneracion.findAllByGenerica");
	      q.setParameter("esGenerica", c.getGenericaAdministrativo());
	      return q.getResultList();
	}
}
