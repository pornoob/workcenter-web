package workcenter.negocio.taller;

import java.util.List;
import workcenter.dao.OtDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import workcenter.entidades.OrdenTrabajo;
import workcenter.entidades.SolicitanteOt;

/**
 *
 * @author Claudio Olivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaOt {
    
    @Autowired
    private OtDao otDao;

    public void create(OrdenTrabajo ot) {
        otDao.save(ot);
    }

    public List<OrdenTrabajo> findByStatus(Integer estadoOt) {
        return otDao.findByStatus(estadoOt);
    }

    public List<SolicitanteOt> findApplicants() {
        return otDao.findApplicants();
    }
}
