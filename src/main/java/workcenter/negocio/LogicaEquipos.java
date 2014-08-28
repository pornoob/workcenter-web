package workcenter.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.EquipoDao;
import workcenter.entidades.Equipo;
import workcenter.entidades.EquipoSancionado;
import workcenter.entidades.TipoEquipo;

import java.util.List;

/**
 * Created by colivares on 19-08-14.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaEquipos {
    @Autowired
    EquipoDao equipoDao;

    @Transactional(readOnly = true)
    public List<Equipo> obtenerTodos() {
        return equipoDao.obtenerTodos();
    }

    @Transactional(readOnly = true)
    public List<Equipo> obtenerTractos() {
        return equipoDao.obtenerTractos();
    }

    @Transactional(readOnly = true)
    public List<Equipo> obtenerBateas() {
        return equipoDao.obtenerBateas();
    }

    @Transactional(readOnly = true)
    public EquipoSancionado obtenerEquipoSancionado(Equipo e) {
        return equipoDao.obtenerEquipoSancionado(e);
    }

    @Transactional(readOnly = true)
    public List<TipoEquipo> obtenerTipos() {
        return equipoDao.obtenerTipos();
    }
}
