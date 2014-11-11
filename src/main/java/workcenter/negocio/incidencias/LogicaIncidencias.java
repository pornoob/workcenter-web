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

    @Transactional(readOnly = true)
    public MirEstadoIncidencia obtEstadoActual(MirIncidencia i) {
        return mirIncidenciasDao.obtEstadoActual(i);
    }

    @Transactional(readOnly = true)
    public List<MirEstadoIncidencia> obtenerEstadosDisponiblesInformador(MirIncidencia incidencia) {
        return mirIncidenciasDao.obtenerEstadosDisponibles(incidencia, "informador");
    }

    @Transactional(readOnly = true)
    public List<MirEstadoIncidencia> obtenerEstadosDisponiblesApoyo(MirIncidencia incidencia) {
        return mirIncidenciasDao.obtenerEstadosDisponibles(incidencia, "apoyo");
    }

    @Transactional(readOnly = true)
    public List<MirIncidencia> obtenerIncidenciasPorEstado(Integer idEstado) {
        return mirIncidenciasDao.obtenerIncidenciasPorEstado(idEstado);
    }

    @Transactional(readOnly = true)
    public List<MirIncidencia> obtPorInformador(Integer rut) {
        return mirIncidenciasDao.obtenerPorInformador(rut);
    }

    @Transactional(readOnly = true)
    public List<MirIncidencia> obtPorApoyo(Integer rut) {
        return mirIncidenciasDao.obtenerPorApoyo(rut);
    }

    @Transactional(readOnly = true)
    public MirSeveridad obtenerMaximaSeveridad() {
        return mirIncidenciasDao.obtenerSeveridad(3);
    }

    @Transactional(readOnly = true)
    public MirPrioridad obtenerMaximaPrioridad() {
        return mirIncidenciasDao.obtenerPriodidad(3);
    }

    @Transactional(readOnly = true)
    public MirSeveridad obtenerMedianaSeveridad() {
        return mirIncidenciasDao.obtenerSeveridad(2);
    }

    @Transactional(readOnly = true)
    public MirPrioridad obtenerMedianaPrioridad() {
        return mirIncidenciasDao.obtenerPriodidad(2);
    }

    public int calcularPesoIncidencia(MirIncidencia i) {
        int pesoDias = 5;
        try {
            switch (i.getSeveridad().getPeso() * i.getPrioridad().getPeso()) {
                case 1:
                    pesoDias = 5;
                    break;
                case 2:
                    pesoDias = 4;
                    break;
                case 3:
                    pesoDias = 3;
                    break;
                case 4:
                    pesoDias = 2;
                    break;
                case 6:
                    pesoDias = 1;
                    break;
                case 9:
                    pesoDias = 0;
                    break;
            }
        } catch (Exception e) {

        }
        return pesoDias;
    }

    @Transactional(readOnly = true)
    public MirIncidencia obtenerIncidencia(Integer id) {
        return mirIncidenciasDao.obtenerIncidencia(id);
    }

    @Transactional(readOnly = true)
    public MirTrazabilidadIncidencia obtenerTrazabilidadActual(MirIncidencia i) {
        return mirIncidenciasDao.obtenerTrazabilidadActual(i);
    }

    @Transactional(readOnly = true)
    public MirEstadoIncidencia obtenerEstadoIncidencia(Integer id) {
        return mirIncidenciasDao.obtenerEstadoIncidencia(id);
    }
}
