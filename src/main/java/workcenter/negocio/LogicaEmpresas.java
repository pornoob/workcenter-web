package workcenter.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.EmpresaDao;
import workcenter.entidades.Empresa;

import java.util.List;

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

    @Transactional(readOnly = true)
    public Empresa obtenerEmpresa(Integer rut) {
        return empresaDao.obtenerEmpresa(rut);
    }
}
