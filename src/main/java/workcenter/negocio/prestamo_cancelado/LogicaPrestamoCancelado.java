package workcenter.negocio.prestamo_cancelado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.PrestamoCanceladoDao;
import workcenter.entidades.PrestamoCancelado;

/**
 * Created by renholders on 11-11-2015.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaPrestamoCancelado {

    @Autowired
    PrestamoCanceladoDao prestamoCanceladoDao;

    @Transactional(readOnly = false)
    public Boolean guardarPrestamoCancelado(PrestamoCancelado pCancelado){
     return prestamoCanceladoDao.guardarPrestamoCancelado(pCancelado);
    }
}
