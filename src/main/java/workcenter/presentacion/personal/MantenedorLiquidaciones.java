package workcenter.presentacion.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import workcenter.entidades.Personal;
import workcenter.negocio.personal.LogicaLiquidaciones;
import workcenter.negocio.personal.LogicaPersonal;

import java.io.Serializable;
import java.util.List;

/**
 * Created by claudio on 16-05-15.
 */
@Component
@Scope("flow")
public class MantenedorLiquidaciones implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Personal> personal;

    @Autowired
    private LogicaPersonal logicaPersonal;
    
    @Autowired
    private LogicaLiquidaciones logicaLiquidaciones;

    public String ingresarLiquidacionOtros() {
        personal = logicaPersonal.obtenerTodos();
        return "flowAgregarLiqOtros";
    }

    public String ingresarLiquidacionConductores() {
        personal = logicaPersonal.obtenerTodos();
        return "flowAgregarLiqConductores";
    }

    public List<Personal> getPersonal() {
        return personal;
    }

    public void setPersonal(List<Personal> personal) {
        this.personal = personal;
    }
}
