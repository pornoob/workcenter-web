package workcenter.negocio.tramo_contrato;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import workcenter.dao.TramoContratoDao;
import workcenter.entidades.ContratoEmpresa;
import workcenter.entidades.TramoContrato;

import java.util.List;

/**
 * Created by renholders on 25-11-2015.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaTramoContrato {

    @Autowired
    TramoContratoDao tramoContratoDao;

    public List<TramoContrato> obtenerTramoPorContrato(ContratoEmpresa contratoEmpresa){
        return tramoContratoDao.obtenerTramoPorContrato(contratoEmpresa);
    }
}
