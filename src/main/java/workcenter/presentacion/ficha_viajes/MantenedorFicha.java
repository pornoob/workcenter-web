package workcenter.presentacion.ficha_viajes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import workcenter.entidades.Equipo;
import workcenter.entidades.Personal;
import workcenter.entidades.ViajesTortola;
import workcenter.negocio.equipos.LogicaEquipos;
import workcenter.negocio.ficha_viajes.LogicaFicha;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.util.components.FacesUtil;

@Component
@Scope("flow")
public class MantenedorFicha implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int SUBTIPO = 1;
    private List<Equipo> equipos;
    private List<Personal> personal;
    private Personal persona;
    private List<String> destino;
    private ViajesTortola viajes;
    
	
	@Autowired
	private LogicaFicha logicaFicha;
	
	@Autowired
	private LogicaEquipos logicaEquipos;
	
	@Autowired
	private LogicaPersonal logicaPersonal;
	
	public void inicio() {
	viajes = new ViajesTortola();
	personal = logicaPersonal.obtenerConductores();
	equipos = logicaEquipos.obtenerTractos();
	destino = new ArrayList<String>();
    destino.add("CHAGRES");
    destino.add("PVSA");
		
    }
	
	public List<String> destinoAutocomplete(String query) {
				
        List<String> destinoFiltro = new ArrayList<String>();
         
        for (int i = 0; i < destino.size(); i++) {
        	String skin = destino.get(i);
            if(skin.toLowerCase().startsWith(query)) {
            	destinoFiltro.add(skin);
            }
        }
         
        return destinoFiltro;
    }
	
	public Equipo obtenerCamion(){
		//camion = logicaEquipos.obtenerEquipo(numCamion,1);
		return null;
	}
	
	public void guardarFicha(){
		
		for (Equipo e : equipos) {
		
			if (e.getNumero().intValue() == viajes.getNumerTracto().intValue()){
			 viajes.setNomTracto(e.getPatente());
			 viajes.setNumerTracto(e.getNumero());
			 break;
		 	}
		}		

		logicaFicha.guardarFicha(viajes);		
		FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Se ha guardado correctamente la ficha con ID: ".concat(viajes.getId()));
		viajes = new ViajesTortola();
	
	}

	public Personal getPersona() {
		return persona;
	}

	public void setPersona(Personal persona) {
		this.persona = persona;
	}

	public List<Equipo> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<Equipo> equipos) {
		this.equipos = equipos;
	}

	public List<Personal> getPersonal() {
		return personal;
	}

	public void setPersonal(List<Personal> personal) {
		this.personal = personal;
	}

	public List<String> getDestino() {
		return destino;
	}

	public void setDestino(List<String> destino) {
		this.destino = destino;
	}

	public ViajesTortola getViajes() {
		return viajes;
	}

	public void setViajes(ViajesTortola viajes) {
		this.viajes = viajes;
	}

}
