package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.TipoDocPersonal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by claudio on 25-09-15.
 */
@Repository
public class TipoDocPersonalDao {

    @PersistenceContext
    private EntityManager em;

    public TipoDocPersonal obtenerPorID(Integer id) {
        return em.find(TipoDocPersonal.class, id);
    }
}
