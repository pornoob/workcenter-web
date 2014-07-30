package workcenter.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import workcenter.entidades.ContratoPersonal;
import workcenter.entidades.Prevision;
import workcenter.entidades.PrevisionContrato;

/**
 *
 * @author colivares
 */
@Repository
public class PrevisionDao {
    @PersistenceContext
    EntityManager em;
    
    public List<Prevision> obtenerIsapres() {
        return obtenerPrevisionesPorTipo("salud");
    }
    
    public List<Prevision> obtenerAfps(){
        return obtenerPrevisionesPorTipo("afp");
    }
    
    private List<Prevision> obtenerPrevisionesPorTipo(String tipo) {
        return em.createNamedQuery("Prevision.findByTipo").setParameter("tipo", tipo).getResultList();
    }

    public List<PrevisionContrato> obtenerPrevisionesContrato(ContratoPersonal cp) {
        return em.createNamedQuery("PrevisionContrato.findByContrato").setParameter("contrato", cp).getResultList();
    }
}
