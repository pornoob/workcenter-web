package workcenter.negocio.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.RemuneracionDao;
import workcenter.entidades.Empresa;
import workcenter.entidades.Personal;
import workcenter.entidades.Remuneracion;

import java.util.List;

/**
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaLibroRemuneraciones {

    @Autowired
    private RemuneracionDao remuneracionDao;

    @Transactional(readOnly = true)
    public List<Remuneracion> obtenerSegunConductor(Personal c, Integer mes, Integer anio) {
        if (mes != null && mes < 1) {
            mes = null;
        }
        if (anio != null && anio <= 0) {
            anio = null;
        }
        return remuneracionDao.obtener(c, mes, anio);
    }

    @Transactional(readOnly = true)
    public List<Remuneracion> obtenerSegunEmpleador(Empresa e, Integer mes, Integer anio) {
        if (mes != null && mes < 1) {
            mes = null;
        }
        if (anio != null && anio <= 0) {
            anio = null;
        }
        return remuneracionDao.obtener(e, mes, anio);
    }

    @Transactional(readOnly = true)
    public List<Remuneracion> obtenerRemuneraciones() {
        return remuneracionDao.obtenerTodas();
    }

    public boolean liquidacionEsImagen(Remuneracion r) {
        String formato = r.getExtension().toLowerCase().trim();
        if (formato.equals("jpg") || formato.equals("png") || formato.equals("tiff")) {
            return true;
        }
        return false;
    }
}
