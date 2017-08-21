package workcenter.negocio.equipos;

import java.util.List;
import java.util.Set;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.MmeMantencionesDao;
import workcenter.entidades.Equipo;
import workcenter.entidades.MmeMantencionMaquina;
import workcenter.entidades.MmeMantencionSemirremolque;
import workcenter.entidades.MmeMantencionTracto;
import workcenter.entidades.MmeTareaMaquina;
import workcenter.entidades.MmeTipoMantencion;
import workcenter.negocio.LogicaDocumentos;

/**
 * Created by claudio on 08-09-14.
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaMantenciones {

    @Autowired
    MmeMantencionesDao mmeMantencionesDao;

    @Autowired
    private LogicaDocumentos logicaDocumentos;

    @Transactional(readOnly = true)
    public List<MmeTipoMantencion> obtenerTiposMantencion() {
        return mmeMantencionesDao.obtenerTiposMantencion();
    }

    @Transactional(readOnly = false)
    public void guardar(MmeMantencionTracto m) {
        mmeMantencionesDao.guardar(m);
    }

    @Transactional(readOnly = false)
    public void guardar(MmeMantencionSemirremolque m) {
        mmeMantencionesDao.guardar(m);
    }

    @Transactional(readOnly = true)
    public List<MmeMantencionTracto> obtenerUltimasMantenciones() {
        return mmeMantencionesDao.obtenerUltimasMantenciones();
    }

    @Transactional(readOnly = true, noRollbackFor = SQLGrammarException.class)
    public MmeMantencionTracto obtenerUltimaPanne(Equipo e) {
        return mmeMantencionesDao.obtenerUltimaPanne(e);
    }

    @Transactional(readOnly = true)
    public Integer obtenerUltimoCiclo(Equipo tracto) {
        return mmeMantencionesDao.obtenerUltimoCiclo(tracto);
    }

    @Transactional(readOnly = true)
    public List<MmeMantencionSemirremolque> obtenerUltimasMantencionesSemiremolques() {
        return mmeMantencionesDao.obtenerUltimasMantencionesSemiremolques();
    }

    @Transactional(readOnly = true)
    public List<MmeMantencionTracto> obtenerMantenciones(Equipo e) {
        return mmeMantencionesDao.obtenerMantenciones(e);
    }

    @Transactional(readOnly = true)
    public List<MmeMantencionSemirremolque> obtenerMantencionesSemiremolques(Equipo e) {
        return mmeMantencionesDao.obtenerMantencionesSemiremolques(e);
    }

    @Transactional(readOnly = true)
    public List<MmeTareaMaquina> obtenerTiposMantencionMaquina() {
        return mmeMantencionesDao.obtenerTiposMantencionMaquina();
    }

    @Transactional(readOnly = false)
    public MmeMantencionMaquina guardar(MmeMantencionMaquina mantencionMaquina) {
        return mmeMantencionesDao.guardar(mantencionMaquina);
    }

    @Transactional(readOnly = true)
    public Set<MmeMantencionMaquina> obtenerUltimasMantencionesMaquina(Integer mes, Integer anio) {
        return mmeMantencionesDao.obtenerUltimasMantencionesMaquina(mes, anio);
    }

    @Transactional(readOnly = true)
    public MmeMantencionMaquina obtenerUltimaMantencionMaquina(Equipo maquina) {
        return mmeMantencionesDao.obtenerUltimaMantencionMaquina(maquina);
    }

    @Transactional(readOnly = true)
    public Set<MmeMantencionMaquina> obtenerMantencionesMaquina(Equipo e) {
        return mmeMantencionesDao.obtenerMantencionesMaquina(e);
    }

    @Transactional(readOnly = true)
    public MmeMantencionMaquina obtenerMantencionMaquinaPrevia(MmeMantencionMaquina mantencion) {
        return mmeMantencionesDao.obtenerMantencionMaquinaPrevia(mantencion);
    }
}
