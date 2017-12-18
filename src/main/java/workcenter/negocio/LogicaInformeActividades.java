package workcenter.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.AlarmasGpsDao;
import workcenter.dao.InformeActividadesDao;
import workcenter.entidades.ActividadDiaria;
import workcenter.entidades.MpaContrato;
import workcenter.entidades.Servicio;
import workcenter.entidades.TipoActividadDiaria;
import workcenter.util.dto.Semana;
import workcenter.util.dto.UsuarioDto;

import java.util.List;

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
    public List<Servicio> obtenerServicios(UsuarioDto u) {
        if (!u.isExterno())
            return alarmasGpsDao.obtenerServicios(u.getRut());
        else
            return alarmasGpsDao.obtenerServicios(u.getUsuario());
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
    public List<ActividadDiaria> obtenerActividades(Servicio servicio, MpaContrato contrato, Semana semana) {
        return informeActividadesDao.obtenerActividades(servicio, contrato, semana);
    }
}
