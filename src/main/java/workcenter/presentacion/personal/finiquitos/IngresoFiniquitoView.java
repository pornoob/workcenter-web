package workcenter.presentacion.personal.finiquitos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Empresa;
import workcenter.entidades.Finiquito;
import workcenter.entidades.Personal;
import workcenter.negocio.LogicaEmpresas;
import workcenter.negocio.personal.LogicaFiniquito;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.util.components.FacesUtil;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Claudio Olivares
 */
@Component
@Scope("view")
public class IngresoFiniquitoView implements Serializable {
    private List<Empresa> empleadores;
    private List<Personal> trabajadores;
    
    private Empresa empleador;
    private Personal trabajador;
    private Date fecha;
    private Integer monto;
    
    @Autowired
    private LogicaEmpresas logicaEmpresas;
    @Autowired
    private LogicaPersonal logicaPersonal;
    @Autowired
    private LogicaFiniquito logicaFiniquito;
    
    @PostConstruct
    public void inicio() {
        empleadores = logicaEmpresas.obtenerEmpleadores();
        trabajadores = logicaPersonal.findAll();
    }
    
    public void guardar() {
        Finiquito finiquito = new Finiquito();
        
        finiquito.setFechaFiniquito(fecha);
        finiquito.setMonto(monto);
        finiquito.setEmpleador(empleador);
        finiquito.setTrabajador(trabajador);
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            logicaFiniquito.guardar(finiquito);
            FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Finiquito guardado exitosamente (" + sdf.format(new Date()) + ")");
        } catch(Exception e) {
            FacesUtil.mostrarMensajeError("Operación Fallida", "Finiquito no se ha guardado, contacte con el administrador");
        }
    }

    public List<Empresa> getEmpleadores() {
        return empleadores;
    }

    public void setEmpleadores(List<Empresa> empleadores) {
        this.empleadores = empleadores;
    }

    public List<Personal> getTrabajadores() {
        return trabajadores;
    }

    public void setTrabajadores(List<Personal> trabajadores) {
        this.trabajadores = trabajadores;
    }

    public Empresa getEmpleador() {
        return empleador;
    }

    public void setEmpleador(Empresa empleador) {
        this.empleador = empleador;
    }

    public Personal getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Personal trabajador) {
        this.trabajador = trabajador;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }
}
