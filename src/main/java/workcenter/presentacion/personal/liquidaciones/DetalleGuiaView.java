package workcenter.presentacion.personal.liquidaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Personal;
import workcenter.entidades.Vuelta;
import workcenter.negocio.maestro_guias.LogicaMaestroGuias;
import workcenter.util.others.DetalleGuiaPdf;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Scope("view")
public class DetalleGuiaView implements Serializable {
    private Personal persona;
    private List<Vuelta> vueltas;

    @Autowired
    private LogicaMaestroGuias logicaMaestroGuias;

    @Autowired
    private DetalleGuiaPdf detalleGuiaPdf;

    public void init(Personal persona, Date fecha) throws ParseException, IOException {
        this.persona = persona;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);

        StringBuilder fechaStr = new StringBuilder("01-");

        if (c.get(Calendar.MONTH)+1 < 10)
            fechaStr.append('0');

        fechaStr.append(c.get(Calendar.MONTH)+1).append(c.get(Calendar.YEAR));

        this.vueltas = logicaMaestroGuias.buscarConProductos(sdf.parse(fechaStr.toString()), fecha, persona);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        response.reset();
        response.setContentType("application/pdf");

        response.setHeader("Content-disposition", "filename=" + "DetalleGuia-"
                + fechaStr.substring(3) + "-" + persona.getRut() + ".pdf");

        OutputStream output = response.getOutputStream();
        output.write(detalleGuiaPdf.render(persona, vueltas));
        output.close();

        facesContext.responseComplete();
    }
}
