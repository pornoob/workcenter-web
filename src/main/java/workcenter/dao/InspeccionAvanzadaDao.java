package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.Equipo;
import workcenter.entidades.MiaInspeccionAvanzada;
import workcenter.entidades.MiaPregunta;
import workcenter.entidades.MiaRespuesta;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Date;
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

    public List<MiaInspeccionAvanzada> obtenerInspecciones(Date fecha, Equipo e) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from mia_inspeccion_avanzada i ");
        sql.append("where i.fecha = :fecha ");
        sql.append("and (i.tracto = :patente or i.batea = :patente)");
        Query q = em.createNativeQuery(sql.toString(), MiaInspeccionAvanzada.class);
        q.setParameter("fecha", fecha);
        q.setParameter("patente", e.getPatente());
        return q.getResultList();
    }

    public Integer obtenerCantInspecciones(Date fecha, Equipo e) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) from mia_inspeccion_avanzada i ");
        sql.append("where i.fecha = :fecha ");
        sql.append("and (i.tracto = :patente or i.batea = :patente)");
        Query q = em.createNativeQuery(sql.toString());
        q.setParameter("fecha", fecha);
        q.setParameter("patente", e.getPatente());
        return ((BigInteger) q.getSingleResult()).intValue();
    }
}
