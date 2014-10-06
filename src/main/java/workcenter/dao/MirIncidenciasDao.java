package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by claudio on 30-09-14.
 */
@Repository
public class MirIncidenciasDao {
    @PersistenceContext
    EntityManager em;

    public List<MirSeveridad> obtSeveridades() {
        return em.createNamedQuery("MirSeveridad.findAll").getResultList();
    }

    public List<MirPrioridad> obtPrioridades() {
        return em.createNamedQuery("MirPrioridad.findAll").getResultList();
    }

    public void guardarIncidencia(MirIncidencia incidencia) {
        if (incidencia.getId() == null) em.persist(incidencia);
        else em.merge(incidencia);
    }

    public void guardarTrazabilidad(MirTrazabilidadIncidencia trazabilidad) {
        if (trazabilidad.getId() == null) em.persist(trazabilidad);
        else em.merge(trazabilidad);
    }

    public List<MirApoyo> obtApoyos() {
        return em.createNamedQuery("MirApoyo.findAll").getResultList();
    }

    public MirApoyo obtSiguienteApoyo() {
        StringBuilder sql = new StringBuilder();
        sql.append("select a.* from mir_apoyos a where a.orden = (");
        sql.append("select coalesce(");
        sql.append("( select a.orden ");
        sql.append("from mir_incidencia i ");
        sql.append("inner join mir_apoyos a on (i.id_apoyo = a.id) ");
        sql.append("order by i.id desc limit 1 ) ");
        sql.append(",0)%(select count(a.id) from mir_apoyos a) + 1 as orden ");
        sql.append(')');
        return (MirApoyo) em.createNativeQuery(sql.toString(), MirApoyo.class).getSingleResult();
    }

    public MirEstadoIncidencia obtEstado(Integer id) {
        return em.find(MirEstadoIncidencia.class, id);
    }

    public List<MirIncidencia> obtTodas() {
        return em.createNamedQuery("MirIncidencia.findAll").getResultList();
    }

    public String obtDetalleInicial(MirIncidencia i) {
        StringBuilder sql = new StringBuilder();
        sql.append("select t.detalle_informador from mir_trazabilidad_incidencia t ");
        sql.append("inner join mir_incidencia i on (t.id_incidencia=i.id) ");
        sql.append("where i.id=:incidencia ");
        sql.append("order by t.fecha asc limit 1 ");
        return (String) em.createNativeQuery(sql.toString()).setParameter("incidencia", i.getId()).getSingleResult();
    }
}
