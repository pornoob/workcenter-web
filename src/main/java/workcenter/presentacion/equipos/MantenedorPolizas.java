package workcenter.presentacion.equipos;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Documento;
import workcenter.entidades.Empresa;
import workcenter.entidades.Equipo;
import workcenter.entidades.SeguroEquipo;
import workcenter.negocio.LogicaDocumentos;
import workcenter.negocio.LogicaEmpresas;
import workcenter.negocio.LogicaEquipos;
import workcenter.presentacion.includes.FicheroUploader;
import workcenter.util.WorkcenterFileListener;
import workcenter.util.components.SesionCliente;
import workcenter.util.pojo.Descargable;

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
        logicaEquipos.guardarSeguro(seguroEquipo);
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
