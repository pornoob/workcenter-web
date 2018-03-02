package workcenter.presentacion.admin_contratos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Contacto;
import workcenter.entidades.ContactoEmpresa;
import workcenter.entidades.Empresa;
import workcenter.negocio.LogicaEmpresas;
import workcenter.util.components.FacesUtil;

import java.io.Serializable;
import java.util.HashSet;

@Component
@Scope("flow")
public class AdminContratosFlow implements Serializable {
    private Empresa empresa;
    private ContactoEmpresa contactoEmpresa;

    @Autowired
    private LogicaEmpresas logicaEmpresas;

    public String irCrearEmpresa() {
        empresa = new Empresa();
        empresa.setContactos(new HashSet<ContactoEmpresa>());
        return "editarEmpresa";
    }

    public String irEditarEmpresa(Empresa e) {
        empresa = logicaEmpresas.obtenerEmpresaConContactos(e.getId());
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

    public String irEditarContacto(ContactoEmpresa ce) {
        contactoEmpresa = ce;
        return "editarContacto";
    }

    public String guardarContacto() {
        if (!empresa.getContactos().contains(contactoEmpresa)) {
            empresa.getContactos().add(contactoEmpresa);
        }
        logicaEmpresas.save(empresa);
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Datos Contacto guardados correctamente");
        return "listar";
    }

    public String irEditarContratos(Empresa e) {
        empresa = logicaEmpresas.obtenerEmpresaConContratos(e.getId());;
        return "editarContratos";
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
}
