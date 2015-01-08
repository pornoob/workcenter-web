package workcenter.negocio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.EmpresaDao;
import workcenter.entidades.Empresa;

/**
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaEmpresas {
    
    @Autowired
    EmpresaDao empresaDao;
    
    @Transactional(readOnly = true)
    public List<Empresa> obtenerEmpleadores() {
        return empresaDao.obtenerEmpleadores();
    }

    @Transactional(readOnly = true)
    public List<Empresa> obtenerEmpresas() {
        return empresaDao.obtenerEmpresas();
    }
}
