package workcenter.presentacion.personal;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import workcenter.entidades.BonoDescuentoPersonal;
import workcenter.entidades.ContratoPersonal;
import workcenter.entidades.Personal;
import workcenter.entidades.Remuneracion;
import workcenter.entidades.ValorImpuestoUnico;
import workcenter.entidades.ValorPrevisionPersonal;
import workcenter.entidades.Variable;
import workcenter.negocio.personal.LogicaLiquidaciones;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.util.components.Constantes;

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

    private List<ValorPrevisionPersonal> valorprevision;
    
    private List<BonoDescuentoPersonal> bonoDescuentoPersonal;
    
    private List<Remuneracion> listaRemuneraciones;
    
    private DualListModel<BonoDescuentoPersonal> bonos;

    @Autowired
    private LogicaPersonal logicaPersonal;

    @Autowired
    private LogicaLiquidaciones logicaLiquidaciones;

    @Autowired
    private Constantes constantes;

    private Remuneracion liquidacion;
    
    private Integer anio;
    
    private String mes;

    public void inicio() {
    	
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
        valorprevision = new ArrayList<ValorPrevisionPersonal>();
        Integer totalNoImponible = 0;
        Integer totalImponible = 0;
        
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
                
            } else {
                bonoNoImponibles.add(bDP);
                if ( bDP.getMonto() != null){
                	totalNoImponible = totalNoImponible + bDP.getMonto().intValue();
                }else{
                	totalNoImponible = totalNoImponible + 0;
                }
                
            }
        }
        // sueldo base y gratificacion
        ContratoPersonal cp = logicaLiquidaciones.obtenerDatosContrato(liquidacion.getIdPersonal());
        valorprevision = logicaLiquidaciones.obtenerDatosPrevision(cp);

        liquidacion.setAnticipoSueldo(logicaLiquidaciones.obtenerAnticipoSueldo(liquidacion.getIdPersonal().getRut(), mes, anio));
        liquidacion.setSueldoBase(cp.getSueldoBase());

        if (liquidacion.getDiasTrabajados() < constantes.getDiasTrabajados()) {
            double sBase = (double) ((cp.getSueldoBase() * liquidacion.getDiasTrabajados() / constantes.getDiasTrabajados()));
            liquidacion.setSueldoBase((int) sBase);
        }

        Double gratificacion = (liquidacion.getSueldoBase() * 0.25);
        liquidacion.setGratificacion(gratificacion.intValue());
        liquidacion.setTotalImponible(liquidacion.getSueldoBase() + liquidacion.getGratificacion() + totalImponible);
        liquidacion.setTotalHaberes(liquidacion.getSueldoBase() + liquidacion.getGratificacion() + totalImponible + totalNoImponible);

        //calculo afp y salud
        for (ValorPrevisionPersonal vPP : valorprevision) {

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
        liquidacion.setAlcanceLiquido(liquidacion.getTotalHaberes() - (liquidacion.getDctoPrevision() + liquidacion.getDectoAFP()));

        try {
			liquidacion.setImpUnico(calcularImpuestoUnico(liquidacion.getRentaAfecta(), Integer.parseInt(utm.getValor())));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

        // seguro cesantia
        Double seguroEmpresa = (liquidacion.getTotalImponible() * constantes.getAportePorcentajeEmpleador()) / 100;
        Double seguroTrabajador = (liquidacion.getTotalImponible() * constantes.getAportePorcentajeTrabajador()) / 100;
        if (cp.getVencimiento() != null){
        	 Double noIndefinido = seguroEmpresa+seguroTrabajador;
             liquidacion.setAporteMontoEmpresa(noIndefinido.intValue());
         	 liquidacion.setAporteMontoTrabajador(0);
        }else{
	       	 liquidacion.setAporteMontoEmpresa(seguroEmpresa.intValue());
	         liquidacion.setAporteMontoTrabajador(seguroTrabajador.intValue());
        }
        liquidacion.setAlcanceLiquido((liquidacion.getAlcanceLiquido() - liquidacion.getAporteMontoTrabajador()) - liquidacion.getAnticipoSueldo());
        liquidacion.setHorasExtras(0);
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd");
		try {
			liquidacion.setFechaLiquidacion(formatoDeFecha.parse((anio.toString()+"-"+mes.toString()+"-"+"01")));
			} catch (ParseException ex) {
			ex.printStackTrace();
			}	
        liquidacion.setEsGenerica(true);
    }

    public Double calcularImpuestoUnico(int rentaAfecta, int utm) throws Exception {

        try {
            float rentaAfectaUtm = rentaAfecta / utm;
            List<ValorImpuestoUnico> valorImpuestoUnicos = logicaLiquidaciones.obtenerValoresVigentesImpUnico();

            for (ValorImpuestoUnico viu : valorImpuestoUnicos) {
                if ((viu.getCotaMin() == null || rentaAfectaUtm > viu.getCotaMin().floatValue()) &&
                        (viu.getCotaMax() == null || rentaAfectaUtm <= viu.getCotaMax().floatValue())) {
                    return Double.valueOf((rentaAfectaUtm * viu.getFactor().floatValue() - viu.getSubstraendo().floatValue()) * utm);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return -1.0;
    }
    
    public void agregarBonos(){
    	
    }
    
    public String guardarDatosLiquidacion(){
    	logicaLiquidaciones.guardarDatosLiquidacion(liquidacion);
    	listaRemuneraciones = logicaLiquidaciones.obtenerListaRemuneraciones();
    	return "flowMenuLiquidaciones";
    }

    public String ingresarLiquidacionOtros() {
        personal = logicaPersonal.obtenerTodos();
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

    public List<ValorPrevisionPersonal> getValorprevision() {
        return valorprevision;
    }

    public void setValorprevision(List<ValorPrevisionPersonal> valorprevision) {
        this.valorprevision = valorprevision;
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

}
