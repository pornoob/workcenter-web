package workcenter.negocio.personal;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.FiniquitoDao;
import workcenter.entidades.Empresa;
import workcenter.entidades.Finiquito;
import workcenter.entidades.Personal;

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
    
    public List<Finiquito> obtenerFiniquitosTrabajador(Personal p, Integer anio) {
        return finiquitoDao.obtenerFiniquitosTrabajador(p,anio);
    }
    
    public List<Finiquito> obtenerFiniquitosTrabajador(Personal p, Integer anio, Integer mes) {
        return finiquitoDao.obtenerFiniquitosTrabajador(p,anio, mes);
    }

    public List<Finiquito> obtenerFiniquitosTrabajador(Empresa empleador, Integer anio) {
        return finiquitoDao.obtenerFiniquitosTrabajador(empleador, anio);
    }
}
