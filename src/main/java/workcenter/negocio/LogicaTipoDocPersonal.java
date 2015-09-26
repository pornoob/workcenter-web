package workcenter.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import workcenter.dao.TipoDocPersonalDao;
import workcenter.entidades.TipoDocPersonal;

/**
 * Created by claudio on 26-09-15.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaTipoDocPersonal {
    @Autowired
    private TipoDocPersonalDao tipoDocPersonalDao;

    public TipoDocPersonal obtenerPorID(Integer id) {
        return tipoDocPersonalDao.obtenerPorID(id);
    }
}
