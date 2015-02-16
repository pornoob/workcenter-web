package workcenter.negocio.registros.reportes_sii;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.ReportesSIIDao;
import workcenter.entidades.FactorActualizacionSII;

import java.util.List;

/**
 * Created by claudio on 15-02-15.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaReportesSII {
    @Autowired
    private ReportesSIIDao reportesSIIDao;

    @Transactional(readOnly = true)
    public List<FactorActualizacionSII> obtenerFactoresActualizacionSII() {
        return reportesSIIDao.obtenerFactoresActualizacionSII();
    }

    @Transactional(readOnly = false)
    public void guardarFactoresActualizacion(List<FactorActualizacionSII> valores) {
        for (FactorActualizacionSII f : valores) {
            reportesSIIDao.guardarFactorActualizacion(f);
        }
    }
}
