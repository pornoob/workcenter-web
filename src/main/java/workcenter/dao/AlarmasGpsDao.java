package workcenter.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import workcenter.entidades.AlarmaGps;
import workcenter.entidades.GestionAlarmaGps;
import workcenter.entidades.Servicio;

/**
 *
 * @author colivares
 */
@Repository
public class AlarmasGpsDao {

    @PersistenceContext
    private EntityManager em;

    public List<String> obtenerConductores(Date dia, Date diaSiguente) {
        StringBuilder sql = new StringBuilder();
        sql.append("select t.chofer from (");
        sql.append("select chofer,count(*) from alarmas_gps where fecha>=:fechaInicial and fecha<:fechaTope group by chofer having count(*)>0 order by 2 desc ");
        sql.append(") t");
        Query q = em.createNativeQuery(sql.toString());
        q.setParameter("fechaInicial", dia);
        q.setParameter("fechaTope", diaSiguente);
        return q.getResultList();
    }

    public List<String> obtenerConductores(Date dia, Date diaSiguente, Servicio servicio) {
        StringBuilder sql = new StringBuilder();
        sql.append("select t.chofer from (");
        sql.append("select a.chofer,count(*) from alarmas_gps a ");
        sql.append("inner join servicio_ruta sr on (a.ruta like sr.patron) ");
        sql.append("where fecha>=:fechaInicial and fecha<:fechaTope and sr.id_servicio=:servicio ");
        sql.append("group by chofer having count(*)>0 order by 2 desc ");
//        sql.append("select chofer,count(*) from alarmas_gps where ruta=:ruta and fecha>=:fechaInicial and fecha<:fechaTope group by chofer having count(*)>0 order by 2 desc");
        sql.append(") t");
        Query q = em.createNativeQuery(sql.toString());
        q.setParameter("fechaInicial", dia);
        q.setParameter("fechaTope", diaSiguente);
        q.setParameter("servicio", servicio.getId());
        return q.getResultList();
    }

    public List<AlarmaGps> obtenerPorMes(String mes, String anio, Servicio servicio) {
        StringBuilder sql = new StringBuilder();
        sql.append("select a.* from alarmas_gps a ");
        sql.append("inner join servicio_ruta sr on (upper(a.ruta) like upper(sr.patron)) ");
        sql.append("where month(fecha)=:mes and year(fecha)=:anio and sr.id_servicio=:servicio order by fecha asc");
        Query q = em.createNativeQuery(sql.toString(), AlarmaGps.class);
        q.setParameter("mes", mes);
        q.setParameter("anio", anio);
        q.setParameter("servicio", servicio.getId());
        return q.getResultList();
    }

    public List<AlarmaGps> obtenerPorMes(String mes, String anio) {
        String sql = "select a.* from alarmas_gps a where month(a.fecha)=:mes and year(a.fecha)=:anio order by a.ruta asc, a.fecha asc";
        Query q = em.createNativeQuery(sql, AlarmaGps.class);
        q.setParameter("mes", mes);
        q.setParameter("anio", anio);
        return q.getResultList();
    }

    public void guardar(AlarmaGps a) {
        if (a.getId() != null) {
            em.merge(a);
        } else {
            em.persist(a);
        }
    }

    public List<String> obtenerRutas() {
        String sql = "select distinct a.ruta from alarmas_gps a order by a.ruta asc";
        Query q = em.createNativeQuery(sql);
        return q.getResultList();
    }

    public void guardarGestion(GestionAlarmaGps ga) {
        if (ga.getIdGestion() != null) {
            em.merge(ga);
        } else {
            em.persist(ga);
        }
    }

    public List<Servicio> obtenerServicios(Integer rut) {
        StringBuilder sql = new StringBuilder();
        sql.append("select s.* from servicio s ");
        sql.append("inner join usuario_servicio_ruta usr on (s.id = usr.id_servicio and usr.rut=:rut)");
        Query q = em.createNativeQuery(sql.toString(), Servicio.class);
        q.setParameter("rut", rut);
        return q.getResultList();
    }
}
