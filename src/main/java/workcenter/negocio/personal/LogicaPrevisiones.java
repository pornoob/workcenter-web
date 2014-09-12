package workcenter.negocio.personal;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.PrevisionDao;
import workcenter.entidades.ContratoPersonal;
import workcenter.entidades.Prevision;
import workcenter.entidades.PrevisionContrato;

/**
 *
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaPrevisiones {
    @Autowired
    PrevisionDao previsionDao;
    
    @Transactional(readOnly = true)
    public List<Prevision> obtenerIsapres() {
        return previsionDao.obtenerIsapres();
    }
    
    @Transactional(readOnly = true)
    public List<Prevision> obtenerAfps() {
        return previsionDao.obtenerAfps();
    }

    @Transactional(readOnly = true)
    public List<PrevisionContrato> obtenerPrevisionesContrato(ContratoPersonal cp) {
        return previsionDao.obtenerPrevisionesContrato(cp);
    }
}
