package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.Empresa;
import workcenter.entidades.Personal;
import workcenter.entidades.Remuneracion;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author colivares
 */
@Repository
public class RemuneracionDao {

    @PersistenceContext
    EntityManager em;

    public List<Remuneracion> obtener(Personal c, Integer mes, Integer anio) {
        StringBuilder sb = new StringBuilder();
        sb.append("select r.* from maestroGuias r ");
        sb.append("where 1=1 ");

        if (c != null) {
            sb.append("and r.idPersonal = :rut ");
        }
        if (mes != null) {
            sb.append("and month(r.fechaLiquidacion) = :mes ");
        }
        if (anio != null) {
            sb.append("and year(r.fechaLiquidacion) = :anio ");
        }

        Query q = em.createNativeQuery(sb.toString(), Remuneracion.class);

        if (c != null) {
            q.setParameter("rut", c.getRut());
        }
        if (mes != null) {
            q.setParameter("mes", String.valueOf(mes));
        }
        if (anio != null) {
            q.setParameter("anio", String.valueOf(anio));
        }
        return q.getResultList();
    }

    public List<Remuneracion> obtener(Empresa e, Integer mes, Integer anio) {
        StringBuilder sb = new StringBuilder();
        sb.append("select r.* from maestroGuias r ");
        sb.append("where 1=1 ");

        if (e != null) {
            sb.append("and r.rutEmpleador = :rut ");
        }
        if (mes != null) {
            sb.append("and month(r.fechaLiquidacion) = :mes ");
        }
        if (anio != null) {
            sb.append("and year(r.fechaLiquidacion) = :anio ");
        }
        Query q = em.createNativeQuery(sb.toString(), Remuneracion.class);
        if (e != null) {
            q.setParameter("rut", e.getRut());
        }
        if (mes != null) {
            q.setParameter("mes", String.valueOf(mes));
        }
        if (anio != null) {
            q.setParameter("anio", String.valueOf(anio));
        }
        sb.append("order by r.fechaLiquidacion asc, r.idPersonal ");
//        System.out.println("Query: "+sb.toString());
        return q.getResultList();
    }

    public List<Remuneracion> obtenerTodas() {
        return em.createNamedQuery("Remuneracion.findAll").getResultList();
    }
}
