package workcenter.negocio.maestro_guias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import workcenter.dao.MaestroDeGuiasDAO;
import workcenter.entidades.Vuelta;

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
}
