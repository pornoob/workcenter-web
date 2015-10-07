package workcenter.presentacion.consulta_doc_equipo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import workcenter.entidades.DocumentoEquipo;
import workcenter.entidades.Equipo;
import workcenter.entidades.TipoDocumentoEquipo;
import workcenter.entidades.TipoEquipo;
import workcenter.negocio.equipos.LogicaEquipos;

@Component
@Scope("flow")
public class MantenedorConsultaDocEquipo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<Equipo, List<DocumentoEquipo>> equipoDocs;
    private List<TipoDocumentoEquipo> tiposDocs;
    private Map<TipoDocumentoEquipo, Boolean> mostrarTipoDoc;
    private Equipo equipoSeleccionado;
    private List<TipoEquipo> lstTipoEquipo;
    private TipoEquipo tipoEquipo;
    
    @Autowired
    private LogicaEquipos logicaEquipo;
	
	public void inicio(){
		lstTipoEquipo = logicaEquipo.obtenerTipos();
	}
	
	public void buscarEquipo() {
        List<Equipo> equipo = logicaEquipo.obtenerEquipoTipo(tipoEquipo);
		equipoDocs = new HashMap<Equipo, List<DocumentoEquipo>>();
        mostrarTipoDoc = new HashMap<TipoDocumentoEquipo, Boolean>();
        HashSet<TipoDocumentoEquipo> tiposDocs = new HashSet<TipoDocumentoEquipo>();

        for(Equipo q : equipo) {
            List<DocumentoEquipo> docs = logicaEquipo.obtenerDocumentosActualizados(q);
            for (DocumentoEquipo d : docs) {
                tiposDocs.add(d.getTipo());
                mostrarTipoDoc.put(d.getTipo(), Boolean.TRUE);
            }
            equipoDocs.put(q, docs);
        }
        this.tiposDocs = new ArrayList<TipoDocumentoEquipo>(tiposDocs);
    }
	
	public String obtenerLuz(Equipo e, TipoDocumentoEquipo td) {
        List<DocumentoEquipo> docs = equipoDocs.get(e);
        for (DocumentoEquipo dp : docs) {
            if (!dp.getTipo().equals(td)) continue;
            if (dp.getVencimiento() == null) return "luz_verde";
             Date fechaActual = new Date();
             long diaResta = dp.getVencimiento().getTime()-fechaActual.getTime();
             long dias = TimeUnit.DAYS.convert(diaResta, TimeUnit.MILLISECONDS);
             
            if (dias <= td.getDiasalerta() && dias > 0) return "luz_amarilla";
            else if (dias<=0) return "luz_roja";
            else return "luz_verde";
           
        }
        return null;
    }
	
	public String obtenerMensaje(Equipo e, TipoDocumentoEquipo td) {
        List<DocumentoEquipo> docs = equipoDocs.get(e);
        for (DocumentoEquipo dp : docs) {
            if (!dp.getTipo().equals(td)) continue;
            if (dp.getVencimiento() == null) return "";
            Date fechaActual = new Date();
            long diaResta = dp.getVencimiento().getTime()-fechaActual.getTime();
            long dias = TimeUnit.DAYS.convert(diaResta, TimeUnit.MILLISECONDS);

            if (dias <= td.getDiasalerta() && dias > 0) return " - Vence en "+dias+" día(s)";
            else if (dias<=0) return " - Venció hace "+(dias*-1)+" día(s)";
            else return "";

        }
        return null;
    }
	
	// GETTER AND SETTER
	public Map<Equipo, List<DocumentoEquipo>> getEquipoDocs() {
		return equipoDocs;
	}

	public void setEquipoDocs(Map<Equipo, List<DocumentoEquipo>> equipoDocs) {
		this.equipoDocs = equipoDocs;
	}

	public List<TipoDocumentoEquipo> getTiposDocs() {
		return tiposDocs;
	}

	public void setTiposDocs(List<TipoDocumentoEquipo> tiposDocs) {
		this.tiposDocs = tiposDocs;
	}

	public Map<TipoDocumentoEquipo, Boolean> getMostrarTipoDoc() {
		return mostrarTipoDoc;
	}

	public void setMostrarTipoDoc(Map<TipoDocumentoEquipo, Boolean> mostrarTipoDoc) {
		this.mostrarTipoDoc = mostrarTipoDoc;
	}

	public Equipo getEquipoSeleccionado() {
		return equipoSeleccionado;
	}

	public void setEquipoSeleccionado(Equipo equipoSeleccionado) {
		this.equipoSeleccionado = equipoSeleccionado;
	}

	public List<TipoEquipo> getLstTipoEquipo() {
		return lstTipoEquipo;
	}

	public void setLstTipoEquipo(List<TipoEquipo> lstTipoEquipo) {
		this.lstTipoEquipo = lstTipoEquipo;
	}

	public TipoEquipo getTipoEquipo() {
		return tipoEquipo;
	}

	public void setTipoEquipo(TipoEquipo tipoEquipo) {
		this.tipoEquipo = tipoEquipo;
	}
	
	

}
