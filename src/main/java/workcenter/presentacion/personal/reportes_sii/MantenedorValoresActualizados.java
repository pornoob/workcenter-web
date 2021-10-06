package workcenter.presentacion.personal.reportes_sii;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.LogicaEmpresas;
import workcenter.negocio.personal.LogicaFiniquito;
import workcenter.negocio.personal.LogicaLibroRemuneraciones;
import workcenter.negocio.registros.reportes_sii.LogicaReportesSII;
import workcenter.util.components.Constantes;
import workcenter.util.dto.Mes;
import workcenter.util.dto.ValorActualizadoDTO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Claudio Olivares on 15-02-15.
 */
@Component
@Scope("flow")
public class MantenedorValoresActualizados implements Serializable {
    @Autowired
    private LogicaReportesSII logicaReportesSII;

    @Autowired
    private LogicaLibroRemuneraciones logicaLibroRemuneraciones;

    @Autowired
    private LogicaEmpresas logicaEmpresas;

    @Autowired
    private LogicaFiniquito logicaFiniquito;

    @Autowired
    private Constantes constantes;

    private List<FactorActualizacionSII> valores;
    private Map<String, String> valoresMap;
    private List<Personal> conductores;
    private List<Empresa> empleadores;
    private Empresa empleador;
    private Personal conductor;
    private String anio;
    private List<Remuneracion> remuneraciones;
    private List<Finiquito> finiquitos;
    private Map<Personal, ValorActualizadoDTO> valoresActualizadoMap;
    private List<ValorActualizadoDTO> valoresDesagregados;
    private Integer sumatoriaImponible;
    private Integer sumatoriaRentaAfecta;
    private Integer sumatoriaRentaNoAfecta;
    private Integer sumatoriaImpUnico;
    private Integer sumatoriaImponibleActualizado;
    private Integer sumatoriaRentaAfectaActualizada;
    private Integer sumatoriaNoRentaAfectaActualizada;
    private Integer sumatoriaImpUnicoActualizado;

    // Variables que solo afectan el render
    private Boolean primeraCargaPaso2;
    private Boolean primeraCargaPaso3;
    private Boolean primeraCargaPaso4;

    public void inicio() {
        valores = logicaReportesSII.obtenerFactoresActualizacionSII();
        valoresMap = new HashMap<>();
        for (FactorActualizacionSII f : valores) {
            String idMes = String.valueOf(f.getIdMes());
            if (f.getIdMes() < 10) idMes = "0" + idMes;
            valoresMap.put(idMes, String.valueOf(f.getValor()));
        }
        primeraCargaPaso2 = Boolean.TRUE;
        valoresActualizadoMap = new HashMap<>();
    }

    public String actualizarValoresConductores() {
        if (valores == null || valores.isEmpty()) {
            valores = new ArrayList<>();
            for (Map.Entry<String, String> e : valoresMap.entrySet()) {
                FactorActualizacionSII f = new FactorActualizacionSII();
                f.setIdMes(Integer.parseInt(e.getKey()));
                f.setValor(Float.parseFloat(e.getValue()));
                valores.add(f);
            }
        } else {
            for (FactorActualizacionSII f : valores) {
                String idMes = String.valueOf(f.getIdMes());
                if (f.getIdMes() < 10) idMes = "0" + idMes;
                f.setValor(Float.parseFloat(valoresMap.get(idMes)));
            }
        }
        logicaReportesSII.guardarFactoresActualizacion(valores);
        empleadores = logicaEmpresas.obtenerEmpleadores();
        anio = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        return "flowCorregirImpuesto";
    }

