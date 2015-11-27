package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.EstacionServicio;
import workcenter.entidades.GuiaPetroleo;
import workcenter.entidades.Vuelta;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by renholders on 26-11-2015.
 */
@Repository
public class GuiaDePetroleoDao {
    @PersistenceContext
    EntityManager em;

    public List<GuiaPetroleo> obtenerPetrolioPorOrden(Vuelta ordenDeCarga) {
        return em.createNamedQuery("GuiaPetroleo.findByOrdendecarga").setParameter("ordendecarga", ordenDeCarga.getOrdenDeCarga()).getResultList();
    }

    public List<EstacionServicio> obtenerEstacionesDeServicio(){
        return em.createNamedQuery("EstacionServicio.findAll").getResultList();
    }
}
