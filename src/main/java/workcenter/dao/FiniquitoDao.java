package workcenter.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import workcenter.entidades.Finiquito;

/**
 *
 * @author Claudio Olivares
 */
@Repository
public class FiniquitoDao {

    @PersistenceContext
    private EntityManager em;
    
    public void guardar(Finiquito finiquito) {
        if (finiquito.getId() != null) {
            em.persist(finiquito);
        } else {
            em.merge(finiquito);    
        }
    }
}
