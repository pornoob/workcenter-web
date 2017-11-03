package workcenter.negocio.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.UsuarioDao;
import workcenter.entidades.Permiso;
import workcenter.entidades.Proyecto;
import workcenter.util.dto.UsuarioDto;
import workcenter.util.pojo.Md5;

import java.util.List;
import workcenter.entidades.Usuario;

/**
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaUsuario {
    
    @Autowired
    UsuarioDao usuarioDao;
    
    @Transactional(readOnly = true)
    public UsuarioDto logIn(String rut, String pass) {
        try {
            return usuarioDao.obtenerUsuario(Integer.valueOf(rut.split("-")[0]), Md5.hash(pass));
        } catch(Exception e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Proyecto> obtenerPermisos(Integer rut) {
        return usuarioDao.obtenerPermisos(rut);
    }

    @Transactional(readOnly = true)
    public UsuarioDto logInExterno(String usuario, String password) {
        try {
            return usuarioDao.obtenerUsuario(usuario, Md5.hash(password));
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Proyecto> obtenerPermisos(String usuario) {
        return usuarioDao.obtenerPermisos(usuario);
    }

    @Transactional(readOnly = false)
    public void cambiarClave(String usuario, String clave) {
        usuarioDao.cambiarClave(usuario, clave);
    }

    @Transactional(readOnly = false)
    public void cambiarClave(Integer rut, String clave) {
        usuarioDao.cambiarClave(rut, clave);
    }

    @Transactional(readOnly = true)
    public UsuarioDto obtenerUsuario(Integer rut) {
        return usuarioDao.obtenerUsuario(rut);
    }

    @Transactional(readOnly = true)
    public UsuarioDto obtenerUsuario(String user) {
        return usuarioDao.obtenerUsuario(user);
    }

    @Transactional(readOnly = true)
    public Permiso obtenerPermiso(Integer rut, String permiso, Integer nivel) {
        return usuarioDao.obtenerPermiso(rut, permiso, nivel);
    }

    @Transactional(readOnly = true)
    public Permiso obtenerPermiso(String usuario, String permiso, Integer nivel) {
        return usuarioDao.obtenerPermiso(usuario, permiso, nivel);
    }
}
