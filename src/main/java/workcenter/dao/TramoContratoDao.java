package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.ContratoEmpresa;
import workcenter.entidades.TramoContrato;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by renholders on 25-11-2015.
 */
@Repository
public class TramoContratoDao {
    @PersistenceContext
    EntityManager em;

    public List<TramoContrato> obtenerTramoPorContrato(ContratoEmpresa contratoEmpresa){
        return em.createNamedQuery("TramoContrato.findByContrato").setParameter("contrato",contratoEmpresa).getResultList();
    }
}