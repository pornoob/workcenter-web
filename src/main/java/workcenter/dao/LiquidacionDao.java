package workcenter.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import workcenter.entidades.*;

@Repository
public class LiquidacionDao {

    @PersistenceContext
    private EntityManager em;

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
}
