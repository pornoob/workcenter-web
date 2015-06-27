package workcenter.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import workcenter.entidades.ContratoPersonal;
import workcenter.entidades.Personal;
import workcenter.entidades.ValorPrevisionPersonal;

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
}
