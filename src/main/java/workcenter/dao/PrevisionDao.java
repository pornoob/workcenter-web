package workcenter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import workcenter.entidades.ContratoPersonal;
import workcenter.entidades.Prevision;
import workcenter.entidades.PrevisionContrato;
import workcenter.util.components.Constantes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 * @author colivares
 */
@Repository
public class PrevisionDao {
    @PersistenceContext
    EntityManager em;

    @Autowired
    private Constantes constantes;
    
    public List<Prevision> obtenerIsapres() {
        List<Prevision> previsiones = obtenerPrevisionesPorTipo("salud");
        Prevision fonasa = new Prevision(constantes.getFonasaId());
        previsiones.remove(fonasa);
        return previsiones;
    }
    
    public List<Prevision> obtenerAfps(){
        return obtenerPrevisionesPorTipo("afp");
    }
    
    private List<Prevision> obtenerPrevisionesPorTipo(String tipo) {
        return em.createNamedQuery("Prevision.findByTipo").setParameter("tipo", tipo).getResultList();
    }

    public List<PrevisionContrato> obtenerPrevisionesContrato(ContratoPersonal cp) {
        Query q = em.createQuery("SELECT pc FROM PrevisionContrato pc INNER JOIN FETCH pc.prevision p WHERE pc.contrato = :contrato ORDER BY pc.fechainicio DESC ");
        q.setParameter("contrato", cp);
        return q.getResultList();
    }
}
