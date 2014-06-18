package workcenter.util.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author colivares
 */
public class FechaConverter implements Converter {

    private static final SimpleDateFormat formatoBD = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final SimpleDateFormat formatoBD2 = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat formatoPantalla = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    private static final SimpleDateFormat formatoPantalla2 = new SimpleDateFormat("dd-MM-yyyy");

    public Object getAsObject(FacesContext fc, UIComponent uic, String str) {
        try {
            System.out.println("FECHA CONVERTER s->o PRIMER INTENTO");
            return formatoBD.parse(str);
        } catch (ParseException ex) {
//            Logger.getLogger(FechaConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("FECHA CONVERTER s->o SEGUNDO INTENTO");
        try {
            return formatoBD2.parse(str);
        } catch (ParseException ex) {
//            Logger.getLogger(FechaConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("RET0RNO NULL");
        return null;
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        try {
            return formatoPantalla.format((Date) o);
        } catch (Exception ex) {
            Logger.getLogger(FechaConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            return formatoPantalla2.format((Date) o);
        } catch (Exception ex) {
            Logger.getLogger(FechaConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
