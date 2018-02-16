package workcenter.presentacion.admin_contratos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Empresa;
import workcenter.negocio.LogicaEmpresas;

import java.io.Serializable;

@Component
@Scope("flow")
public class AdminContratosFlow implements Serializable {
    private Empresa empresa;

    @Autowired
    private LogicaEmpresas logicaEmpresas;

    public String irEditarEmpresa(Empresa e) {
        empresa = logicaEmpresas.obtenerEmpresaConContactos(e.getId());
        return "editarEmpresa";
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
