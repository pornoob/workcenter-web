package workcenter.negocio.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.LiquidacionDao;
import workcenter.dao.PersonalDao;
import workcenter.entidades.*;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaLiquidaciones {

    @Autowired
    private LiquidacionDao liquidacionDao;

    @Autowired
    private PersonalDao personalDao;

    @Transactional(readOnly = true)
    public ContratoPersonal obtenerDatosContrato(Personal p) {

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
    public List<ValorPrevisionPersonal> obtenerDatosPrevision(ContratoPersonal cp) {
        List<ValorPrevisionPersonal> lstPrevisionPersonal = new ArrayList<ValorPrevisionPersonal>();
        ValorPrevisionPersonal salud = personalDao.obtenerValorPrevisionSaludActual(cp);
        ValorPrevisionPersonal afp = personalDao.obtenerValorPrevisionAfpActual(cp);
        lstPrevisionPersonal.add(salud);
        lstPrevisionPersonal.add(afp);
        return lstPrevisionPersonal;

    }

    @Transactional(readOnly = true)
    public List<ValorImpuestoUnico> obtenerValoresVigentesImpUnico() {
        return liquidacionDao.obtenerValoresVigentesImpUnico();
    }

    @Transactional(readOnly = false)
    public void guardarDatosLiquidacion(Remuneracion liquidacion) {
        liquidacionDao.guardarDatosLiquidacion(liquidacion);
    }

    @Transactional(readOnly = true)
    public List<BonoDescuentoPersonal> obtenerBonosDescuentos() {
        return liquidacionDao.obtenerBonosDescuentos();
    }

    @Transactional(readOnly = true)
    public Integer obtenerAnticipoSueldo(Personal idPers, String mes, Integer anio) {
        return liquidacionDao.obtenerAnticipoSueldo(idPers, mes, anio);
    }

    @Transactional(readOnly = true)
    public Integer obtenerAnticipoViatico(Personal idPers, String mes, Integer anio) {
        return liquidacionDao.obtenerAnticipoViatico(idPers, mes, anio);
    }

    @Transactional(readOnly = true)
    public List<Remuneracion> obtenerListaRemuneraciones() {
        return liquidacionDao.obtenerListaRemuneraciones();
    }

    @Transactional(readOnly = true)
    public List<BonoDescuentoPersonal> obtenerBonosFaltantes(Personal p) {
        return liquidacionDao.obtenerBonosFaltantes(p);
    }

    @Transactional(readOnly = false)
    public void eliminarBonosPersonal(List<BonoDescuentoPersonal> bdp) {
        liquidacionDao.eliminarBonosPersonal(bdp);
    }

    @Transactional(readOnly = false)
    public void guardarDatosLiquidacion(List<Remuneracion> remuneraciones) {
        for (Remuneracion remuneracion : remuneraciones) {
            liquidacionDao.guardarDatosLiquidacion(remuneracion);
        }
    }

    public Remuneracion obtenerLiquidacion(Long id) {
        return liquidacionDao.obtenerLiquidacion(id);
    }

    @Transactional(readOnly = true)
    public Remuneracion obtenerIngresoPrevio(Remuneracion liquidacion) {
        return liquidacionDao.obtenerIngresoPrevio(liquidacion);
    }

    public List<Remuneracion> obtRemuDesdeMesAnterior() {
        return liquidacionDao.obtRemuDesdeMesAnterior();
    }
}
