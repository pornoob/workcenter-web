package workcenter.negocio.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.CargoDao;
import workcenter.dao.PersonalDao;
import workcenter.entidades.*;
import workcenter.util.components.Constantes;
import workcenter.util.pojo.Md5;

import java.util.List;

/**
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaPersonal {

    @Autowired
    PersonalDao personalDao;
    
    @Autowired
    CargoDao cargoDao;
    
    @Autowired
    Constantes constantes;

    @Transactional(readOnly = true)
    public List<Personal> obtenerTodos() {
        return personalDao.obtenerTodos();
    }

    @Transactional(readOnly = true)
    public Personal obtenerConAccesos(Integer rut) {
        return personalDao.obtenerConAccesos(rut);
    }

    @Transactional(readOnly = true)
    public List<Personal> obtenerTodosConUsuario() {
        return personalDao.obtenerTodosConUsuario();
    }

    @Transactional(readOnly = false)
    public void guardarUsuario(Personal personal) {
        boolean nuevo = false;
        if (personal.getUsuario().getRut() == null) {
            personal.getUsuario().setRut(personal.getRut());
            personal.getUsuario().setPassword(Md5.hash(personal.getRut().toString()));
            nuevo = true;
        }
        personal.getUsuario().setPersonal(personal);

        if (nuevo) {
            personalDao.crearUsuario(personal.getUsuario());
        } else {
            personalDao.actualizarUsuario(personal.getUsuario());
        }
    }
    
    @Transactional(readOnly = false)
    public void guardar(Personal personal) {
        personalDao.guardar(personal);
    }

    @Transactional(readOnly = true)
    public List<Personal> obtenerConductores() {
        return personalDao.obtenerSegunCargo(constantes.getCargoConductor());
    }

    @Transactional(readOnly = true)
    public List<Personal> obtenerPersonalPorPermiso(String modulo) {
        return personalDao.obtenerPorPermiso(modulo);
    }

    @Transactional(readOnly = true)
    public Personal obtener(Integer rut) {
        return personalDao.obtener(rut);
    }

    @Transactional(readOnly = true)
    public Empresa obtenerEmpleador(Personal p) {
        return personalDao.obtenerEmpleador(p);
    }

    @Transactional(readOnly = true)
    public List<DocumentoPersonal> obtenerDocumentos(Personal personal) {
        return personalDao.obtenerDocumentos(personal);
    }
    
    @Transactional(readOnly = true)
    public List<ContratoPersonal> obtenerContratos(Personal personal) {
        return personalDao.obtenerContratos(personal);
    }
    
    @Transactional(readOnly = true)
    public List<Cargo> obtenerCargos() {
        return cargoDao.obtenerTodos();
    }

    @Transactional(readOnly = true)
    public ValorPrevisionPersonal obtenerValorPrevisionSaludActual(ContratoPersonal cp) {
        return personalDao.obtenerValorPrevisionSaludActual(cp);
    }

    @Transactional(readOnly = true)
    public List<SancionRetiradaPersonal> obtenerSancionesRetiradas(Personal p) {
        return personalDao.obtenerSancionesRetiradas(p);
    }

    @Transactional(readOnly = true)
    public Sancionado obtenerSancion(Personal p) {
        return personalDao.obtenerSancion(p);
    }

    @Transactional(readOnly = true)
    public List<DocumentoPersonal> obtenerDocumentosActualizados(Personal p) {
        return personalDao.obtenerDocumentosActualizados(p);
    }

    @Transactional(readOnly = true)
    public List<Personal> obtenerMecanicos() {
        List<Personal> mecanicos = personalDao.obtenerSegunCargo(constantes.getCargoMecanico());
        mecanicos.add(personalDao.obtener(8846226)); // don freddy (socio empresa)
        return mecanicos;
    }

    @Transactional(readOnly = false)
    public void guardarDocumento(DocumentoPersonal doc) {
        personalDao.guardarDocumento(doc);
    }

    @Transactional(readOnly = false)
    public void guardarHistorialDocumento(HistorialDocumentosPersonal respaldo) {
        personalDao.guardarHistorialDocumento(respaldo);
    }

    @Transactional(readOnly = true)
    public List<TipoDocPersonal> obtenerTiposDocPorCargo(Personal p) {
        return personalDao.obtenerTiposDocPorCargo(p);
    }
    
    @Transactional(readOnly = true)
    public List<TipoDocPersonal> obtenerTiposDocPersonal() {
        return personalDao.obtenerTiposDocPersonal();
    }

    @Transactional(readOnly = false)
    public void guardarContrato(ContratoPersonal contrato) {
        personalDao.guardarContrato(contrato);
    }

    @Transactional(readOnly = true)
    public List<Personal> obtenerPorCargo(Cargo cargo) {
        if (cargo.getId().intValue() == constantes.getCargoMecanico()) return obtenerMecanicos();
        return personalDao.obtenerSegunCargo(cargo.getId());
    }

    @Transactional(readOnly = true)
    public List<Personal> obtenerPorCargoServicio(Cargo cargo, Servicio servicio) {
        return personalDao.obtenerSegunCargoServicio(cargo, servicio);
    }

    @Transactional(readOnly = true)
    public Personal obtenerConDatosLiquidacion(Personal p) {
        return personalDao.obtenerConDatosLiquidacion(p);
    }

    @Transactional(readOnly = true)
    public ContratoPersonal obtenerContratoActual(Personal personal) {
        return personalDao.obtenerContratoActual(personal);
    }
}
