package workcenter.negocio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.MpaActividadDao;
import workcenter.dao.MpaPlanDao;
import workcenter.dao.MpaProgramaDao;
import workcenter.entidades.MpaActividad;
import workcenter.entidades.MpaEjecucionPlan;
import workcenter.entidades.MpaPlanPrograma;
import workcenter.entidades.MpaPrograma;
import workcenter.entidades.Personal;
import workcenter.util.dto.Mes;

/**
 *
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaProgramaActividades {
    @Autowired
    MpaProgramaDao mpaProgramaDao;
    
    @Autowired
    MpaActividadDao mpaActividadDao;
    
    @Autowired
    MpaPlanDao mpaPlanDao;

    @Transactional(readOnly = false)
    public void guardarPrograma(MpaPrograma p) {
        mpaProgramaDao.guardar(p);
    }

    @Transactional(readOnly = false)
    public void guardarActividad(MpaActividad actividad) {
        mpaActividadDao.guardar(actividad);
    }

    @Transactional(readOnly = true)
    public List<MpaPrograma> obtenerProgramas() {
        return mpaProgramaDao.obtenerProgramas();
    }

    @Transactional(readOnly = true)
    public List<MpaActividad> obtenerActividades() {
        return mpaActividadDao.obtenerTodas();
    }

    @Transactional(readOnly = true)
    public List<MpaActividad> obtenerActividades(MpaPrograma programa) {
        return mpaActividadDao.obtener(programa);
    }

    @Transactional(readOnly = false)
    public void guardarPlan(MpaPlanPrograma plan) {
        mpaProgramaDao.guardarPlan(plan);
    }

    @Transactional(readOnly = true)
    public List<MpaPlanPrograma> obtenerPlanes(MpaPrograma programa, Personal responsable) {
        return mpaProgramaDao.obtenerPlanes(programa, responsable);
    }

    @Transactional(readOnly = true)
    public List<MpaPlanPrograma> obtenerPlanes(MpaPrograma programa) {
        return mpaProgramaDao.obtenerPlanes(programa);
    }

    @Transactional(readOnly = true)
    public List<MpaPlanPrograma> obtenerPlanes(Personal responsable) {
        return mpaProgramaDao.obtenerPlanes(responsable);
    }
    
    @Transactional(readOnly = true)
    public List<MpaPlanPrograma> obtenerPlanes() {
        return mpaProgramaDao.obtenerPlanes();
    }

    @Transactional(readOnly = true)
    public Integer obtenerCantEjecuciones(MpaPlanPrograma plan, Mes mes, Integer responsable) {
        return mpaPlanDao.obtenerCantEjecuciones(plan, mes, responsable);
    }
    
    @Transactional(readOnly = true)
    public Integer obtenerCantEjecuciones(MpaPlanPrograma plan, Integer responsable) {
        return mpaPlanDao.obtenerCantEjecuciones(plan, responsable);
    }

    @Transactional(readOnly = false)
    public void guardarEjecucion(MpaEjecucionPlan ejecucion) {
        mpaPlanDao.guardarEjecucion(ejecucion);
    }

    @Transactional(readOnly = true)
    public List<MpaEjecucionPlan> obtenerEjecuciones(MpaPlanPrograma plan, Mes mes, Integer responsable) {
        return mpaPlanDao.obtenerEjecuciones(plan, mes, responsable);
    }
}
