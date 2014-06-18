package workcenter.negocio;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.AlarmasGpsDao;
import workcenter.entidades.AlarmaGps;
import workcenter.entidades.GestionAlarmaGps;
import workcenter.entidades.Servicio;

/**
 *
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaAlarmasGps {
    
    @Autowired
    AlarmasGpsDao alarmasGpsDao;

    @Transactional(readOnly = true)
    public List<String> obtenerConductores(Date dia, Date diaSiguiente) {
        return alarmasGpsDao.obtenerConductores(dia, diaSiguiente);
    }
    
    @Transactional(readOnly = true)
    public List<String> obtenerConductores(Date dia, Date diaSiguiente, Servicio servicio) {
        return alarmasGpsDao.obtenerConductores(dia, diaSiguiente, servicio);
    }

    @Transactional(readOnly = true)
    public List<AlarmaGps> obtenerPorMes(String mes, String anio, Servicio servicio) {
        return alarmasGpsDao.obtenerPorMes(mes, anio, servicio);
    }

    @Transactional(readOnly = true)
    public List<AlarmaGps> obtenerPorMes(String mes, String anio) {
        return alarmasGpsDao.obtenerPorMes(mes, anio);
    }

    @Transactional(readOnly = false)
    public void guardarAlarma(AlarmaGps a) {
        alarmasGpsDao.guardar(a);
    }
    
    @Transactional(readOnly = true)
    public List<Servicio> obtenerServicios(Integer rut) {
        return alarmasGpsDao.obtenerServicios(rut);
    }

    @Transactional(readOnly = true)
    public List<String> obtenerRutas() {
        return alarmasGpsDao.obtenerRutas();
    }
    
    @Transactional(readOnly = false)
    public void guardarGestionAlarma(GestionAlarmaGps ga) {
        alarmasGpsDao.guardarGestion(ga);
    }
}
