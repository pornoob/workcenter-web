package workcenter.negocio.taller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.OtDao;
import workcenter.entidades.*;

import java.util.List;

/**
 *
 * @author Claudio Olivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaOt {
    
    @Autowired
    private OtDao otDao;

    @Transactional(readOnly = false)
    public void create(OrdenTrabajo ot, MmeMantencionTracto mantencionTracto) {
        otDao.save(ot);
    }
    
    @Transactional(readOnly = false)    
    public void create(OrdenTrabajo ot, MmeMantencionSemirremolque mantencionSemirremolque) {
        otDao.save(ot);
    }
    
    @Transactional(readOnly = false)    
    public void create(OrdenTrabajo ot, MmeMantencionMaquina mantencionMaquina) {
        otDao.save(ot);
    }
    
    @Transactional(readOnly = false)
    public void create(OrdenTrabajo ot, MmeMantencionTracto mantencionTracto, MmeMantencionSemirremolque mantencionSemirremolque) {
        otDao.save(ot);
    }

    @Transactional(readOnly = true)
    public List<OrdenTrabajo> findByStatus(Integer estadoOt) {
        return otDao.findByStatus(estadoOt);
    }

    @Transactional(readOnly = true)
    public List<SolicitanteOt> findApplicants() {
        return otDao.findApplicants();
    }

    @Transactional(readOnly = true)
    public OrdenTrabajo findById(Long otId) {
        return otDao.findById(otId);
    }

    @Transactional(readOnly = true)
    public OrdenTrabajo findByIdAndStatus(Long id, Integer status) {
        return otDao.findByIdAndStatus(id, status);
    }

    @Transactional(readOnly = true)
    public OrdenTrabajo findWithMantenimientos(Long id) {
        return otDao.findWithMantenimientos(id);
    }

    @Transactional(readOnly = false)
    public void updateOt(OrdenTrabajo ot) {
        otDao.save(ot);
    }
}
