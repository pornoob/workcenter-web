package workcenter.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import workcenter.entidades.MpaActividad;
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

    public List<MpaPlanPrograma> obtenerPlanes(MpaPrograma programa, Personal responsable, Integer anioSeleccionado) {
        StringBuilder sql = new StringBuilder();
        
        sql.append("select t1.* from mpa_plan_programa t1 ");
        sql.append("inner join ( ");
        sql.append("    select max(fecha) as fecha,id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append("    from mpa_plan_programa ");
        sql.append("    group by id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append(") t2 ");
        sql.append("on t1.id_programa = t2.id_programa and t1.id_actividad=t2.id_actividad ");
        sql.append("and t1.rut_responsable=t2.rut_responsable and t1.rut_creador=t2.rut_creador ");
        sql.append("and t1.fecha=t2.fecha ");
        sql.append("where t1.id_programa=:programa ");
        sql.append("and t1.rut_responsable=:responsable ");
        sql.append("and t1.anio_vigencia=:anio ");
        
        return em.createNativeQuery(sql.toString(), MpaPlanPrograma.class)
                .setParameter("programa", programa.getId())
                .setParameter("responsable", responsable.getRut())
                .setParameter("anio", anioSeleccionado)
                .getResultList();
    }

    public List<MpaPlanPrograma> obtenerPlanes(MpaPrograma programa, MpaActividad actividad, Personal responsable, Integer anioSeleccionado) {
        StringBuilder sql = new StringBuilder();

        sql.append("select t1.* from mpa_plan_programa t1 ");
        sql.append("inner join ( ");
        sql.append("    select max(fecha) as fecha,id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append("    from mpa_plan_programa ");
        sql.append("    group by id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append(") t2 ");
        sql.append("on t1.id_programa = t2.id_programa and t1.id_actividad=t2.id_actividad ");
        sql.append("and t1.rut_responsable=t2.rut_responsable and t1.rut_creador=t2.rut_creador ");
        sql.append("and t1.fecha=t2.fecha ");
        sql.append("where t1.id_programa=:programa ");
        sql.append("and t1.id_actividad=:actividad ");
        sql.append("and t1.rut_responsable=:responsable ");
        sql.append("and t1.anio_vigencia=:anio ");

        return em.createNativeQuery(sql.toString(), MpaPlanPrograma.class)
                .setParameter("programa", programa.getId())
                .setParameter("actividad", actividad.getId())
                .setParameter("responsable", responsable.getRut())
                .setParameter("anio", anioSeleccionado)
                .getResultList();
    }

    public List<MpaPlanPrograma> obtenerPlanes(MpaPrograma programa, Integer anioSeleccionado) {
        StringBuilder sql = new StringBuilder();
        
        sql.append("select t1.* from mpa_plan_programa t1 ");
        sql.append("inner join ( ");
        sql.append("    select max(fecha) as fecha,id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append("    from mpa_plan_programa ");
        sql.append("    group by id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append(") t2 ");
        sql.append("on t1.id_programa = t2.id_programa and t1.id_actividad=t2.id_actividad ");
        sql.append("and t1.rut_responsable=t2.rut_responsable and t1.rut_creador=t2.rut_creador ");
        sql.append("and t1.fecha=t2.fecha ");
        sql.append("where t1.id_programa=:programa ");
        sql.append("and t1.anio_vigencia=:anio ");
        
        return em.createNativeQuery(sql.toString(), MpaPlanPrograma.class)
                .setParameter("programa", programa.getId())
                .setParameter("anio", anioSeleccionado)
                .getResultList();
    }

    public List<MpaPlanPrograma> obtenerPlanes(MpaPrograma programa, MpaActividad actividad, Integer anioSeleccionado) {
        StringBuilder sql = new StringBuilder();

        sql.append("select t1.* from mpa_plan_programa t1 ");
        sql.append("inner join ( ");
        sql.append("    select max(fecha) as fecha,id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append("    from mpa_plan_programa ");
        sql.append("    group by id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append(") t2 ");
        sql.append("on t1.id_programa = t2.id_programa and t1.id_actividad=t2.id_actividad ");
        sql.append("and t1.rut_responsable=t2.rut_responsable and t1.rut_creador=t2.rut_creador ");
        sql.append("and t1.fecha=t2.fecha ");
        sql.append("where t1.id_programa=:programa ");
        sql.append("and t1.id_actividad=:actividad ");
        sql.append("and t1.anio_vigencia=:anio ");

        return em.createNativeQuery(sql.toString(), MpaPlanPrograma.class)
                .setParameter("programa", programa.getId())
                .setParameter("actividad", actividad.getId())
                .setParameter("anio", anioSeleccionado)
                .getResultList();
    }

    public List<MpaPlanPrograma> obtenerPlanes(Personal responsable, Integer anioSeleccionado) {
        StringBuilder sql = new StringBuilder();
        
        sql.append("select t1.* from mpa_plan_programa t1 ");
        sql.append("inner join ( ");
        sql.append("    select max(fecha) as fecha,id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append("    from mpa_plan_programa ");
        sql.append("    group by id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append(") t2 ");
        sql.append("on t1.id_programa = t2.id_programa and t1.id_actividad=t2.id_actividad ");
        sql.append("and t1.rut_responsable=t2.rut_responsable and t1.rut_creador=t2.rut_creador ");
        sql.append("and t1.fecha=t2.fecha ");
        sql.append("where t1.rut_responsable=:responsable ");
        sql.append("and t1.anio_vigencia=:anio ");
        
        return em.createNativeQuery(sql.toString(), MpaPlanPrograma.class)
                .setParameter("responsable", responsable.getRut())
                .setParameter("anio", anioSeleccionado)
                .getResultList();
    }

    public List<MpaPlanPrograma> obtenerPlanes(MpaActividad actividad, Personal responsable, Integer anioSeleccionado) {
        StringBuilder sql = new StringBuilder();

        sql.append("select t1.* from mpa_plan_programa t1 ");
        sql.append("inner join ( ");
        sql.append("    select max(fecha) as fecha,id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append("    from mpa_plan_programa ");
        sql.append("    group by id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append(") t2 ");
        sql.append("on t1.id_programa = t2.id_programa and t1.id_actividad=t2.id_actividad ");
        sql.append("and t1.rut_responsable=t2.rut_responsable and t1.rut_creador=t2.rut_creador ");
        sql.append("and t1.fecha=t2.fecha ");
        sql.append("where t1.rut_responsable=:responsable ");
        sql.append("and t1.anio_vigencia=:anio ");
        sql.append("and t1.id_actividad=:actividad ");

        return em.createNativeQuery(sql.toString(), MpaPlanPrograma.class)
                .setParameter("actividad", actividad.getId())
                .setParameter("responsable", responsable.getRut())
                .setParameter("anio", anioSeleccionado)
                .getResultList();
    }
    
    public List<MpaPlanPrograma> obtenerPlanes(Integer anioSeleccionado) {
        StringBuilder sql = new StringBuilder();
        sql.append("select t1.* from mpa_plan_programa t1 ");
        sql.append("inner join ( ");
        sql.append("    select max(fecha) as fecha,id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append("    from mpa_plan_programa ");
        sql.append("    group by id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append(") t2 ");
        sql.append("on t1.id_programa = t2.id_programa and t1.id_actividad=t2.id_actividad ");
        sql.append("and t1.rut_responsable=t2.rut_responsable and t1.rut_creador=t2.rut_creador ");
        sql.append("and t1.fecha=t2.fecha ");
        sql.append("and t1.anio_vigencia=:anio ");
        return em.createNativeQuery(sql.toString(), MpaPlanPrograma.class).setParameter("anio", anioSeleccionado).getResultList();
    }

    public List<MpaPlanPrograma> obtenerPlanes(MpaActividad actividad, Integer anioSeleccionado) {
        StringBuilder sql = new StringBuilder();
        sql.append("select t1.* from mpa_plan_programa t1 ");
        sql.append("inner join ( ");
        sql.append("    select max(fecha) as fecha,id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append("    from mpa_plan_programa ");
        sql.append("    group by id_programa, id_actividad, rut_responsable, rut_creador ");
        sql.append(") t2 ");
        sql.append("on t1.id_programa = t2.id_programa and t1.id_actividad=t2.id_actividad ");
        sql.append("and t1.rut_responsable=t2.rut_responsable and t1.rut_creador=t2.rut_creador ");
        sql.append("and t1.fecha=t2.fecha ");
        sql.append("and t1.anio_vigencia=:anio ");
        sql.append("and t1.id_actividad=:actividad ");
        return em.createNativeQuery(sql.toString(), MpaPlanPrograma.class)
                .setParameter("anio", anioSeleccionado)
                .setParameter("actividad", actividad.getId())
                .getResultList();
    }
}
