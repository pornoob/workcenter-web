package workcenter.presentacion.ficha_viajes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.taglibs.standard.tag.common.fmt.ParseDateSupport;
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
	private Equipo equipo;
	private List<Personal> personal;
	private List<ViajesTortola> lViajes;
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
		lViajes = logicaFicha.obtenerTodasFichaViajes();
		destino = new ArrayList<String>();
		destino.add("CHAGRES");
		destino.add("PVSA");

	}

	public List<String> destinoAutocomplete(String query) {

		List<String> destinoFiltro = new ArrayList<String>();

		for (int i = 0; i < destino.size(); i++) {
			String skin = destino.get(i);
			if (skin.toLowerCase().startsWith(query)) {
				destinoFiltro.add(skin);
			}
		}

		return destinoFiltro;
	}
	public  void obtenerPatente(){
	    Map<String, String> requestParamMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	    if (requestParamMap.containsKey("numero")) {
	        String numPatente = requestParamMap.get("numero");
	        boolean encontrarPatente = false;
		    for (Equipo e : equipos) {

			if (e.getNumero().intValue() == Integer.parseInt(numPatente)) {
				equipo = e;
				encontrarPatente = true;
			}
		}
		if (!encontrarPatente){equipo = new Equipo();}
	    }

	}

	public void guardarFicha() {
		if (viajes.getNumerTracto() == null || viajes.getDestino() == null
				|| viajes.getFecha() == null
				|| viajes.getNom_conductor() == null
				|| viajes.getHora() == null || viajes.getTonelaje() == null
				|| viajes.getNumGuia() == null) {
			FacesUtil.mostrarMensajeError("Operación Fallida",
					"Debe ingresar todos los campos");
		} else {
			ViajesTortola guia = logicaFicha.obtenerGuia(viajes.getNumGuia());
			if (guia == null) {
				for (Equipo e : equipos) {

					if (e.getNumero().intValue() == viajes.getNumerTracto()
							.intValue()) {
						viajes.setNomTracto(e.getPatente());
						viajes.setNumerTracto(e.getNumero());
						break;
					}
				}

				logicaFicha.guardarFicha(viajes);
				FacesUtil.mostrarMensajeInformativo("Operación exitosa",
						"Se ha guardado correctamente la ficha con ID: "
								.concat(viajes.getId()));
				viajes = new ViajesTortola();
			} else
				FacesUtil.mostrarMensajeError("Operación Fallida",
						"Numero de Guía Duplicada : ".concat(viajes
								.getNumGuia().toString()));
		}
	}

	public String regresarListado() {
		lViajes = logicaFicha.obtenerTodasFichaViajes();
		return "flowMenuViajes";
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

	public List<ViajesTortola> getlViajes() {
		return lViajes;
	}

	public void setlViajes(List<ViajesTortola> lViajes) {
		this.lViajes = lViajes;
	}

	public Equipo getEquipo() {
	    return equipo;
	}

	public void setEquipo(Equipo equipo) {
	    this.equipo = equipo;
	}

}
