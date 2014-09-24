package workcenter.negocio.equipos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.EquipoDao;
import workcenter.entidades.Equipo;
import workcenter.entidades.RendimientoCopec;

import javax.persistence.PersistenceException;
import java.util.Date;

/**
 * Created by claudio on 23-09-14.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaProveedorPetroleo {
    @Autowired
    private EquipoDao equipoDao;

    @Transactional(readOnly = false)
    public void guardarRendimientoDiario(RendimientoCopec rc) throws PersistenceException {
        equipoDao.guardarRendimientoDiario(rc);
    }

    @Transactional(readOnly = true)
    public Date obtenerUltimaActualizacion() {
        return equipoDao.obtenerFechaUltimoRendimientoDiario();
    }

    @Transactional(readOnly = true)
    public Integer obtenerUltimoOdometro(Equipo e) {
        return equipoDao.obtenerUltimoKmProveedor(e);
    }
}
