package workcenter.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import workcenter.entidades.MpaPlanPrograma;
import workcenter.entidades.MpaPrograma;
import workcenter.entidades.Personal;

/**
 *
 * @author colivares
 */
@Repository
public class MpaProgramaDao {

    @PersistenceContext
    EntityManager em;
    
    public void guardar(MpaPrograma p) {
        if (p.getId() == null) {
            em.persist(p);
        } else {
            em.merge(p);
        }
    }

    public List<MpaPrograma> obtenerProgramas() {
        return em.createNamedQuery("MpaPrograma.findAll").getResultList();
    }

    public void guardarPlan(MpaPlanPrograma plan) {
        if (plan.getId() == null) {
            em.persist(plan);
        } else {
            em.merge(plan);
        }
    }

    public List<MpaPlanPrograma> obtenerPlanes(MpaPrograma programa, Personal responsable) {
        StringBuilder sql = new StringBuilder();
        sql.append("select max(fecha) as fecha, id, id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append("from mpa_plan_programa ");
        sql.append("where id_programa=:programa ");
        sql.append("and rut_responsable=:responsable ");
        sql.append("group by 3,4,5,6 ");
        return em.createNativeQuery(sql.toString(), MpaPlanPrograma.class)
                .setParameter("programa", programa.getId())
                .setParameter("responsable", responsable.getRut())
                .getResultList();
    }

    public List<MpaPlanPrograma> obtenerPlanes(MpaPrograma programa) {
        StringBuilder sql = new StringBuilder();
        sql.append("select max(fecha) as fecha, id, id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append("from mpa_plan_programa ");
        sql.append("where id_programa=:programa ");
        sql.append("group by 3,4,5,6 ");
        return em.createNativeQuery(sql.toString(), MpaPlanPrograma.class)
                .setParameter("programa", programa.getId())
                .getResultList();
    }

    public List<MpaPlanPrograma> obtenerPlanes(Personal responsable) {
        StringBuilder sql = new StringBuilder();
        sql.append("select max(fecha) as fecha, id, id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append("from mpa_plan_programa ");
        sql.append("where rut_responsable=:responsable ");
        sql.append("group by 3,4,5,6 ");
        return em.createNativeQuery(sql.toString(), MpaPlanPrograma.class)
                .setParameter("responsable", responsable.getRut())
                .getResultList();
    }
    
    public List<MpaPlanPrograma> obtenerPlanes() {
        StringBuilder sql = new StringBuilder();
        sql.append("select max(fecha) as fecha, id, id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append("from mpa_plan_programa ");
        sql.append("group by 3,4,5,6 ");
        return em.createNativeQuery(sql.toString(), MpaPlanPrograma.class).getResultList();
    }
}
