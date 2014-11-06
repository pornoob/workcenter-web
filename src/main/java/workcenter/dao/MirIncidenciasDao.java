package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
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
        sql.append("select t.detalle from mir_trazabilidad_incidencia t ");
        sql.append("inner join mir_incidencia i on (t.id_incidencia=i.id) ");
        sql.append("where i.id=:incidencia ");
        sql.append("order by t.fecha asc limit 1 ");
        try {
            return (String) em.createNativeQuery(sql.toString()).setParameter("incidencia", i.getId()).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public MirEstadoIncidencia obtEstadoActual(MirIncidencia i) {
        StringBuilder sql = new StringBuilder();
        sql.append("select e.* from mir_trazabilidad_incidencia ti ");
        sql.append("inner join mir_estado_incidencia e on (ti.id_estado=e.id) ");
        sql.append("where ti.id_incidencia = :incidencia order by ti.fecha desc limit 1 ");
        return (MirEstadoIncidencia) em.createNativeQuery(sql.toString(), MirEstadoIncidencia.class)
                .setParameter("incidencia", i.getId()).getSingleResult();
    }

    public List<MirEstadoIncidencia> obtenerEstadosDisponibles(MirIncidencia incidencia, String tipo) {
        StringBuilder sql = new StringBuilder();
        sql.append("select e.* from mir_estado_incidencia e ");
        sql.append("inner join mir_transicion_estado te on (e.id = te.id_estado_destino) ");
        sql.append("inner join ");

        sql.append("(select e.id from mir_trazabilidad_incidencia ti ");
        sql.append("inner join mir_estado_incidencia e on (ti.id_estado=e.id) ");
        sql.append("where ti.id_incidencia = :incidencia order by ti.fecha desc limit 1) ea ");

        sql.append("on ea.id = te.id_estado_origen ");
        sql.append("where e.nombre like :tipo");

        return em.createNativeQuery(sql.toString(), MirEstadoIncidencia.class)
                .setParameter("incidencia", incidencia.getId())
                .setParameter("tipo", "%"+tipo+"%")
                .getResultList();
    }

    public List<MirIncidencia> obtenerIncidenciasPorEstado(Integer idEstado) {
        StringBuilder sql = new StringBuilder();
        sql.append("select i.* from mir_incidencia i ");
        sql.append("inner join mir_trazabilidad_incidencia ti on (i.id = ti.id_incidencia) ");
        sql.append("inner join ( ");
        sql.append("select id_incidencia, max(fecha) as fecha from mir_trazabilidad_incidencia group by id_incidencia ");
        sql.append(") t on (ti.id_incidencia = t.id_incidencia and ti.fecha = t.fecha) ");
        sql.append("where ti.id_estado = :estado ");
        return em.createNativeQuery(sql.toString(), MirIncidencia.class)
                .setParameter("estado", idEstado).getResultList();
    }

    public MirEstadoIncidencia obtenerEstadoIncidencia(Integer id) {
        return em.find(MirEstadoIncidencia.class, id);
    }

    public List<MirIncidencia> obtenerPorInformador(Integer rut) {
        return em.createNamedQuery("MirIncidencia.findByInformador")
                .setParameter("rut", rut)
                .getResultList();
    }

    public List<MirIncidencia> obtenerPorApoyo(Integer rut) {
        return em.createNamedQuery("MirIncidencia.findByApoyo")
                .setParameter("rut", rut)
                .getResultList();
    }
}
