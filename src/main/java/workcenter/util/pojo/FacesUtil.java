package workcenter.util.pojo;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author colivares
 */
@Component
@Scope("application")
public class FacesUtil {
    public static String obtenerContextPath() {
        return obtenerHttpServletRequest().getContextPath();
    }
    
    public static HttpServletRequest obtenerHttpServletRequest() {
        return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }
    
    public static HttpServletResponse obtenerHttpServletResponse() {
        return (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }
    
    public static String obtenerParametroSesion(String param) {
        return (String) ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().getAttribute(param);
    }

    public static void mostrarMensajeError(String resumen, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resumen, detalle));
    }
    
    public static void mostrarMensajeInformativo(String resumen, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, resumen, detalle));
    }

    public static void redirigir(String url) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(obtenerHttpServletRequest().getContextPath()+url);
        } catch (IOException ex) {
            Logger.getLogger(FacesUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String obtenerScreenFlow() {
        return obtenerHttpServletRequest().getParameter("javax.faces.ViewState");
    }
}
