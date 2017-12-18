package workcenter.negocio.concepto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.ConceptoDao;
import workcenter.entidades.Concepto;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaConceptos {

    @Autowired
    private ConceptoDao conceptoDao;

    @Transactional(readOnly = true)
    public List<Concepto> obtenerConceptos(){
        return conceptoDao.obtenerTodos();
    }
}
