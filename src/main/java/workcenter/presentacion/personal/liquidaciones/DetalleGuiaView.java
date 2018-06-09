package workcenter.presentacion.personal.liquidaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Personal;
import workcenter.entidades.Vuelta;
import workcenter.negocio.maestro_guias.LogicaMaestroGuias;

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
    LogicaMaestroGuias logicaMaestroGuias;

    public void init(Personal persona, Date fecha) throws ParseException {
        this.persona = persona;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        StringBuilder fechaInicio = new StringBuilder("01-");

        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        if (c.get(Calendar.MONTH) < 10)
            fechaInicio.append('0');
        fechaInicio.append(c.get(Calendar.MONTH)+1)
                .append('-')
                .append(c.get(Calendar.YEAR));

        this.vueltas = logicaMaestroGuias.buscar(sdf.parse(fechaInicio.toString()), fecha, this.persona);
    }

    public List<Vuelta> getVueltas() {
        return vueltas;
    }

    public void setVueltas(List<Vuelta> vueltas) {
        this.vueltas = vueltas;
    }
}
