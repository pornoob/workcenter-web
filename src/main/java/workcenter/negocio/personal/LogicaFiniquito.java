package workcenter.negocio.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.FiniquitoDao;
import workcenter.entidades.Finiquito;

/**
 *
 * @author Claudio Olivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaFiniquito {
    
    @Autowired
    private FiniquitoDao finiquitoDao;
    
    @Transactional(readOnly = false)
    public void guardar(Finiquito finiquito) {
        finiquitoDao.guardar(finiquito);
    }
}
