package workcenter.presentacion.personal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import workcenter.entidades.BonoDescuentoPersonal;
import workcenter.entidades.ContratoPersonal;
import workcenter.entidades.Personal;
import workcenter.entidades.Remuneracion;
import workcenter.entidades.ValorPrevisionPersonal;
import workcenter.negocio.personal.LogicaLiquidaciones;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.util.components.Constantes;

/**
 * Created by claudio on 16-05-15.
 */
@Component
@Scope("flow")
public class MantenedorLiquidaciones implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Personal> personal;

    private List<BonoDescuentoPersonal> bonoNoImponibles;

    private List<BonoDescuentoPersonal> bonoImponibles;

    private List<ValorPrevisionPersonal> valorprevision;

    @Autowired
    private LogicaPersonal logicaPersonal;

    @Autowired
    private LogicaLiquidaciones logicaLiquidaciones;

    private Remuneracion liquidacion;

    public void inicio() {
        liquidacion = new Remuneracion();
    }

    public void cargarDatos() {
        //obs: ingresar constasntes en la clase existente;
        bonoImponibles = new ArrayList<BonoDescuentoPersonal>();
        bonoNoImponibles = new ArrayList<BonoDescuentoPersonal>();
        valorprevision = new ArrayList<ValorPrevisionPersonal>();
        Integer totalNoImponible = 0;
        Integer totalImponible = 0;

        if (liquidacion.getIdPersonal() == null) return;

        liquidacion.setIdPersonal(logicaPersonal.obtenerConDatosLiquidacion(liquidacion.getIdPersonal()));
        for (BonoDescuentoPersonal bDP : liquidacion.getIdPersonal().getBonosDescuentos()) {
            if (bDP.getIdBonodescuento().getImponible()) {
                bonoImponibles.add(bDP);
                totalImponible = totalImponible + bDP.getMonto().intValue();
            } else {
                bonoNoImponibles.add(bDP);
                totalNoImponible = totalNoImponible + bDP.getMonto().intValue();
            }
        }
        // sueldo base y gratificacion
        ContratoPersonal cp = logicaLiquidaciones.obtenerDatosContrato(liquidacion.getIdPersonal());
        valorprevision = logicaLiquidaciones.obtenerDatosPrevision(cp.getNumero());
        liquidacion.setSueldoBase(cp.getSueldoBase());
        Double gratificacion = (cp.getSueldoBase() * 0.25);
        liquidacion.setGratificacion(gratificacion.intValue());
        liquidacion.setTotalImponible(liquidacion.getSueldoBase() + liquidacion.getGratificacion() + totalImponible);
        liquidacion.setTotalHaberes(liquidacion.getSueldoBase() + liquidacion.getGratificacion() + totalImponible + totalNoImponible);
        //calculo afp y salud
        for (ValorPrevisionPersonal vPP : valorprevision) {
            Double descuentoPrevision = (liquidacion.getTotalImponible() * vPP.getValor() / 100);
            Double descuentoAfp = liquidacion.getTotalImponible() * vPP.getValor() / 100;
            if (vPP.getPrevision().getTipo().equals("salud")) {
                liquidacion.setDctoPrevision(descuentoPrevision.intValue());
            } else {
                liquidacion.setDectoAFP(descuentoAfp.intValue());
            }
        }
        liquidacion.setRentaAfecta(liquidacion.getTotalImponible() -
                (liquidacion.getDctoPrevision() + liquidacion.getDectoAFP()));
        liquidacion.setAlcanceLiquido(liquidacion.getTotalHaberes() -
                (liquidacion.getDctoPrevision() + liquidacion.getDectoAFP()));
        // seguro cesantia
        Double seguroEmpresa = (liquidacion.getTotalImponible()*constantes.getAportePorcentajeEmpleador()) / 100;
    	Double seguroTrabajador = (liquidacion.getTotalImponible()*constantes.getAportePorcentajeTrabajador()) / 100;
        liquidacion.setAporteMontoEmpresa(seguroEmpresa.intValue());
        liquidacion.setAporteMontoTrabajador(seguroTrabajador.intValue());
        liquidacion.setAlcanceLiquido(liquidacion.getAlcanceLiquido() - liquidacion.getAporteMontoTrabajador());
        liquidacion.setHorasExtras(0);
    }

    public String ingresarLiquidacionOtros() {
        personal = logicaPersonal.obtenerTodos();
        return "flowAgregarLiqOtros";
    }

    public String ingresarLiquidacionConductores() {
        personal = logicaPersonal.obtenerTodos();
        return "flowAgregarLiqConductores";
    }

    public List<Personal> getPersonal() {
        return personal;
    }

    public void setPersonal(List<Personal> personal) {
        this.personal = personal;
    }

    public Remuneracion getLiquidacion() {
        return liquidacion;
    }

    public void setLiquidacion(Remuneracion liquidacion) {
        this.liquidacion = liquidacion;
    }

    public List<BonoDescuentoPersonal> getBonoNoImponibles() {
        return bonoNoImponibles;
    }

    public void setBonoNoImponibles(List<BonoDescuentoPersonal> bonoNoImponibles) {
        this.bonoNoImponibles = bonoNoImponibles;
    }

    public List<BonoDescuentoPersonal> getBonoImponibles() {
        return bonoImponibles;
    }

    public void setBonoImponibles(List<BonoDescuentoPersonal> bonoImponibles) {
        this.bonoImponibles = bonoImponibles;
    }

    public List<ValorPrevisionPersonal> getValorprevision() {
        return valorprevision;
    }

    public void setValorprevision(List<ValorPrevisionPersonal> valorprevision) {
        this.valorprevision = valorprevision;
    }


}
