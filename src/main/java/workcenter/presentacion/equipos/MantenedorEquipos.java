package workcenter.presentacion.equipos;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.LogicaEmpresas;
import workcenter.negocio.LogicaEquipos;
import workcenter.util.components.Constantes;
import workcenter.util.pojo.Descargable;
import workcenter.util.pojo.FilterOption;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by colivares on 28-08-14.
 */
@Component
@Scope("flow")
public class MantenedorEquipos implements Serializable {
    @Autowired
    private LogicaEquipos logicaEquipos;

    @Autowired
    private Constantes constantes;

    private List<Equipo> equipos;
    private List<Equipo> equiposFiltrados;
    private List<TipoEquipo> tiposEquipos;
    private List<SubtipoEquipo> subtiposEquipos;
    private List<MarcaEquipo> marcasEquipos;
    private List<ModeloEquipo> modelos;
    private List<FilterOption> colores;
    private List<FilterOption> posicionesFoto;
    private List<Empresa> empresas;
    private Equipo equipo;
    private String posicionFoto;
    private SeguroEquipo seguroEquipo;

    public void inicio() {
        equipos = logicaEquipos.obtenerTodos();
        tiposEquipos = logicaEquipos.obtenerTipos();
        subtiposEquipos = logicaEquipos.obtenerSubtipos();
        marcasEquipos = logicaEquipos.obtenerMarcas();
        modelos = logicaEquipos.obtenerModelos();
        colores = new ArrayList<FilterOption>();
        colores.add(new FilterOption(1, "Blanco"));
        colores.add(new FilterOption(2, "Negro"));
        colores.add(new FilterOption(3, "Azul"));
        colores.add(new FilterOption(4, "Rojo"));
        colores.add(new FilterOption(5, "Amarillo"));
        colores.add(new FilterOption(6, "Verde"));
        posicionesFoto = new ArrayList<FilterOption>();
        posicionesFoto.add(new FilterOption(1, "Frontal"));
        posicionesFoto.add(new FilterOption(2, "Posterior"));
        posicionesFoto.add(new FilterOption(3, "Izquierda"));
        posicionesFoto.add(new FilterOption(4, "Derecha"));
    }

    public String irEditar(Equipo e) {
        equipo = e;
        equipo.setFotos(logicaEquipos.obtenerFotos(e));
        return "flowEditar";
    }

    public String irPolizas() {
        seguroEquipo = logicaEquipos.obtenerUltimoSeguro(equipo);
        return "flowPoliza";
    }

    public StreamedContent generaStreamedContent(FotoEquipo fe) {
        File archivo = new File(constantes.getPathArchivos() + fe.getFoto());
        Descargable d = new Descargable(archivo);
        return d.getStreamedContent();
    }

    public boolean estaHabilitado(Equipo e) {
        return logicaEquipos.obtenerEquipoSancionado(e) == null;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public List<Equipo> getEquiposFiltrados() {
        return equiposFiltrados;
    }

    public void setEquiposFiltrados(List<Equipo> equiposFiltrados) {
        this.equiposFiltrados = equiposFiltrados;
    }

    public List<TipoEquipo> getTiposEquipos() {
        return tiposEquipos;
    }

    public void setTiposEquipos(List<TipoEquipo> tiposEquipos) {
        this.tiposEquipos = tiposEquipos;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public List<SubtipoEquipo> getSubtiposEquipos() {
        return subtiposEquipos;
    }

    public void setSubtiposEquipos(List<SubtipoEquipo> subtiposEquipos) {
        this.subtiposEquipos = subtiposEquipos;
    }

    public List<MarcaEquipo> getMarcasEquipos() {
        return marcasEquipos;
    }

    public void setMarcasEquipos(List<MarcaEquipo> marcasEquipos) {
        this.marcasEquipos = marcasEquipos;
    }

    public List<ModeloEquipo> getModelos() {
        return modelos;
    }

    public List<FilterOption> getColores() {
        return colores;
    }

    public void setColores(List<FilterOption> colores) {
        this.colores = colores;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public void setModelos(List<ModeloEquipo> modelos) {
        this.modelos = modelos;
    }

    public List<FilterOption> getPosicionesFoto() {
        return posicionesFoto;
    }

    public void setPosicionesFoto(List<FilterOption> posicionesFoto) {
        this.posicionesFoto = posicionesFoto;
    }

    public String getPosicionFoto() {
        return posicionFoto;
    }

    public void setPosicionFoto(String posicionFoto) {
        this.posicionFoto = posicionFoto.toLowerCase();
    }

    public SeguroEquipo getSeguroEquipo() {
        return seguroEquipo;
    }

    public void setSeguroEquipo(SeguroEquipo seguroEquipo) {
        this.seguroEquipo = seguroEquipo;
    }
}