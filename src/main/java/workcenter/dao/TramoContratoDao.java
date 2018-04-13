package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.ContratoEmpresa;
import workcenter.entidades.TramoContrato;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by renholders on 25-11-2015.
 */
@Repository
public class TramoContratoDao {
    @PersistenceContext
    EntityManager em;

    public List<TramoContrato> obtenerTramoPorContrato(ContratoEmpresa contratoEmpresa){
        return em.createNamedQuery("TramoContrato.findByContrato", TramoContrato.class)
                .setParameter("contrato",contratoEmpresa).getResultList();
    }

    public List<TramoContrato> obtenerTramoPorContratoParaGuias(ContratoEmpresa contratoEmpresa){
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT DISTINCT tc FROM TramoContrato tc ")
                .append("INNER JOIN FETCH tc.tipoProducto ")
                .append("INNER JOIN FETCH tc.origen ")
                .append("INNER JOIN FETCH tc.destino ")
                .append("WHERE tc.contrato = :contrato");
        Query q = em.createQuery(jpql.toString(), TramoContrato.class);
        q.setParameter("contrato", contratoEmpresa);
        return q.getResultList();
    }
}