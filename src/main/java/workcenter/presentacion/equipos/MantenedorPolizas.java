package workcenter.presentacion.equipos;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Documento;
import workcenter.entidades.Empresa;
import workcenter.entidades.Equipo;
import workcenter.entidades.SeguroEquipo;
import workcenter.negocio.LogicaDocumentos;
import workcenter.negocio.LogicaEmpresas;
import workcenter.negocio.equipos.LogicaEquipos;
import workcenter.presentacion.includes.FicheroUploader;
import workcenter.util.WorkcenterFileListener;
import workcenter.util.components.Constantes;
import workcenter.util.components.FacesUtil;
import workcenter.util.components.SesionCliente;
import workcenter.util.pojo.Descargable;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by colivares on 29-08-14.
 */
@Component
@Scope("flow")
public class MantenedorPolizas implements Serializable, WorkcenterFileListener {
    @Autowired
    private LogicaEquipos logicaEquipos;

    @Autowired
    private LogicaEmpresas logicaEmpresas;

    @Autowired
    private LogicaDocumentos logicaDocumentos;

    @Autowired
    private FicheroUploader ficheroUploader;

    @Autowired
    private SesionCliente sesionCliente;

    @Autowired
    private Constantes constantes;

    private List<SeguroEquipo> segurosEquipo;
    private List<Empresa> empresas;
    private Equipo equipo;
    private SeguroEquipo seguroEquipo;
    private boolean esEdicion;
    private Map<String, List<Documento>> comprobante;

    public void inicio(Equipo e) {
        equipo = logicaEquipos.obtenerEquipo(e);
        segurosEquipo = logicaEquipos.obtenerSeguros(equipo);
        empresas = logicaEmpresas.obtenerEmpleadores();
    }

    public String irCrearPoliza() {
        seguroEquipo = new SeguroEquipo();
        seguroEquipo.setEquipo(equipo);
        esEdicion = false;
        return "flowEditar";
    }

    public String irListar() {
        return "flowListar";
    }

    public String finalizarFlow() {
        return "flowFin";
    }

    public void guardar() {
        if (comprobante == null || comprobante.get(sesionCliente.getUsuario().getRut() + "|poliza") == null) {
            FacesUtil.mostrarMensajeError("Operación fallida", "Debes adjuntar la poliza involucrada");
            return;
        }
        logicaEquipos.guardarSeguro(seguroEquipo);
        for (Documento d : comprobante.get(sesionCliente.getUsuario().getRut() + "|poliza")) {
            logicaDocumentos.asociarDocumento(d, seguroEquipo);
        }
        if (!esEdicion)
            segurosEquipo.add(seguroEquipo);
        FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Se ha guardado la poliza");
    }

    public StreamedContent generaDescargable(SeguroEquipo s) {
        List<Documento> docs = logicaDocumentos.obtenerDocumentosAsociados(s);
        File f = new File(constantes.getPathArchivos() + docs.get(docs.size() - 1).getId());
        Descargable d = new Descargable(f);
        d.setNombre(docs.get(docs.size() - 1).getNombreOriginal());
        return d.getStreamedContent();
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public SeguroEquipo getSeguroEquipo() {
        return seguroEquipo;
    }

    public void setSeguroEquipo(SeguroEquipo seguroEquipo) {
        this.seguroEquipo = seguroEquipo;
    }

    public List<SeguroEquipo> getSegurosEquipo() {
        return segurosEquipo;
    }

    public void setSegurosEquipo(List<SeguroEquipo> segurosEquipo) {
        this.segurosEquipo = segurosEquipo;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public boolean isEsEdicion() {
        return esEdicion;
    }

    public void setEsEdicion(boolean esEdicion) {
        this.esEdicion = esEdicion;
    }

    @Override
    public void subir(FileUploadEvent fue) {
        Documento d = ficheroUploader.subir(fue);
        if (comprobante == null) comprobante = new HashMap<String, List<Documento>>();
        if (comprobante.get(sesionCliente.getUsuario().getRut() + "|poliza") == null) {
            List<Documento> docs = new ArrayList<Documento>();
            docs.add(d);
            comprobante.put(sesionCliente.getUsuario().getRut() + "|poliza", docs);
        } else {
            List<Documento> docs = comprobante.get(sesionCliente.getUsuario().getRut() + "|poliza");
            docs.add(d);
            comprobante.put(sesionCliente.getUsuario().getRut() + "|poliza", docs);
        }
    }

    @Override
    public void enlazar(Descargable descargable) {
        Documento d = logicaDocumentos.obtenerPorCodigo(descargable.getArchivo().getName());
        if (comprobante == null) comprobante = new HashMap<String, List<Documento>>();
        if (comprobante.get(sesionCliente.getUsuario().getRut() + "|poliza") == null) {
            List<Documento> docs = new ArrayList<Documento>();
            docs.add(d);
            comprobante.put(sesionCliente.getUsuario().getRut() + "|poliza", docs);
        } else {
            List<Documento> docs = comprobante.get(sesionCliente.getUsuario().getRut() + "|poliza");
            docs.add(d);
            comprobante.put(sesionCliente.getUsuario().getRut() + "|poliza", docs);
        }
    }
}
