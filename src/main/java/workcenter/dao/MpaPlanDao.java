package workcenter.dao;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import workcenter.entidades.MpaEjecucionPlan;
import workcenter.entidades.MpaPlanPrograma;
import workcenter.util.dto.Mes;

/**
 *
 * @author colivares
 */
@Repository
public class MpaPlanDao {

    @PersistenceContext
    EntityManager em;

    public Integer obtenerCantEjecuciones(MpaPlanPrograma plan, Mes mes, Integer rut) {
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
        q.setParameter("responsable", rut);

        try {
            return ((BigInteger) q.getSingleResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void guardarEjecucion(MpaEjecucionPlan ejecucion) {
        System.out.println("LLEGO");
        if (ejecucion.getId() == null) {
            em.persist(ejecucion);
        } else {
            em.merge(ejecucion);
        }
    }

    public Integer obtenerCantEjecuciones(MpaPlanPrograma plan, Integer rut) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) from mpa_ejecucion_plan plan ");
        sql.append("where plan.id_programa = :programa ");
        sql.append("and plan.id_actividad = :actividad ");
        sql.append("and plan.rut_responsable = :responsable ");

        Query q = em.createNativeQuery(sql.toString());
        q.setParameter("programa", plan.getIdPrograma().getId());
        q.setParameter("actividad", plan.getIdActividad().getId());
        q.setParameter("responsable", rut);

        try {
            return ((BigInteger) q.getSingleResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<MpaEjecucionPlan> obtenerEjecuciones(MpaPlanPrograma plan, Mes mes, Integer rut) {
        StringBuilder sql = new StringBuilder();
        sql.append("select plan.* from mpa_ejecucion_plan plan ");
        sql.append("where plan.id_programa = :programa ");
        sql.append("and plan.id_actividad = :actividad ");
        sql.append("and plan.id_mes = :mes ");
        sql.append("and plan.rut_responsable = :responsable ");
        
//        plan = em.find(MpaPlanPrograma.class, plan.getId());

        Query q = em.createNativeQuery(sql.toString(), MpaEjecucionPlan.class);
        q.setParameter("programa", plan.getIdPrograma().getId());
        q.setParameter("actividad", plan.getIdActividad().getId());
        q.setParameter("mes", Integer.parseInt(mes.getId()));
        q.setParameter("responsable", rut);
        
        return q.getResultList();
    }
}
