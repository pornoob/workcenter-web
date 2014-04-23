package workcenter.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import workcenter.entidades.Proyecto;
import workcenter.entidades.Usuario;
import workcenter.util.dto.UsuarioDto;

/**
 * @author colivares
 */
@Repository
public class UsuarioDao implements Serializable {
    
    @PersistenceContext
    private EntityManager em;
    
    public UsuarioDto obtenerUsuario(Integer rut, String pass) {
        UsuarioDto usuario = null;
        Query q = em.createNamedQuery("Usuario.findByRutAndPassword", Usuario.class);
        q.setParameter("rut", rut);
        q.setParameter("password", pass);
        List<Usuario> usuarios = q.getResultList();
        if (!usuarios.isEmpty()) {
            usuario = new UsuarioDto();
            usuario.setRut(usuarios.get(0).getRut());
            usuario.setNombres(usuarios.get(0).getPersonal().getNombres());
            usuario.setApellidos(usuarios.get(0).getPersonal().getApellidos());
            usuario.setExterno(false);
        }
        return usuario;
    }
    
    public UsuarioDto obtenerUsuario(Integer rut) {
        UsuarioDto usuario = null;
        Query q = em.createNamedQuery("Usuario.findByRut", Usuario.class);
        q.setParameter("rut", rut);
        List<Usuario> usuarios = q.getResultList();
        if (!usuarios.isEmpty()) {
            usuario = new UsuarioDto();
            usuario.setRut(usuarios.get(0).getRut());
            usuario.setNombres(usuarios.get(0).getPersonal().getNombres());
            usuario.setApellidos(usuarios.get(0).getPersonal().getApellidos());
            usuario.setExterno(false);
        }
        return usuario;
    }

    public List<Proyecto> obtenerPermisos(Integer rut) {
        StringBuilder sb = new StringBuilder();
        sb.append("select pro.* from permisos per ");
        sb.append("inner join proyectos pro on (pro.tipo='app' and per.proyecto=pro.id) ");
        sb.append("where per.usuario = :rut");
        Query q = em.createNativeQuery(sb.toString(), Proyecto.class);
        q.setParameter("rut", rut);
        return q.getResultList();
    }
}
