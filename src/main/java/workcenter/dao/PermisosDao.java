package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.MuePermisoUsuario;
import workcenter.entidades.Permiso;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author colivares
 */
@Repository
public class PermisosDao {
    @PersistenceContext
    private EntityManager em;
    
    public void actualizar(List<Permiso> permisos) {
        for (Permiso p : permisos) {
            if (p.getId() != null) em.merge(p);
            else em.persist(p);
        }
    }
    
    public Permiso obtener(Integer id) {
        return em.find(Permiso.class, id);
    }

    public List<Permiso> obtenerTodos(Long rut) {
        return em.createNamedQuery("Permiso.findByRutWithProyecto").setParameter("rut", rut).getResultList();
    }

    public List<MuePermisoUsuario> obtenerTodos(String usuario) {
        return em.createNamedQuery("MuePermisoUsuario.findByUsuario").setParameter("usuario", usuario).getResultList();
    }
}
