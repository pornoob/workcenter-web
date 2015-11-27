package workcenter.negocio.guia_petroleo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import workcenter.dao.GuiaDePetroleoDao;
import workcenter.entidades.EstacionServicio;
import workcenter.entidades.GuiaPetroleo;
import workcenter.entidades.Vuelta;

import java.util.List;

/**
 * Created by renholders on 26-11-2015.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaGuiaDePetroleo {

    @Autowired
    GuiaDePetroleoDao guiaDePetroleoDao;

    public List<GuiaPetroleo> obtenerPetrolioPorOrden(Vuelta ordenDeCarga){
        return guiaDePetroleoDao.obtenerPetrolioPorOrden(ordenDeCarga);
    }

    public List<EstacionServicio> obtenerEstacionesDeServicio(){
        return guiaDePetroleoDao.obtenerEstacionesDeServicio();
    }
}
