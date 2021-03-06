package workcenter.presentacion.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.security.LogInManager;
import workcenter.util.components.FacesUtil;
import workcenter.util.components.SesionCliente;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author colivares
 */
@Component
@Scope("view")
public class AutentificacionBean implements Serializable {

    private String rut;
    private String pass;

    @Autowired
    LogInManager logInManager;

    @Autowired
    SesionCliente sesionCliente;

    public String logIn() {
        try {
            HttpServletRequest request = FacesUtil.obtenerHttpServletRequest();
            request.login(rut, pass);
            sesionCliente.setInicioSesion(true);
            FacesUtil.redirigir("/inicio.jsf");
        } catch (ServletException ex) {
            FacesUtil.mostrarMensajeError(ex.getMessage(), null);
        }
        return null;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
