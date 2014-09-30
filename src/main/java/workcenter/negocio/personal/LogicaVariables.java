
package workcenter.negocio.personal;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import workcenter.dao.VariableDao;
import workcenter.entidades.TipoUnidad;
import workcenter.entidades.Variable;

/**
 *
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaVariables {
    @Autowired
    VariableDao variableDao;

    public Variable obtenerSueldoMinimoActual() {
        return obtenerVariableActual("sueldominimo");
    }
    
    private Variable obtenerVariableActual(String llave){
        return variableDao.obtenerActual(llave);
    }

    public TipoUnidad obtenerTipoUnidad(Integer id) {
        return variableDao.obtenerTipoUnidad(id);
    }

    public List<TipoUnidad> obtenerTiposUnidad() {
        return variableDao.obtenerTiposUnidad();
    }
}
