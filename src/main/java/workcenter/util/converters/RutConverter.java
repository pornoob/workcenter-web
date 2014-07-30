package workcenter.util.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * @author colivares
 */
public class RutConverter implements Converter {

    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string == null) {
            return null;
        }
        String rut = string.split("-")[0];
        rut = rut.replaceAll("\\.", "");
        char dv = string.split("-")[1].charAt(0);
        dv = dv == 'k' ? 'K' : dv;
        StringBuilder sb = new StringBuilder();
        int cont = 1;
        for (int i = rut.length() - 1; i >= 0; i--) {
            sb.insert(0, rut.charAt(i));
            if (cont % 3 == 0 && i > 0) {
                sb.insert(0, '.');
            }
            cont = (cont + 1) % 3;
        }
        return sb.toString()+"-"+dv;
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        String string = String.valueOf(o);
        if (string == null) {
            return null;
        }
        String rut = string.split("-")[0];
        char dv = string.split("-")[1].charAt(0);
        dv = dv == 'k' ? 'K' : dv;
        StringBuilder sb = new StringBuilder();
        int cont = 1;
        for (int i = rut.length() - 1; i >= 0; i--) {
            sb.insert(0, rut.charAt(i));
            if (cont % 3 == 0 && i > 0) {
                sb.insert(0, '.');
            }
            cont = (cont + 1) % 3;
        }
        return sb.toString()+"-"+dv;
    }
}
