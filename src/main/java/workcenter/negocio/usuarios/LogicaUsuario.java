package workcenter.negocio.usuarios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.UsuarioDao;
import workcenter.entidades.Proyecto;
import workcenter.util.dto.UsuarioDto;
import workcenter.util.pojo.Md5;

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
}
