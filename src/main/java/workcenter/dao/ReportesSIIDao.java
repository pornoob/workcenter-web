package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.FactorActualizacionSII;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by claudio on 15-02-15.
 */
@Repository
public class ReportesSIIDao {
    @PersistenceContext
    private EntityManager em;


    public List<FactorActualizacionSII> obtenerFactoresActualizacionSII() {
        return em.createNamedQuery("FactorActualizacionSII.findAll").getResultList();
    }

    public void guardarFactorActualizacion(FactorActualizacionSII f) {
        if (f.getId() == null) em.persist(f);
        else em.merge(f);
    }
}
