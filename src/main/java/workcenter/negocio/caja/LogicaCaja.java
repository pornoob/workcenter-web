package workcenter.negocio.caja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.DineroDAO;
import workcenter.entidades.Dinero;

import java.util.List;

/**
 * Created by renholders on 09-10-2015.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaCaja {

    @Autowired DineroDAO dineroDao;

    @Transactional(readOnly = true)
    public List<Dinero> obtenerDineros() {return dineroDao.obtenerDineros();}

    @Transactional(readOnly = false)
    public Boolean guardarEntradas(Dinero dinero){return dineroDao.guardarDatosDineros(dinero);}
}
