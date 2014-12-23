package workcenter.negocio.registros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.FatigaSomnolenciaDao;
import workcenter.entidades.MfsEncuesta;
import workcenter.entidades.MfsPregunta;
import workcenter.entidades.MfsRespuesta;
import workcenter.entidades.MfsTest;

import java.util.Date;
import java.util.List;

/**
 * Created by claudio on 14-11-14.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaFatigaSomnolencia {
    @Autowired
    private FatigaSomnolenciaDao fatigaSomnolenciaDao;

    @Transactional(readOnly = true)
    public List<MfsPregunta> obtenerPreguntasSeccion1() {
        return fatigaSomnolenciaDao.obtenerPreguntasPorSeccion(1);
    }

    @Transactional(readOnly = true)
    public List<MfsPregunta> obtenerPreguntasSeccion2() {
        return fatigaSomnolenciaDao.obtenerPreguntasPorSeccion(2);
    }

    @Transactional(readOnly = false)
    public void guardar(MfsEncuesta encuesta) {
        fatigaSomnolenciaDao.guardar(encuesta);
    }

    @Transactional(readOnly = true)
    public List<MfsEncuesta> obtenerEncuestas(Date inicio, Date fin) {
        return fatigaSomnolenciaDao.obtenerEncuestas(inicio, fin);
    }

    @Transactional(readOnly = true)
    public List<MfsRespuesta> obtenerRespuestas(MfsEncuesta e) {
        return fatigaSomnolenciaDao.obtenerRespuestas(e);
    }

    @Transactional(readOnly = true)
    public List<MfsTest> obtenerTests(MfsEncuesta e) {
        return fatigaSomnolenciaDao.obtenerTests(e);
    }
}
