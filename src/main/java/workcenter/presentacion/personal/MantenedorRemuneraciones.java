package workcenter.presentacion.personal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.BonoDescuentoRemuneracion;
import workcenter.entidades.Empresa;
import workcenter.entidades.Remuneracion;
import workcenter.entidades.Personal;
import workcenter.negocio.LogicaEmpresas;
import workcenter.negocio.personal.LogicaLibroRemuneraciones;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.util.pojo.*;
import workcenter.util.components.Constantes;
import workcenter.util.components.SesionCliente;

/**
 * @author colivares
 */
@Component
@Scope("flow")
public class MantenedorRemuneraciones implements Serializable {

    @Autowired
    private LogicaLibroRemuneraciones logicaLibroRemuneraciones;

    @Autowired
    private LogicaPersonal logicaPersonal;

    @Autowired
    private LogicaEmpresas logicaEmpresas;

    @Autowired
    private Constantes constantes;

    @Autowired
    private SesionCliente sesionCliente;

    private Integer criterio = null;
    private List<Personal> conductores;
    private Personal conductorSeleccionado;
    private List<Empresa> empleadores;
    private Empresa empleadorSeleccionado;
    private List<Remuneracion> remuneraciones;
    private Integer mesSeleccionado;
    private Integer anioIngresado;
    private BonosTablaRemuneracion bonosTablaRemuneracion;
    private DescuentosTablaRemuneracion descuentosTablaRemuneracion;
    private Remuneracion remuneracionSeleccionada;

    public String inicio() {
        conductores = obtenerConductores();
        empleadores = obtenerEmpleadores();
        anioIngresado = Calendar.getInstance().get(Calendar.YEAR);
        return "flowInicio";
    }
    
