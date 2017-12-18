
package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.*;
import workcenter.util.dto.Semana;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

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

    public List<ActividadDiaria> obtenerActividades(Servicio servicio, MpaContrato contrato, Semana semana) {
        em.createQuery("select ad from ActividadDiaria ad where ad.idServicio = :servicio and ad.contrato=:contrato and ad.fecha >= :fechaInicio and fecha<= :fechaTermino");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ActividadDiaria> cq = cb.createQuery(ActividadDiaria.class);
        Root<ActividadDiaria> actividad = cq.from(ActividadDiaria.class);
        cq.select(actividad);
        List<Predicate> filtros = new ArrayList<Predicate>();

        filtros.add(cb.equal(actividad.get(ActividadDiaria_.idServicio), servicio));
        filtros.add(cb.greaterThanOrEqualTo(actividad.get(ActividadDiaria_.fecha), semana.getDias()[0].getFecha()));
        filtros.add(cb.lessThanOrEqualTo(actividad.get(ActividadDiaria_.fecha), semana.getDias()[6].getFecha()));

        if (contrato != null) {
            filtros.add(cb.equal(actividad.get(ActividadDiaria_.contrato), contrato));
        }

        cq.where(filtros.toArray(new Predicate[]{}));
        return em.createQuery(cq).getResultList();
    }
    
}
