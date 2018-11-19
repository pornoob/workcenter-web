package workcenter.presentacion.maestro_guias;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.contrato_empresa.LogicaContratoEmpresa;
import workcenter.negocio.equipos.LogicaEquipos;
import workcenter.negocio.guia_petroleo.LogicaGuiaDePetroleo;
import workcenter.negocio.maestro_guias.LogicaMaestroGuias;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.negocio.tramo_contrato.LogicaTramoContrato;

import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.AjaxBehaviorEvent;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by renholders on 23-11-2015.
 */
@Component
@Scope("flow")
public class MantenedorMaestroGuias implements Serializable {
    private static final Logger LOG = Logger.getLogger(MantenedorMaestroGuias.class.getName());

    private Integer ordenConsulta;
    private Vuelta ordenDeCarga;
    private List<Equipo> lstEquipos;
    private List<Equipo> lstBateas;
    private Equipo equipoSeleccionado;
    private List<Personal> lstConductores;
    private List<ContratoEmpresa> lstEmpresas;
    private List<TramoContrato> lstTramoContrato;
    private ContratoEmpresa empresaSeleccionada;
    private TramoContrato tramoContratoSeleccionado;
    private List<TipoProducto> lstTipoDeProductos;
    private Producto producto;
    private GuiaPetroleo guiaPetroleo;
    private GuiaPetroleo guiaPetroleoToEdit;
    private List<EstacionServicio> lstEstacionServicio;
    private Date fechaDesde;
    private Date fechaHasta;
    private Personal conductor;
    private List<Personal> conductores;
    private List<Vuelta> ordenesCarga;

    @Autowired
    private LogicaMaestroGuias logicaMaestroGuias;
    @Autowired
    private LogicaEquipos logicaEquipos;
    @Autowired
    private LogicaPersonal logicaPersonal;
    @Autowired
    private LogicaContratoEmpresa logicaContratoEmpresa;
    @Autowired
    private LogicaTramoContrato logicaTramoContrato;
    @Autowired
    private LogicaGuiaDePetroleo logicaGuiaDePetroleo;

    public void inicio() {
        ordenDeCarga = new Vuelta();
        ordenDeCarga.setGuiasPetroleo(new ArrayList<GuiaPetroleo>());
        ordenDeCarga.setProductosList(new ArrayList<Producto>());
        lstEquipos = logicaEquipos.obtenerTractos();
        lstConductores = logicaPersonal.obtenerConductoresConSanciones();
        lstBateas = logicaEquipos.obtenerBateas();
        lstEmpresas = logicaContratoEmpresa.obtenerContratoEmpresasConEmpresa();
    }

    public void buscarGuias() {
        ordenesCarga = logicaMaestroGuias.buscar(fechaDesde, fechaHasta, conductor);
    }

    public void consultarOrdenDeCarga() {
        ordenDeCarga = logicaMaestroGuias.obtenerOrdendeCarga(ordenConsulta);
    }

    public boolean estaBloqueado() {
        if (ordenDeCarga.getConductor() != null) {
            return ordenDeCarga.getConductor().getSancion() != null;
        }
        return false;
    }

    public boolean estaBloqueadoTracto() {
        if (ordenDeCarga.getTracto() == null) return false;
        //terminar metodo
        return false;
    }

    public void buscarTramoContrato(AjaxBehaviorEvent event) {
        empresaSeleccionada = (ContratoEmpresa) ((HtmlSelectOneMenu) event.getSource()).getValue();
        lstTipoDeProductos = new ArrayList<>();
        lstTramoContrato = logicaTramoContrato.obtenerTramoPorContratoParaGuias(empresaSeleccionada);
    }

    public void irGuardarProducto() {
        producto = new Producto();
    }

    public void irEditarProducto(Producto p) {
        producto = p;
    }

    public void irEliminarProducto(Producto p) {
        ordenDeCarga.getProductosList().remove(p);
    }

    public void irGuiasDePretrolio() {
        if (ordenDeCarga == null) return;
        System.err.println("se obtiene lstEstacionServicio, guia de pretroleo se instancea");
        lstEstacionServicio = logicaGuiaDePetroleo.obtenerEstacionesDeServicio();
        guiaPetroleo = new GuiaPetroleo();
    }

    public void irEditarGuiaPetroleo(GuiaPetroleo gp) {
        if (ordenDeCarga == null) return;
        lstEstacionServicio = logicaGuiaDePetroleo.obtenerEstacionesDeServicio();
        guiaPetroleo = gp;
    }

    public void irEliminarGuiaPetroleo(GuiaPetroleo gp) {
        if (ordenDeCarga == null) return;
        ordenDeCarga.getGuiasPetroleo().remove(gp);
    }

    public void onRowSelect(SelectEvent event) {
        guiaPetroleo = guiaPetroleoToEdit;
    }

    public void onRowUnselect(UnselectEvent event) {
        System.err.println("rowUnselect");
        guiaPetroleo = new GuiaPetroleo();
    }

    public Integer calcularTotalRendicion(GuiaPetroleo guiaPetroleo) {
        if (guiaPetroleo == null) return 0;
        return (int) (guiaPetroleo.getLitros() * guiaPetroleo.getPreciolitro());
    }