    public String formatoFecha(Date f) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        return sdf.format(f);
    }

    public StreamedContent generaLiquidacion(Remuneracion r) {
        try {
            if (r.getArchivo() == null) {
                FacesUtil.mostrarMensajeError("Operación fallida", "No se adjuntó la liquidación de "+r.getIdPersonal().getNombreCompleto());
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String liquidacion = constantes.getPathArchivos() + "/tmp/" + sesionCliente.getUsuario().getRut() + "/liq_" + r.getIdPersonal().getRut() + "_" + sdf.format(r.getFechaLiquidacion()) + "." + r.getExtension();
            new File(liquidacion.substring(0, liquidacion.lastIndexOf('/'))).mkdirs();

            File archivo = new File(liquidacion);
            if (archivo.exists() && new Date(archivo.lastModified()).after(r.getFechaLiquidacion())) {
                return new Descargable(archivo).getStreamedContent();
            }
            FileOutputStream fos = new FileOutputStream(liquidacion);
            fos.write(r.getArchivo());
            return new Descargable(archivo).getStreamedContent();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MantenedorRemuneraciones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MantenedorRemuneraciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesUtil.mostrarMensajeError("Operación fallida", "Ha ocurrido un error interno inténtelo más tarde");
        return null;
    }

    public void filtrarRemuneraciones() {
        if (criterio == constantes.getFiltroConductor()) {
            remuneraciones = logicaLibroRemuneraciones.obtenerSegunConductor(conductorSeleccionado, mesSeleccionado, anioIngresado);
        } else {
            remuneraciones = logicaLibroRemuneraciones.obtenerSegunEmpleador(empleadorSeleccionado, mesSeleccionado, anioIngresado);
        }
        bonosTablaRemuneracion = new BonosTablaRemuneracion(remuneraciones);
        descuentosTablaRemuneracion = new DescuentosTablaRemuneracion(remuneraciones);
    }

    public Integer getTotalBase() {
        int total = 0;
        if (remuneraciones == null) return total;
        for (Remuneracion r : remuneraciones) {
            total += r.getSueldoBase();
        }
        return total;
    }

    public Integer getTotalGratificacion() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getGratificacion();
        }
        return total;
    }

    public Integer getTotalHrsEspera() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getHoraEspera();
        }
        return total;
    }

    public Integer getTotalSemanaCorrida() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getSemanaCorrida();
        }
        return total;
    }

    public Integer getTotalHrsExtra() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getSemanaCorrida();
        }
        return total;
    }

    public Integer obtenerTotalBono(String nombreBono) {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            for (BonoDescuentoRemuneracion b : obtenerBonos(r)) {
                if (b.getDescripcion().equals(nombreBono)) {
                    total += b.getMonto().intValue();
                }
            }
        }
        return total;
    }

    public Integer getSumaImponibles() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getTotalImponible();
        }
        return total;
    }

    public Integer getTotalViaticos() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getViatico();
        }
        return total;
    }

    public Integer getSumaHaberes() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getTotalHaberes();
        }
        return total;
    }

    public Integer getTotalDctoAfp() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getDectoAFP();
        }
        return total;
    }

    public Integer getTotalAporteTrabajador() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getAporteTrabajador();
        }
        return total;
    }

    public Integer getTotalDctoPrevision() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getDctoPrevision();
        }
        return total;
    }

    public Integer getTotalImpUnico() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getImpUnico();
        }
        return total;
    }

    public Integer obtenerTotalDescuento(String nombreDescuento) {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            for (BonoDescuentoRemuneracion d : obtenerDescuentos(r)) {
                if (d.getDescripcion().equals(nombreDescuento)) {
                    total += d.getMonto().intValue();
                }
            }
        }
        return total;
    }

    public Integer getSumaTotalDescuentos() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getTotalDctos();
        }
        return total;
    }

    public Integer getTotalAlcanceLiquido() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getAlcanceLiquido();
        }
        return total;
    }

    public Integer getTotalAnticipoSueldo() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getAnticipoSueldo();
        }
        return total;
    }

    public Integer getTotalDifCaja() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getDifCaja();
        }
        return total;
    }

    public Integer getTotalLiqPagar() {
        int total = 0;
        for (Remuneracion r : remuneraciones) {
            total += r.getLiqPagar();
        }
        return total;
    }

    public List<BonoDescuentoRemuneracion> obtenerBonos(Remuneracion r) {
        List<BonoDescuentoRemuneracion> retorno = new ArrayList<BonoDescuentoRemuneracion>();
        if (bonosTablaRemuneracion == null) {
            return retorno;
        }
        for (String s : bonosTablaRemuneracion.getTipos()) {
            boolean encontrado = false;
            if (r != null && r.getRemuneracionBonoDescuentoList() != null) {
                for (BonoDescuentoRemuneracion b : r.getRemuneracionBonoDescuentoList()) {
                    if (b.getDescripcion().equals(s)) {
                        encontrado = true;
                        retorno.add(b);
                        break;
                    }
                }
            }
            if (!encontrado) {
                BonoDescuentoRemuneracion b = new BonoDescuentoRemuneracion();
                b.setDescripcion(s);
                b.setMonto(BigInteger.ZERO);
                retorno.add(b);
            }
        }
        return retorno;
    }

    public List<BonoDescuentoRemuneracion> obtenerDescuentos(Remuneracion r) {
        List<BonoDescuentoRemuneracion> retorno = new ArrayList<BonoDescuentoRemuneracion>();
        if (descuentosTablaRemuneracion == null) {
            return retorno;
        }
        for (String s : descuentosTablaRemuneracion.getTipos()) {
            boolean encontrado = false;
            if (r != null && r.getRemuneracionBonoDescuentoList() != null) {
                for (BonoDescuentoRemuneracion d : r.getRemuneracionBonoDescuentoList()) {
                    if (d.getDescripcion().equals(s)) {
                        encontrado = true;
                        retorno.add(d);
                        break;
                    }
                }
            }
            if (!encontrado) {
                BonoDescuentoRemuneracion d = new BonoDescuentoRemuneracion();
                d.setDescripcion(s);
                d.setMonto(BigInteger.ZERO);
                retorno.add(d);
            }
        }
        return retorno;
    }

    public List<Personal> obtenerConductores() {
        return logicaPersonal.obtenerConductores();
    }

    public List<Empresa> obtenerEmpleadores() {
        return logicaEmpresas.obtenerEmpleadores();
    }

    public void criterioEmpleador() {
        criterio = constantes.getFiltroEmpleador();
    }

    public void criterioConductor() {
        criterio = constantes.getFiltroConductor();
    }

    public Integer getCriterio() {
        return criterio;
    }

    public void setCriterio(Integer criterio) {
        this.criterio = criterio;
    }

    public List<Personal> getConductores() {
        return conductores;
    }

    public void setConductores(List<Personal> conductores) {
        this.conductores = conductores;
    }

    public Personal getConductorSeleccionado() {
        return conductorSeleccionado;
    }

    public void setConductorSeleccionado(Personal conductorSeleccionado) {
        this.conductorSeleccionado = conductorSeleccionado;
    }

    public List<Empresa> getEmpleadores() {
        return empleadores;
    }

    public void setEmpleadores(List<Empresa> empleadores) {
        this.empleadores = empleadores;
    }

    public Empresa getEmpleadorSeleccionado() {
        return empleadorSeleccionado;
    }

    public void setEmpleadorSeleccionado(Empresa empleadorSeleccionado) {
        this.empleadorSeleccionado = empleadorSeleccionado;
    }

    public List<Remuneracion> getRemuneraciones() {
        return remuneraciones;
    }

    public void setRemuneraciones(List<Remuneracion> remuneraciones) {
        this.remuneraciones = remuneraciones;
    }

    public Integer getMesSeleccionado() {
        return mesSeleccionado;
    }

    public void setMesSeleccionado(Integer mesSeleccionado) {
        this.mesSeleccionado = mesSeleccionado;
    }

    public Integer getAnioIngresado() {
        return anioIngresado;
    }

    public void setAnioIngresado(Integer anioIngresado) {
        this.anioIngresado = anioIngresado;
    }

    public BonosTablaRemuneracion getBonosTablaRemuneracion() {
        return bonosTablaRemuneracion;
    }

    public void setBonosTablaRemuneracion(BonosTablaRemuneracion bonosTablaRemuneracion) {
        this.bonosTablaRemuneracion = bonosTablaRemuneracion;
    }

    public DescuentosTablaRemuneracion getDescuentosTablaRemuneracion() {
        return descuentosTablaRemuneracion;
    }

    public void setDescuentosTablaRemuneracion(DescuentosTablaRemuneracion descuentosTablaRemuneracion) {
        this.descuentosTablaRemuneracion = descuentosTablaRemuneracion;
    }

    public Remuneracion getRemuneracionSeleccionada() {
        return remuneracionSeleccionada;
    }

    public void setRemuneracionSeleccionada(Remuneracion remuneracionSeleccionada) {
        this.remuneracionSeleccionada = remuneracionSeleccionada;
    }
}
