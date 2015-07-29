package workcenter.presentacion.hoja_servicio;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import workcenter.entidades.ValoresCargasFamiliares;
import workcenter.negocio.hoja_servicio.LogicaCargasFamiliares;


@Component
@Scope("flow")
public class MantenedorCargarFamiliares implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private LogicaCargasFamiliares logicaCargasFamiliares;
	
	private List<ValoresCargasFamiliares> valoresListaCargasFamiliares;
	
	private ValoresCargasFamiliares oBjValoresCargasFamiliares;
	
	
	public void inicio() {
		valoresListaCargasFamiliares = logicaCargasFamiliares.obtenerValoresCargasFamiliares();
		oBjValoresCargasFamiliares = new ValoresCargasFamiliares();
    }
	
	public void agregarValores(){
		System.out.println("Entro al metodo agregarValores()");
		System.out.println(oBjValoresCargasFamiliares.getMonto());
		logicaCargasFamiliares.guardarValoresCargasFamiliares(oBjValoresCargasFamiliares);
		inicio();
		
	}
	
	public void eliminarValores(ValoresCargasFamiliares vCF){
		logicaCargasFamiliares.eliminarValorCargaFamiliar(vCF);
		inicio();
	}
	
	public void modificarValores(ValoresCargasFamiliares vCF){
		oBjValoresCargasFamiliares = vCF;
	}
	
	
	public List<ValoresCargasFamiliares> getListaCargasFamiliares() {
		return valoresListaCargasFamiliares;
	}

	public void setListaCargasFamiliares(
			List<ValoresCargasFamiliares> listaCargasFamiliares) {
		this.valoresListaCargasFamiliares = listaCargasFamiliares;
	}

	public ValoresCargasFamiliares getoBjValoresCargasFamiliares() {
		return oBjValoresCargasFamiliares;
	}

	public void setoBjValoresCargasFamiliares(
			ValoresCargasFamiliares oBjValoresCargasFamiliares) {
		this.oBjValoresCargasFamiliares = oBjValoresCargasFamiliares;
	}

   
}