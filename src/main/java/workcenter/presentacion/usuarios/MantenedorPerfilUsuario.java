package workcenter.presentacion.usuarios;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Usuario;

import java.io.Serializable;

/**
 * Created by colivares on 28-08-14.
 */
@Component
@Scope("flow")
public class MantenedorPerfilUsuario implements Serializable {
    public void inicio() {
    }
}
