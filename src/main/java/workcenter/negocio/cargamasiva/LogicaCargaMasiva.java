package workcenter.negocio.cargamasiva;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import workcenter.dao.ConceptoDao;
import workcenter.dao.DineroDAO;
import workcenter.entidades.Concepto;
import workcenter.entidades.Dinero;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaCargaMasiva {
	
	@Autowired
	private ConceptoDao conceptoDao;
	
	@Autowired
	private DineroDAO dineroDao;
	
	@Transactional(readOnly = true)
	public List<Concepto> obtenerConceptos(){
		return conceptoDao.obtenerTodos();
	}
	
	@Transactional(readOnly = false)
	public void guardarDinero(Dinero d){
		dineroDao.guardarDatosDineros(d);
	}
}