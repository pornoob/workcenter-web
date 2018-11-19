package workcenter.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.EmpresaDao;
import workcenter.entidades.*;

import java.util.List;

/**
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaEmpresas {
    
    @Autowired
    EmpresaDao empresaDao;
    
    @Transactional(readOnly = true)
    public List<Empresa> obtenerEmpleadores() {
        return empresaDao.obtenerEmpleadores();
    }

    @Transactional(readOnly = true)
    public List<Empresa> obtenerEmpresas() {
        return empresaDao.obtenerEmpresas();
    }

    @Transactional(readOnly = true)
    public Empresa obtenerEmpresa(Long rut) {
        return empresaDao.obtenerEmpresa(rut);
    }

    @Transactional(readOnly = true)
    public Empresa obtenerEmpresaConContactos(Integer id) {
        return empresaDao.obtenerEmpresaConContactos(id);
    }

    @Transactional(readOnly = false)
    public void save(Empresa empresa) {
        empresaDao.save(empresa);
    }

    @Transactional(readOnly = true)
    public Empresa obtenerEmpresaConContratos(Integer id) {
        return empresaDao.obtenerEmpresaConContratos(id);
    }

    @Transactional(readOnly = false)
    public void eliminarEmpresa(Empresa e) {
        empresaDao.eliminarEmpresa(e);
    }

    @Transactional(readOnly = true)
    public ContratoEmpresa obtenerContratosParaEdicion(Integer id) {
        return empresaDao.obtenerContratosParaEdicion(id);
    }

    @Transactional(readOnly = true)
    public List<OrigenDestino> obtenerOrigenesDestinos() {
        return empresaDao.obtenerOrigenesDestinos();
    }

    @Transactional(readOnly = true)
    public List<TipoProducto> obtenerProductos() {
        return empresaDao.obtenerProductos();
    }

    @Transactional(readOnly = true)
    public TramoContrato obtenerTramoParaEdicion(Integer id) {
        return empresaDao.obtenerTramoParaEdicion(id);
    }

    @Transactional(readOnly = false)
    public void saveContrato(ContratoEmpresa contratoEmpresa) {
        empresaDao.saveContrato(contratoEmpresa);
    }

    @Transactional(readOnly = false)
    public void saveTramo(TramoContrato tt) {
        empresaDao.saveTramo(tt);
    }

    @Transactional(readOnly = true)
    public List<TipoTarifa> obtenerTiposTarifas() {
        return empresaDao.obtenerTiposTarifas();
    }

    @Transactional(readOnly = false)
    public void saveTarifa(TarifaTramo tarifaTramo) {
        empresaDao.saveTarifa(tarifaTramo);
    }
}
