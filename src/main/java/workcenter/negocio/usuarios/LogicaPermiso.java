package workcenter.negocio.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.PermisosDao;
import workcenter.entidades.MuePermisoUsuario;
import workcenter.entidades.Permiso;

import java.util.List;

/**
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaPermiso {
    @Autowired
    PermisosDao permisoDao;
    
    @Transactional(readOnly = true)
    public Permiso obtenerPermiso(Integer id) {
        return permisoDao.obtener(id);
    }

    @Transactional(readOnly = true)
    public List<Permiso> obtPermisosUsuarioInterno(Long rut) {
        return permisoDao.obtenerTodos(rut);
    }

    @Transactional(readOnly = true)
    public List<MuePermisoUsuario> obtPermisosUsuarioExterno(String usuario) {
        return permisoDao.obtenerTodos(usuario);
    }
}
