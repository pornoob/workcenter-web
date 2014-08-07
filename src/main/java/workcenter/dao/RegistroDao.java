package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.MarActividad;
import workcenter.entidades.MarParticipantesAct;
import workcenter.entidades.MarRegistro;
import workcenter.entidades.MarTipoActividad;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by claudio on 04-08-14.
 */
@Repository
public class RegistroDao {
    @PersistenceContext
    EntityManager em;

    public List<MarActividad> obtenerActividadesSegunRegistro(Integer idRegistro) {
        StringBuilder query = new StringBuilder();
        query.append("select ma.* from mar_actividad ma ");
        query.append("inner join mar_tipo_actividad mta ");
        query.append("where mta.id_registro = :idRegistro ");
//        System.err.println("QUERY: "+query.toString().replace(":idRegistro", ""+idRegistro));
        Query q = em.createNativeQuery(query.toString(), MarActividad.class);
        q.setParameter("idRegistro", idRegistro);
        return q.getResultList();
    }

    public Integer obtenerAsistentesSegunActividad(MarActividad a) {
        StringBuilder query = new StringBuilder();
        query.append("select count(pa.id) from mar_participantes_act pa ");
        query.append("where pa.id_actividad = :idActividad ");
        Query q = em.createNativeQuery(query.toString());
        q.setParameter("idActividad", a.getId());
//        System.err.println("QUERY: "+query.toString().replace(":idActividad", ""+a.getId()));
        return ((BigInteger) q.getSingleResult()).intValue();
    }

    public List<MarTipoActividad> obtenerTiposActividades(Integer idRegistro) {
        return em.createNamedQuery("MarTipoActividad.findByIdRegistro").setParameter("idRegistro", idRegistro).getResultList();
    }

    public MarRegistro obtenerRegistro(Integer idRegistro) {
        return em.find(MarRegistro.class, idRegistro);
    }

    public void guardarTipoActividad(MarTipoActividad tipoActividad) {
        if (tipoActividad.getId() == null)
            em.persist(tipoActividad);
        else
            em.merge(tipoActividad);
    }

    public void guardarActividad(MarActividad actividad) {
        if (actividad.getId() == null)
            em.persist(actividad);
        else
            em.merge(actividad);
    }

    public List<MarParticipantesAct> obtenerParticipantes(MarActividad a) {
        return em.createNamedQuery("MarParticipantesAct.findByActividad").setParameter("actividad", a).getResultList();
    }
}
