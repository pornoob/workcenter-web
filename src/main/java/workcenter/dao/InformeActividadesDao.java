
package workcenter.dao;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import workcenter.entidades.ActividadDiaria;
import workcenter.entidades.Servicio;
import workcenter.entidades.TipoActividadDiaria;
import workcenter.util.dto.Semana;

/**
 *
 * @author colivares
 */
@Repository
public class InformeActividadesDao {
    @PersistenceContext
    private EntityManager em;

    public List<TipoActividadDiaria> obtenerTiposActividades() {
        return em.createNamedQuery("TipoActividadDiaria.findAll").getResultList();
    }

    public void guardar(ActividadDiaria actividadDiaria) {
        if (actividadDiaria.getId() == null) {
            em.persist(actividadDiaria);
        } else {
            em.merge(actividadDiaria);
        }
    }

    public List<ActividadDiaria> obtenerActividades(Servicio servicio, Semana semana) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ad.* from actividad_diaria ad ");
        sql.append("inner join servicio s on (ad.id_servicio=s.id and ad.id_servicio = :idServicio) ");
        sql.append("where ad.fecha >= :fechaInicio and fecha<= :fechaTermino");
        Query q = em.createNativeQuery(sql.toString(), ActividadDiaria.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(sql.toString().replaceAll(":idServicio", String.valueOf(servicio.getId())).replaceAll(":fechaInicio", sdf.format(semana.getDias()[0].getFecha())).replaceAll(":fechaTermino", sdf.format(semana.getDias()[6].getFecha())));
        q.setParameter("idServicio", servicio.getId());
        q.setParameter("fechaInicio", semana.getDias()[0].getFecha());
        q.setParameter("fechaTermino", semana.getDias()[6].getFecha());
        return q.getResultList();
    }
    
}