    public void guardarGuiaDePetrolio() {
        guiaPetroleo.setOrdendecarga(ordenDeCarga);
        if (!ordenDeCarga.getGuiasPetroleo().contains(guiaPetroleo)) ordenDeCarga.getGuiasPetroleo().add(guiaPetroleo);
        guiaPetroleo = new GuiaPetroleo();
    }

    public void guardarProducto() {
        producto.setOrdencarga(ordenDeCarga);
        producto.setTramo(tramoContratoSeleccionado);
        if (!ordenDeCarga.getProductosList().contains(producto)) ordenDeCarga.getProductosList().add(producto);
        producto = new Producto();
    }

    public String irConsulta() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String fechaDesdeTmp = sdf.format(new Date());
        String fechaHastaTmp = sdf.format(new Date());
        try {
            fechaDesdeTmp = fechaDesdeTmp.concat("-01");
            int anio = Integer.parseInt(fechaDesdeTmp.split("-")[0]);
            int mes = Integer.parseInt(fechaDesdeTmp.split("-")[1]);
            Calendar calendario = Calendar.getInstance();
            calendario.set(anio, mes - 1, 1);
            int ultimoDiaMes = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
            fechaHastaTmp = fechaHastaTmp.concat("-".concat(String.valueOf(ultimoDiaMes)));
            SimpleDateFormat sdfParse = new SimpleDateFormat("yyyy-MM-dd");
            fechaDesde = sdfParse.parse(fechaDesdeTmp);
            fechaHasta = sdfParse.parse(fechaHastaTmp);
            conductores = logicaPersonal.obtenerConductores();
        } catch (NumberFormatException | ParseException ex) {
            LOG.info(ex.getMessage());
        }
        return "flowConsulta";
    }

    public void guardarOrdenDeCarga() {
        logicaMaestroGuias.guardarOrdenDeCarga(ordenDeCarga);
    }

    public Integer getOrdenConsulta() {
        return ordenConsulta;
    }

    public void setOrdenConsulta(Integer ordenConsulta) {
        this.ordenConsulta = ordenConsulta;
    }

    public Vuelta getOrdenDeCarga() {
        return ordenDeCarga;
    }

    public void setOrdenDeCarga(Vuelta ordenDeCarga) {
        this.ordenDeCarga = ordenDeCarga;
    }

    public List<Equipo> getLstEquipos() {
        return lstEquipos;
    }

    public void setLstEquipos(List<Equipo> lstEquipos) {
        this.lstEquipos = lstEquipos;
    }

    public Equipo getEquipoSeleccionado() {
        return equipoSeleccionado;
    }

    public void setEquipoSeleccionado(Equipo equipoSeleccionado) {
        this.equipoSeleccionado = equipoSeleccionado;
    }

    public List<Personal> getLstConductores() {
        return lstConductores;
    }

    public void setLstConductores(List<Personal> lstConductores) {
        this.lstConductores = lstConductores;
    }

    public List<Equipo> getLstBateas() {
        return lstBateas;
    }

    public void setLstBateas(List<Equipo> lstBateas) {
        this.lstBateas = lstBateas;
    }

    public List<ContratoEmpresa> getLstEmpresas() {
        return lstEmpresas;
    }

    public void setLstEmpresas(List<ContratoEmpresa> lstEmpresas) {
        this.lstEmpresas = lstEmpresas;
    }

    public List<TramoContrato> getLstTramoContrato() {
        return lstTramoContrato;
    }

    public void setLstTramoContrato(List<TramoContrato> lstTramoContrato) {
        this.lstTramoContrato = lstTramoContrato;
    }

    public ContratoEmpresa getEmpresaSeleccionada() {
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(ContratoEmpresa empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

    public List<TipoProducto> getLstTipoDeProductos() {
        return lstTipoDeProductos;
    }

    public void setLstTipoDeProductos(List<TipoProducto> lstTipoDeProductos) {
        this.lstTipoDeProductos = lstTipoDeProductos;
    }

    public TramoContrato getTramoContratoSeleccionado() {
        return tramoContratoSeleccionado;
    }

    public void setTramoContratoSeleccionado(TramoContrato tramoContratoSeleccionado) {
        this.tramoContratoSeleccionado = tramoContratoSeleccionado;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public GuiaPetroleo getGuiaPetroleo() {
        return guiaPetroleo;
    }

    public void setGuiaPetroleo(GuiaPetroleo guiaPetroleo) {
        this.guiaPetroleo = guiaPetroleo;
    }

    public List<EstacionServicio> getLstEstacionServicio() {
        return lstEstacionServicio;
    }

    public void setLstEstacionServicio(List<EstacionServicio> lstEstacionServicio) {
        this.lstEstacionServicio = lstEstacionServicio;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Personal getConductor() {
        return conductor;
    }

    public void setConductor(Personal conductor) {
        this.conductor = conductor;
    }

    public List<Personal> getConductores() {
        return conductores;
    }

    public void setConductores(List<Personal> conductores) {
        this.conductores = conductores;
    }

    public List<Vuelta> getOrdenesCarga() {
        return ordenesCarga;
    }

    public void setOrdenesCarga(List<Vuelta> ordenesCarga) {
        this.ordenesCarga = ordenesCarga;
    }

    public GuiaPetroleo getGuiaPetroleoToEdit() {
        return guiaPetroleoToEdit;
    }

    public void setGuiaPetroleoToEdit(GuiaPetroleo guiaPetroleoToEdit) {
        this.guiaPetroleoToEdit = guiaPetroleoToEdit;
    }
}
