package workcenter.presentacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.AlarmaGps;
import workcenter.entidades.Documento;
import workcenter.entidades.GestionAlarmaGps;
import workcenter.entidades.Servicio;
import workcenter.entidades.Usuario;
import workcenter.negocio.LogicaAlarmasGps;
import workcenter.negocio.LogicaDocumentos;
import workcenter.presentacion.includes.FicheroUploader;
import workcenter.util.WorkcenterFileListener;
import workcenter.util.components.Constantes;
import workcenter.util.components.SesionCliente;
import workcenter.util.dto.Mes;
import workcenter.util.pojo.Descargable;
import workcenter.util.pojo.DynamicColumnDataTable;
import workcenter.util.components.FacesUtil;

/**
 * @author colivares
 */
@Component
@Scope("flow")
public class MantenedorAlarmasGps implements Serializable, WorkcenterFileListener {

    private String mesSeleccionado;
    private String anioSeleccionado;
    private Servicio servicioSeleccionado;
    private String conductorSeleccionado;
    private String fechaSeleccionada;
    private String fechaSeleccionadaMod;
    private List<String> conductores;
    private List<AlarmaGps> alarmas;
    private List<AlarmaGps> detalleAlarmas;
    private List<Servicio> servicios;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    private boolean indicarGestionActivado;
    private GestionAlarmaGps gestionAlarma;
    private String indicadorCumplimiento;
    private List<Descargable> descargables;
    private Map<String, List<Documento>> documentosSubidos;

    @Autowired
    LogicaAlarmasGps logicaAlarmasGps;

    @Autowired
    LogicaDocumentos logicaDocumentos;

    @Autowired
    SesionCliente sesionCliente;

    @Autowired
    Constantes constantes;

    @Autowired
    FicheroUploader ficheroUploader;

    public String inicio() {
        mesSeleccionado = Calendar.getInstance().get(Calendar.MONTH) < 9 ? "0" + (Calendar.getInstance().get(Calendar.MONTH) + 1) : String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        anioSeleccionado = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        obtenerServicios();
        alarmas = null;
        descargables = new ArrayList<Descargable>();
        return "flowListarAlarmas";
    }

    public void subir(FileUploadEvent fue) {
        Documento d = ficheroUploader.subir(fue);
        if (documentosSubidos == null) {
            documentosSubidos = new HashMap<String, List<Documento>>();
        }
        if (d == null) {
            return;
        }
        if (documentosSubidos.get(fechaSeleccionada + "|" + conductorSeleccionado) == null) {
            List<Documento> docs = new ArrayList<Documento>();
            docs.add(d);
            documentosSubidos.put(fechaSeleccionada + "|" + conductorSeleccionado, docs);
        } else {
            List<Documento> docs = documentosSubidos.get(fechaSeleccionada + "|" + conductorSeleccionado);
            docs.add(d);
            documentosSubidos.put(fechaSeleccionada + "|" + conductorSeleccionado, docs);
        }
    }

    public void enlazar(Descargable descargable) {
        Documento d = logicaDocumentos.obtenerPorCodigo(descargable.getArchivo().getName());
        if (documentosSubidos == null) {
            documentosSubidos = new HashMap<String, List<Documento>>();
        }
        if (documentosSubidos.get(fechaSeleccionada + "|" + conductorSeleccionado) == null) {
            List<Documento> docs = new ArrayList<Documento>();
            docs.add(d);
            documentosSubidos.put(fechaSeleccionada + "|" + conductorSeleccionado, docs);
        } else {
            List<Documento> docs = documentosSubidos.get(fechaSeleccionada + "|" + conductorSeleccionado);
            docs.add(d);
            documentosSubidos.put(fechaSeleccionada + "|" + conductorSeleccionado, docs);
        }
        FacesUtil.mostrarMensajeInformativo("Archivo enlazado", "Se ha enlazado el documento " + d.getId() + " a la gestión");
    }

