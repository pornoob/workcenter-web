package workcenter.presentacion;

import java.io.Serializable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.security.LogInManager;
import workcenter.util.pojo.FacesUtil;

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

    public String logIn() {
        try {
            HttpServletRequest request = FacesUtil.obtenerHttpServletRequest();
            request.login(rut, pass);
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
