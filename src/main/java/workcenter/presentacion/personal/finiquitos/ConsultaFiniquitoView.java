package workcenter.presentacion.personal.finiquitos;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Finiquito;
import workcenter.negocio.personal.LogicaFiniquito;

/**
 *
 * @author Claudio Olivares
 */
@Component
@Scope("view")
public class ConsultaFiniquitoView implements Serializable {

    private Integer mes;
    private Integer anio;
    
    private List<Finiquito> finiquitos;
    
    @Autowired
    private LogicaFiniquito logicaFiniquito;

    @PostConstruct
    private void init() {
        Date fecha = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        mes = calendar.get(Calendar.MONTH) + 1;
        anio = calendar.get(Calendar.YEAR);
        consultar();
    }
    
    public void consultar() {
        finiquitos = logicaFiniquito.obtenerFiniquitosTrabajador(mes, anio);
    }
    
    public void eliminar(Finiquito finiquito) {
        logicaFiniquito.eliminar(finiquito);
        finiquitos = logicaFiniquito.obtenerFiniquitosTrabajador(mes, anio);
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public List<Finiquito> getFiniquitos() {
        return finiquitos;
    }

    public void setFiniquitos(List<Finiquito> finiquitos) {
        this.finiquitos = finiquitos;
    }
}
