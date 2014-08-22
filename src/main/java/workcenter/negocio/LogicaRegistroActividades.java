package workcenter.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.RegistroDao;
import workcenter.entidades.MarActividad;
import workcenter.entidades.MarParticipantesAct;
import workcenter.entidades.MarRegistro;
import workcenter.entidades.MarTipoActividad;

import java.util.List;

/**
 * Created by colivares on 04-08-14.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaRegistroActividades {
    @Autowired
    private RegistroDao registroDao;

    @Transactional(readOnly = true)
    public List<MarActividad> obtenerActividadesSegunRegistro(Integer idRegistro) {
        return registroDao.obtenerActividadesSegunRegistro(idRegistro);
    }

    @Transactional(readOnly = true)
    public Integer obtenerAsistentesSegunActividad(MarActividad a) {
        return registroDao.obtenerAsistentesSegunActividad(a);
    }

    @Transactional(readOnly = true)
    public List<MarTipoActividad> obtenerTiposActividades(Integer idRegistro) {
        return registroDao.obtenerTiposActividades(idRegistro);
    }

    @Transactional(readOnly = true)
    public MarRegistro obtenerRegistro(Integer idRegistro) {
        return registroDao.obtenerRegistro(idRegistro);
    }

    @Transactional(readOnly = false)
    public void guardarTipoActividad(MarTipoActividad tipoActividad) {
        registroDao.guardarTipoActividad(tipoActividad);
    }

    @Transactional(readOnly = false)
    public void guardarActividad(MarActividad actividad) {
        registroDao.guardarActividad(actividad);
    }

    @Transactional(readOnly = true)
    public List<MarParticipantesAct> obtenerParticipantes(MarActividad a) {
        return registroDao.obtenerParticipantes(a);
    }

    @Transactional(readOnly = true)
    public List<MarTipoActividad> obtenerSubTiposActividades(MarTipoActividad tipoActividad) {
        return registroDao.obtenerSubTiposActividades(tipoActividad);
    }
}
