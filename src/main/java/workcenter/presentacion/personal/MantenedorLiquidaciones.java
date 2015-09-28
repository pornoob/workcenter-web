package workcenter.presentacion.personal;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.hoja_servicio.LogicaCargasFamiliares;
import workcenter.negocio.personal.LogicaLiquidaciones;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.negocio.personal.LogicaVariables;
import workcenter.util.components.Constantes;
import workcenter.util.components.FacesUtil;
import workcenter.util.others.RenderPdf;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by claudio on 16-05-15.
 */
@Component
@Scope("flow")
public class MantenedorLiquidaciones implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Personal> personal;

    private List<BonoDescuentoPersonal> bonoNoImponibles;

    private List<BonoDescuentoPersonal> bonoImponibles;
    
    private List<BonoDescuentoPersonal> descuentos;

    private List<ValorPrevisionPersonal> valorPrevision;
    
    private List<BonoDescuentoPersonal> bonoDescuentoPersonal;
    
    private List<Remuneracion> listaRemuneraciones;
    
    private DualListModel<BonoDescuentoPersonal> bonos;
    
    private BonoDescuentoPersonal bonoEditar;
    
    private Integer asignacionFamiliarMonto;
    
    private int cantidadCargasFamiliares;
    
    private Variable variable;
    
    @Autowired
    private LogicaPersonal logicaPersonal;

    @Autowired
    private LogicaLiquidaciones logicaLiquidaciones;
    
    @Autowired
    private LogicaCargasFamiliares logicaCargasFamiliares;

    @Autowired
    private Constantes constantes;
    
    @Autowired
    private LogicaVariables logicaVariables;

    @Autowired
    private RenderPdf renderPdf;

    private Remuneracion liquidacion;
    
    private Integer anio;
    
    private String mes;

    public void inicio() {
    	
    	variable = logicaVariables.obtenerSueldoMinimoActual();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        String fechaActual = sdf.format(new Date());
        anio = Integer.parseInt(fechaActual.split("-")[1]);
        mes = fechaActual.split("-")[0];        
        listaRemuneraciones = logicaLiquidaciones.obtenerListaRemuneraciones();        
        bonoDescuentoPersonal = logicaLiquidaciones.obtenerBonosDescuentos();        
        liquidacion = new Remuneracion();
        liquidacion.setDiasTrabajados(constantes.getDiasTrabajados());
        bonos = new DualListModel<BonoDescuentoPersonal>();
    }

    public void cargarDatos(){
    	
    	if (liquidacion.getIdPersonal() == null) return;
        bonoImponibles = new ArrayList<BonoDescuentoPersonal>();
        bonoNoImponibles = new ArrayList<BonoDescuentoPersonal>();
        descuentos = new ArrayList<BonoDescuentoPersonal>();
        valorPrevision = new ArrayList<ValorPrevisionPersonal>();
        bonoEditar =  new BonoDescuentoPersonal();
        Integer totalNoImponible = 0;
        Integer totalImponible = 0;
        Integer totalDescuentos = 0;
        asignacionFamiliarMonto = 0;
        
		Variable utm = logicaLiquidaciones.obtenerValorUtm(Integer.parseInt(mes), anio);
        Variable uf = logicaLiquidaciones.obtenerValorUf(Integer.parseInt(mes), anio);
		
        liquidacion.setIdPersonal(logicaPersonal.obtenerConDatosLiquidacion(liquidacion.getIdPersonal()));
        bonos.setSource(logicaLiquidaciones.obtenerBonosFaltantes(liquidacion.getIdPersonal()));
        bonos.setTarget(liquidacion.getIdPersonal().getBonosDescuentos());
        for (BonoDescuentoPersonal bDP : liquidacion.getIdPersonal().getBonosDescuentos()) {
            if (bDP.getIdBonodescuento().getImponible()) {
                bonoImponibles.add(bDP);
                if ( bDP.getMonto() != null){
                	totalImponible = totalImponible + bDP.getMonto().intValue();
                }else{
                	totalImponible = totalImponible + 0;
                }
                
            } else if ( !bDP.getIdBonodescuento().getImponible() &&
            		bDP.getIdBonodescuento().getIdTipoBonodescuento().getDescripcion().equals(new String("Bono")) ) {
                bonoNoImponibles.add(bDP);
                if ( bDP.getMonto() != null){
                	totalNoImponible = totalNoImponible + bDP.getMonto().intValue();
                }else{
                	totalNoImponible = totalNoImponible + 0;
                }   
            }else {
            	descuentos.add(bDP);
            	if ( bDP.getMonto() != null){
            		totalDescuentos = totalDescuentos + bDP.getMonto().intValue();
                }else{
                	totalDescuentos = totalDescuentos + 0;
                }             	
            }
        }
        // sueldo base y gratificacion
        ContratoPersonal cp = logicaLiquidaciones.obtenerDatosContrato(liquidacion.getIdPersonal());
        valorPrevision = logicaLiquidaciones.obtenerDatosPrevision(cp);
        liquidacion.setEmpleador(cp.getEmpleador().getNombre());
        liquidacion.setRutEmpleador(cp.getEmpleador().getRut().toString()+"-"+cp.getEmpleador().getDigitoverificador());
        liquidacion.setAnticipoSueldo(logicaLiquidaciones.obtenerAnticipoSueldo(liquidacion.getIdPersonal().getRut(), mes, anio));
        liquidacion.setSueldoBase(cp.getSueldoBase());

        if (liquidacion.getDiasTrabajados() < constantes.getDiasTrabajados()) {
            double sBase = (double) ((cp.getSueldoBase() * liquidacion.getDiasTrabajados() / constantes.getDiasTrabajados()));
            liquidacion.setSueldoBase((int) sBase);
        }

        Double gratificacion;

        if (cp.getSinTope() == Boolean.TRUE)
            gratificacion = liquidacion.getSueldoBase() * 0.25;
        else
            gratificacion = (double) (liquidacion.getSueldoBase() / 4 < ((int) ((4.75 * Integer.parseInt(variable.getValor())) / 12)) ?
        				(liquidacion.getSueldoBase() / 4): (int) ((4.75 * Integer.parseInt(variable.getValor())) / 12));

        liquidacion.setGratificacion(gratificacion.intValue());
        liquidacion.setTotalImponible(liquidacion.getSueldoBase() + liquidacion.getGratificacion() + totalImponible);
        liquidacion.setTotalHaberes(liquidacion.getSueldoBase() + liquidacion.getGratificacion() + totalImponible + totalNoImponible);
        
        // asiganacion familiar
        
        for (ValoresCargasFamiliares vCF : logicaCargasFamiliares.obtenerValoresCargasFamiliares()) {
			if (vCF.getHasta() == null){
				
			}else if (vCF.getHasta()>=liquidacion.getTotalImponible()){
				if (asignacionFamiliarMonto != null){
					asignacionFamiliarMonto = cantidadCargasFamiliares*vCF.getMonto();
				}
				//OJO : verificar calculo
				liquidacion.setTotalHaberes(liquidacion.getTotalHaberes()+asignacionFamiliarMonto);
				//liquidacion.setTotalHaberes(liquidacion.getTotalHaberes()+(asiganacionFamiliar*vCF.getMonto()));
			}
		}
        
        //calculo afp y salud
        liquidacion.setDctoPrevision(0);
        liquidacion.setDectoAFP(0);
        for (ValorPrevisionPersonal vPP : valorPrevision) {

            if (vPP.getPrevision().getTipo().equals("salud")) {
                if (vPP.getUnidad().getId().intValue() == constantes.getUnidadPesos()) {
                    liquidacion.setDctoPrevision((int) vPP.getValor());
                } else if (vPP.getUnidad().getId().intValue() == constantes.getUnidadPorcentaje()) {
                    liquidacion.setDctoPrevision((int) Math.round(liquidacion.getTotalImponible() * vPP.getValor() / 100));
                } else if (vPP.getUnidad().getId() == constantes.getUnidadUf()) {
                    liquidacion.setDctoPrevision((int) Math.round(vPP.getValor() * Float.parseFloat(uf.getValor())));
                }
            } else {
                if (vPP.getUnidad().getId().intValue() == constantes.getUnidadPesos()) {
                    liquidacion.setDectoAFP((int) vPP.getValor());
                } else if (vPP.getUnidad().getId().intValue() == constantes.getUnidadPorcentaje()) {
                    liquidacion.setDectoAFP((int) Math.round(liquidacion.getTotalImponible() * vPP.getValor() / 100));
                } else if (vPP.getUnidad().getId() == constantes.getUnidadUf()) {
                    liquidacion.setDectoAFP((int) Math.round(vPP.getValor() * Float.parseFloat(uf.getValor())));
                }
            }
        }

        liquidacion.setRentaAfecta(liquidacion.getTotalImponible() - (liquidacion.getDctoPrevision() + liquidacion.getDectoAFP()));

        // seguro cesantia
        Double seguroEmpresa = (liquidacion.getTotalImponible() * constantes.getAportePorcentajeEmpleador()) / 100;
        Double seguroTrabajador = (liquidacion.getTotalImponible() * constantes.getAportePorcentajeTrabajador()) / 100;
        if (cp.getVencimiento() != null){
        	 Double noIndefinido = seguroEmpresa+seguroTrabajador;
             liquidacion.setAporteEmpresa(noIndefinido);
         	 liquidacion.setAporteTrabajador(0.0);
        }else{
	       	 liquidacion.setAporteEmpresa(seguroEmpresa);
	         liquidacion.setAporteTrabajador(seguroTrabajador);
        }
        liquidacion.setHorasExtras(0);
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd");
		try {
			liquidacion.setFechaLiquidacion(formatoDeFecha.parse((anio.toString()+"-"+mes.toString()+"-"+"01")));
			} catch (ParseException ex) {
			ex.printStackTrace();
			}
		liquidacion.setRentaAfecta(liquidacion.getRentaAfecta()- liquidacion.getAporteTrabajador().intValue());
		
        try {
			liquidacion.setImpUnico(calcularImpuestoUnico(liquidacion.getRentaAfecta(), Integer.parseInt(utm.getValor())));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		liquidacion.setTotalDctos((liquidacion.getTotalImponible() - liquidacion.getRentaAfecta()) + totalDescuentos);
		liquidacion.setAlcanceLiquido(liquidacion.getTotalHaberes()-liquidacion.getTotalDctos());
	    liquidacion.setLiqPagar(liquidacion.getAlcanceLiquido()-liquidacion.getAnticipoSueldo());
        liquidacion.setEsGenerica(true);
    }

    public Integer getAsignacionFamiliarMonto() {
		return asignacionFamiliarMonto;
	}

	public void setAsignacionFamiliarMonto(Integer asignacionFamiliarMonto) {
		this.asignacionFamiliarMonto = asignacionFamiliarMonto;
	}

	public Double calcularImpuestoUnico(int rentaAfecta, int utm) throws Exception {

        try {
            double rentaAfectaUtm = (double)rentaAfecta / (double)utm;
            List<ValorImpuestoUnico> valorImpuestoUnicos = logicaLiquidaciones.obtenerValoresVigentesImpUnico();

            for (ValorImpuestoUnico viu : valorImpuestoUnicos) {
                if ((viu.getCotaMin() == null || rentaAfectaUtm > viu.getCotaMin().floatValue()) &&
                        (viu.getCotaMax() == null || rentaAfectaUtm <= viu.getCotaMax().floatValue())) {
                    //return Double.valueOf((rentaAfectaUtm * viu.getFactor().doubleValue() - viu.getSubstraendo().doubleValue()) * utm);
                    return Math.round(Double.valueOf((rentaAfectaUtm * viu.getFactor().doubleValue() - viu.getSubstraendo().doubleValue()) * utm)*Math.pow(10,0))/Math.pow(10,0);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return -1.0;
    }
    
    public void agregarQuitarBonos(){
        liquidacion.getIdPersonal().setBonosDescuentos(bonos.getTarget());
        logicaPersonal.guardar(liquidacion.getIdPersonal());
        cargarDatos();
    }
    
    public String guardarDatosLiquidacion(){
    	
    	liquidacion.setRemuneracionBonoDescuentoList(new ArrayList<BonoDescuentoRemuneracion>());
    	unirBonosRemuneracion();
    	renderPdf.generarLiquidacion(liquidacion);
        
//        if (true) return null;
    	
    	//liquidacion.getIdPersonal().setBonosDescuentos(unirBonosPersonal());
    	listaRemuneraciones = logicaLiquidaciones.obtenerListaRemuneraciones();
    	Boolean encotrado = false;
    	for ( Remuneracion lstRemuneracion : listaRemuneraciones) {
			if (lstRemuneracion.getFechaLiquidacion().equals(liquidacion.getFechaLiquidacion())
					&& lstRemuneracion.getIdPersonal().getRut().equals(liquidacion.getIdPersonal().getRut())){
				FacesUtil.mostrarMensajeError("Ingreso Fallido", "La liquidacion ya existe con esa Fecha");
				encotrado = true;
			}
		}
    	if (!encotrado){
    		logicaLiquidaciones.guardarDatosLiquidacion(liquidacion);
        	listaRemuneraciones = logicaLiquidaciones.obtenerListaRemuneraciones();
        	FacesUtil.mostrarMensajeInformativo("Ingreso Exitoso", "La liquidacion fué registrada");
        	return "flowMenuLiquidaciones";
    	}else {
    		return "flowIngresar";
    	}
    }
    
    public String crearUrl(String ruta){
    	ruta = ruta.substring(ruta.indexOf("/static"),ruta.length()).replace("//", "/");
    	return ruta;
    }
    
    public void visualizarPDF(Remuneracion verLiquidacion) throws IOException{
    	
    	 if (verLiquidacion == null) return;
    	 FacesContext facesContext = FacesContext.getCurrentInstance();
    	    ExternalContext externalContext = facesContext.getExternalContext();
    	    HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

    	    response.reset();
    	    response.setContentType("application/pdf");
    	    response.setHeader("Content-disposition", "filename="+verLiquidacion.getIdPersonal().getRut()+".pdf");

    	    OutputStream output = response.getOutputStream();
    	    output.write(verLiquidacion.getArchivo());
    	    output.close();

    	    facesContext.responseComplete();
    	}
    
    public void unirBonosRemuneracion(){
      	for (BonoDescuentoPersonal bI : bonoImponibles ){
    		BonoDescuentoRemuneracion bdr = new BonoDescuentoRemuneracion();
    		bdr.setIdMaestroGuia(liquidacion);
    		bdr.setBono(true);
    		bdr.setDescripcion(bI.getIdBonodescuento().getDescripcion());
    		bdr.setImponible(true);
    		bdr.setMonto(bI.getMonto());    		
    		liquidacion.getRemuneracionBonoDescuentoList().add(bdr);
    	}
    	
    	for (BonoDescuentoPersonal bI : bonoNoImponibles ){
    		BonoDescuentoRemuneracion bdr = new BonoDescuentoRemuneracion();
    		bdr.setIdMaestroGuia(liquidacion);
    		bdr.setBono(true);
    		bdr.setDescripcion(bI.getIdBonodescuento().getDescripcion());
    		bdr.setImponible(false);
    		bdr.setMonto(bI.getMonto());    		
    		liquidacion.getRemuneracionBonoDescuentoList().add(bdr);
    	}
    	
    	for (BonoDescuentoPersonal bI : descuentos ){
    		BonoDescuentoRemuneracion bdr = new BonoDescuentoRemuneracion();
    		bdr.setIdMaestroGuia(liquidacion);
    		bdr.setBono(false);
    		bdr.setDescripcion(bI.getIdBonodescuento().getDescripcion());
    		bdr.setImponible(false);
    		bdr.setMonto(bI.getMonto());    		
    		liquidacion.getRemuneracionBonoDescuentoList().add(bdr);
    	}
    	
    	
    }
    
    public void imprimirLiquidacion() throws IOException{
    	if (liquidacion.getIdPersonal() == null) return;
    	liquidacion.setRemuneracionBonoDescuentoList(new ArrayList<BonoDescuentoRemuneracion>());
    	unirBonosRemuneracion();
    	renderPdf.generarLiquidacion(liquidacion);
   	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

	    response.reset();
	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "filename="+liquidacion.getIdPersonal().getRut()+".pdf");

	    OutputStream output = response.getOutputStream();
	    output.write(liquidacion.getArchivo());
	    output.close();

	    facesContext.responseComplete();
    }
    
    public void editarMontoBono() {
       if (bonoEditar == null) return;
       for (int i = 0; i < bonoImponibles.size(); i++) {		
				if (bonoImponibles.get(i).getId() == (bonoEditar.getId())){
					bonoImponibles.get(i).setMonto(bonoEditar.getMonto());
				}  
       }
       liquidacion.getIdPersonal().setBonosDescuentos(unirBonosPersonal());
       logicaPersonal.guardar(liquidacion.getIdPersonal());
       cargarDatos();
    }
    
    public void editarBono(BonoDescuentoPersonal b){   	
    	bonoEditar = b;
    }
    
    public List<BonoDescuentoPersonal> unirBonosPersonal(){
    	
    	List<BonoDescuentoPersonal> unionBonos = new ArrayList<BonoDescuentoPersonal>();
        for (BonoDescuentoPersonal bdp : bonoImponibles) {
 			unionBonos.add(bdp);
 		}
        for (BonoDescuentoPersonal bdp : bonoNoImponibles) {
 			unionBonos.add(bdp);
 		}
        
        for (BonoDescuentoPersonal bdp : descuentos) {
        	unionBonos.add(bdp);
        }
    	return unionBonos;
    }
    
    public String ingresarLiquidacionOtros() {
        personal = logicaPersonal.obtenerTodos();
        inicio();
        return "flowAgregarLiqOtros";
    }

    public String ingresarLiquidacionConductores() {
        personal = logicaPersonal.obtenerTodos();
        return "flowAgregarLiqConductores";
    }

    public List<Personal> getPersonal() {
        return personal;
    }

    public void setPersonal(List<Personal> personal) {
        this.personal = personal;
    }

    public Remuneracion getLiquidacion() {
        return liquidacion;
    }

    public void setLiquidacion(Remuneracion liquidacion) {
        this.liquidacion = liquidacion;
    }

    public List<BonoDescuentoPersonal> getBonoNoImponibles() {
        return bonoNoImponibles;
    }

    public void setBonoNoImponibles(List<BonoDescuentoPersonal> bonoNoImponibles) {
        this.bonoNoImponibles = bonoNoImponibles;
    }

    public List<BonoDescuentoPersonal> getBonoImponibles() {
        return bonoImponibles;
    }

    public void setBonoImponibles(List<BonoDescuentoPersonal> bonoImponibles) {
        this.bonoImponibles = bonoImponibles;
    }

    public List<ValorPrevisionPersonal> getValorPrevision() {
        return valorPrevision;
    }

    public void setValorPrevision(List<ValorPrevisionPersonal> valorPrevision) {
        this.valorPrevision = valorPrevision;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

	public List<BonoDescuentoPersonal> getBonoDescuentoPersonal() {
		return bonoDescuentoPersonal;
	}

	public void setBonoDescuentoPersonal(
			List<BonoDescuentoPersonal> bonoDescuentoPersonal) {
		this.bonoDescuentoPersonal = bonoDescuentoPersonal;
	}

	public List<Remuneracion> getListaRemuneraciones() {
		return listaRemuneraciones;
	}

	public void setListaRemuneraciones(List<Remuneracion> listaRemuneraciones) {
		this.listaRemuneraciones = listaRemuneraciones;
	}

	public DualListModel<BonoDescuentoPersonal> getBonos() {
		return bonos;
	}

	public void setBonos(DualListModel<BonoDescuentoPersonal> bonos) {
		this.bonos = bonos;
	}

	public BonoDescuentoPersonal getBonoEditar() {
		return bonoEditar;
	}

	public void setBonoEditar(BonoDescuentoPersonal bonoEditar) {
		this.bonoEditar = bonoEditar;
	}

	public int getCantidadCargasFamiliares() {
		return cantidadCargasFamiliares;
	}

	public void setCantidadCargasFamiliares(int cantidadCargasFamiliares) {
		this.cantidadCargasFamiliares = cantidadCargasFamiliares;
	}

	public Variable getVariable() {
		return variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	public List<BonoDescuentoPersonal> getDescuentos() {
		return descuentos;
	}

	public void setDescuentos(List<BonoDescuentoPersonal> descuentos) {
		this.descuentos = descuentos;
	}

}
