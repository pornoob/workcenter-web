package workcenter.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import workcenter.entidades.MueUsuarioExterno;
import workcenter.entidades.Permiso;
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

    public Permiso obtenerPermiso(Integer rut, String permiso, Integer acceso) {
        StringBuilder sb = new StringBuilder();
        sb.append("select per.* from permisos per ");
        sb.append("inner join proyectos pro on (pro.tipo='app' and per.proyecto=pro.id) ");
        sb.append("where per.usuario = :rut ");
        sb.append("and pro.titulo = :permiso ");
        sb.append("and per.nivel = :acceso ");
        Query q = em.createNativeQuery(sb.toString(), Permiso.class);
        q.setParameter("rut", rut);
        q.setParameter("permiso", permiso);
        q.setParameter("acceso", acceso);
        try {
            return (Permiso) q.getSingleResult();
        } catch(Exception e) {
            return null;
        }
    }

    public UsuarioDto obtenerUsuario(String usuario, String password) {
        UsuarioDto u = null;
        try {
            MueUsuarioExterno ue = (MueUsuarioExterno) em.createNamedQuery("MueUsuarioExterno.findByUsuarioAndPass")
                    .setParameter("usuario", usuario)
                    .setParameter("pass", password).getSingleResult();
            u = new UsuarioDto();
            u.setUsuario(ue.getUsuario());
            u.setNombres(ue.getNombreCompleto());
            u.setExterno(true);
        } catch (Exception e) {
        }
        return u;
    }

    public UsuarioDto obtenerUsuario(String usuario) {
        UsuarioDto u = null;
        try {
            MueUsuarioExterno ue = (MueUsuarioExterno) em.createNamedQuery("MueUsuarioExterno.findByUsuario")
                    .setParameter("usuario", usuario).getSingleResult();
            u = new UsuarioDto();
            u.setUsuario(ue.getUsuario());
            u.setNombres(ue.getNombreCompleto());
            u.setExterno(true);
        } catch (Exception e) {
        }
        return u;
    }

    public List<Proyecto> obtenerPermisos(String usuario) {
        StringBuilder sb = new StringBuilder();
        sb.append("select pro.* from mue_permisos_usuarios per ");
        sb.append("inner join proyectos pro on (pro.tipo='app' and per.id_modulo=pro.id) ");
        sb.append("inner join mue_usuario_externo u on (u.id = per.id_usuario) ");
        sb.append("where u.usuario = :usuario");
        Query q = em.createNativeQuery(sb.toString(), Proyecto.class);
        q.setParameter("usuario", usuario);
        return q.getResultList();
    }
}
