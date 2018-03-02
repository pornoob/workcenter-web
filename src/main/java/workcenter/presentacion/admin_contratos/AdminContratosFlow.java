package workcenter.presentacion.admin_contratos;

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

    public String irEditarContacto(Contacto c) {
        contactoEmpresa = c.getContactoEmpresa();
        return "editarContacto";
    }

    public String irEditarContacto(ContactoEmpresa ce) {
        contactoEmpresa = ce;
        return "editarContacto";
    }

    public String irEditarContrato(ContratoEmpresa ce) {
        contratoEmpresa = ce;
        return "editarContrato";
    }

    public String irEditarTramo(TramoContrato tc) {
        return "editarTramo";
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
}
