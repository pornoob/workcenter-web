package workcenter.presentacion.consulta_doc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.LogicaServicio;
import workcenter.negocio.consulta_doc.LogicaConsultaDoc;
import workcenter.negocio.personal.LogicaPersonal;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by claudio on 11-04-15.
 */
@Component
@Scope("flow")
public class MantenedorConsultaDoc implements Serializable {
    private List<Cargo> cargos;
    private List<Servicio> servicios;
    private Map<Personal, List<DocumentoPersonal>> personalDocs;
    private List<TipoDocPersonal> tiposDocs;
    private Map<TipoDocPersonal, Boolean> mostrarTipoDoc;

    private Cargo cargo;
    private Servicio servicio;

    @Autowired
    private LogicaConsultaDoc logicaConsultaDoc;
    @Autowired
    private LogicaServicio logicaServicio;
    @Autowired
    private LogicaPersonal logicaPersonal;

    public void inicio() {
        cargos = logicaConsultaDoc.obtenerCargos();
        servicios = logicaServicio.obtenerTodos();
    }

    public void buscarPersonal() {
        List<Personal> personal = logicaPersonal.obtenerPorCargoServicio(cargo, servicio);
        personalDocs = new HashMap<Personal, List<DocumentoPersonal>>();
        mostrarTipoDoc = new HashMap<TipoDocPersonal, Boolean>();
        HashSet<TipoDocPersonal> tiposDocs = new HashSet<TipoDocPersonal>();

        for(Personal p : personal) {
            List<DocumentoPersonal> docs = logicaPersonal.obtenerDocumentosActualizados(p);
            for (DocumentoPersonal d : docs) {
                tiposDocs.add(d.getTipo());
                mostrarTipoDoc.put(d.getTipo(), Boolean.TRUE);
            }
            personalDocs.put(p, docs);
        }
        this.tiposDocs = new ArrayList<TipoDocPersonal>(tiposDocs);
    }

    public String obtenerLuz(Personal p, TipoDocPersonal td) {
        List<DocumentoPersonal> docs = personalDocs.get(p);
        for (DocumentoPersonal dp : docs) {
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

    // getters and setters
    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public List<TipoDocPersonal> getTiposDocs() {
        return tiposDocs;
    }

    public void setTiposDocs(List<TipoDocPersonal> tiposDocs) {
        this.tiposDocs = tiposDocs;
    }

    public Map<TipoDocPersonal, Boolean> getMostrarTipoDoc() {
        return mostrarTipoDoc;
    }

    public void setMostrarTipoDoc(Map<TipoDocPersonal, Boolean> mostrarTipoDoc) {
        this.mostrarTipoDoc = mostrarTipoDoc;
    }

    public Map<Personal, List<DocumentoPersonal>> getPersonalDocs() {
        return personalDocs;
    }

    public void setPersonalDocs(Map<Personal, List<DocumentoPersonal>> personalDocs) {
        this.personalDocs = personalDocs;
    }
}
