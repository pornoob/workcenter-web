package workcenter.presentacion.caja;

import com.itextpdf.text.log.SysoLogger;
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
import workcenter.util.others.RenderPdfCaja;

import javax.crypto.spec.DESedeKeySpec;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
    private Integer montoEntrada;
    private Integer montoSalida;
    private List<Descuento> lstDescuento;
    private Integer numeroCuotas;
    private Personal personal;
    private List<String> motivos;
    private List<TipoDinero> lstTipoDinero;
    private List<Dinero> lstDineros;
    private List<Dinero> lstDinerosRendicion;
    private List<Dinero> lstDinerosRendicionFiltro;
    private List<Dinero> lstDinerosConsultaFiltro;
    private List<Personal> lstPersonal;
    private List<Concepto> lstConceptos;
    private List<Concepto> lstConpcetosFiltrada;
    private Dinero dinero;
    private Concepto concepto;
    @Autowired LogicaCaja logicaCaja;
    @Autowired LogicaPersonal logicaPersonal;
    @Autowired LogicaConceptos logicaConceptos;
    @Autowired Constantes constantes;
    @Autowired RenderPdfCaja renderPdfCaja;

    public void inicio(){
        inicializarDinero();
        lstDineros = logicaCaja.obtenerDinerosConDescuentos();
        lstPersonal = logicaPersonal.obtenerTodos();
        lstConceptos = logicaConceptos.obtenerConceptos();
    }

    public void inicializarDinero(){
        dinero = new Dinero();
    }
    
    public void guardarDatosCaja() throws IOException {
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
            imprimirPdf(renderPdfCaja.generarImpresionCaja(dinero));
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
        dinero = new Dinero();
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
        saldo = 0;
        // Obtener Mes actual
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
        filtrarDinerosConsulta();
        return "flowConsultaCaja";
    }

    public void filtrarDinerosConsulta(){
        saldo = 0;
        montoEntrada = 0;
        montoSalida  = 0;
        lstDinerosConsultaFiltro = logicaCaja.obtenerDinerosFiltros(personal,concepto,fechaDesde,fechaHasta);
    }

    public Integer saldoConsulta(Dinero dinero){
            if (dinero.getConcepto().getSalida()){
                saldo = saldo - dinero.getMonto();
                montoSalida = montoSalida + dinero.getMonto();
            }else{
                saldo = saldo + dinero.getMonto();
                montoEntrada = montoEntrada + dinero.getMonto();
            }
        return saldo;
    }

    public String irAsignacionCaja(int tipoConcepto){
        asignarConcepto(tipoConcepto);
        lstTipoDinero = constantes.getLstTipoDineros();
        dinero.setFechareal(new Date());
        dinero.setFechaactivo(new Date());
        return "flowAsignacionCaja";
    }

    public String irSueldoCaja(){
        dinero = new Dinero();
        lstConpcetosFiltrada = new ArrayList<Concepto>();
        for (Concepto c : lstConceptos){
            if (c.getId() == constantes.getANTICIPO() ||
                    c.getId() == constantes.getAGUINALDO_ANTICIPADO() ||
                    c.getId() == constantes.getSUELDO() ||
                    c.getId() == constantes.getFINIQUITO()||
                    c.getId() == constantes.getBONO_ANTICIPADO()){
                lstConpcetosFiltrada.add(c);
            }
        }
        dinero.setFechareal(new Date());
        return "flowSueldoCaja";
    }

    public String irPrestamoCuotas(int tipoConcepto){
        asignarConcepto(tipoConcepto);
        dinero = new Dinero();
        dinero.setFechaactivo(new Date());
        return "flowPrestamoCuotas";
    }

    public void guardarPrestamoCuotas(){

       logicaCaja.guardarEntradas(dinero);
    }

    public void calcularCuotas(){
        if(numeroCuotas == null || dinero.getMonto() ==null || dinero.getMonto() == 0) return;
        lstDescuento = new ArrayList<Descuento>();
        //Settear la fecha segundo el mes primera cuota en adelante
        Integer montoCuotas = dinero.getMonto() / numeroCuotas;
        for (int i=1; i<=numeroCuotas; i++){
            Descuento descuentoTmp = new Descuento();
            descuentoTmp.setMonto(montoCuotas);
            descuentoTmp.setNombre("Prestamo Cuotas");
            descuentoTmp.setPersona(dinero.getReceptor().getRut());
            lstDescuento.add(descuentoTmp);
        }
        dinero.setLstDescuentos(lstDescuento);
    }

    public void imprimirPdf(byte[] pdf) throws IOException {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        response.reset();
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "filename="+"Impresión.pdf");

        OutputStream output = response.getOutputStream();
        output.write(pdf);
        output.close();

        facesContext.responseComplete();
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

    public List<Concepto> getLstConpcetosFiltrada() {
        return lstConpcetosFiltrada;
    }

    public void setLstConpcetosFiltrada(List<Concepto> lstConpcetosFiltrada) {
        this.lstConpcetosFiltrada = lstConpcetosFiltrada;
    }

    public Integer getMontoEntrada() {
        return montoEntrada;
    }

    public void setMontoEntrada(Integer montoEntrada) {
        this.montoEntrada = montoEntrada;
    }

    public Integer getMontoSalida() {
        return montoSalida;
    }

    public void setMontoSalida(Integer montoSalida) {
        this.montoSalida = montoSalida;
    }

    public List<Descuento> getLstDescuento() {
        return lstDescuento;
    }

    public void setLstDescuento(List<Descuento> lstDescuento) {
        this.lstDescuento = lstDescuento;
    }

    public Integer getNumeroCuotas() {
        return numeroCuotas;
    }

    public void setNumeroCuotas(Integer numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }
}
