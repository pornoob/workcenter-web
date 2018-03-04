package workcenter.presentacion.admin_empresas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.TipoTarifa;
import workcenter.negocio.LogicaEmpresas;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

@Component
@Scope("view")
public class EditarTarifaView implements Serializable {
    private List<TipoTarifa> tipoTarifaList;

    @Autowired
    private LogicaEmpresas logicaEmpresas;

    @PostConstruct
    private void init() {
        tipoTarifaList = logicaEmpresas.obtenerTiposTarifas();
    }

    public List<TipoTarifa> getTipoTarifaList() {
        return tipoTarifaList;
    }

    public void setTipoTarifaList(List<TipoTarifa> tipoTarifaList) {
        this.tipoTarifaList = tipoTarifaList;
    }
}
