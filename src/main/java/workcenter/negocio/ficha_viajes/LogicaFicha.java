package workcenter.negocio.ficha_viajes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import workcenter.dao.FichaDao;
import workcenter.entidades.ViajesTortola;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaFicha {
	
	@Autowired
    FichaDao fichaDao;
	
	 @Transactional(readOnly = false)
	    public void guardarFicha(ViajesTortola viajes) {    
	        	fichaDao.crearFicha(viajes);
	        } 
	    }
