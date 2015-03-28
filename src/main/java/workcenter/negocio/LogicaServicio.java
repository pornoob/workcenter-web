package workcenter.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import workcenter.dao.ServicioDao;
import workcenter.entidades.Servicio;

import java.util.List;

/**
 * Created by claudio on 28-03-15.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaServicio {
    @Autowired
    private ServicioDao servicioDao;

    public List<Servicio> obtenerTodos() {
        return servicioDao.obtenerTodos();
    }
}
