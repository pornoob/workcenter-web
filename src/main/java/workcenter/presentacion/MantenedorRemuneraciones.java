package workcenter.presentacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.BonoDescuentoRemuneracion;
import workcenter.entidades.Empresa;
import workcenter.entidades.Remuneracion;
import workcenter.entidades.Personal;
import workcenter.negocio.LogicaEmpresas;
import workcenter.negocio.LogicaLibroRemuneraciones;
import workcenter.negocio.LogicaPersonal;
import workcenter.util.pojo.FacesUtil;
import workcenter.util.components.Constantes;
import workcenter.util.components.SesionCliente;
import workcenter.util.pojo.BonosTablaRemuneracion;
import workcenter.util.pojo.DescuentosTablaRemuneracion;
import workcenter.util.pojo.StaticContext;

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
    private final String[] claseTablas = {
        "ui-datatable-even", "ui-datatable-odd"
    };
    private int colorFila;
    private String urlLiquidacion;
    private Remuneracion remuneracionSeleccionada;

    public String inicio() {
        conductores = obtenerConductores();
        empleadores = obtenerEmpleadores();
        colorFila = 0;
        return "flowInicio";
    }
    
    public String formatoFecha(Date f) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        return sdf.format(f);
    }

    public void generaLiquidacion(Remuneracion r) {
        try {
            if (r.getArchivo() == null) {
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dir = System.getProperty("catalina.base");
//            String liquidacion = constantes.getContextoEstatico() + FacesUtil.obtenerHttpServletRequest().getContextPath() + "/" + sesionCliente.getUsuario().getRut() + "/liq_" + r.getIdPersonal().getRut() + "_" + sdf.format(r.getFechaLiquidacion()) + "." + r.getExtension();
            String liquidacion = "";
            new File(dir+liquidacion.substring(0, liquidacion.lastIndexOf('/'))).mkdirs();

            File archivo = new File(dir + liquidacion);
            if (archivo.exists() && new Date(archivo.lastModified()).after(r.getFechaLiquidacion())
                    && r.equals(remuneracionSeleccionada)) {
                remuneracionSeleccionada = r;
                urlLiquidacion = StaticContext.obtenerUrlServidor() + liquidacion;
                System.out.println("URL: " + urlLiquidacion);
                return;
            }
            FileOutputStream fos = new FileOutputStream(dir + liquidacion);
            fos.write(r.getArchivo());
            urlLiquidacion = StaticContext.obtenerUrlServidor() + liquidacion;
            remuneracionSeleccionada = r;
            System.out.println("URL: " + urlLiquidacion);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MantenedorRemuneraciones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MantenedorRemuneraciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void filtrarRemuneraciones() {
        if (criterio == constantes.getFiltroConductor()) {
            remuneraciones = logicaLibroRemuneraciones.obtenerSegunConductor(conductorSeleccionado, mesSeleccionado, anioIngresado);
        } else {
            remuneraciones = logicaLibroRemuneraciones.obtenerSegunEmpleador(empleadorSeleccionado, mesSeleccionado, anioIngresado);
        }
        bonosTablaRemuneracion = new BonosTablaRemuneracion(remuneraciones);
        descuentosTablaRemuneracion = new DescuentosTablaRemuneracion(remuneraciones);
        colorFila = 0;
    }

    public String obtenerColorFila() {
        return claseTablas[(colorFila++ % claseTablas.length)];
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

    public String getUrlLiquidacion() {
        return urlLiquidacion;
    }

    public void setUrlLiquidacion(String urlLiquidacion) {
        this.urlLiquidacion = urlLiquidacion;
    }

    public Remuneracion getRemuneracionSeleccionada() {
        return remuneracionSeleccionada;
    }

    public void setRemuneracionSeleccionada(Remuneracion remuneracionSeleccionada) {
        this.remuneracionSeleccionada = remuneracionSeleccionada;
    }
}
