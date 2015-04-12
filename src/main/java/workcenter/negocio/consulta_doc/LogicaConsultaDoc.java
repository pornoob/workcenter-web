package workcenter.negocio.consulta_doc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import workcenter.dao.CargoDao;
import workcenter.entidades.Cargo;

import java.util.List;

/**
 * Created by claudio on 11-04-15.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaConsultaDoc {
    @Autowired
    private CargoDao cargoDao;

    public List<Cargo> obtenerCargos() {
        return cargoDao.obtenerTodos();
    }
}
