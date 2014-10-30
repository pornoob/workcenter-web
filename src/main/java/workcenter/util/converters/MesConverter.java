package workcenter.util.converters;

import org.springframework.beans.factory.annotation.Autowired;
import workcenter.util.components.Constantes;
import workcenter.util.dto.Mes;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Created by claudio on 16-10-14.
 */
public class MesConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Mes m = new Mes(s.split("\\|")[0], s.split("\\|")[1]);
        return m;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (!(o instanceof Mes))
            return null;
        return ((Mes)o).getId()+"|"+((Mes)o).getNombre();
    }
}
