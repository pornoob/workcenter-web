package workcenter.negocio.contrato_empresa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.ContratoEmpresaDao;
import workcenter.entidades.ContratoEmpresa;

import java.util.List;

/**
 * Created by renholders on 25-11-2015.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaContratoEmpresa {

    @Autowired
    ContratoEmpresaDao contratoEmpresaDao;

    @Transactional(readOnly = true)
    public List<ContratoEmpresa> obtenerContratoEmpresas(){
        return contratoEmpresaDao.obtenerContratoEmpresas();
    }

    @Transactional(readOnly = true)
    public List<ContratoEmpresa> obtenerContratoEmpresasConEmpresa(){
        return contratoEmpresaDao.obtenerContratoEmpresasConEmpresas();
    }
}

