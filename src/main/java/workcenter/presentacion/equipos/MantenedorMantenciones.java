package workcenter.presentacion.equipos;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.LogicaDocumentos;
import workcenter.negocio.equipos.LogicaEquipos;
import workcenter.negocio.equipos.LogicaMantenciones;
import workcenter.negocio.equipos.LogicaProveedorPetroleo;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.presentacion.includes.FicheroUploader;
import workcenter.util.WorkcenterFileListener;
import workcenter.util.components.Constantes;
import workcenter.util.components.SesionCliente;
import workcenter.util.pojo.Descargable;
import workcenter.util.components.FacesUtil;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * Created by claudio on 08-09-14.
 */
@Component
@Scope("flow")
public class MantenedorMantenciones implements Serializable, WorkcenterFileListener {
    @Autowired
    private LogicaEquipos logicaEquipos;

    @Autowired
    private LogicaMantenciones logicaMantenciones;

    @Autowired
    private LogicaPersonal logicaPersonal;

    @Autowired
    private LogicaDocumentos logicaDocumentos;

    @Autowired
    private FicheroUploader ficheroUploader;

    @Autowired
    private SesionCliente sesionCliente;

    @Autowired
    private Constantes constantes;

    @Autowired
    private LogicaProveedorPetroleo logicaProveedorPetroleo;

    private List<Equipo> tractos;
    private Equipo equipo;
    private List<Equipo> bateas;
    private List<MmeMantencionTracto> mantencionesTractos;
    private List<MmeMantencionSemiremolque> mantencionesSemiremolque;
    private List<MmeTipoMantencion> tiposMantencion;
    private List<Personal> mecanicos;
    private MmeMantencionTracto mantencionTracto;
    private MmeMantencionSemiremolque mantencionSemiremolque;
    private Map<String, List<Documento>> comprobantesMantencion;

    // caché
    private MmeMantencionTracto panne;
    private int ciclos;

    public void inicio() {
        tractos = logicaEquipos.obtenerTractos();
        bateas = logicaEquipos.obtenerBateas();
        mantencionesTractos = logicaMantenciones.obtenerUltimasMantenciones();
        mantencionesSemiremolque = logicaMantenciones.obtenerUltimasMantencionesSemiremolques();
        tiposMantencion = logicaMantenciones.obtenerTiposMantencion();
        ciclos = (tiposMantencion.get(1).getCotaKilometraje().intValue() / tiposMantencion.get(0).getCotaKilometraje()) - 1;
    }

    public String irListar() {
        inicio();
        return "flowListar";
    }

    public String irEditar() {
        mecanicos = logicaPersonal.obtenerMecanicos();
        mantencionTracto = new MmeMantencionTracto();
        mantencionSemiremolque = new MmeMantencionSemiremolque();
        return "flowEditar";
    }

    public String irHistorial(Equipo e) {
        equipo = e;
        mantencionesTractos = logicaMantenciones.obtenerMantenciones(e);
        return "flowHistorial";
    }

    public String irHistorialSemiremolque(Equipo e) {
        equipo = e;
        mantencionesSemiremolque = logicaMantenciones.obtenerMantencionesSemiremolques(e);
        return "flowHistorial";
    }

    public boolean dibujarSemaforoRojo(Integer valor) {
        return valor.intValue() <= 0;
    }

    public boolean dibujarSemaforoAmarillo(Integer valor) {
        return valor.intValue() > 0 && valor.intValue() <= constantes.getAlarmaProximaMantencion();
    }

    public boolean dibujarSemaforoVerde(Integer valor) {
        return valor.intValue() > constantes.getAlarmaProximaMantencion();
    }

    public boolean filtroEstado(Object valor, Object filtro, Locale idioma) {
        if (filtro == null) return true;
        if (filtro.equals("proximas")) {
            return dibujarSemaforoAmarillo(obtenerKmsFaltante((MmeMantencionTracto) valor));
        } else if (filtro.equals("lejanas")) {
            return dibujarSemaforoVerde(obtenerKmsFaltante((MmeMantencionTracto) valor));
        } else if (filtro.equals("atrasadas")) {
            return dibujarSemaforoRojo(obtenerKmsFaltante((MmeMantencionTracto) valor));
        }
        return false;
    }

    public boolean dibujarSemaforoRojoSemiremolque(MmeMantencionSemiremolque m) {
        Calendar fechaMantencion = Calendar.getInstance();
        fechaMantencion.setTime(m.getFecha());
        fechaMantencion.add(Calendar.DAY_OF_MONTH, m.getCriterioSiguiente().intValue());

        return new Date().after(fechaMantencion.getTime());
    }

