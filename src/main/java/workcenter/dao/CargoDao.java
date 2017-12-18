package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.Cargo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
