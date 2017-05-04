package workcenter.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import workcenter.entidades.Equipo;
import workcenter.entidades.MmeMantencionMaquina;
import workcenter.entidades.MmeMantencionSemiremolque;
import workcenter.entidades.MmeMantencionTracto;
import workcenter.entidades.MmeTareaMaquina;
import workcenter.entidades.MmeTipoMantencion;

/**
 * Created by claudio on 09-09-14.
 */
@Repository
public class MmeMantencionesDao {

    @PersistenceContext
    EntityManager em;

    public List<MmeTipoMantencion> obtenerTiposMantencion() {
        return em.createNamedQuery("MmeTipoMantencion.findAll").getResultList();
    }

    public void guardar(MmeMantencionSemiremolque m) {
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
        sql.append("select m.id, m.id_tipo, m.fecha as fecha, m.mecanico_responsable, m.equipo, m.kilometraje, m.ciclo ");
        sql.append("from mme_mantenciones_tractos m ");
        sql.append("inner join (");
        sql.append("select max(fecha) as fecha,equipo from mme_mantenciones_tractos group by 2");
        sql.append(") u on m.fecha = u.fecha and m.equipo = u.equipo ");
        sql.append("where m.id_tipo is not null ");
        sql.append("order by u.fecha desc");
        return em.createNativeQuery(sql.toString(), MmeMantencionTracto.class).getResultList();
    }

    public MmeMantencionTracto obtenerUltimaPanne(Equipo e) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ( ");
        sql.append("select m.id, m.id_tipo, max(m.fecha) as fecha, m.mecanico_responsable, m.equipo, m.kilometraje, m.ciclo ");
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

    public List<MmeMantencionSemiremolque> obtenerUltimasMantencionesSemiremolques() {
        StringBuilder sql = new StringBuilder();
        sql.append("select m.id, m.criterio_siguiente, m.equipo, max(m.fecha) as fecha, m.mecanico_responsable ");
        sql.append("from mme_mantenciones_semiremolque m group by m.equipo order by fecha desc ");
        return em.createNativeQuery(sql.toString(), MmeMantencionSemiremolque.class).getResultList();
    }

    public List<MmeMantencionTracto> obtenerMantenciones(Equipo e) {
        return em.createNamedQuery("MmeMantencionTracto.findByTracto").setParameter("tracto", e).getResultList();
    }

    public List<MmeMantencionSemiremolque> obtenerMantencionesSemiremolques(Equipo e) {
        return em.createNamedQuery("MmeMantencionSemiremolque.findBySemiremolque").setParameter("semiremolque", e).getResultList();
    }

    public List<MmeMantencionMaquina> obtenerUltimasMantencionesMaquina() {
        return em.createNamedQuery("MmeMantencionMaquina.findAll", MmeMantencionMaquina.class).getResultList();
    }

    public List<MmeTareaMaquina> obtenerTiposMantencionMaquina() {
        return em.createNamedQuery("MmeTareaMaquina.findAll", MmeTareaMaquina.class).getResultList();
    }

    public void guardar(MmeMantencionMaquina m) {
        if (m.getId() == null) {
            em.persist(m);
        } else {
            em.merge(m);
        }
    }

    public MmeMantencionMaquina obtenerUltimaMantencionMaquina(Equipo maquina) {
        Query q = em.createNamedQuery("MmeMantencionMaquina.findLastByMaquina", MmeMantencionMaquina.class);
        q.setParameter("maquina", maquina);
        q.setMaxResults(1);

        try {
            return (MmeMantencionMaquina) q.getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }

    }
}