    public boolean dibujarSemaforoAmarilloSemiremolque(MmeMantencionSemiremolque m) {
        Calendar fechaMantencion = Calendar.getInstance();
        fechaMantencion.setTime(m.getFecha());
        fechaMantencion.add(Calendar.DAY_OF_MONTH, m.getCriterioSiguiente().intValue());

        if (new Date().before(fechaMantencion.getTime())) {
            fechaMantencion.add(Calendar.DAY_OF_MONTH, -5);
            return new Date().after(fechaMantencion.getTime());
        }
        return false;
    }

    public boolean dibujarSemaforoVerdeSemiremolque(MmeMantencionSemiremolque m) {
        Calendar fechaMantencion = Calendar.getInstance();
        fechaMantencion.setTime(m.getFecha());
        fechaMantencion.add(Calendar.DAY_OF_MONTH, m.getCriterioSiguiente().intValue());

        if (new Date().before(fechaMantencion.getTime())) {
            fechaMantencion.add(Calendar.DAY_OF_MONTH, -5);
            return new Date().before(fechaMantencion.getTime());
        }
        return false;
    }

    public Date obtenerFechaUltimaPanne(Equipo e) {
        panne = logicaMantenciones.obtenerUltimaPanne(e);
        return panne != null ? panne.getFecha() : null;
    }

    public Integer obtenerKmUltimaPanne(Equipo e) {
        return panne != null ? panne.getKilometraje() : null;
    }

    public Integer obtenerKmSegunGuias(Equipo e) {
        Vuelta ultimaVuelta = logicaEquipos.obtenerUltimaVuelta(e);
        try {
            return obtenerKmSegunVueltaGuia(e);
        } catch (Exception ex) {
            System.err.println("EX: " + ex.getMessage());
            return null;
        }
    }

    public Integer obtenerKmSegunProveedor(Equipo e) {
        return logicaProveedorPetroleo.obtenerUltimoOdometro(e);
    }

    private Integer obtenerKmSegunVueltaGuia(Equipo e) {
        Vuelta ultimaVuelta = logicaEquipos.obtenerUltimaVuelta(e);
        return ultimaVuelta.getKmFinal() <= 0 ? (ultimaVuelta.getKmInicial() <= 0 ? 0 : ultimaVuelta.getKmInicial())
                : ultimaVuelta.getKmFinal();
    }

    public Integer obtenerKmSiguienteMantencion(MmeMantencionTracto mt) {
        return mt.getKilometraje().intValue() + (mt.getCiclo() == 0 || mt.getCiclo() < ciclos ? tiposMantencion.get(0).getCotaKilometraje() : tiposMantencion.get(1).getCotaKilometraje());
    }

    public Date obtenerFechaSiguienteMantencion(MmeMantencionTracto mt) {
        Calendar c = Calendar.getInstance();
        int dias = obtenerKmsFaltante(mt) / 500;
        c.add(Calendar.DAY_OF_MONTH, dias);
        return c.getTime();
    }

    public Date obtenerFechaSiguienteMantencionSemiremolque(MmeMantencionSemiremolque ms) {
        Calendar c = Calendar.getInstance();
        c.setTime(ms.getFecha());
        c.add(Calendar.DAY_OF_MONTH, 30);
        return c.getTime();
    }

    public Integer obtenerKmsFaltante(MmeMantencionTracto mt) {
        Equipo e = mt.getTracto();
        Integer kmCopec = obtenerKmSegunProveedor(e);
        Integer kmGuia = obtenerKmSegunVueltaGuia(e);
        int kms = 0;
        if (kmCopec != null && kmGuia != null) kms = kmCopec.intValue() > kmGuia.intValue() ? kmCopec.intValue() : kmGuia.intValue();
        else if (kmCopec == null && kmGuia != null) kms = kmGuia.intValue();
        else if (kmCopec != null && kmGuia == null) kms = kmCopec.intValue();
        return obtenerKmSiguienteMantencion(mt).intValue() - kms;
    }

