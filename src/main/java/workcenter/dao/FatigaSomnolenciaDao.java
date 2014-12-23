package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.MfsEncuesta;
import workcenter.entidades.MfsPregunta;
import workcenter.entidades.MfsRespuesta;
import workcenter.entidades.MfsTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
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

    public void guardar(MfsEncuesta encuesta) {
        if (encuesta.getId() == null) em.persist(encuesta);
        else em.merge(encuesta);
    }

    public List<MfsEncuesta> obtenerEncuestas(Date inicio, Date fin) {
        return em.createNamedQuery("MfsEncuesta.findByDateRange")
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .getResultList();
    }

    public List<MfsRespuesta> obtenerRespuestas(MfsEncuesta e) {
        return em.createNamedQuery("MfsRespuesta.findByEncuesta").setParameter("encuesta", e).getResultList();
    }

    public List<MfsTest> obtenerTests(MfsEncuesta e) {
        return em.createNamedQuery("MfsTest.findByEncuesta").setParameter("encuesta", e).getResultList();
    }
}
