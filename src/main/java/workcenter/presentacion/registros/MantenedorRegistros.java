package workcenter.presentacion.registros;

import java.io.Serializable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
}