    public void verGestion() throws FileNotFoundException {
        try {
            descargables = new ArrayList<Descargable>();
            gestionAlarma = new ArrayList<GestionAlarmaGps>(detalleAlarmas.get(0).getGestionAlarmasGpsCollection()).get(0);
            List<Documento> docs = logicaDocumentos.obtenerDocumentosAsociados(gestionAlarma);
            for (Documento d : docs) {
                Descargable descargable = new Descargable(new File(constantes.getPathArchivos() + d.getId()));
                descargable.setNombre(d.getNombreOriginal());
                descargables.add(descargable);
            }
            indicarGestionActivado = true;
        } catch (Exception e) {
            Logger.getLogger(MantenedorAlarmasGps.class.getName()).log(Level.SEVERE, null, e);
            indicarGestionActivado = false;
        }
    }

    public void indicarGestion() {
        gestionAlarma = new GestionAlarmaGps();
        indicarGestionActivado = true;
    }

    public void guardarGestion() {
        SimpleDateFormat formatoTabla = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = new Date();
        indicarGestionActivado = false;
        for (AlarmaGps a : detalleAlarmas) {
            GestionAlarmaGps ga = new GestionAlarmaGps();
            ga.setDetalle(gestionAlarma.getDetalle());
            ga.setIdAlarma(a);
            ga.setIdUsuario(new Usuario(sesionCliente.getUsuario().getRut()));
            ga.setFecha(fecha);
            ga.setIdAlarma(a);
            if (documentosSubidos == null || documentosSubidos.isEmpty()) {
                FacesUtil.mostrarMensajeError("No se han guardado los cambios", "Para cada gestión es necesario adjuntar un comprobante");
                ga = null;
                return;
            }

            List<Documento> docs = documentosSubidos.get(formatoTabla.format(a.getFecha()) + "|" + a.getChofer());
            if (docs != null && !docs.isEmpty()) {
                logicaAlarmasGps.guardarGestionAlarma(ga);
                logicaDocumentos.asociarDocumentos(docs, ga);
            } else {
                FacesUtil.mostrarMensajeError("No se han guardado los cambios", "Para cada gestión es necesario adjuntar un comprobante");
            }
        }
        FacesUtil.mostrarMensajeInformativo("Gestión guardada exitosamente", "Se han enlazado los ficheros respectivos");
        documentosSubidos = new HashMap<String, List<Documento>>();
    }

