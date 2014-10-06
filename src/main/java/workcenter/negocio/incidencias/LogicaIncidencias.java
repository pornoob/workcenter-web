package workcenter.negocio.incidencias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.MirIncidenciasDao;
import workcenter.entidades.*;

import java.util.List;

/**
 * Created by claudio on 30-09-14.
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaIncidencias {
    @Autowired
    MirIncidenciasDao mirIncidenciasDao;

    @Transactional(readOnly = true)
    public List<MirSeveridad> obtSeveridades() {
        return mirIncidenciasDao.obtSeveridades();
    }

    @Transactional(readOnly = true)
    public List<MirPrioridad> obtPrioridades() {
        return mirIncidenciasDao.obtPrioridades();
    }

    @Transactional(readOnly = false)
    public void guardarIncidencia(MirIncidencia incidencia, MirTrazabilidadIncidencia trazabilidad) {
        mirIncidenciasDao.guardarIncidencia(incidencia);
        mirIncidenciasDao.guardarTrazabilidad(trazabilidad);
    }

    @Transactional(readOnly = true)
    public List<MirApoyo> obtApoyos() {
        return mirIncidenciasDao.obtApoyos();
    }

    @Transactional(readOnly = true)
    public MirApoyo obtSiguienteApoyo() {
        return mirIncidenciasDao.obtSiguienteApoyo();
    }

    @Transactional(readOnly = true)
    public MirEstadoIncidencia obtEstado(Integer id) {
        return mirIncidenciasDao.obtEstado(id);
    }

    @Transactional(readOnly = true)
    public List<MirIncidencia> obtTodas() {
        return mirIncidenciasDao.obtTodas();
    }

    @Transactional(readOnly = true)
    public String obtDetalleInicial(MirIncidencia i) {
        return mirIncidenciasDao.obtDetalleInicial(i);
    }
}