    public void cargarDatosConductores() {
        primeraCargaPaso2 = Boolean.FALSE;
        primeraCargaPaso3 = Boolean.TRUE;
        valoresDesagregados = new ArrayList<>();

        remuneraciones = logicaLibroRemuneraciones.obtenerSegunEmpleador(empleador, null, Integer.parseInt(anio));
        finiquitos = logicaFiniquito.obtenerFiniquitosTrabajador(empleador, Integer.parseInt(anio));


        sumatoriaImponible = 0;
        sumatoriaRentaAfecta = 0;
        sumatoriaRentaNoAfecta = 0;
        sumatoriaImpUnico = 0;
        sumatoriaImponibleActualizado = 0;
        sumatoriaRentaAfectaActualizada = 0;
        sumatoriaImpUnicoActualizado = 0;
        sumatoriaNoRentaAfectaActualizada = 0;
        Calendar c = Calendar.getInstance();
        for (Remuneracion r : remuneraciones) {
            sumatoriaImponible += r.getTotalImponible();
            sumatoriaRentaAfecta += r.getRentaAfecta();
            sumatoriaImpUnico += r.getImpUnico().intValue();
            c.setTime(r.getFechaLiquidacion());
            int rentaExentaMes = 0;
            for (Finiquito finiquito : logicaFiniquito.obtenerFiniquitosTrabajador(r.getIdPersonal(), c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1)) {
                rentaExentaMes += finiquito.getMonto();
            }

            for (BonoDescuentoRemuneracion bonoDescuentoRemuneracion : r.getRemuneracionBonoDescuentoList()) {
                if (!Boolean.TRUE.equals(bonoDescuentoRemuneracion.getImponible())) {
                    rentaExentaMes += bonoDescuentoRemuneracion.getMonto().intValue();
                }
            }

            sumatoriaImponibleActualizado += (int)(r.getTotalImponible() * obtenerFactor(r.getFechaLiquidacion()) / 100 + r.getTotalImponible());
            sumatoriaRentaAfectaActualizada += (int)(r.getRentaAfecta() * obtenerFactor(r.getFechaLiquidacion()) / 100 + r.getRentaAfecta());
            sumatoriaImpUnicoActualizado += (int)(r.getImpUnico() * obtenerFactor(r.getFechaLiquidacion()) / 100 + r.getImpUnico());
            sumatoriaNoRentaAfectaActualizada += (int)(rentaExentaMes * obtenerFactor(r.getFechaLiquidacion()) / 100 + rentaExentaMes);
        }
    }

    public String formatoFecha(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        return sdf.format(d);
    }

    public Float obtenerFactor(Date d) {
        if (d == null) return (float)0;
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return Float.parseFloat(valoresMap.get(sdf.format(d)));
    }

