package workcenter.negocio.personal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import workcenter.dao.LiquidacionDao;
import workcenter.entidades.*;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaLiquidaciones {
	
	@Autowired
	private LiquidacionDao liquidacionDao;
	
	@Transactional(readOnly = true)
	public ContratoPersonal obtenerDatosContrato(Personal p){
		
		return liquidacionDao.obtenerDatosContrato(p);
	}

    @Transactional(readOnly = true)
    public Variable obtenerValorUtm(Integer mes, Integer anio) {
        return liquidacionDao.obtenerValorUtm(mes, anio);
    }

    @Transactional(readOnly = true)
    public Variable obtenerValorUf(Integer mes, Integer anio) {
        return liquidacionDao.obtenerValorUf(mes, anio);
    }

    @Transactional(readOnly = true)
	public List<ValorPrevisionPersonal> obtenerDatosPrevision(ContratoPersonal cp){
		return liquidacionDao.obtenerDatosPrevision(cp);
	}

    @Transactional(readOnly = true)
    public List<ValorImpuestoUnico> obtenerValoresVigentesImpUnico() {
        return liquidacionDao.obtenerValoresVigentesImpUnico();
    }
    @Transactional(readOnly = false)
    public void guardarDatosLiquidacion(Remuneracion liquidacion){
    	liquidacionDao.guardarDatosLiquidacion(liquidacion);
    }
    
    @Transactional(readOnly = true)
	public List<BonoDescuentoPersonal> obtenerBonosDescuentos() {
		return liquidacionDao.obtenerBonosDescuentos();
	}

    @Transactional(readOnly = true)
    public Integer obtenerAnticipoSueldo(Integer idPers, String mes, Integer anio) {
        return liquidacionDao.obtenerAnticipoSueldo(idPers, mes, anio);
    }
    
    @Transactional(readOnly = true)
	public List<Remuneracion> obtenerListaRemuneraciones() {
		return liquidacionDao.obtenerListaRemuneraciones();
	}
    
    @Transactional(readOnly = true)
	public List<BonoDescuentoPersonal> obtenerBonosFaltantes(Personal p) {
		return liquidacionDao.obtenerBonosFaltantes(p);
	}
}
