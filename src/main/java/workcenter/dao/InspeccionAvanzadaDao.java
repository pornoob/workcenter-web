package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.MiaInspeccionAvanzada;
import workcenter.entidades.MiaPregunta;
import workcenter.entidades.MiaRespuesta;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by colivares on 20-08-14.
 */
@Repository
public class InspeccionAvanzadaDao {
    @PersistenceContext
    EntityManager em;

    public List<MiaPregunta> obtenerPreguntas() {
        return em.createNamedQuery("MiaPregunta.findAll").getResultList();
    }

    public void guardar(MiaInspeccionAvanzada inspeccionAvanzada, List<MiaRespuesta> respuestas) {
        if (inspeccionAvanzada.getId() == null) {
            em.persist(inspeccionAvanzada);
        } else {
            em.merge(inspeccionAvanzada);
        }
        List<MiaRespuesta> respuestasPersistidas = obtenerRespuestas(inspeccionAvanzada);
        if (respuestasPersistidas == null || respuestasPersistidas.isEmpty()) {
            for (MiaRespuesta r :respuestas) {
                em.persist(r);
            }
        } else {
            for (MiaRespuesta rp : respuestasPersistidas) {
                for (MiaRespuesta r : respuestas) {
                    if (!rp.getMiaPreguntasByIdPregunta().equals(r.getMiaPreguntasByIdPregunta())) continue;
                    rp.setCumple(r.getCumple());
                    em.merge(rp);
                }
            }
        }
    }

    public List<MiaRespuesta> obtenerRespuestas(MiaInspeccionAvanzada inspeccionAvanzada) {
        return em.createNamedQuery("MiaRespuesta.findByInspeccion")
                .setParameter("inspeccion", inspeccionAvanzada)
                .getResultList();
    }

    public List<MiaInspeccionAvanzada> obtenerInspecciones() {
        return em.createNamedQuery("MiaInspeccionAvanzada.findAll").getResultList();
    }
}
