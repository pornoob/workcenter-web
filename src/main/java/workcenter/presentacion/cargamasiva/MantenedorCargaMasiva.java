package workcenter.presentacion.cargamasiva;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import workcenter.entidades.Concepto;
import workcenter.negocio.cargamasiva.LogicaCargaMasiva;

@Component
@Scope("flow")
public class MantenedorCargaMasiva implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	LogicaCargaMasiva logicaCargaMasiva;	
	private List<Concepto> listaConceptos;

	
	public void inicio() {
		listaConceptos = logicaCargaMasiva.obtenerConceptos();
    }

	public List<Concepto> getListaConceptos() {
		return listaConceptos;
	}

	public void setListaConceptos(List<Concepto> listaConceptos) {
		this.listaConceptos = listaConceptos;
	}

}