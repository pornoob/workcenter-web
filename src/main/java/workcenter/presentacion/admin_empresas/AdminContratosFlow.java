package workcenter.presentacion.admin_empresas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.LogicaEmpresas;
import workcenter.util.components.FacesUtil;

import java.io.Serializable;
import java.util.HashSet;

@Component
@Scope("flow")
public class AdminContratosFlow implements Serializable {
    private Empresa empresa;
    private ContactoEmpresa contactoEmpresa;
    private ContratoEmpresa contratoEmpresa;
    private TramoContrato tramoContrato;
    private TarifaTramo tarifaTramo;

    private Boolean contactViewfromContrato;

    @Autowired
    private LogicaEmpresas logicaEmpresas;

    public String irCrearEmpresa() {
        empresa = new Empresa();
        empresa.setContactos(new HashSet<ContactoEmpresa>());
        return "editarEmpresa";
    }

    public String irEditarEmpresa(Empresa e) {
        empresa = logicaEmpresas.obtenerEmpresaConContactos(e.getId());
        contactViewfromContrato = Boolean.FALSE;
        return "editarEmpresa";
    }

    public void guardarEmpresa() {
        logicaEmpresas.save(empresa);
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Datos Empresa guardados correctamente");
    }

    public String irCrearContacto() {
        contactoEmpresa = new ContactoEmpresa();
        contactoEmpresa.setContacto(new Contacto());
        return "editarContacto";
    }

    public String irCrearTarifa() {
        tarifaTramo = new TarifaTramo();
        return "editarTarifa";
    }

    public String irCrearTramo() {
        tramoContrato = new TramoContrato();
        return "editarContrato";
    }

    public String irEditarContacto(Contacto c) {
        contactoEmpresa = c.getContactoEmpresa();
        return "editarContacto";
    }

    public String irEditarContacto(ContactoEmpresa ce) {
        contactoEmpresa = ce;
        return "editarContacto";
    }

    public String irEditarContrato(ContratoEmpresa ce) {
        contratoEmpresa = logicaEmpresas.obtenerContratosParaEdicion(ce.getId());
        return "editarContrato";
    }

    public String irEditarTramo(TramoContrato tc) {
        tramoContrato = logicaEmpresas.obtenerTramoParaEdicion(tc.getId());
        return "editarTramo";
    }

    public String irEditarTarifa(TarifaTramo tt) {
        tarifaTramo = tt;
        return "editarTarifa";
    }

    public void eliminarTramo(TramoContrato tc) {
        contratoEmpresa.getTramos().remove(tc);
        logicaEmpresas.saveContrato(contratoEmpresa);
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Tramo eliminado exitosamente");
    }

    public void eliminarTarifa(TarifaTramo tt) {
        tramoContrato.getTarifas().remove(tt);
        logicaEmpresas.saveTramo(tramoContrato);
        tramoContrato = logicaEmpresas.obtenerTramoParaEdicion(tramoContrato.getId());
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Tarifa eliminada exitosamente");
    }

    public void guardarContrato() {
        logicaEmpresas.saveContrato(contratoEmpresa);
        empresa = logicaEmpresas.obtenerEmpresaConContratos(empresa.getId());
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Contrato editado exitosamente");
    }

    public void guardarTramo() {
        tramoContrato.setContrato(contratoEmpresa);
        logicaEmpresas.saveTramo(tramoContrato);
        contratoEmpresa = logicaEmpresas.obtenerContratosParaEdicion(contactoEmpresa.getId());
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Contrato editado exitosamente");
    }

    public void guardarTarifa() {
        tarifaTramo.setTramo(tramoContrato);
        logicaEmpresas.saveTarifa(tarifaTramo);
        tramoContrato = logicaEmpresas.obtenerTramoParaEdicion(tramoContrato.getId());
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Tarifa creada exitosamente");
    }

    public String guardarContacto() {
        contactoEmpresa.setEmpresa(empresa);
        if (Boolean.TRUE.equals(contactViewfromContrato)
                && !contratoEmpresa.getContactos().contains(contactoEmpresa.getContacto())) {
            contratoEmpresa.getContactos().add(contactoEmpresa.getContacto());
        }
        if (!empresa.getContactos().contains(contactoEmpresa)) {
            empresa.getContactos().add(contactoEmpresa);
        }
        logicaEmpresas.save(empresa);
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Datos Contacto guardados correctamente");
        return "backContacto";
    }

    public String irListarContratos(Empresa e) {
        empresa = logicaEmpresas.obtenerEmpresaConContratos(e.getId());
        contactViewfromContrato = Boolean.TRUE;
        return "listarContratos";
    }

    public void eliminarContrato(ContratoEmpresa ce) {
        empresa.getContratos().remove(ce);
        logicaEmpresas.save(empresa);
        empresa = logicaEmpresas.obtenerEmpresaConContratos(empresa.getId());
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Contrato eliminado de manera exitosa");
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public ContactoEmpresa getContactoEmpresa() {
        return contactoEmpresa;
    }

    public void setContactoEmpresa(ContactoEmpresa contactoEmpresa) {
        this.contactoEmpresa = contactoEmpresa;
    }

    public ContratoEmpresa getContratoEmpresa() {
        return contratoEmpresa;
    }

    public void setContratoEmpresa(ContratoEmpresa contratoEmpresa) {
        this.contratoEmpresa = contratoEmpresa;
    }

    public Boolean getContactViewfromContrato() {
        return contactViewfromContrato;
    }

    public void setContactViewfromContrato(Boolean contactViewfromContrato) {
        this.contactViewfromContrato = contactViewfromContrato;
    }

    public TramoContrato getTramoContrato() {
        return tramoContrato;
    }

    public void setTramoContrato(TramoContrato tramoContrato) {
        this.tramoContrato = tramoContrato;
    }

    public TarifaTramo getTarifaTramo() {
        return tarifaTramo;
    }

    public void setTarifaTramo(TarifaTramo tarifaTramo) {
        this.tarifaTramo = tarifaTramo;
    }
}
