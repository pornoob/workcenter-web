package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.MpaActividad;
import workcenter.entidades.MpaPrograma;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author colivares
 */
@Repository
public class MpaActividadDao {

    @PersistenceContext
    EntityManager em;
    
    public void guardar(MpaActividad a) {
        if (a.getId() == null) {
            em.persist(a);
        } else {
            em.merge(a);
        }
    }

    public List<MpaActividad> obtenerTodas() {
        return em.createNamedQuery("MpaActividad.findAll").getResultList();
    }

    public List<MpaActividad> obtener(MpaPrograma programa) {
        return em.createNamedQuery("MpaActividad.findByPrograma").setParameter("programa", programa).getResultList();
    }
}
