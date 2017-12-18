package workcenter.negocio.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.ProyectoDao;
import workcenter.entidades.Proyecto;

import java.util.List;

/**
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaProyecto {
    @Autowired
    ProyectoDao proyectoDao;

    @Transactional(readOnly = true)
    public List<Proyecto> obtenerTodos() {
        return proyectoDao.obtenerTodos();
    }

}
