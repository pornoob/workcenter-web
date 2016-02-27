package workcenter.dao.BonoDescuentoDao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.BonoDescuento;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by claudio on 27-02-16.
 */
@Repository
public class BonoDescuentoDao {
    @PersistenceContext
    private EntityManager em;

    public List<BonoDescuento> obtenerTodos() {
        return em.createNamedQuery("BonoDescuento.findAll").getResultList();
    }
}
