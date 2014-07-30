package workcenter.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import workcenter.entidades.Cargo;

/**
 *
 * @author colivares
 */
@Repository
public class CargoDao {
    @PersistenceContext
    EntityManager em;
    
    public List<Cargo> obtenerTodos() {
        return em.createNamedQuery("Cargo.findAll").getResultList();
    }
}
