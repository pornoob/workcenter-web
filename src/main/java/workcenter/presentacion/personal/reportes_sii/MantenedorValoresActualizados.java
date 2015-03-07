package workcenter.presentacion.personal.reportes_sii;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Empresa;
import workcenter.entidades.FactorActualizacionSII;
import workcenter.entidades.Personal;
import workcenter.entidades.Remuneracion;
import workcenter.negocio.LogicaEmpresas;
import workcenter.negocio.personal.LogicaLibroRemuneraciones;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.negocio.registros.reportes_sii.LogicaReportesSII;
import workcenter.util.dto.Mes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by claudio on 15-02-15.
 */
@Component
@Scope("flow")
public class MantenedorValoresActualizados implements Serializable {
    @Autowired
    private LogicaReportesSII logicaReportesSII;

    @Autowired
    private LogicaLibroRemuneraciones logicaLibroRemuneraciones;

    @Autowired
    private LogicaPersonal logicaPersonal;

    @Autowired
    private LogicaEmpresas logicaEmpresas;

    private List<FactorActualizacionSII> valores;
    private Map<String, String> valoresMap;
    private List<Personal> conductores;
    private List<Empresa> empleadores;
    private Empresa empleador;
    private Personal conductor;
    private String anio;
    private List<Remuneracion> remuneraciones;

    // Variables que solo afectan el render
    private Boolean primeraCargaPaso2;
    private Boolean primeraCargaPaso3;
    private Boolean primeraCargaPaso4;

    public void inicio() {
        valores = logicaReportesSII.obtenerFactoresActualizacionSII();
        valoresMap = new HashMap<String, String>();
        for (FactorActualizacionSII f : valores) {
            String idMes = String.valueOf(f.getIdMes());
            if (f.getIdMes() < 10) idMes = "0" + idMes;
            valoresMap.put(idMes, String.valueOf(f.getValor()));
        }
        primeraCargaPaso2 = Boolean.TRUE;
    }

    public String actualizarValoresConductores() {
        if (valores == null || valores.isEmpty()) {
            valores = new ArrayList<FactorActualizacionSII>();
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
        remuneraciones = logicaLibroRemuneraciones.obtenerSegunEmpleador(empleador, null, Integer.parseInt(anio));
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
        conductores = new ArrayList<Personal>();
        for (Remuneracion r : remuneraciones) {
            if (conductores.contains(r.getIdPersonal())) continue;
            conductores.add(r.getIdPersonal());
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

    public Integer obtenerTotalImponible(Personal p) {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            if (!r.getEmpleador().equals(empleador.getNombre()) || !r.getIdPersonal().equals(p)) continue;
            total += r.getTotalImponible();
        }
        return total;
    }

    public Integer obtenerTotalRentaAfecta(Personal p) {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            if (!r.getEmpleador().equals(empleador.getNombre()) || !r.getIdPersonal().equals(p)) continue;
            total += r.getRentaAfecta();
        }
        return total;
    }

    public Integer obtenerTotalImpuestoUnico(Personal p) {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            if (!r.getEmpleador().equals(empleador.getNombre()) || !r.getIdPersonal().equals(p)) continue;
            total += r.getImpUnico();
        }
        return total;
    }

    public Integer obtenerTotalImponibleActualizado(Personal p) {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            if (!r.getEmpleador().equals(empleador.getNombre()) || !r.getIdPersonal().equals(p)) continue;
            total += (r.getTotalImponible() * obtenerFactor(r.getFechaLiquidacion()) / 100 + r.getTotalImponible());
        }
        return total;
    }

    public Integer obtenerTotalRentaAfectaActualizado(Personal p) {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            if (!r.getEmpleador().equals(empleador.getNombre()) || !r.getIdPersonal().equals(p)) continue;
            total += (r.getRentaAfecta() * obtenerFactor(r.getFechaLiquidacion()) / 100 + r.getRentaAfecta());
        }
        return total;
    }

    public Integer obtenerTotalImpuestoUnicoActualizado(Personal p) {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            if (!r.getEmpleador().equals(empleador.getNombre()) || !r.getIdPersonal().equals(p)) continue;
            total += (r.getImpUnico() * obtenerFactor(r.getFechaLiquidacion()) / 100 + r.getImpUnico());
        }
        return total;
    }

    public Integer getSumaTotalImponible() {
        int total = 0;
        if (remuneraciones != null)
            for (Remuneracion r : remuneraciones) {
                total += r.getTotalImponible();
            }
        return total;
    }

    public Integer getSumaTotalImponibleActualizado() {
        int total = 0;
        if (remuneraciones != null)
            for (Remuneracion r : remuneraciones) {
                total += (r.getTotalImponible() * this.obtenerFactor(r.getFechaLiquidacion()) + r.getTotalImponible());
            }
        return total;
    }

    public Integer getSumaRentaAfecta() {
        int total = 0;
        if (remuneraciones != null)
            for (Remuneracion r : remuneraciones) {
                total += r.getRentaAfecta();
            }
        return total;
    }

    public Integer getSumaRentaAfectaActualizada() {
        int total = 0;
        if (remuneraciones != null)
            for (Remuneracion r : remuneraciones) {
                total += (r.getRentaAfecta() * this.obtenerFactor(r.getFechaLiquidacion()) + r.getRentaAfecta());
            }
        return total;
    }

    public Integer getSumaImpuestoUnico() {
        int total = 0;
        if (remuneraciones != null)
            for (Remuneracion r : remuneraciones) {
                total += r.getImpUnico();
            }
        return total;
    }

    public Integer getSumaImpuestoUnicoActualizado() {
        int total = 0;
        if (remuneraciones != null)
            for (Remuneracion r : remuneraciones) {
                total += (r.getImpUnico() * this.obtenerFactor(r.getFechaLiquidacion()) + r.getImpUnico());
            }
        return total;
    }

    // Zona ficticia para generar las remuneraciones asociados al empleador y al conductor seleccionado
    public List<Remuneracion> getRemuneracionesInforme() {
        List<Remuneracion> retorno = new ArrayList<Remuneracion>();
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
                total += (r.getTotalImponible() * this.obtenerFactor(r.getFechaLiquidacion()) + r.getTotalImponible());
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
                total += (r.getRentaAfecta() * this.obtenerFactor(r.getFechaLiquidacion()) + r.getRentaAfecta());
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
                total += (r.getImpUnico() * this.obtenerFactor(r.getFechaLiquidacion()) + r.getImpUnico());
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
}
