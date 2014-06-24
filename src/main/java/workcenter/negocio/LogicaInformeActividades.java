package workcenter.negocio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.AlarmasGpsDao;
import workcenter.dao.InformeActividadesDao;
import workcenter.entidades.ActividadDiaria;
import workcenter.entidades.Servicio;
import workcenter.entidades.TipoActividadDiaria;
import workcenter.util.dto.Semana;

/**
 *
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaInformeActividades {
    @Autowired
    AlarmasGpsDao alarmasGpsDao;
    
    @Autowired
    InformeActividadesDao informeActividadesDao;
    
    @Transactional(readOnly = true)
    public List<Servicio> obtenerServicios(Integer rut) {
        return alarmasGpsDao.obtenerServicios(rut);
    }

    @Transactional(readOnly = true)
    public List<TipoActividadDiaria> obtenerTiposActividades() {
        return informeActividadesDao.obtenerTiposActividades();
    }

    @Transactional(readOnly = false)
    public void guardar(ActividadDiaria actividadDiaria) {
        informeActividadesDao.guardar(actividadDiaria);
    }

    @Transactional(readOnly = true)
    public List<ActividadDiaria> obtenerActividades(Servicio servicioSeleccionado, Semana semana) {
        return informeActividadesDao.obtenerActividades(servicioSeleccionado, semana);
    }
}
