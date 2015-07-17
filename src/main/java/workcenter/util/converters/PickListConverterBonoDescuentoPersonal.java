package workcenter.util.converters;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;
import workcenter.entidades.BonoDescuentoPersonal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PickListConverterBonoDescuentoPersonal implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String json) {
        PickList p = (PickList) component;
        DualListModel dl = (DualListModel) p.getValue();
//        System.err.println(json);
//        String regexp = "\"idBono\": \"(.*)\", \"rutPersonal\": \"(.*)\"";
//        Pattern pattern = Pattern.compile(regexp);
//        Matcher matcher = pattern.matcher(json);

//        if (!matcher.find()) return null;
        String[] tmpArreglo = json.split("-");
        Integer idBono = Integer.parseInt(tmpArreglo[0]);
        Integer idPersonal = Integer.parseInt(tmpArreglo[1]);

        for (int i = 0; i < dl.getSource().size(); i++) {
            BonoDescuentoPersonal bdp = (BonoDescuentoPersonal) dl.getSource().get(i);
            if (bdp.getIdBonodescuento().getId().intValue() == idBono.intValue()
                    && bdp.getIdPersonal().getRut().intValue() == idPersonal) {
                return bdp;
            }
        }

        for (int i = 0; i < dl.getTarget().size(); i++) {
            BonoDescuentoPersonal bdp = (BonoDescuentoPersonal) dl.getTarget().get(i);
            if (bdp.getIdBonodescuento().getId().intValue() == idBono.intValue()
                    && bdp.getIdPersonal().getRut().intValue() == idPersonal) {
                return bdp;
            }
        }
        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        BonoDescuentoPersonal bdp = (BonoDescuentoPersonal) value;
        String reg = bdp.getIdBonodescuento().getId().toString()+"-"+ bdp.getIdPersonal().getRut().toString();
        return reg.trim();
        //return "{\"idBono\":\"" + bdp.getIdBonodescuento().getId() + "\", \"rutPersonal\": \"" + bdp.getIdPersonal().getRut() + "\"}";
    }
}