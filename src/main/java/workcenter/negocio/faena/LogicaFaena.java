package workcenter.negocio.faena;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import workcenter.dao.FaenaDao;
import workcenter.entidades.Faena;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaFaena {
	
	 @Autowired
	 FaenaDao faenaDao;
	 
	 @Transactional(readOnly = true)
	 public List<Faena> obtenerTodasFaenas() {
	 return faenaDao.obtenerTodos();
	 }
	 
	 @Transactional(readOnly = true)
	 public Faena obtenerFaena(String filtro) {
	 return faenaDao.obtenerFaena(filtro);
	 }

}
