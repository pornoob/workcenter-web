package workcenter.presentacion;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.BonoDescuentoRemuneracion;
import workcenter.entidades.Empresa;
import workcenter.entidades.Remuneracion;
import workcenter.entidades.Personal;
import workcenter.negocio.LogicaEmpresas;
import workcenter.negocio.LogicaLibroRemuneraciones;
import workcenter.negocio.LogicaPersonal;
import workcenter.util.components.Constantes;
import workcenter.util.pojo.BonosTablaRemuneracion;
import workcenter.util.pojo.DescuentosTablaRemuneracion;

/**
 * @author colivares
 */
@Component
@Scope("flow")
public class MantenedorRemuneraciones implements Serializable {

    @Autowired
    private LogicaLibroRemuneraciones logicaLibroRemuneraciones;

    @Autowired
    private LogicaPersonal logicaPersonal;

    @Autowired
    private LogicaEmpresas logicaEmpresas;

    @Autowired
    private Constantes constantes;

    private Integer criterio = null;
    private List<Personal> conductores;
    private Personal conductorSeleccionado;
    private List<Empresa> empleadores;
    private Empresa empleadorSeleccionado;
    private List<Remuneracion> remuneraciones;
    private Integer mesSeleccionado;
    private Integer anioIngresado;
    private BonosTablaRemuneracion bonosTablaRemuneracion;
    private DescuentosTablaRemuneracion descuentosTablaRemuneracion;
    private String[] claseTablas = {
        "ui-datatable-even", "ui-datatable-odd"
    };
    private int colorFila;

    public String inicio() {
        conductores = obtenerConductores();
        empleadores = obtenerEmpleadores();
        colorFila = 0;
        return "flowInicio";
    }

    public void filtrarRemuneraciones() {
        if (criterio == constantes.getFiltroConductor()) {
            remuneraciones = logicaLibroRemuneraciones.obtenerSegunConductor(conductorSeleccionado, mesSeleccionado, anioIngresado);
        } else {
            remuneraciones = logicaLibroRemuneraciones.obtenerSegunEmpleador(empleadorSeleccionado, mesSeleccionado, anioIngresado);
        }
        bonosTablaRemuneracion = new BonosTablaRemuneracion(remuneraciones);
        descuentosTablaRemuneracion = new DescuentosTablaRemuneracion(remuneraciones);
        colorFila = 0;
    }

    public String obtenerColorFila() {
        return claseTablas[(colorFila++%claseTablas.length)];
    }

    public List<BonoDescuentoRemuneracion> obtenerBonos(Remuneracion r) {
        List<BonoDescuentoRemuneracion> retorno = new ArrayList<BonoDescuentoRemuneracion>();
        if (bonosTablaRemuneracion == null) {
            return retorno;
        }
        for (String s : bonosTablaRemuneracion.getTipos()) {
            boolean encontrado = false;
            if (r != null && r.getRemuneracionBonoDescuentoList() != null) {
                for (BonoDescuentoRemuneracion b : r.getRemuneracionBonoDescuentoList()) {
                    if (b.getDescripcion().equals(s)) {
                        encontrado = true;
                        retorno.add(b);
                        break;
                    }
                }
            }
            if (!encontrado) {
                BonoDescuentoRemuneracion b = new BonoDescuentoRemuneracion();
                b.setDescripcion(s);
                b.setMonto(BigInteger.ZERO);
                retorno.add(b);
            }
        }
        return retorno;
    }
    
    public List<BonoDescuentoRemuneracion> obtenerDescuentos(Remuneracion r) {
        List<BonoDescuentoRemuneracion> retorno = new ArrayList<BonoDescuentoRemuneracion>();
        if (descuentosTablaRemuneracion == null) {
            return retorno;
        }
        for (String s : descuentosTablaRemuneracion.getTipos()) {
            boolean encontrado = false;
            if (r != null && r.getRemuneracionBonoDescuentoList() != null) {
                for (BonoDescuentoRemuneracion d : r.getRemuneracionBonoDescuentoList()) {
                    if (d.getDescripcion().equals(s)) {
                        encontrado = true;
                        retorno.add(d);
                        break;
                    }
                }
            }
            if (!encontrado) {
                BonoDescuentoRemuneracion d = new BonoDescuentoRemuneracion();
                d.setDescripcion(s);
                d.setMonto(BigInteger.ZERO);
                retorno.add(d);
            }
        }
        return retorno;
    }

    public List<Personal> obtenerConductores() {
        return logicaPersonal.obtenerConductores();
    }

    public List<Empresa> obtenerEmpleadores() {
        return logicaEmpresas.obtenerEmpleadores();
    }

    public void criterioEmpleador() {
        criterio = constantes.getFiltroEmpleador();
    }

    public void criterioConductor() {
        criterio = constantes.getFiltroConductor();
    }

    public Integer getCriterio() {
        return criterio;
    }

    public void setCriterio(Integer criterio) {
        this.criterio = criterio;
    }

    public List<Personal> getConductores() {
        return conductores;
    }

    public void setConductores(List<Personal> conductores) {
        this.conductores = conductores;
    }

    public Personal getConductorSeleccionado() {
        return conductorSeleccionado;
    }

    public void setConductorSeleccionado(Personal conductorSeleccionado) {
        this.conductorSeleccionado = conductorSeleccionado;
    }

    public List<Empresa> getEmpleadores() {
        return empleadores;
    }

    public void setEmpleadores(List<Empresa> empleadores) {
        this.empleadores = empleadores;
    }

    public Empresa getEmpleadorSeleccionado() {
        return empleadorSeleccionado;
    }

    public void setEmpleadorSeleccionado(Empresa empleadorSeleccionado) {
        this.empleadorSeleccionado = empleadorSeleccionado;
    }

    public List<Remuneracion> getRemuneraciones() {
        return remuneraciones;
    }

    public void setRemuneraciones(List<Remuneracion> remuneraciones) {
        this.remuneraciones = remuneraciones;
    }

    public Integer getMesSeleccionado() {
        return mesSeleccionado;
    }

    public void setMesSeleccionado(Integer mesSeleccionado) {
        this.mesSeleccionado = mesSeleccionado;
    }

    public Integer getAnioIngresado() {
        return anioIngresado;
    }

    public void setAnioIngresado(Integer anioIngresado) {
        this.anioIngresado = anioIngresado;
    }

    public BonosTablaRemuneracion getBonosTablaRemuneracion() {
        return bonosTablaRemuneracion;
    }

    public void setBonosTablaRemuneracion(BonosTablaRemuneracion bonosTablaRemuneracion) {
        this.bonosTablaRemuneracion = bonosTablaRemuneracion;
    }

    public DescuentosTablaRemuneracion getDescuentosTablaRemuneracion() {
        return descuentosTablaRemuneracion;
    }

    public void setDescuentosTablaRemuneracion(DescuentosTablaRemuneracion descuentosTablaRemuneracion) {
        this.descuentosTablaRemuneracion = descuentosTablaRemuneracion;
    }
}
