package workcenter.negocio.cargamasiva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.BonoDescuentoDao.BonoDescuentoDao;
import workcenter.dao.ConceptoDao;
import workcenter.dao.DineroDAO;
import workcenter.entidades.BonoDescuento;
import workcenter.entidades.Concepto;
import workcenter.entidades.Dinero;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaCargaMasiva {
	
	@Autowired
	private ConceptoDao conceptoDao;

	@Autowired
	private BonoDescuentoDao bonoDescuentoDao;
	
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

	public List<BonoDescuento> obtenerBonosDescuentos() {
		return bonoDescuentoDao.obtenerTodos();
	}
}