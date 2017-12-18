package workcenter.presentacion.registros;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *
 * @author colivares
 */
@Component
@Scope("flow")
public class MantenedorRegistros implements Serializable {
    
    public void inicio() {
    }
    
    public String irFormularioR112() {
        return "flowListarR112";
    }

    public String irInspeccionAvanzada() { return "flowListarInspeccionesAvanzadas"; }

    public String irFatigaSomnolencia() {
        return "flowFatigaSomnolencia";
    }
}