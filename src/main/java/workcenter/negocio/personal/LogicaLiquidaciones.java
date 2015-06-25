package workcenter.negocio.personal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import workcenter.dao.LiquidacionDao;
import workcenter.entidades.ContratoPersonal;
import workcenter.entidades.Personal;
import workcenter.entidades.ValorPrevisionPersonal;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaLiquidaciones {
	
	@Autowired
	private LiquidacionDao liquidacionDao;
	
	@Transactional(readOnly = true)
	public ContratoPersonal obtenerDatosContrato(Personal p){
		
		return liquidacionDao.obtenerDatosContrato(p);
	}
	
	public List<ValorPrevisionPersonal> obtenerDatosPrevision(Integer numeroContrato){
		return liquidacionDao.obtenerDatosPrevision(numeroContrato);
	}

}
