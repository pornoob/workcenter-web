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
    private String anio;
    private List<Remuneracion> remuneraciones;

    public void inicio() {
        valores = logicaReportesSII.obtenerFactoresActualizacionSII();
        valoresMap = new HashMap<String, String>();
        for (FactorActualizacionSII f : valores) {
            String idMes = String.valueOf(f.getIdMes());
            if (f.getIdMes() < 10) idMes = "0" + idMes;
            valoresMap.put(idMes, String.valueOf(f.getValor()));
        }
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
        remuneraciones = logicaLibroRemuneraciones.obtenerSegunEmpleador(empleador, null, Integer.parseInt(anio));
    }

    public String formatoFecha(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        return sdf.format(d);
    }

    public Float obtenerFactor(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return Float.parseFloat(valoresMap.get(sdf.format(d)));
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
    
    public Integer getSumaTotalImponible() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getTotalImponible();
        }
        return total;
    }
    
    public Integer getSumaRentaAfecta() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getRentaAfecta();
        }
        return total;
    }
    
    public Integer getSumaImpuestoUnico() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getImpUnico();
        }
        return total;
    }
}
