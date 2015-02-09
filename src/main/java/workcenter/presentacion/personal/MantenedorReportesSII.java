package workcenter.presentacion.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Remuneracion;
import workcenter.negocio.personal.LogicaLibroRemuneraciones;

import java.io.Serializable;
import java.util.List;

/**
 * Created by colivares on 09-02-15.
 */
@Component
@Scope("flow")
public class MantenedorReportesSII implements Serializable {

    @Autowired
    private LogicaLibroRemuneraciones logicaLibroRemuneraciones;

    private List<Remuneracion> remuneraciones;

    public void inicio() {

    }

    // getters and setters de aquí para abajo
    public List<Remuneracion> getRemuneraciones() {
        return remuneraciones;
    }

    public void setRemuneraciones(List<Remuneracion> remuneraciones) {
        this.remuneraciones = remuneraciones;
    }
}
