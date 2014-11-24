package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.MfsPregunta;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by claudio on 14-11-14.
 */
@Repository
public class FatigaSomnolenciaDao {
    @PersistenceContext
    private EntityManager em;

    public List<MfsPregunta> obtenerPreguntasPorSeccion(Integer seccion) {
        return em.createNamedQuery("MfsPregunta.findBySeccion").setParameter("seccion", seccion).getResultList();
    }
}