    public boolean puedeGestionar() {
        try {
            if (fechaSeleccionadaMod == null || !fechaSeleccionadaMod.contains("/")) {
                return false;
            }
            Date fecha = sdf.parse(fechaSeleccionada + " 00:00:00");
            Date diaSiguiente = new Date(fecha.getTime() + (1000 * 60 * 60 * 24));
            for (AlarmaGps a : alarmas) {
                if ((a.getChofer().equals(conductorSeleccionado)
                        && ((a.getFecha().after(fecha) && a.getFecha().before(diaSiguiente)) || a.getFecha().equals(fecha)))
                        && (a.getGestionAlarmasGpsCollection() == null || a.getGestionAlarmasGpsCollection().isEmpty())) {
                    return true;
                }
            }
            return false;
        } catch (ParseException ex) {
            Logger.getLogger(MantenedorAlarmasGps.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean puedeVerGestion() {
        try {
            if (!fechaSeleccionadaMod.contains("/")) {
                return false;
            }
            Date fecha = sdf.parse(fechaSeleccionada + " 00:00:00");
            Date diaSiguiente = new Date(fecha.getTime() + (1000 * 60 * 60 * 24));
            for (AlarmaGps a : alarmas) {
                if ((a.getChofer().equals(conductorSeleccionado)
                        && ((a.getFecha().after(fecha) && a.getFecha().before(diaSiguiente)) || a.getFecha().equals(fecha)))
                        && (a.getGestionAlarmasGpsCollection() == null || a.getGestionAlarmasGpsCollection().isEmpty())) {
                    return false;
                }
            }
            return true;
        } catch (ParseException ex) {
            Logger.getLogger(MantenedorAlarmasGps.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public String obtenerDetalleAlarmas(String conductor, String fecha) {
        indicarGestionActivado = false;
        this.conductorSeleccionado = conductor;
        this.fechaSeleccionada = fecha;
        detalleAlarmas = new ArrayList<AlarmaGps>();
        try {
            if (!fecha.equals("conductor")) {
                Date fechaSolicitada = sdf.parse(fecha + " 00:00:00");
                Date diaSiguiente = new Date(fechaSolicitada.getTime() + (1000 * 60 * 60 * 24));
                for (AlarmaGps a : alarmas) {
                    if (a.getChofer().equals(conductor)
                            && ((a.getFecha().after(fechaSolicitada) && a.getFecha().before(diaSiguiente))
                            || a.getFecha().equals(fechaSolicitada))) {
                        detalleAlarmas.add(a);
                    }
                }
                fechaSeleccionadaMod = "día " + fecha;
            } else {
                for (AlarmaGps a : alarmas) {
                    if (a.getChofer().equals(conductor)) {
                        detalleAlarmas.add(a);
                    }
                }
                Mes e = null;
                for (Mes m : constantes.getMeses()) {
                    if (m.getId().equals(mesSeleccionado)) {
                        e = m;
                    }
                }
                this.fechaSeleccionadaMod = "mes de " + e.getNombre();
            }
        } catch (ParseException ex) {
            Logger.getLogger(MantenedorAlarmasGps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "flowDetalleAlarmas";
    }

    public void obtenerServicios() {
        if (!sesionCliente.getUsuario().isExterno())
            servicios = logicaAlarmasGps.obtenerServicios(sesionCliente.getUsuario().getRut());
        else
            servicios = logicaAlarmasGps.obtenerServicios(sesionCliente.getUsuario().getUsuario());
    }

    public boolean obtenerColorCelda(String conductor, String columna) {
        try {
            Date fecha = sdf.parse(columna + " 00:00:00");
            Date diaSiguiente = new Date(fecha.getTime() + (1000 * 60 * 60 * 24));
            for (AlarmaGps a : alarmas) {
                if (a.getChofer().equals(conductor)
                        && ((a.getFecha().after(fecha) && a.getFecha().before(diaSiguiente)) || a.getFecha().equals(fecha))) {
                    if (a.getGestionAlarmasGpsCollection() == null || a.getGestionAlarmasGpsCollection().isEmpty()) {
                        return false;
                    }
                }
            }
            return true;
        } catch (ParseException ex) {

            for (AlarmaGps a : alarmas) {
                if (a.getChofer().equals(conductor)) {
                    if (a.getGestionAlarmasGpsCollection() == null || a.getGestionAlarmasGpsCollection().isEmpty()) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public boolean noEsCero(String conductor, String columna) {
        return !obtenerValorCelda(conductor, columna).equals("0");
    }

    public String obtenerValorCelda(String conductor, String columna) {
        try {
            Date fecha = sdf.parse(columna + " 00:00:00");
            Date diaSiguiente = new Date(fecha.getTime() + (1000 * 60 * 60 * 24));
            int contadorAlarmas = 0;
            for (AlarmaGps a : alarmas) {
                if (a.getChofer().equals(conductor)
                        && ((a.getFecha().after(fecha) && a.getFecha().before(diaSiguiente)) || a.getFecha().equals(fecha))) {
                    contadorAlarmas++;
                }
            }
            return String.valueOf(contadorAlarmas);
        } catch (ParseException ex) {
//            Logger.getLogger(MantenedorAlarmasGps.class.getName()).log(Level.SEVERE, null, ex);
            int contadorAlarmas = 0;
            for (AlarmaGps a : alarmas) {
                if (a.getChofer().equals(conductor)) {
                    contadorAlarmas++;
                }
            }
            return conductor + " (" + contadorAlarmas + ")";
        }
    }

    public void filtrar() {
        try {
            obtenerConductores();
            indicarGestionActivado = false;
            indicadorCumplimiento = "Calculando...";
            alarmas = logicaAlarmasGps.obtenerPorMes(mesSeleccionado, anioSeleccionado, servicioSeleccionado);
            Date dia = sdf.parse("01/" + mesSeleccionado + "/" + anioSeleccionado + " 00:00:00");
            Date diaSiguiente;
            Calendar c = Calendar.getInstance();
            c.setTime(dia);
            int cantDiasMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            float acumulador = 0;
            int cantPromedios = 0;
            for (int i = 1; i <= cantDiasMes; i++) {
                HashSet<String> conductoresDia = new HashSet<String>();
                HashMap<String, Integer> resultadoDiario = new HashMap<String, Integer>();
                float promedioDiario;
                float acumuladorDiario = 0;
                int conductoresConIncidencias = 0;

                if (i < 10) {
                    dia = sdf.parse("0" + i + "/" + mesSeleccionado + "/" + anioSeleccionado + " 00:00:00");
                    if (i < 9) {
                        diaSiguiente = sdf.parse("0" + (i + 1) + "/" + mesSeleccionado + "/" + anioSeleccionado + " 00:00:00");
                    } else {
                        diaSiguiente = sdf.parse((i + 1) + "/" + mesSeleccionado + "/" + anioSeleccionado + " 00:00:00");
                    }
                } else {
                    dia = sdf.parse(i + "/" + mesSeleccionado + "/" + anioSeleccionado + " 00:00:00");
                    diaSiguiente = sdf.parse((i + 1) + "/" + mesSeleccionado + "/" + anioSeleccionado + " 00:00:00");
                }

                for (AlarmaGps a : alarmas) {
                    if ((a.getFecha().after(dia) && a.getFecha().before(diaSiguiente)) || a.getFecha().equals(dia)) {
                        conductoresDia.add(a.getChofer());
                        if (resultadoDiario.get(a.getChofer()) == null) {
                            resultadoDiario.put(a.getChofer(), 0);
                        }
                        if (a.getGestionAlarmasGpsCollection() == null || a.getGestionAlarmasGpsCollection().isEmpty()) {
                            Integer valor = resultadoDiario.get(a.getChofer());
                            valor = valor.intValue() + 1;
                            resultadoDiario.put(a.getChofer(), valor);
                        }
                    }
                }
                Iterator<String> it = conductoresDia.iterator();
                while (it.hasNext()) {
                    conductoresConIncidencias++;
                    if (resultadoDiario.get(it.next()).intValue() == 0) {
                        acumuladorDiario++;
                    }
                }
                if (conductoresConIncidencias > 0) {
                    promedioDiario = (float) acumuladorDiario / conductoresConIncidencias;
                    acumulador += promedioDiario;
                    cantPromedios++;
                }
            }
            NumberFormat porcentaje = NumberFormat.getPercentInstance();
            porcentaje.setMaximumFractionDigits(0);
            if (cantPromedios > 0) {
                indicadorCumplimiento = String.valueOf(porcentaje.format((double) acumulador / cantPromedios));
            } else {
                indicadorCumplimiento = String.valueOf(porcentaje.format(0));
            }
        } catch (ParseException ex) {
            Logger.getLogger(MantenedorAlarmasGps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obtenerConductores() {
        try {
            Date dia = sdf.parse("01/" + mesSeleccionado + "/" + anioSeleccionado + " 00:00:00");
            Calendar c = Calendar.getInstance();
            c.setTime(dia);
            int cantDiasMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            Date diaSiguiente = sdf.parse(cantDiasMes + "/" + mesSeleccionado + "/" + anioSeleccionado + " 00:00:00");
            conductores = logicaAlarmasGps.obtenerConductores(dia, diaSiguiente, servicioSeleccionado);
        } catch (ParseException ex) {
            Logger.getLogger(MantenedorAlarmasGps.class.getName()).log(Level.SEVERE, null, ex);
            conductores = new ArrayList<String>();
        }
    }

    public List<DynamicColumnDataTable> obtenerColumnas() {
        List<DynamicColumnDataTable> columnas = new ArrayList<DynamicColumnDataTable>();
        try {
            Date fechaGenerada = sdf.parse("01/" + mesSeleccionado + "/" + anioSeleccionado + " 00:00:00");
            Calendar c = Calendar.getInstance();
            c.setTime(fechaGenerada);
            int cantDiasMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = 1; i <= cantDiasMes; i++) {
                String dia = i < 10 ? "0" + i : String.valueOf(i);
                columnas.add(new DynamicColumnDataTable(dia + "/" + mesSeleccionado + "/" + anioSeleccionado, dia + "/" + mesSeleccionado + "/" + anioSeleccionado));
            }
        } catch (ParseException ex) {
//            Logger.getLogger(MantenedorAlarmasGps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return columnas;
    }

    public String getMesSeleccionado() {
        return mesSeleccionado;
    }

    public void setMesSeleccionado(String mesSeleccionado) {
        this.mesSeleccionado = mesSeleccionado;
    }

    public String getAnioSeleccionado() {
        return anioSeleccionado;
    }

    public void setAnioSeleccionado(String anioSeleccionado) {
        this.anioSeleccionado = anioSeleccionado;
    }

    public List<String> getConductores() {
        return conductores;
    }

    public void setConductores(List<String> conductores) {
        this.conductores = conductores;
    }

    public List<AlarmaGps> getAlarmas() {
        return alarmas;
    }

    public void setAlarmas(List<AlarmaGps> alarmas) {
        this.alarmas = alarmas;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public Servicio getServicioSeleccionado() {
        return servicioSeleccionado;
    }

    public void setServicioSeleccionado(Servicio servicioSeleccionado) {
        this.servicioSeleccionado = servicioSeleccionado;
    }

    public List<AlarmaGps> getDetalleAlarmas() {
        return detalleAlarmas;
    }

    public void setDetalleAlarmas(List<AlarmaGps> detalleAlarmas) {
        this.detalleAlarmas = detalleAlarmas;
    }

    public String getConductorSeleccionado() {
        return conductorSeleccionado;
    }

    public void setConductorSeleccionado(String conductorSeleccionado) {
        this.conductorSeleccionado = conductorSeleccionado;
    }

    public String getFechaSeleccionada() {
        return fechaSeleccionada;
    }

    public void setFechaSeleccionada(String fechaSeleccionada) {
        this.fechaSeleccionada = fechaSeleccionada;
    }

    public String getFechaSeleccionadaMod() {
        return fechaSeleccionadaMod;
    }

    public void setFechaSeleccionadaMod(String fechaSeleccionadaMod) {
        this.fechaSeleccionadaMod = fechaSeleccionadaMod;
    }

    public boolean isIndicarGestionActivado() {
        return indicarGestionActivado;
    }

    public void setIndicarGestionActivado(boolean indicarGestionActivado) {
        this.indicarGestionActivado = indicarGestionActivado;
    }

    public GestionAlarmaGps getGestionAlarma() {
        return gestionAlarma;
    }

    public void setGestionAlarma(GestionAlarmaGps gestionAlarma) {
        this.gestionAlarma = gestionAlarma;
    }

    public String getIndicadorCumplimiento() {
        return indicadorCumplimiento;
    }

    public void setIndicadorCumplimiento(String indicadorCumplimiento) {
        this.indicadorCumplimiento = indicadorCumplimiento;
    }

    public List<Descargable> getDescargables() {
        return descargables;
    }

    public void setDescargables(List<Descargable> descargables) {
        this.descargables = descargables;
    }
}
