package workcenter.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import workcenter.entidades.Proyecto;

/**
 * @author colivares
 */
@Repository
public class ProyectoDao {
    
    @PersistenceContext
    private EntityManager em;

    public List<Proyecto> obtenerTodos() {
        Query q = em.createNamedQuery("Proyecto.findAll", Proyecto.class);
        return q.getResultList();
    }
}
