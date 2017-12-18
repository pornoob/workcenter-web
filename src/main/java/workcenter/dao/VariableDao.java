package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.TipoUnidad;
import workcenter.entidades.Variable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author colivares
 */
@Repository
public class VariableDao {

    @PersistenceContext
    EntityManager em;

    public Variable obtenerActual(String llave) {
        try {
            return (Variable) em.createNamedQuery("Variable.findActualByLlave").setParameter("llave", llave).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }    

    public TipoUnidad obtenerTipoUnidad(Integer id) {
        return em.find(TipoUnidad.class, id);
    }

    public List<TipoUnidad> obtenerTiposUnidad() {
        return em.createNamedQuery("TipoUnidad.findAll").getResultList();
    }
}
