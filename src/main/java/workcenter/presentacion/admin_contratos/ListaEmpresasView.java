package workcenter.presentacion.admin_contratos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Empresa;
import workcenter.negocio.LogicaEmpresas;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

@Component
@Scope("view")
public class ListaEmpresasView implements Serializable {

    public List<Empresa> empresas;

    @Autowired
    private LogicaEmpresas logicaEmpresas;

    @PostConstruct
    public void init() {
        empresas = logicaEmpresas.obtenerEmpresas();
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }
}
