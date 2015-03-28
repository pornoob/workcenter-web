package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.Servicio;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by claudio on 28-03-15.
 */
@Repository
public class ServicioDao {
    @PersistenceContext
    private EntityManager em;

    public List<Servicio> obtenerTodos() {
        return em.createNamedQuery("Servicio.findAll").getResultList();
    }
}
