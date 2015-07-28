package workcenter.negocio.hoja_servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import workcenter.dao.CargasFamiliaresDao;
import workcenter.entidades.ValoresCargasFamiliares;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaCargasFamiliares {
	
	@Autowired
    CargasFamiliaresDao cargasFamiliaresDao;
	
	@Transactional(readOnly = true)
    public List<ValoresCargasFamiliares> obtenerCargasFamiliares() {
        return cargasFamiliaresDao.obtenerCargasFamiliares();
    }

}
