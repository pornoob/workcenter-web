package workcenter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import workcenter.entidades.*;
import workcenter.util.components.Constantes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LiquidacionDao {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private Constantes c;

    public ContratoPersonal obtenerDatosContrato(Personal p) {
        StringBuilder sb = new StringBuilder();
        sb.append("select cp.* from contratospersonal cp ");
        sb.append("where rut=:rut ");
        sb.append("ORDER BY fecha DESC ,numero DESC ");
        sb.append("LIMIT 1");
        Query q = em.createNativeQuery(sb.toString(), ContratoPersonal.class);
        q.setParameter("rut", p.getRut());
        try {
            return (ContratoPersonal) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<ValorPrevisionPersonal> obtenerDatosPrevision(ContratoPersonal cp) {
        Query q = em.createNamedQuery("ValorPrevisionPersonal.findByContrato");
        q.setParameter("contrato", cp);
        return q.getResultList();
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

    public Variable obtenerValorUf(Integer mes, Integer anio) {
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

    public void guardarDatosLiquidacion(Remuneracion liquidacion) {	
    	if (liquidacion.getIdMaestro() != null){    	
			em.merge(liquidacion);
    	}else{
            em.persist(liquidacion);
        }
    }

    public List<BonoDescuentoPersonal> obtenerBonosDescuentos() {
        return em.createNamedQuery("BonoDescuentoPersonal.findAll").getResultList();
    }

    @SuppressWarnings("unchecked")
    public Integer obtenerAnticipoSueldo(Integer idPers, String mes,
                                         Integer anio) {

        Query q = em.createNamedQuery("Dinero.findByConceptoFecha");
        q.setParameter("mes", Integer.parseInt(mes));
        q.setParameter("anio", anio);
        q.setParameter("receptor", idPers);
        /**se modifica el ciclo for ya que la persona puede tener mas de un anticipo en el mes registrado (caso especial),
        //por ende se debera acumular el monto para luego retornarlo**/
        Integer monto = 0;
        for (Dinero listaDineros : (List<Dinero>) q.getResultList()) {
            if (listaDineros.getConcepto().getId() == c.getConceptoAnticipo()) {
                monto = monto + listaDineros.getMonto();
            }
        }
        return monto;
    }

    public List<Remuneracion> obtenerListaRemuneraciones() {
        Query q = em.createNamedQuery("Remuneracion.findAllByGenerica");
        q.setParameter("esGenerica", c.getGenericaAdministrativo());
        return q.getResultList();
    }

    public List<BonoDescuentoPersonal> obtenerBonosFaltantes(Personal p) {
        Query q = em.createNamedQuery("BonoDescuento.findFaltantesByPersonal");
        q.setParameter("personal", p);
        List<BonoDescuento> bonosDescuentos = q.getResultList();

        List<BonoDescuentoPersonal> retorno = new ArrayList<BonoDescuentoPersonal>();
        for (BonoDescuento b : bonosDescuentos) {
            BonoDescuentoPersonal bdp = new BonoDescuentoPersonal();
            bdp.setIdPersonal(p);
            bdp.setIdBonodescuento(b);
            bdp.setMonto(BigInteger.valueOf(b.getMonto()));
            retorno.add(bdp);
        }
        return retorno;
    }
    
    public void eliminarBonosPersonal(List<BonoDescuentoPersonal> bdp){
    	
    	for (BonoDescuentoPersonal bonoDescuentoPersonal : bdp) {
    		BonoDescuentoPersonal b = em.find(BonoDescuentoPersonal.class, bonoDescuentoPersonal.getId());
    		em.remove(b);
		}
    }
}
