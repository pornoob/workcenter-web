package workcenter.negocio.incidencias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.MirIncidenciasDao;
import workcenter.entidades.MirIncidencia;
import workcenter.entidades.MirPrioridad;
import workcenter.entidades.MirSeveridad;
import workcenter.entidades.MirTrazabilidadIncidencia;

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
}
