package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.MirIncidencia;
import workcenter.entidades.MirPrioridad;
import workcenter.entidades.MirSeveridad;
import workcenter.entidades.MirTrazabilidadIncidencia;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by claudio on 30-09-14.
 */
@Repository
public class MirIncidenciasDao {
    @PersistenceContext
    EntityManager em;

    public List<MirSeveridad> obtSeveridades() {
        return em.createNamedQuery("MirSeveridad.findAll").getResultList();
    }

    public List<MirPrioridad> obtPrioridades() {
        return em.createNamedQuery("MirPrioridad.findAll").getResultList();
    }

    public void guardarIncidencia(MirIncidencia incidencia) {
        if (incidencia.getId() == null) em.persist(incidencia);
        else em.merge(incidencia);
    }

    public void guardarTrazabilidad(MirTrazabilidadIncidencia trazabilidad) {
        if (trazabilidad.getId() == null) em.persist(trazabilidad);
        else em.merge(trazabilidad);
    }
}
