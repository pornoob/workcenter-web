package workcenter.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.InspeccionAvanzadaDao;
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
    InspeccionAvanzadaDao inspeccionAvanzadaDao;

    @Transactional(readOnly = true)
    public List<MiaPregunta> obtenerPreguntas() {
        return inspeccionAvanzadaDao.obtenerPreguntas();
    }

    @Transactional(readOnly = false)
    public void guardar(MiaInspeccionAvanzada inspeccionAvanzada, List<MiaRespuesta> respuestas) {
        inspeccionAvanzadaDao.guardar(inspeccionAvanzada, respuestas);
    }

    @Transactional(readOnly = true)
    public List<MiaInspeccionAvanzada> obtenerTodas() {
        return inspeccionAvanzadaDao.obtenerInspecciones();
    }

    @Transactional(readOnly = true)
    public List<MiaRespuesta> obtenerRespuestas(MiaInspeccionAvanzada i) {
        return inspeccionAvanzadaDao.obtenerRespuestas(i);
    }

    @Transactional(readOnly = true)
    public List<MiaInspeccionAvanzada> obtenerInspecciones(Date fecha, Equipo e) {
        return inspeccionAvanzadaDao.obtenerInspecciones(fecha, e);
    }

    @Transactional(readOnly = true)
    public Integer obtenerCantInspecciones(Date fecha, Equipo e) {
        return inspeccionAvanzadaDao.obtenerCantInspecciones(fecha, e);
    }
}
