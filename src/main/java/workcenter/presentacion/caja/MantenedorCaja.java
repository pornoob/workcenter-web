package workcenter.presentacion.caja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Concepto;
import workcenter.entidades.Descuento;
import workcenter.entidades.Dinero;
import workcenter.entidades.Personal;
import workcenter.negocio.caja.LogicaCaja;
import workcenter.negocio.concepto.LogicaConceptos;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.util.components.Constantes;
import workcenter.util.components.FacesUtil;
import workcenter.util.dto.TipoDinero;

import java.io.Serializable;
import java.util.*;

/**
 * Created by renholders on 09-10-2015.
 */
@Component
@Scope("flow")
public class MantenedorCaja implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer saldo;
    private Integer gastos;
    private Integer devolucion;
    private String detalleRendicion;
    private Date fechaDesde;
    private Date fechaHasta;
    private Personal personal;
    private List<String> motivos;
    private List<TipoDinero> lstTipoDinero;
    private List<Dinero> lstDineros;
    private List<Dinero> lstDinerosRendicion;
    private List<Dinero> lstDinerosRendicionFiltro;
    private List<Dinero> lstDinerosConsultaFiltro;
    private List<Personal> lstPersonal;
    private List<Concepto> lstConceptos;
    private Dinero dinero;
    private Concepto concepto;
    @Autowired LogicaCaja logicaCaja;
    @Autowired LogicaPersonal logicaPersonal;
    @Autowired LogicaConceptos logicaConceptos;
    @Autowired Constantes constantes;

    public void inicio(){
        inicializarDinero();
        lstDineros = logicaCaja.obtenerDinerosConDescuentos();
        lstPersonal = logicaPersonal.obtenerTodos();
        lstConceptos = logicaConceptos.obtenerConceptos();
    }

    public void inicializarDinero(){
        dinero = new Dinero();
    }
    
    public void guardarDatosCaja(){
    	if (dinero.getReceptor() == null) return;
        dinero.setConcepto(concepto);
        dinero.setFechareal(dinero.getFechaactivo());
        if (dinero.getConcepto().getId() ==constantes.getASIGNACION_CAJA() ){
            Descuento d = new Descuento();
            d.setMonto(dinero.getMonto());
            d.setFecha(dinero.getFechaactivo());
            d.setMotivo(dinero);
            d.setNombre(dinero.getConcepto().getEtiqueta());
            d.setPersona(dinero.getReceptor().getRut());
            List<Descuento> lstDesc = new ArrayList<Descuento>();
            lstDesc.add(d);
            dinero.setLstDescuentos(lstDesc);
        }
        if (logicaCaja.guardarEntradas(dinero)){
            FacesUtil.mostrarMensajeInformativo("Ingreso Exitoso", dinero.getId().toString()
                                                                    +" "+dinero.getConcepto().getEtiqueta()
                                                                    +" "+dinero.getReceptor().getNombreCompleto()
                                                                    +" "+dinero.getMonto());
            dinero = new Dinero();
        }else{
            FacesUtil.mostrarMensajeError("Ingreso Fallido",
                    "Se ha producido un erro al ingresar: "+dinero.getId()
                            +" "+dinero.getConcepto().getEtiqueta()
                            +""+dinero.getReceptor().getNombreCompleto()
                            +" "+dinero.getMonto());
            dinero = new Dinero();
        }
        lstDineros = logicaCaja.obtenerDinerosConDescuentos();
    }

    public void asignarConcepto(int t){
        for (Concepto cpt : lstConceptos){
            if (cpt.getId().intValue() == t){
                concepto = cpt;
                break;
            }
        }
    }

    public String irIngresoCaja(int tipoConcepto){
        asignarConcepto(tipoConcepto);
        return "flowIngresaCaja";
    }

    public String irRendirAsignacion(int tipoConcepto){
        asignarConcepto(tipoConcepto);
        saldo = 0;
        devolucion = 0;
        gastos = 0;
        motivos = constantes.getLstMotivos();
        lstDinerosRendicion = new ArrayList<Dinero>();
        for (Dinero d : lstDineros){
            if (d.getConcepto().getId() == constantes.getASIGNACION_CAJA() ){
                lstDinerosRendicion.add(d);
            }
        }
        return "flowRendirAsignacion";
    }

    public void calculoRendicion(){
        saldo = dinero.getMonto()-gastos-devolucion;
        dinero.getLstDescuentos().get(0).setMonto(saldo);
    }

    public void guardarRendicion(){
        if(saldo > 0){
            dinero.setComentario(dinero.getComentario()+" "+detalleRendicion);
            logicaCaja.guardarEntradas(dinero);
        } else {
            dinero.getLstDescuentos().remove(dinero.getLstDescuentos().get(0));
            logicaCaja.guardarEntradas(dinero);
        }
        if(devolucion > 0){
            Dinero dev = new Dinero();
            Concepto c = new Concepto();
            c.setId(constantes.getRENDICION_ASIGNACION());
            dev.setConcepto(c);
            dev.setFechaactivo(new Date());
            dev.setFechareal(new Date());
            dev.setMonto(devolucion);
            dev.setReceptor(dinero.getReceptor());
            logicaCaja.guardarEntradas(dev);
        }
        lstDineros = logicaCaja.obtenerDinerosConDescuentos();
        devolucion = 0;
        gastos = 0;
        saldo = 0;
    }

    public String irConsultaCaja(){
        return "flowConsultaCaja";
    }

    public void filtrarDinerosConsulta(){
        lstDinerosConsultaFiltro = new ArrayList<Dinero>();
        for (Dinero d : lstDineros){
            if (personal.equals(d.getReceptor())){
                lstDinerosConsultaFiltro.add(d);
            }
        }
    }

    public String irAsignacionCaja(int tipoConcepto){
        asignarConcepto(tipoConcepto);
        lstTipoDinero = constantes.getLstTipoDineros();
        dinero.setFechareal(new Date());
        dinero.setFechaactivo(new Date());
        return "flowAsignacionCaja";
    }

    public List<Dinero> getLstDineros() {
        return lstDineros;
    }

    public void setLstDineros(List<Dinero> lstDineros) {
        this.lstDineros = lstDineros;
    }

    public List<Personal> getLstPersonal() {
        return lstPersonal;
    }

    public void setLstPersonal(List<Personal> lstPersonal) {
        this.lstPersonal = lstPersonal;
    }

    public List<Concepto> getLstConceptos() {
        return lstConceptos;
    }

    public void setLstConceptos(List<Concepto> lstConceptos) {
        this.lstConceptos = lstConceptos;
    }

    public Dinero getDinero() {
        return dinero;
    }

    public void setDinero(Dinero dinero) {
        this.dinero = dinero;
    }

    public Concepto getConcepto() {
        return concepto;
    }

    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }

    public List<Dinero> getLstDinerosRendicion() {
        return lstDinerosRendicion;
    }

    public void setLstDinerosRendicion(List<Dinero> lstDinerosRendicion) {
        this.lstDinerosRendicion = lstDinerosRendicion;
    }

    public List<Dinero> getLstDinerosRendicionFiltro() {
        return lstDinerosRendicionFiltro;
    }

    public void setLstDinerosRendicionFiltro(List<Dinero> lstDinerosRendicionFiltro) {
        this.lstDinerosRendicionFiltro = lstDinerosRendicionFiltro;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public Integer getGastos() {
        return gastos;
    }

    public void setGastos(Integer gastos) {
        this.gastos = gastos;
    }

    public Integer getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Integer devolucion) {
        this.devolucion = devolucion;
    }

    public List<String> getMotivos() {
        return motivos;
    }

    public void setMotivos(List<String> motivos) {
        this.motivos = motivos;
    }

    public List<TipoDinero> getLstTipoDinero() {
        return lstTipoDinero;
    }

    public void setLstTipoDinero(List<TipoDinero> lstTipoDinero) {
        this.lstTipoDinero = lstTipoDinero;
    }

    public String getDetalleRendicion() {
        return detalleRendicion;
    }

    public void setDetalleRendicion(String detalleRendicion) {
        this.detalleRendicion = detalleRendicion;
    }

    public List<Dinero> getLstDinerosConsultaFiltro() {
        return lstDinerosConsultaFiltro;
    }

    public void setLstDinerosConsultaFiltro(List<Dinero> lstDinerosConsultaFiltro) {
        this.lstDinerosConsultaFiltro = lstDinerosConsultaFiltro;
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

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }
}
