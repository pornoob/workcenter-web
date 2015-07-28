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
	LogicaCargasFamiliares logicaCargasFamiliares;
	
	List<ValoresCargasFamiliares> listaCargasFamiliares;
	
	public void inicio() {
		listaCargasFamiliares = logicaCargasFamiliares.obtenerCargasFamiliares();
    }

	public List<ValoresCargasFamiliares> getListaCargasFamiliares() {
		return listaCargasFamiliares;
	}

	public void setListaCargasFamiliares(
			List<ValoresCargasFamiliares> listaCargasFamiliares) {
		this.listaCargasFamiliares = listaCargasFamiliares;
	}

   
}