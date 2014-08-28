package workcenter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import workcenter.entidades.Equipo;
import workcenter.entidades.EquipoSancionado;
import workcenter.entidades.TipoEquipo;
import workcenter.util.components.Constantes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by colivares on 19-08-14.
 */
@Repository
public class EquipoDao {
    @PersistenceContext
    EntityManager em;

    @Autowired
    Constantes constantes;

    public List<Equipo> obtenerTodos() {
        return em.createNamedQuery("Equipo.findAll").getResultList();
    }

    public List<Equipo> obtenerTractos() {
        return em.createNamedQuery("Equipo.findByTipo")
                .setParameter("tipo", new TipoEquipo(constantes.getEquipoTipoTracto())).getResultList();
    }

    public List<Equipo> obtenerBateas() {
        return em.createNamedQuery("Equipo.findByTipo")
                .setParameter("tipo", new TipoEquipo(constantes.getEquipoTipoBatea())).getResultList();
    }

    public EquipoSancionado obtenerEquipoSancionado(Equipo e) {
        try {
            return (EquipoSancionado) em.createNamedQuery("EquipoSancionado.findByEquipo").setParameter("equipo", e).getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<TipoEquipo> obtenerTipos() {
        return em.createNamedQuery("TipoEquipo.findAll").getResultList();
    }
}