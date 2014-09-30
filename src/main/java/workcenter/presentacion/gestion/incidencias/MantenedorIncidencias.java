package workcenter.presentacion.gestion.incidencias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.MirIncidencia;
import workcenter.entidades.MirPrioridad;
import workcenter.entidades.MirSeveridad;
import workcenter.entidades.MirTrazabilidadIncidencia;
import workcenter.negocio.LogicaDocumentos;
import workcenter.negocio.incidencias.LogicaIncidencias;
import workcenter.presentacion.includes.FicheroUploader;
import workcenter.util.components.SesionCliente;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by claudio on 26-09-14.
 */
@Component
@Scope("flow")
public class MantenedorIncidencias implements Serializable {
    @Autowired
    private SesionCliente sesionCliente;

    @Autowired
    private LogicaDocumentos logicaDocumentos;

    @Autowired
    private FicheroUploader ficheroUploader;

    @Autowired
    private LogicaIncidencias logicaIncidencias;

    private List<MirSeveridad> severidades;
    private List<MirPrioridad> prioridades;
    private MirIncidencia incidencia;
    private MirTrazabilidadIncidencia trazabilidad;

    public void inicio() {
        severidades = logicaIncidencias.obtSeveridades();
        prioridades = logicaIncidencias.obtPrioridades();
    }

    public void calcularPeso() {
        int pesoDias = 0;
        Calendar c = Calendar.getInstance();
        try {
            switch (incidencia.getPrioridad().getPeso() * incidencia.getSeveridad().getPeso()) {
                case 1:
                    pesoDias = 5;
                    break;
                case 2:
                    pesoDias = 4;
                    break;
                case 3:
                    pesoDias = 3;
                    break;
                case 4:
                    pesoDias = 2;
                    break;
                case 6:
                    pesoDias = 1;
                    break;
                case 9:
                    pesoDias = 0;
                    break;
            }
        } catch (NullPointerException ne) {
            pesoDias = 5;
        }
        c.add(Calendar.DAY_OF_MONTH, pesoDias);
        incidencia.setResolucionProgramada(c.getTime());
    }

    public String irListar() {
        return "flowListar";
    }

    public String irIngresar() {
        incidencia = new MirIncidencia();
        incidencia.setRutInformador(sesionCliente.getUsuario().getRut());
        trazabilidad = new MirTrazabilidadIncidencia();
        trazabilidad.setIdIncidencia(incidencia);
        calcularPeso();
        return "flowIngresar";
    }

    public String irCambiarEstado(MirIncidencia i) {
        incidencia = i;
        return "flowCambiarEstado";
    }

    public void guardarIncidencia() {
        incidencia.setFecha(new Date());
        incidencia.setIdApoyo(0);
        trazabilidad.setIdEstado(null);
        logicaIncidencias.guardarIncidencia(incidencia, trazabilidad);
    }

    public List<MirSeveridad> getSeveridades() {
        return severidades;
    }

    public void setSeveridades(List<MirSeveridad> severidades) {
        this.severidades = severidades;
    }

    public List<MirPrioridad> getPrioridades() {
        return prioridades;
    }

    public void setPrioridades(List<MirPrioridad> prioridades) {
        this.prioridades = prioridades;
    }

    public MirIncidencia getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(MirIncidencia incidencia) {
        this.incidencia = incidencia;
    }

    public MirTrazabilidadIncidencia getTrazabilidad() {
        return trazabilidad;
    }

    public void setTrazabilidad(MirTrazabilidadIncidencia trazabilidad) {
        this.trazabilidad = trazabilidad;
    }
}
