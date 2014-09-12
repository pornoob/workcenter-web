package workcenter.negocio.equipos;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.MmeMantencionesDao;
import workcenter.entidades.Equipo;
import workcenter.entidades.MmeMantencionSemiremolque;
import workcenter.entidades.MmeMantencionTracto;
import workcenter.entidades.MmeTipoMantencion;

import java.util.List;

/**
 * Created by claudio on 08-09-14.
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaMantenciones {
    @Autowired
    MmeMantencionesDao mmeMantencionesDao;

    @Transactional(readOnly = true)
    public List<MmeTipoMantencion> obtenerTiposMantencion() {
        return mmeMantencionesDao.obtenerTiposMantencion();
    }

    @Transactional(readOnly = false)
    public void guardar(MmeMantencionTracto m) {
        mmeMantencionesDao.guardar(m);
    }

    @Transactional(readOnly = false)
    public void guardar(MmeMantencionSemiremolque m) {
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
    public List<MmeMantencionSemiremolque> obtenerUltimasMantencionesSemiremolques() {
        return mmeMantencionesDao.obtenerUltimasMantencionesSemiremolques();
    }

    @Transactional(readOnly = true)
    public List<MmeMantencionTracto> obtenerMantenciones(Equipo e) {
        return mmeMantencionesDao.obtenerMantenciones(e);
    }

    @Transactional(readOnly = true)
    public List<MmeMantencionSemiremolque> obtenerMantencionesSemiremolques(Equipo e) {
        return mmeMantencionesDao.obtenerMantencionesSemiremolques(e);
    }
}
