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
import workcenter.entidades.*;
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
    public List<MpaPlanPrograma> obtenerPlanes(MpaPrograma programa, MpaActividad actividad, Personal responsable, Integer anioSeleccionado, MpaContrato contrato) {
        return mpaProgramaDao.obtenerPlanes(programa, actividad, responsable, contrato, anioSeleccionado);
    }

    @Transactional(readOnly = true)
    public Integer obtenerCantEjecuciones(MpaPlanPrograma plan, Mes mes) {
        return mpaPlanDao.obtenerCantEjecuciones(plan, mes);
    }
    
    @Transactional(readOnly = true)
    public Integer obtenerCantEjecuciones(MpaPlanPrograma plan) {
        return mpaPlanDao.obtenerCantEjecuciones(plan);
    }

    @Transactional(readOnly = false)
    public void guardarEjecucion(MpaEjecucionPlan ejecucion) {
        mpaPlanDao.guardarEjecucion(ejecucion);
    }

    @Transactional(readOnly = true)
    public List<MpaEjecucionPlan> obtenerEjecuciones(MpaPlanPrograma plan, Mes mes) {
        return mpaPlanDao.obtenerEjecuciones(plan, mes);
    }

    @Transactional(readOnly = true)
    public float obtenerCumplimientoResponsable(MpaPlanPrograma plan, Mes mes) {
        return mpaPlanDao.obtenerCumplimientoResponsable(plan, mes);
    }

    @Transactional(readOnly = true)
    public float obtenerCumplimientoPrograma(MpaPlanPrograma plan, Mes mes) {
        return mpaPlanDao.obtenerCumplimientoPrograma(plan, mes);
    }

    @Transactional(readOnly = true)
    public List<MpaContrato> obtenerContratos() {
        return mpaPlanDao.obtenerContratos();
    }

    @Transactional(readOnly = true)
    public MpaPlanPrograma obtenerPlan(MpaPrograma programa, MpaActividad actividad, Personal responsable, MpaContrato contrato, Integer anio) {
        return mpaPlanDao.obtenerPlan(programa, actividad, responsable, contrato, anio);
    }

    public List<MpaContrato> obtenerContratos(Servicio servicio) {
        return mpaPlanDao.obtenerContratos(servicio);
    }
}
