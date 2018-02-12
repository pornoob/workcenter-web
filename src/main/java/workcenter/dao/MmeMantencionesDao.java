package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by claudio on 09-09-14.
 */
@Repository
public class MmeMantencionesDao extends MyDao {

    @PersistenceContext
    EntityManager em;

    public List<MmeTipoMantencion> obtenerTiposMantencion() {
        return em.createNamedQuery("MmeTipoMantencion.findAll").getResultList();
    }

    public void guardar(MmeMantencionSemirremolque m) {
        if (m.getId() == null) {
            em.persist(m);
        } else {
            em.merge(m);
        }
    }

    public void guardar(MmeMantencionTracto m) {
        if (m.getId() == null) {
            em.persist(m);
        } else {
            em.merge(m);
        }
    }

    public List<MmeMantencionTracto> obtenerUltimasMantenciones() {
        StringBuilder sql = new StringBuilder();
        sql.append("select m.id, m.id_tipo, m.fecha as fecha, m.mecanico_responsable, m.equipo, m.kilometraje, m.ciclo, m.ot_id ");
        sql.append("from mme_mantenciones_tractos m ");
        sql.append("inner join (");
        sql.append("select max(fecha) as fecha,equipo from mme_mantenciones_tractos group by 2");
        sql.append(") u on m.fecha = u.fecha and m.equipo = u.equipo ");
        sql.append("where m.id_tipo is not null ");
        sql.append("order by u.fecha desc");
        return em.createNativeQuery(sql.toString(), MmeMantencionTracto.class)
                .getResultList();
    }

