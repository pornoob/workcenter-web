package workcenter.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.PermisosDao;
import workcenter.entidades.Permiso;

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
}
