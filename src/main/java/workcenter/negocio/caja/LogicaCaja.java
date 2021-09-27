package workcenter.negocio.caja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.DineroDAO;
import workcenter.entidades.Concepto;
import workcenter.entidades.Dinero;
import workcenter.entidades.Personal;
import workcenter.entidades.Vuelta;

import java.util.Date;
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

    @Transactional(readOnly = true)
    public List<Dinero> obtenerDinerosConDescuentos() {return dineroDao.obtenerDinerosConDescuentos();}

    @Transactional(readOnly = false)
    public Dinero guardarEntradas(Dinero dinero){return dineroDao.guardarDatosDineros(dinero);}

    @Transactional(readOnly = true)
    public List<Dinero> obtenerDinerosFiltros(Personal personal, Concepto concepto, Date fechaDesde, Date fechaHasta) {
        return dineroDao.obtenerDinerosFiltros(personal, concepto, fechaDesde, fechaHasta);
    }

    @Transactional(readOnly = true)
    public List<Dinero> obtenerDinerosNoCancelados(Concepto conceptoParam){
        return dineroDao.obtenerDinerosNoCancelados(conceptoParam);
    }

    @Transactional(readOnly = true)
    public List<Dinero> obtenerDinerosSegunVueltas(List<Vuelta> vueltas) {
        return dineroDao.obtenerDineros(vueltas);
    }
}
