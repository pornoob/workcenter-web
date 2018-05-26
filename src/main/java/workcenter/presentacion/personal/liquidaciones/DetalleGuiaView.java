package workcenter.presentacion.personal.liquidaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Personal;
import workcenter.entidades.Vuelta;
import workcenter.negocio.maestro_guias.LogicaMaestroGuias;

import java.io.Serializable;
import java.util.List;

@Component
@Scope("view")
public class DetalleGuiaView implements Serializable {
    private Personal persona;
    private List<Vuelta> vueltas;

    @Autowired
    LogicaMaestroGuias logicaMaestroGuias;

    public void init(Personal persona) {
        this.persona = persona;
        //logicaMaestroGuias.buscar();
    }
}