    public void guardar() {
        if (comprobantesMantencion == null || comprobantesMantencion.get(sesionCliente.getUsuario().getRut() + "|mantencion") == null) {
            FacesUtil.mostrarMensajeError("Operación fallida", "Debes adjuntar al menos un respaldo de la mantención");
            return;
        }
        if (mantencionTracto.getTracto() == null && mantencionSemiremolque.getSemiRemolque() == null) {
            FacesUtil.mostrarMensajeError("Operación fallida", "La mantención se realiza sobre un equipo al menos");
            return;
        }
        if (mantencionTracto.getTracto() != null) {
            // es + 2 debido a que a los ciclos de por si se le quita uno
            int cicloActual = (logicaMantenciones.obtenerUltimoCiclo(mantencionTracto.getTracto()) + 2) % ciclos;
            mantencionTracto.setCiclo(cicloActual);
            logicaMantenciones.guardar(mantencionTracto);
        }

        if (mantencionSemiremolque.getSemiRemolque() != null) {
            mantencionSemiremolque.setMecanicoResponsable(mantencionTracto.getMecanicoResponsable());
            mantencionSemiremolque.setFecha(mantencionTracto.getFecha());
            mantencionSemiremolque.setCriterioSiguiente(30);
            logicaMantenciones.guardar(mantencionSemiremolque);
        }
        for (Documento d : comprobantesMantencion.get(sesionCliente.getUsuario().getRut() + "|mantencion")) {
            if (mantencionTracto.getTracto() != null) logicaDocumentos.asociarDocumento(d, mantencionTracto);
            if (mantencionSemiremolque.getSemiRemolque() != null) logicaDocumentos.asociarDocumento(d, mantencionSemiremolque);
        }

        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Se ha guardado la mantención");
        comprobantesMantencion = null;
    }

    public StreamedContent obtenerDescargable(MmeMantencionTracto m) {
        List<Documento> docs = logicaDocumentos.obtenerDocumentosAsociados(m);
        Descargable d = new Descargable(new File(constantes.getPathArchivos() + docs.get(docs.size() - 1).getId()));
        return d.getStreamedContent();
    }

    public StreamedContent obtenerDescargablePanne(Equipo e) {
        panne = logicaMantenciones.obtenerUltimaPanne(e);
        return obtenerDescargable(panne);
    }

    public StreamedContent obtenerDescargableSemiremolque(MmeMantencionSemiremolque m) {
        List<Documento> docs = logicaDocumentos.obtenerDocumentosAsociados(m);
        Descargable d = new Descargable(new File(constantes.getPathArchivos() + docs.get(docs.size() - 1).getId()));
        return d.getStreamedContent();
    }

    public List<Equipo> getTractos() {
        return tractos;
    }

    public void setTractos(List<Equipo> tractos) {
        this.tractos = tractos;
    }

    public List<Equipo> getBateas() {
        return bateas;
    }

    public void setBateas(List<Equipo> bateas) {
        this.bateas = bateas;
    }

    public List<MmeMantencionTracto> getMantencionesTractos() {
        return mantencionesTractos;
    }

    public void setMantencionesTractos(List<MmeMantencionTracto> mantencionesTractos) {
        this.mantencionesTractos = mantencionesTractos;
    }

    public List<MmeMantencionSemiremolque> getMantencionesSemiremolque() {
        return mantencionesSemiremolque;
    }

    public void setMantencionesSemiremolque(List<MmeMantencionSemiremolque> mantencionesSemiremolque) {
        this.mantencionesSemiremolque = mantencionesSemiremolque;
    }

    public MmeMantencionTracto getMantencionTracto() {
        return mantencionTracto;
    }

    public void setMantencionTracto(MmeMantencionTracto mantencionTracto) {
        this.mantencionTracto = mantencionTracto;
    }

    public MmeMantencionSemiremolque getMantencionSemiremolque() {
        return mantencionSemiremolque;
    }

    public void setMantencionSemiremolque(MmeMantencionSemiremolque mantencionSemiremolque) {
        this.mantencionSemiremolque = mantencionSemiremolque;
    }

    public List<MmeTipoMantencion> getTiposMantencion() {
        return tiposMantencion;
    }

    public void setTiposMantencion(List<MmeTipoMantencion> tiposMantencion) {
        this.tiposMantencion = tiposMantencion;
    }

    public List<Personal> getMecanicos() {
        return mecanicos;
    }

    public void setMecanicos(List<Personal> mecanicos) {
        this.mecanicos = mecanicos;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    @Override
    public void subir(FileUploadEvent fue) {
        Documento d = ficheroUploader.subir(fue);
        enlazarLocal(d);
    }

    @Override
    public void enlazar(Descargable descargable) {
        Documento d = logicaDocumentos.obtenerPorCodigo(descargable.getArchivo().getName());
        enlazarLocal(d);
    }

    public void enlazarLocal(Documento d) {
        if (comprobantesMantencion == null) comprobantesMantencion = new HashMap<String, List<Documento>>();
        if (comprobantesMantencion.get(sesionCliente.getUsuario().getRut() + "|mantencion") == null) {
            List<Documento> docs = new ArrayList<Documento>();
            docs.add(d);
            comprobantesMantencion.put(sesionCliente.getUsuario().getRut() + "|mantencion", docs);
        } else {
            List<Documento> docs = comprobantesMantencion.get(sesionCliente.getUsuario().getRut() + "|mantencion");
            docs.add(d);
            comprobantesMantencion.put(sesionCliente.getUsuario().getRut() + "|mantencion", docs);
        }
    }
}
