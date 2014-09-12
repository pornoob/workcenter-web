package workcenter.negocio.registros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.MiaInspeccionAvanzadaDao;
import workcenter.entidades.Equipo;
import workcenter.entidades.MiaInspeccionAvanzada;
import workcenter.entidades.MiaPregunta;
import workcenter.entidades.MiaRespuesta;

import java.util.Date;
import java.util.List;

/**
 * Created by colivares on 19-08-14.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaInspeccionAvanzada {
    @Autowired
    MiaInspeccionAvanzadaDao miaInspeccionAvanzadaDao;

    @Transactional(readOnly = true)
    public List<MiaPregunta> obtenerPreguntas() {
        return miaInspeccionAvanzadaDao.obtenerPreguntas();
    }

    @Transactional(readOnly = false)
    public void guardar(MiaInspeccionAvanzada inspeccionAvanzada, List<MiaRespuesta> respuestas) {
        miaInspeccionAvanzadaDao.guardar(inspeccionAvanzada, respuestas);
    }

    @Transactional(readOnly = true)
    public List<MiaInspeccionAvanzada> obtenerTodas() {
        return miaInspeccionAvanzadaDao.obtenerInspecciones();
    }

    @Transactional(readOnly = true)
    public List<MiaRespuesta> obtenerRespuestas(MiaInspeccionAvanzada i) {
        return miaInspeccionAvanzadaDao.obtenerRespuestas(i);
    }

    @Transactional(readOnly = true)
    public List<MiaInspeccionAvanzada> obtenerInspecciones(Date fecha, Equipo e) {
        return miaInspeccionAvanzadaDao.obtenerInspecciones(fecha, e);
    }

    @Transactional(readOnly = true)
    public Integer obtenerCantInspecciones(Date fecha, Equipo e) {
        return miaInspeccionAvanzadaDao.obtenerCantInspecciones(fecha, e);
    }

    @Transactional(readOnly = true)
    public List<MiaInspeccionAvanzada> obtenerInspecciones(Date fechaInf, Date fechaSup, Equipo e) {
        return miaInspeccionAvanzadaDao.obtenerInspecciones(fechaInf, fechaSup, e);
    }

    @Transactional(readOnly = true)
    public Integer obtenerCantInspecciones(Date fechaInf, Date fechaSup, Equipo e) {
        return miaInspeccionAvanzadaDao.obtenerCantInspecciones(fechaInf, fechaSup, e);
    }
}
