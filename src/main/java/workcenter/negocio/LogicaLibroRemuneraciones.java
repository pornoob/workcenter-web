package workcenter.negocio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.RemuneracionDao;
import workcenter.entidades.Empresa;
import workcenter.entidades.Personal;
import workcenter.entidades.Remuneracion;

/**
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaLibroRemuneraciones {

    @Autowired
    RemuneracionDao remuneracionDao;

    @Transactional(readOnly = true)
    public List<Remuneracion> obtenerSegunConductor(Personal c, Integer mes, Integer anio) {
        if (mes < 1) {
            mes = null;
        }
        if (anio <= 0) {
            anio = null;
        }
        return remuneracionDao.obtener(c, mes, anio);
    }

    @Transactional(readOnly = true)
    public List<Remuneracion> obtenerSegunEmpleador(Empresa e, Integer mes, Integer anio) {
        if (mes < 1) {
            mes = null;
        }
        if (anio <= 0) {
            anio = null;
        }
        return remuneracionDao.obtener(e, mes, anio);
    }

    @Transactional(readOnly = true)
    public List<Remuneracion> obtenerRemuneraciones() {
        return remuneracionDao.obtenerTodas();
    }
}