    public MmeMantencionTracto obtenerUltimaPanne(Equipo e) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ( ");
        sql.append("select m.id, m.id_tipo, max(m.fecha) as fecha, m.mecanico_responsable, m.equipo, m.kilometraje, m.ciclo, m.ot_id ");
        sql.append("from mme_mantenciones_tractos m where m.id_tipo is null and m.equipo = :equipo ");
        sql.append("group by m.equipo order by m.id desc ");
        sql.append(") t group by equipo ");
        try {
            return (MmeMantencionTracto) em.createNativeQuery(sql.toString(), MmeMantencionTracto.class)
                    .setParameter("equipo", e.getPatente()).getSingleResult();
        } catch (NoResultException nr) {
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Integer obtenerUltimoCiclo(Equipo e) {
        String sql = "select ciclo from mme_mantenciones_tractos where equipo=:equipo and id_tipo is not null order by fecha desc limit 1";
        try {
            return (Integer) em.createNativeQuery(sql.toString())
                    .setParameter("equipo", e.getPatente()).getSingleResult();
        } catch (NoResultException nr) {
            return 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmeMantencionSemirremolque> obtenerUltimasMantencionesSemiremolques() {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT  ms FROM MmeMantencionSemirremolque ms ")
                .append("INNER JOIN FETCH ms.semiRremolque e ")
                .append("INNER JOIN FETCH e.tipo ")
                .append("WHERE EXISTS (")
                .append("    SELECT msMax.semiRremolque, max(msMax.fecha) FROM MmeMantencionSemirremolque msMax ")
                .append("    WHERE msMax.semiRremolque = ms.semiRremolque")
                .append("    GROUP BY msMax.semiRremolque ")
                .append("    HAVING max(msMax.fecha) = ms.fecha")
                .append(")");

        return em.createQuery(jpql.toString(), MmeMantencionSemirremolque.class)
                .getResultList();
    }

    public List<MmeMantencionTracto> obtenerMantenciones(Equipo e) {
        Query q = em.createNamedQuery("MmeMantencionTracto.findByTracto");
        EntityGraph<MmeMantencionTracto> graph = em.createEntityGraph(MmeMantencionTracto.class);
        graph.addSubgraph(MmeMantencionTracto_.tipo);
        graph.addSubgraph(MmeMantencionTracto_.mecanicoResponsable);
        q.setParameter("tracto", e);
        q.setHint(ENTITY_GRAPH_OVERRIDE_HINT, graph);
        return q.getResultList();
    }

    public List<MmeMantencionSemirremolque> obtenerMantencionesSemiremolques(Equipo e) {
        Query q = em.createNamedQuery("MmeMantencionSemirremolque.findBySemiremolque");
        EntityGraph<MmeMantencionSemirremolque> graph = em.createEntityGraph(MmeMantencionSemirremolque.class);
        graph.addSubgraph(MmeMantencionSemirremolque_.mecanicoResponsable);
        graph.addSubgraph(MmeMantencionSemirremolque_.semiRremolque);

        q.setParameter("semiremolque", e);
        q.setHint(ENTITY_GRAPH_OVERRIDE_HINT, graph);
        return q.getResultList();
    }

    public Set<MmeMantencionMaquina> obtenerUltimasMantencionesMaquina(Integer mes, Integer anio) {
        String jpql = "SELECT mm FROM MmeMantencionMaquina mm INNER JOIN FETCH mm.maquina m INNER JOIN FETCH m.tipo " +
                "LEFT JOIN FETCH mm.checkeoRealizado check LEFT JOIN FETCH check.tareaMaquina WHERE YEAR(mm.fecha) = :anio AND MONTH(mm.fecha) = :mes";
        Query q = em.createQuery(jpql, MmeMantencionMaquina.class)
                .setParameter("mes", mes)
                .setParameter("anio", anio);
        return new TreeSet<>(q.getResultList());
    }

    public List<MmeTareaMaquina> obtenerTiposMantencionMaquina() {
        return em.createNamedQuery("MmeTareaMaquina.findAll", MmeTareaMaquina.class).getResultList();
    }

    public MmeMantencionMaquina guardar(MmeMantencionMaquina m) {
        return em.merge(m);
    }

    public MmeMantencionMaquina obtenerUltimaMantencionMaquina(Equipo maquina) {
        Query q = em.createNamedQuery("MmeMantencionMaquina.findLastByMaquina", MmeMantencionMaquina.class);
        q.setParameter("maquina", maquina);
        q.setMaxResults(1);
        
        EntityGraph<MmeMantencionMaquina> graph = em.createEntityGraph(MmeMantencionMaquina.class);
        graph.addAttributeNodes("checkeoRealizado", "maquina");
        
        Subgraph<MmeCheckMaquina> checkGraph = graph.addSubgraph("checkeoRealizado", MmeCheckMaquina.class);
        checkGraph.addAttributeNodes("tareaMaquina");
        
        q.setHint(ENTITY_GRAPH_OVERRIDE_HINT, graph);

        try {
            return (MmeMantencionMaquina) q.getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }

    }

    public Set<MmeMantencionMaquina> obtenerMantencionesMaquina(Equipo maquina) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT DISTINCT m FROM MmeMantencionMaquina m ")
                .append("LEFT JOIN FETCH m.mecanicoResponsable ")
                .append("LEFT JOIN FETCH m.checkeoRealizado check ")
                .append("LEFT JOIN FETCH check.tareaMaquina tm ")
                .append("LEFT JOIN FETCH m.maquina maq ")
                .append("LEFT JOIN FETCH maq.modelo ")
                .append("WHERE maq = :maquina ORDER BY m.fecha DESC");
        Query q = em.createQuery(jpql.toString(), MmeMantencionMaquina.class);
        q.setParameter("maquina", maquina);

        return new TreeSet<>(q.getResultList());
    }

    public MmeMantencionMaquina obtenerMantencionMaquinaPrevia(MmeMantencionMaquina mantencion) {
        Query q = em.createNamedQuery("MmeMantencionMaquina.findPreviousByFechaAndMaquina", MmeMantencionMaquina.class);
        q.setParameter("fecha", mantencion.getFecha());
        q.setParameter("maquina", mantencion.getMaquina());
        q.setMaxResults(1);
        
        EntityGraph<MmeMantencionMaquina> graph = em.createEntityGraph(MmeMantencionMaquina.class);
        graph.addAttributeNodes("checkeoRealizado", "maquina");
        
        Subgraph<MmeCheckMaquina> checkGraph = graph.addSubgraph("checkeoRealizado", MmeCheckMaquina.class);
        checkGraph.addAttributeNodes("tareaMaquina");
        
        q.setHint(ENTITY_GRAPH_OVERRIDE_HINT, graph);

        try {
            return (MmeMantencionMaquina) q.getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }
    }
}
