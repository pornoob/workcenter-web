package workcenter.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import workcenter.entidades.*;
import workcenter.util.dto.Mes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author colivares
 */
@Repository
public class MpaPlanDao {

    @PersistenceContext
    EntityManager em;

    public Integer obtenerCantEjecuciones(MpaPlanPrograma plan, Mes mes) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) from mpa_ejecucion_plan plan ");
        sql.append("where plan.id_programa = :programa ");
        sql.append("and plan.id_actividad = :actividad ");
        sql.append("and plan.id_mes = :mes ");
        sql.append("and plan.rut_responsable = :responsable ");

        Query q = em.createNativeQuery(sql.toString());
        q.setParameter("programa", plan.getIdPrograma().getId());
        q.setParameter("actividad", plan.getIdActividad().getId());
        q.setParameter("mes", Integer.parseInt(mes.getId()));
        q.setParameter("responsable", plan.getRutResponsable().getRut());

        try {
            return ((BigInteger) q.getSingleResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void guardarEjecucion(MpaEjecucionPlan ejecucion) {
        if (ejecucion.getId() == null) {
            em.persist(ejecucion);
        } else {
            em.merge(ejecucion);
        }
    }

    public Integer obtenerCantEjecuciones(MpaPlanPrograma plan) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) from mpa_ejecucion_plan plan ");
        sql.append("where plan.id_programa = :programa ");
        sql.append("and plan.id_actividad = :actividad ");
        sql.append("and plan.rut_responsable = :responsable ");

        Query q = em.createNativeQuery(sql.toString());
        q.setParameter("programa", plan.getIdPrograma().getId());
        q.setParameter("actividad", plan.getIdActividad().getId());
        q.setParameter("responsable", plan.getRutResponsable().getRut());

        try {
            return ((BigInteger) q.getSingleResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<MpaEjecucionPlan> obtenerEjecuciones(MpaPlanPrograma plan, Mes mes) {
        StringBuilder sql = new StringBuilder();
        sql.append("select plan.* from mpa_ejecucion_plan plan ");
        sql.append("where plan.id_programa = :programa ");
        sql.append("and plan.id_actividad = :actividad ");
        sql.append("and plan.id_mes = :mes ");
        sql.append("and plan.rut_responsable = :responsable ");

        Query q = em.createNativeQuery(sql.toString(), MpaEjecucionPlan.class);
        q.setParameter("programa", plan.getIdPrograma().getId());
        q.setParameter("actividad", plan.getIdActividad().getId());
        q.setParameter("mes", Integer.parseInt(mes.getId()));
        q.setParameter("responsable", plan.getRutResponsable().getRut());

        return q.getResultList();
    }

    public float obtenerCumplimientoResponsable(MpaPlanPrograma plan, Mes mes) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(mes.getNombre().toLowerCase()).append(" as plan ");
        sql.append(", coalesce(ejecucion.realizado, 0) ");
        sql.append("from mpa_valor_plan_programa valor ");
        sql.append("left join ( ");
        sql.append("    select count(*) as realizado ");
        sql.append("    , ep.id_programa, ep.id_actividad, ep.id_mes, ep.rut_responsable ");
        sql.append("    from mpa_ejecucion_plan ep ");
        sql.append("    where ep.id_programa = :programa ");
        sql.append("    and ep.id_actividad = :actividad ");
        sql.append("    and ep.id_mes = :mes ");
        sql.append("    and ep.rut_responsable = :responsable ");
        sql.append("    group by ep.id_programa, ep.id_actividad, ep.id_mes, ep.rut_responsable ");
        sql.append(") ejecucion on ");
        sql.append("ejecucion.id_programa = :programa ");
        sql.append("and ejecucion.id_actividad = :actividad ");
        sql.append("and ejecucion.id_mes = :mes ");
        sql.append("and ejecucion.rut_responsable = :responsable ");
        sql.append("where valor.id_plan = :plan");

        Session s = em.unwrap(Session.class);
        try {
            org.hibernate.Query q = s.createSQLQuery(sql.toString());
            q.setParameter("programa", plan.getIdPrograma().getId());
            q.setParameter("actividad", plan.getIdActividad().getId());
            q.setParameter("mes", Integer.parseInt(mes.getId()));
            q.setParameter("responsable", plan.getRutResponsable().getRut());
            q.setParameter("plan", plan.getId());

            List<Object[]> resultados = q.list();
            if (((Integer)resultados.get(0)[0]) == 0) {
                return 1;
            } else {
                return (float) ((BigInteger) resultados.get(0)[1]).intValue() / ((Integer) resultados.get(0)[0]);
            }
        } catch (Exception e) {
//            System.err.println(sql.toString()
//                    .replaceAll(":programa", ""+plan.getIdPrograma().getId())
//                    .replaceAll(":actividad", ""+plan.getIdActividad().getId())
//                    .replaceAll(":mes", ""+Integer.parseInt(mes.getId()))
//                    .replaceAll(":responsable", ""+plan.getRutResponsable().getRut())
//                    .replaceAll(":plan", ""+plan.getId())
//            );
            e.printStackTrace();
        }
        return 0;
    }

    public float obtenerCumplimientoPrograma(MpaPlanPrograma plan, Mes mes) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(mes.getNombre().toLowerCase()).append(" as plan ");
        sql.append(", coalesce(ejecucion.realizado, 0) ");
        sql.append("from mpa_valor_plan_programa valor ");
        sql.append("left join ( ");
        sql.append("    select count(*) as realizado ");
        sql.append("    , ep.id_programa, ep.id_actividad, ep.id_mes ");
        sql.append("    from mpa_ejecucion_plan ep ");
        sql.append("    where ep.id_programa = :programa ");
        sql.append("    and ep.id_actividad = :actividad ");
        sql.append("    and ep.id_mes <= :mes ");
        sql.append("    group by ep.id_programa, ep.id_actividad, ep.id_mes, ep.rut_responsable ");
        sql.append(") ejecucion on ");
        sql.append("ejecucion.id_programa = :programa ");
        sql.append("and ejecucion.id_actividad = :actividad ");
        sql.append("and ejecucion.id_mes <= :mes ");

        Session s = em.unwrap(Session.class);
        try {
            org.hibernate.Query q = s.createSQLQuery(sql.toString());
            q.setParameter("programa", plan.getIdPrograma().getId());
            q.setParameter("actividad", plan.getIdActividad().getId());
            q.setParameter("mes", Integer.parseInt(mes.getId()));

            List<Object[]> resultados = q.list();
            if (((Integer) resultados.get(0)[0]).intValue() > 0) {
                return (float) ((BigInteger) resultados.get(0)[1]).intValue() / ((Integer) resultados.get(0)[0]).intValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<MpaContrato> obtenerContratos() {
        return em.createNamedQuery("MpaContrato.findAll").getResultList();
    }

    public MpaPlanPrograma obtenerPlan(MpaPrograma programa, MpaActividad actividad, Personal responsable, MpaContrato contrato, Integer anio) {
        try {
            return (MpaPlanPrograma) em.createNamedQuery("MpaPlanPrograma.find")
                    .setParameter("programa", programa)
                    .setParameter("actividad", actividad)
                    .setParameter("responsable", responsable)
                    .setParameter("contrato", contrato)
                    .setParameter("anio", anio)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<MpaContrato> obtenerContratos(Servicio servicio) {
        return em.createNamedQuery("MpaContrato.findByServicio").setParameter("servicio", servicio).getResultList();
    }
}
