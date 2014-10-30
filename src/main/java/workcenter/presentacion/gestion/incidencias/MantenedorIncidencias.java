package workcenter.presentacion.gestion.incidencias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.LogicaDocumentos;
import workcenter.negocio.incidencias.LogicaIncidencias;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.presentacion.includes.FicheroUploader;
import workcenter.util.components.Constantes;
import workcenter.util.components.FacesUtil;
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
    private LogicaPersonal logicaPersonal;

    @Autowired
    private FicheroUploader ficheroUploader;

    @Autowired
    private Constantes constantes;

    @Autowired
    private LogicaIncidencias logicaIncidencias;

    private List<MirSeveridad> severidades;
    private List<MirPrioridad> prioridades;
    private MirIncidencia incidencia;
    private MirTrazabilidadIncidencia trazabilidad;
    private List<MirApoyo> apoyos;
    private List<MirIncidencia> incidencias;
    private List<MirEstadoIncidencia> estadosDisponibles;

    public void inicio() {
        severidades = logicaIncidencias.obtSeveridades();
        prioridades = logicaIncidencias.obtPrioridades();
        apoyos = logicaIncidencias.obtApoyos();
        incidencias = logicaIncidencias.obtTodas();
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

    public void guardarIncidencia() {
        incidencia.setFecha(new Date());
        incidencia.setIdApoyo(logicaIncidencias.obtSiguienteApoyo());
        trazabilidad.setIdEstado(logicaIncidencias.obtEstado(constantes.getPiirEstadoInicial()));
        trazabilidad.setCreador(sesionCliente.getUsuario().getRut());
        logicaIncidencias.guardarIncidencia(incidencia, trazabilidad);
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Se ha creado la nueva incidencia, el apoyo asignado es: " + incidencia.getIdApoyo().getIdSocio().getNombreCompleto());
        irIngresar();
    }

    public void cambiarEstadoIncidencia() {
        logicaIncidencias.guardarIncidencia(incidencia, trazabilidad);
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Se ha cambiado el estado de la incidencia");
        irIngresar();
    }

    public MirEstadoIncidencia obtenerEstado(MirIncidencia i) {
        return logicaIncidencias.obtEstadoActual(i);
    }

    public boolean esInformador() {
        if (incidencia.getRutInformador() == null) return false;
        return sesionCliente.getUsuario().getRut().intValue() == incidencia.getRutInformador().getRut().intValue();
    }

    public boolean esApoyo() {
        if (incidencia.getIdApoyo().getIdSocio() == null || incidencia.getIdApoyo().getIdSocio().getRut() == null) return false;
        return sesionCliente.getUsuario().getRut().intValue() == incidencia.getIdApoyo().getIdSocio().getRut().intValue();
    }

    public String irListar() {
        incidencias = logicaIncidencias.obtTodas();
        return "flowListar";
    }

    public String irIngresar() {
        incidencia = new MirIncidencia();
        incidencia.setRutInformador(logicaPersonal.obtener(sesionCliente.getUsuario().getRut()));
        trazabilidad = new MirTrazabilidadIncidencia();
        trazabilidad.setIdIncidencia(incidencia);
        calcularPeso();
        return "flowIngresar";
    }

    public String irCambiarEstado(MirIncidencia i) {
        incidencia = i;
        if (!esApoyo()) {
            estadosDisponibles = logicaIncidencias.obtenerEstadosDisponiblesInformador(incidencia);
        } else {
            estadosDisponibles = logicaIncidencias.obtenerEstadosDisponiblesApoyo(incidencia);
        }
        trazabilidad = new MirTrazabilidadIncidencia();
        trazabilidad.setCreador(sesionCliente.getUsuario().getRut());
        trazabilidad.setIdIncidencia(incidencia);
        return "flowCambiarEstado";
    }

    public String detalleInicial(MirIncidencia i) {
        return logicaIncidencias.obtDetalleInicial(i);
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

    public List<MirIncidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<MirIncidencia> incidencias) {
        this.incidencias = incidencias;
    }

    public List<MirEstadoIncidencia> getEstadosDisponibles() {
        return estadosDisponibles;
    }

    public void setEstadosDisponibles(List<MirEstadoIncidencia> estadosDisponibles) {
        this.estadosDisponibles = estadosDisponibles;
    }
}