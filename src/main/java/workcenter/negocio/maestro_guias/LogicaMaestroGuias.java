package workcenter.negocio.maestro_guias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.MaestroDeGuiasDAO;
import workcenter.entidades.Personal;
import workcenter.entidades.Vuelta;

import java.util.Date;
import java.util.List;

/**
 * Created by renholders on 23-11-2015.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaMaestroGuias {

    @Autowired MaestroDeGuiasDAO maestroDeGuiasDAO;
    public Vuelta obtenerOrdendeCarga(Integer ordenConsulta){
        return maestroDeGuiasDAO.obtenerOrdendeCarga(ordenConsulta);
    }

    @Transactional(readOnly = false)
    public void guardarOrdenDeCarga(Vuelta ordenDeCarga) {
        maestroDeGuiasDAO.guardarOrdenDeCarga(ordenDeCarga);
    }

    @Transactional(readOnly = true)
    public List<Vuelta> buscar(Date fechaDesde, Date fechaHasta, Personal conductor) {
        return maestroDeGuiasDAO.buscar(fechaDesde, fechaHasta, conductor);
    }
    @Transactional(readOnly = true)
    public List<Vuelta> buscarConProductos(Date fechaDesde, Date fechaHasta, Personal conductor) {
        return maestroDeGuiasDAO.buscarConProductos(fechaDesde, fechaHasta, conductor);
    }
}
