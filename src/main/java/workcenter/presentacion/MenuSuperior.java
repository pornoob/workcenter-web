package workcenter.presentacion;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by claudio on 21-11-14.
 */
@Component
@Scope("session")
public class MenuSuperior implements Serializable {
    private Integer seleccionado;

    public MenuSuperior() {
        seleccionado = 0;
    }

    public Integer getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Integer seleccionado) {
        this.seleccionado = seleccionado;
    }
}
