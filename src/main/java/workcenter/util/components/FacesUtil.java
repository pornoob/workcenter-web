package workcenter.util.components;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.RequestContextHolder;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    public static String obtenerParametroRequest(String id) {
        return obtenerHttpServletRequest().getParameter(id);
    }
    
    public static HttpServletResponse obtenerHttpServletResponse() {
        return (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }
    
    public static Object obtenerVariableFlow(String variable) {
        return RequestContextHolder.getRequestContext().getFlowScope().get(variable);
    }
    
    public static void setearVariableFlow(String variable, Object value) {
        RequestContextHolder.getRequestContext().getFlowScope().put(variable, value);
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

    public static <T> T getBeanInCurrentFlow(Class<T> cls, String var) {
        org.springframework.webflow.execution.RequestContext req = RequestContextHolder.getRequestContext();
        return req.getActiveFlow().getApplicationContext().getBean(var, cls);
    }

    public static void redirigir(String url) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(obtenerHttpServletRequest().getContextPath()+url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void irEnlaceDocumento(String url) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String obtenerScreenFlow() {
        return obtenerHttpServletRequest().getParameter("javax.faces.ViewState");
    }
}
