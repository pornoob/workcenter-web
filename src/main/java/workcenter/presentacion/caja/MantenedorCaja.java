package workcenter.presentacion.caja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Concepto;
import workcenter.entidades.Dinero;
import workcenter.entidades.Personal;
import workcenter.negocio.caja.LogicaCaja;
import workcenter.negocio.concepto.LogicaConceptos;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.util.components.Constantes;
import workcenter.util.components.FacesUtil;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by renholders on 09-10-2015.
 */
@Component
@Scope("flow")
public class MantenedorCaja implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Dinero> lstDineros;
    private List<Dinero> lstDinerosRendicion;
    private List<Dinero> lstDinerosRendicionFiltro;
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
        lstDineros = logicaCaja.obtenerDineros();
        lstPersonal = logicaPersonal.obtenerTodos();
        lstConceptos = logicaConceptos.obtenerConceptos();
        System.err.println("SE CARGAN LAS LISTAS");
    }

    public void inicializarDinero(){
        dinero = new Dinero();
    }
    
    public void guardarDatosCaja(){
    	if (dinero.getReceptor() == null) return;
        dinero.setConcepto(concepto);
        dinero.setFechareal(dinero.getFechaactivo());
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
        lstDinerosRendicion = new ArrayList<Dinero>();
        for (Dinero d : lstDineros){
            if (d.getConcepto().getId() == constantes.getASIGNACION_CAJA() ){
                lstDinerosRendicion.add(d);
            }
        }
        System.err.println("Cantidad de Registro CONCEPTO 6 :"+lstDinerosRendicion.size() );
        System.err.println("Probando redeploy");
        return "flowRendirAsignacion";
    }

    public String irConsultaCaja(){

        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        GregorianCalendar fecha = new GregorianCalendar();
        for (int i = 0; i < 12; i++) {
            String fechaActual = sdf.format(fecha.getTime());
            if (fecha.get(Calendar.MONTH) == 0) {
                fecha.roll(Calendar.YEAR, false);
            }
            fecha.roll(Calendar.MONTH, false);
            String anio = fechaActual.split("-")[1];
            String mes = fechaActual.split("-")[0];
            System.err.println(mes+"-"+anio);
        }
        return "flowConsultaCaja";
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
}
