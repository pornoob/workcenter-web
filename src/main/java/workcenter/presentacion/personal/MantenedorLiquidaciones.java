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
import workcenter.negocio.personal.LogicaLiquidaciones;
import workcenter.negocio.personal.LogicaPersonal;

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

    @Autowired
    private LogicaPersonal logicaPersonal;
    
    @Autowired
    private LogicaLiquidaciones logicaLiquidaciones;
    
    private Remuneracion liquidacion;
    
    public void inicio(){
    	liquidacion = new Remuneracion();
    	
    }
    
    public void cargarDatos(){
    	bonoImponibles = new ArrayList<BonoDescuentoPersonal>();
    	bonoNoImponibles = new ArrayList<BonoDescuentoPersonal>();
    	if (liquidacion.getIdPersonal() == null){
    		System.out.println("esta nulo");
    	}else{
    	for (BonoDescuentoPersonal bDP : liquidacion.getIdPersonal().getBonosDescuentos()) {
			if (bDP.getIdBonodescuento().getImponible()){
				bonoImponibles.add(bDP);
			}else {bonoNoImponibles.add(bDP);}
		}
    	liquidacion.getIdPersonal().getBonosDescuentos();
    	ContratoPersonal cp = new ContratoPersonal();
    	cp = logicaLiquidaciones.obtenerDatosContrato(liquidacion.getIdPersonal());  	
    	liquidacion.setSueldoBase(cp.getSueldoBase());
    	Double gratificacion = (cp.getSueldoBase()*0.25);
    	liquidacion.setGratificacion(gratificacion.intValue());
    	liquidacion.setHorasExtras(0);
    	}
    }
    
    public  void cargarDiasTrabajados(){
    	
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
    
    
}
