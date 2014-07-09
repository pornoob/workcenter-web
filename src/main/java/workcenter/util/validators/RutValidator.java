package workcenter.util.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author colivares
 */
public class RutValidator implements Validator {

    @SuppressWarnings("null")
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String rut = o.toString();
        if (rut == null || rut.trim().isEmpty() || rut.indexOf('-') < 0 || rut.split("-").length != 2 || rut.trim().indexOf(' ') > 0) {
            lanzarError();
        }
        rut = rut.toUpperCase();
        rut = rut.split("-")[0];
        char dv = rut.split("-")[1].charAt(0);
        for (char c : rut.toCharArray()) {
            if (c < '0' || c > '9') {
                lanzarError();
            }
        }
        if ((dv > '9' || dv < '0') && dv != 'K') {
            lanzarError();
        }
        if (!modulo11(rut, dv)) {
            lanzarError();
        }
    }

    private boolean modulo11(String rut, char dv) {
        int suma = 0, factor = 2;
        for (int i = rut.length() - 1; i >= 0; i--) {
            suma += factor * (rut.charAt(i)-'0');
            factor = factor == 7 ? 2 : factor + 1;
        }
        suma = (suma*10)%11;
        return (suma==10 && dv=='K') || (suma+'0' == dv);
    }

    private void lanzarError() {
        FacesMessage msg = new FacesMessage("El RUT ingresado no es válido");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(msg);
    }
}
