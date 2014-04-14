package workcenter.util.services;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Idioma {

    public String obtenerMensaje(String propiedad) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Locale locale = ctx.getViewRoot().getLocale();
        ResourceBundle rb = ResourceBundle.getBundle("idiomas/idioma", locale);
        return rb.getString(propiedad);
    }
}
