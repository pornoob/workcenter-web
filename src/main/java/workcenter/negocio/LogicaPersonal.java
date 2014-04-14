package workcenter.negocio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.PersonalDao;
import workcenter.entidades.Personal;
import workcenter.util.Md5;
import workcenter.util.components.Constantes;

/**
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaPersonal {

    @Autowired
    PersonalDao personalDao;
    
    @Autowired
    Constantes constantes;
    
    @Transactional(readOnly = true)
    public List<Personal> obtenerTodos() {
        return personalDao.obtenerTodos();
    }

    @Transactional(readOnly = true)
    public Personal obtenerConAccesos(Integer rut) {
        return personalDao.obtenerConAccesos(rut);
    }

    @Transactional(readOnly = true)
    public List<Personal> obtenerTodosConUsuario() {
        return personalDao.obtenerTodosConUsuario();
    }

    @Transactional(readOnly = false)
    public void guardarUsuario(Personal personal) {
        boolean nuevo = false;
        if (personal.getUsuario().getRut() == null) {
            personal.getUsuario().setRut(personal.getRut());
            personal.getUsuario().setPassword(Md5.hash(personal.getRut().toString()));
            nuevo = true;
        }
        personal.getUsuario().setPersonal(personal);

        if (nuevo) {
            personalDao.crearUsuario(personal.getUsuario());
        } else {
            personalDao.actualizarUsuario(personal.getUsuario());
        }
    }

    @Transactional(readOnly = true)
    public List<Personal> obtenerConductores() {
        return personalDao.obtenerSegunCargo(constantes.getCargoConductor());
    }
}
