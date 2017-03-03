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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by renholders on 23-11-2015.
 */
@Component
@Scope("flow")
public class MantenedorMaestroGuias implements Serializable{

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
    private List<GuiaPetroleo> lstGuiaDePetrolio;
    private List<EstacionServicio> lstEstacionServicio;
    private Date fechaDesde;
    private Date fechaHasta;
    @Autowired
    LogicaMaestroGuias logicaMaestroGuias;
    @Autowired
    LogicaEquipos logicaEquipos;
    @Autowired
    LogicaPersonal logicaPersonal;
    @Autowired
    LogicaContratoEmpresa logicaContratoEmpresa;
    @Autowired LogicaTramoContrato logicaTramoContrato;
    @Autowired
    LogicaGuiaDePetroleo logicaGuiaDePetroleo;

    public void inicio(){
        ordenDeCarga = new Vuelta();
        lstEquipos = logicaEquipos.obtenerTractos();
        lstConductores = logicaPersonal.obtenerConductores();
        lstBateas = logicaEquipos.obtenerBateas();
        lstEmpresas = logicaContratoEmpresa.obtenerContratoEmpresas();
    }

    public void consultarOrdenDeCarga(){
        ordenDeCarga = logicaMaestroGuias.obtenerOrdendeCarga(ordenConsulta);
    }

    public boolean estaBloqueado() {
        if (ordenDeCarga.getConductor() != null){
        ordenDeCarga.getConductor().setSancion(logicaPersonal.obtenerSancion(ordenDeCarga.getConductor()));
        if (ordenDeCarga.getConductor().getSancion() == null) {
            return false;
        } else {
            return true;
        }
        }
        return false;
    }

    public boolean estaBloqueadoTracto(){
        if (ordenDeCarga.getTracto() == null) return false;
        //terminar metodo
        return false;
    }

    public void buscarTramoContrato(){
        lstTipoDeProductos = new ArrayList<TipoProducto>();
        lstTramoContrato = logicaTramoContrato.obtenerTramoPorContrato(empresaSeleccionada);
    }

    public void irGuardarProducto(){
        producto = new Producto();
    }

    public void irGuiasDePretrolio(){
        if (ordenDeCarga == null) return;
        System.err.println("se obtiene lstEstacionServicio, guia de pretroleo se instancea");
        lstEstacionServicio = logicaGuiaDePetroleo.obtenerEstacionesDeServicio();
        guiaPetroleo = new GuiaPetroleo();
        lstGuiaDePetrolio = logicaGuiaDePetroleo.obtenerPetrolioPorOrden(ordenDeCarga);
    }

    public void onRowSelect(SelectEvent event) {
        guiaPetroleo = ((GuiaPetroleo) event.getObject());
    }

    public void onRowUnselect(UnselectEvent event) {
        System.err.println("rowUnselect");
        guiaPetroleo = new GuiaPetroleo();
    }

    public Integer calcularTotalRendicion(GuiaPetroleo guiaPetroleo){
        if (guiaPetroleo == null) return 0;
        return (int)(guiaPetroleo.getLitros() * guiaPetroleo.getPreciolitro());
    }

    public void guardarGuiaDePetrolio(){
        System.err.println(guiaPetroleo);
        guiaPetroleo.setOrdendecarga(ordenDeCarga.getId());
        lstGuiaDePetrolio.add(guiaPetroleo);
        guiaPetroleo  = new GuiaPetroleo();
    }

    public void guardarProducto(){
        producto.setOrdencarga(ordenDeCarga);
        producto.setTramo(tramoContratoSeleccionado);
        ordenDeCarga.getProductosList().add(producto);
    }

    public void irConsulta(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String fechaDesdeTmp = sdf.format(new Date());
        String fechaHastaTmp = sdf.format(new Date());
        try{
            fechaDesdeTmp = fechaDesdeTmp.concat("-01");
            int anio= Integer.parseInt(fechaDesdeTmp.split("-")[0]);
            int mes=Integer.parseInt(fechaDesdeTmp.split("-")[1]);
            Calendar calendario =Calendar.getInstance();
            calendario.set(anio,mes-1,1);
            int ultimoDiaMes=calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
            fechaHastaTmp = fechaHastaTmp.concat("-".concat(String.valueOf(ultimoDiaMes)));
            SimpleDateFormat sdfParse = new SimpleDateFormat("yyyy-MM-dd");
            fechaDesde = sdfParse.parse(fechaDesdeTmp);
            fechaHasta = sdfParse.parse(fechaHastaTmp);
        }catch (Exception ex){
            ex.printStackTrace();
        }
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

    public List<GuiaPetroleo> getLstGuiaDePetrolio() {
        return lstGuiaDePetrolio;
    }

    public void setLstGuiaDePetrolio(List<GuiaPetroleo> lstGuiaDePetrolio) {
        this.lstGuiaDePetrolio = lstGuiaDePetrolio;
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
}
