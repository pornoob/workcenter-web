package workcenter.negocio.taller;

import java.util.List;
import workcenter.dao.OtDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.MmeMantencionesDao;
import workcenter.entidades.MmeMantencionMaquina;
import workcenter.entidades.MmeMantencionSemirremolque;
import workcenter.entidades.MmeMantencionTracto;
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
    @Autowired
    private MmeMantencionesDao mantencionesDao;

    @Transactional(readOnly = false)
    public void create(OrdenTrabajo ot, MmeMantencionTracto mantencionTracto) {
        otDao.save(ot);
        mantencionesDao.guardar(mantencionTracto);
    }
    
    @Transactional(readOnly = false)    
    public void create(OrdenTrabajo ot, MmeMantencionSemirremolque mantencionSemirremolque) {
        otDao.save(ot);
        mantencionesDao.guardar(mantencionSemirremolque);
    }
    
    @Transactional(readOnly = false)    
    public void create(OrdenTrabajo ot, MmeMantencionMaquina mantencionMaquina) {
        otDao.save(ot);
        mantencionesDao.guardar(mantencionMaquina);
    }
    
    @Transactional(readOnly = false)
    public void create(OrdenTrabajo ot, MmeMantencionTracto mantencionTracto, MmeMantencionSemirremolque mantencionSemirremolque) {
        otDao.save(ot);
        mantencionesDao.guardar(mantencionTracto);
        mantencionesDao.guardar(mantencionSemirremolque);
    }

    @Transactional(readOnly = true)
    public List<OrdenTrabajo> findByStatus(Integer estadoOt) {
        return otDao.findByStatus(estadoOt);
    }

    @Transactional(readOnly = true)
    public List<SolicitanteOt> findApplicants() {
        return otDao.findApplicants();
    }
}
