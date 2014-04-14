package workcenter.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import workcenter.entidades.Personal;
import workcenter.entidades.Usuario;

/**
 * @author colivares
 */
@Repository
public class PersonalDao {
    
    @PersistenceContext
    private EntityManager em;

    public List<Personal> obtenerTodos() {
        Query q = em.createNamedQuery("Personal.findAll");
        return q.getResultList();
    }

    public Personal obtenerConAccesos(Integer rut) {
        Query q = em.createNamedQuery("Personal.findByRutWithAccess");
        q.setParameter("rut", rut);
        return (Personal) q.getSingleResult();
    }

    public List<Personal> obtenerTodosConUsuario() {
        Query q = em.createNamedQuery("Personal.findAllWithUser");
        return q.getResultList();
    }

    public void actualizarUsuario(Usuario usuario) {
        em.merge(usuario);
    }

    public void crearUsuario(Usuario usuario) {
        em.persist(usuario);
    }

    public List<Personal> obtenerSegunCargo(Integer cargo) {
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct p.* from personal p ");
        sb.append("inner join contratospersonal cp on (p.rut=cp.rut and cp.cargo=:cargo) ");
        sb.append("order by p.apellidos asc");
        Query q = em.createNativeQuery(sb.toString(), Personal.class);
        q.setParameter("cargo", cargo);
        return q.getResultList();
    }
}