    public String prepararInforme() {
        conductores = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (Remuneracion r : remuneraciones) {
            Personal trabajador = r.getIdPersonal();
            if (!valoresActualizadoMap.containsKey(trabajador)) {
                valoresActualizadoMap.put(trabajador, new ValorActualizadoDTO());
                conductores.add(trabajador);
            }

            ValorActualizadoDTO puntero = valoresActualizadoMap.get(trabajador);

            puntero.setImponible(puntero.getImponible() + r.getTotalImponible());
            puntero.setRentaAfecta(puntero.getRentaAfecta() + r.getRentaAfecta());
            puntero.setImpuestoUnico(puntero.getImpuestoUnico() + r.getImpUnico().intValue());

            puntero.setImponibleActualizado(puntero.getImponibleActualizado() + (int)(r.getTotalImponible() * obtenerFactor(r.getFechaLiquidacion()) / 100 + r.getTotalImponible()));
            puntero.setRentaAfectaActualizada(puntero.getRentaAfectaActualizada() + (int)(r.getRentaAfecta() * obtenerFactor(r.getFechaLiquidacion()) / 100 + r.getRentaAfecta()));
            puntero.setImpuestoUnicoActualizado(puntero.getImpuestoUnicoActualizado() + (int)(r.getImpUnico() * obtenerFactor(r.getFechaLiquidacion()) / 100 + r.getImpUnico()));

            calendar.setTime(r.getFechaLiquidacion());
            puntero.getRentasAfectas().put(constantes.getMeses().get(calendar.get(Calendar.MONTH)), r.getRentaAfecta());

            int rentaNoAfecta = 0;
            List<Finiquito> finiquitos = logicaFiniquito.obtenerFiniquitosTrabajador(trabajador, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
            for (Finiquito finiquito : finiquitos) {
                puntero.setRentaNoAfecta(puntero.getRentaNoAfecta() + finiquito.getMonto());
                rentaNoAfecta += finiquito.getMonto();
            }
            for (BonoDescuentoRemuneracion bonoDescuentoRemuneracion : r.getRemuneracionBonoDescuentoList()) {
                if (!Boolean.TRUE.equals(bonoDescuentoRemuneracion.getImponible())) {
                    puntero.setRentaNoAfecta(puntero.getRentaNoAfecta() + bonoDescuentoRemuneracion.getMonto().intValue());
                    rentaNoAfecta += bonoDescuentoRemuneracion.getMonto().intValue();
                }
            }
            puntero.setRentaNoAfectaActualizada(puntero.getRentaNoAfectaActualizada()+ (int)(rentaNoAfecta * obtenerFactor(r.getFechaLiquidacion()) / 100 + rentaNoAfecta));

            puntero.getMesesTrabajados().add(calendar.get(Calendar.MONTH)+1);
        }
        return "flowGenerarReporte";
    }

    public String irPaso2() {
        return "paso2";
    }

    public void generarInforme() {
        primeraCargaPaso3 = Boolean.FALSE;
    }

    public String prepararResumenAnual() {
        primeraCargaPaso4 = Boolean.TRUE;
        return "paso4";
    }

    public void generarResumenAnual() {
        primeraCargaPaso4 = Boolean.FALSE;
    }

    public Boolean mesTrabajado(Personal conductor, Mes mes) {
        for (Integer m : this.valoresActualizadoMap.get(conductor).getMesesTrabajados()) {
            if (m == Integer.parseInt(mes.getId())) return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    // Zona ficticia para generar las remuneraciones asociados al empleador y al conductor seleccionado
    public List<Remuneracion> getRemuneracionesInforme() {
        List<Remuneracion> retorno = new ArrayList<>();
        if (conductor == null || empleador == null) return retorno;
        for (Remuneracion r : remuneraciones) {
            if (!r.getEmpleador().equals(empleador.getNombre()) || !r.getIdPersonal().equals(conductor)) continue;
            retorno.add(r);
        }
        return retorno;
    }

    public Integer getSumaTotalImponibleInforme() {
        int total = 0;
        if (remuneraciones != null)
            for (Remuneracion r : remuneraciones) {
                if (!r.getEmpleador().equals(empleador.getNombre()) || !r.getIdPersonal().equals(conductor)) continue;
                total += r.getTotalImponible();
            }
        return total;
    }

    public Integer getSumaTotalImponibleActualizadoInforme() {
        int total = 0;
        if (remuneraciones != null)
            for (Remuneracion r : remuneraciones) {
                if (!r.getEmpleador().equals(empleador.getNombre()) || !r.getIdPersonal().equals(conductor)) continue;
                total += (r.getTotalImponible() * this.obtenerFactor(r.getFechaLiquidacion()) / 100 + r.getTotalImponible());
            }
        return total;
    }

    public Integer getSumaRentaAfectaInforme() {
        int total = 0;
        if (remuneraciones != null)
            for (Remuneracion r : remuneraciones) {
                if (!r.getEmpleador().equals(empleador.getNombre()) || !r.getIdPersonal().equals(conductor)) continue;
                total += r.getRentaAfecta();
            }
        return total;
    }

    public Integer getSumaRentaAfectaActualizadaInforme() {
        int total = 0;
        if (remuneraciones != null)
            for (Remuneracion r : remuneraciones) {
                if (!r.getEmpleador().equals(empleador.getNombre()) || !r.getIdPersonal().equals(conductor)) continue;
                total += (r.getRentaAfecta() * this.obtenerFactor(r.getFechaLiquidacion()) / 100 + r.getRentaAfecta());
            }
        return total;
    }

    public Integer getSumaImpuestoUnicoInforme() {
        int total = 0;
        if (remuneraciones != null)
            for (Remuneracion r : remuneraciones) {
                if (!r.getEmpleador().equals(empleador.getNombre()) || !r.getIdPersonal().equals(conductor)) continue;
                total += r.getImpUnico();
            }
        return total;
    }

    public Integer getSumaImpuestoUnicoActualizadoInforme() {
        int total = 0;
        if (remuneraciones != null)
            for (Remuneracion r : remuneraciones) {
                if (!r.getEmpleador().equals(empleador.getNombre()) || !r.getIdPersonal().equals(conductor)) continue;
                total += (r.getImpUnico() * this.obtenerFactor(r.getFechaLiquidacion()) / 100 + r.getImpUnico());
            }
        return total;
    }

    // getters and setters
    public Map<String, String> getValoresMap() {
        return valoresMap;
    }

    public void setValoresMap(Map<String, String> valoresMap) {
        this.valoresMap = valoresMap;
    }

    public List<FactorActualizacionSII> getValores() {
        return valores;
    }

    public void setValores(List<FactorActualizacionSII> valores) {
        this.valores = valores;
    }

    public List<Personal> getConductores() {
        return conductores;
    }

    public void setConductores(List<Personal> conductores) {
        this.conductores = conductores;
    }

    public List<Empresa> getEmpleadores() {
        return empleadores;
    }

    public void setEmpleadores(List<Empresa> empleadores) {
        this.empleadores = empleadores;
    }

    public Empresa getEmpleador() {
        return empleador;
    }

    public void setEmpleador(Empresa empleador) {
        this.empleador = empleador;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public List<Remuneracion> getRemuneraciones() {
        return remuneraciones;
    }

    public void setRemuneraciones(List<Remuneracion> remuneraciones) {
        this.remuneraciones = remuneraciones;
    }

    public Boolean getPrimeraCargaPaso2() {
        return primeraCargaPaso2;
    }

    public void setPrimeraCargaPaso2(Boolean primeraCargaPaso2) {
        this.primeraCargaPaso2 = primeraCargaPaso2;
    }

    public Boolean getPrimeraCargaPaso3() {
        return primeraCargaPaso3;
    }

    public void setPrimeraCargaPaso3(Boolean primeraCargaPaso3) {
        this.primeraCargaPaso3 = primeraCargaPaso3;
    }

    public Boolean getPrimeraCargaPaso4() {
        return primeraCargaPaso4;
    }

    public void setPrimeraCargaPaso4(Boolean primeraCargaPaso4) {
        this.primeraCargaPaso4 = primeraCargaPaso4;
    }

    public Personal getConductor() {
        return conductor;
    }

    public void setConductor(Personal conductor) {
        this.conductor = conductor;
    }

    public Map<Personal, ValorActualizadoDTO> getValoresActualizadoMap() {
        return valoresActualizadoMap;
    }

    public void setValoresActualizadoMap(Map<Personal, ValorActualizadoDTO> valoresActualizadoMap) {
        this.valoresActualizadoMap = valoresActualizadoMap;
    }

    public Integer getSumatoriaImponible() {
        return sumatoriaImponible;
    }

    public void setSumatoriaImponible(Integer sumatoriaImponible) {
        this.sumatoriaImponible = sumatoriaImponible;
    }

    public Integer getSumatoriaRentaAfecta() {
        return sumatoriaRentaAfecta;
    }

    public void setSumatoriaRentaAfecta(Integer sumatoriaRentaAfecta) {
        this.sumatoriaRentaAfecta = sumatoriaRentaAfecta;
    }

    public Integer getSumatoriaImpUnico() {
        return sumatoriaImpUnico;
    }

    public void setSumatoriaImpUnico(Integer sumatoriaImpUnico) {
        this.sumatoriaImpUnico = sumatoriaImpUnico;
    }

    public Integer getSumatoriaImponibleActualizado() {
        return sumatoriaImponibleActualizado;
    }

    public void setSumatoriaImponibleActualizado(Integer sumatoriaImponibleActualizado) {
        this.sumatoriaImponibleActualizado = sumatoriaImponibleActualizado;
    }

    public Integer getSumatoriaRentaAfectaActualizada() {
        return sumatoriaRentaAfectaActualizada;
    }

    public void setSumatoriaRentaAfectaActualizada(Integer sumatoriaRentaAfectaActualizada) {
        this.sumatoriaRentaAfectaActualizada = sumatoriaRentaAfectaActualizada;
    }

    public Integer getSumatoriaImpUnicoActualizado() {
        return sumatoriaImpUnicoActualizado;
    }

    public void setSumatoriaImpUnicoActualizado(Integer sumatoriaImpUnicoActualizado) {
        this.sumatoriaImpUnicoActualizado = sumatoriaImpUnicoActualizado;
    }

    public Integer getSumatoriaRentaNoAfecta() {
        return sumatoriaRentaNoAfecta;
    }

    public void setSumatoriaRentaNoAfecta(Integer sumatoriaRentaNoAfecta) {
        this.sumatoriaRentaNoAfecta = sumatoriaRentaNoAfecta;
    }

    public List<Finiquito> getFiniquitos() {
        return finiquitos;
    }

    public void setFiniquitos(List<Finiquito> finiquitos) {
        this.finiquitos = finiquitos;
    }

    public List<ValorActualizadoDTO> getValoresDesagregados() {
        return valoresDesagregados;
    }

    public void setValoresDesagregados(List<ValorActualizadoDTO> valoresDesagregados) {
        this.valoresDesagregados = valoresDesagregados;
    }

    public Integer getSumatoriaNoRentaAfectaActualizada() {
        return sumatoriaNoRentaAfectaActualizada;
    }

    public void setSumatoriaNoRentaAfectaActualizada(Integer sumatoriaNoRentaAfectaActualizada) {
        this.sumatoriaNoRentaAfectaActualizada = sumatoriaNoRentaAfectaActualizada;
    }
}
