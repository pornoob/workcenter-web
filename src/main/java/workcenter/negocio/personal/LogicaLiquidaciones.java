package workcenter.negocio.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import workcenter.dao.LiquidacionDao;
import workcenter.dao.RemuneracionDao;
import workcenter.entidades.ContratoPersonal;
import workcenter.entidades.Personal;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaLiquidaciones {
	
	@Autowired
	private LiquidacionDao liquidacionDao;
	
	@Transactional(readOnly = true)
	public ContratoPersonal obtenerDatosContrato(Personal p){
		
		return liquidacionDao.obtenerDatosContrato(p);
	}

}
