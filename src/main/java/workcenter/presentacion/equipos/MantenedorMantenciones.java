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
import workcenter.negocio.facturas.LogicaStock;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.negocio.taller.LogicaOt;
import workcenter.presentacion.includes.FicheroUploader;
import workcenter.util.WorkcenterFileListener;
import workcenter.util.components.Constantes;
import workcenter.util.components.FacesUtil;
import workcenter.util.components.SesionCliente;
import workcenter.util.pojo.Descargable;

import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.AjaxBehaviorEvent;
import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Claudio Olivares on 08-09-14.
 */
@Component
@Scope("flow")
public class MantenedorMantenciones implements Serializable, WorkcenterFileListener {

    private static final long serialVersionUID = 3894956458108018846L;

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

    @Autowired
    private LogicaOt logicaOt;

    @Autowired
    private LogicaStock logicaStock;

    private List<Equipo> tractos;
    private List<Equipo> bateas;

    private List<MmeTipoMantencion> tiposMantencion;
    private List<MmeTareaMaquina> tiposMantencionMaquina;
    private List<MmeTareaMaquina> tiposMantencionMaquinaSeleccionadas;
    private List<Personal> mecanicos;
    private MmeMantencionTracto mantencionTracto;
    private MmeMantencionSemirremolque mantencionSemiremolque;
    private MmeMantencionMaquina mantencionMaquina;
    private MmeMantencionMaquina ultimaMantencionMaquina;
    private Map<String, List<Documento>> comprobantesMantencion;
    private OrdenTrabajo ot;
    private AsistenteOt asistenteOt;
    private RepuestoOt repuestoOt;
    private List<Personal> personal;
    private List<FactProducto> productos;

    private int ciclos;

    // caché
    private MmeMantencionTracto panne;

    public void inicio() {
        Date fecha = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        FacesUtil.setearVariableFlow("mes", calendar.get(Calendar.MONTH) + 1);
        FacesUtil.setearVariableFlow("anio", calendar.get(Calendar.YEAR));

        tiposMantencion = logicaMantenciones.obtenerTiposMantencion();
        ciclos = (tiposMantencion.get(1).getCotaKilometraje() / tiposMantencion.get(0).getCotaKilometraje()) - 1;
        ot = new OrdenTrabajo();
    }

    // se debe borrar
    public String irEditarSemiRemolque() {
        bateas = logicaEquipos.obtenerBateas();
        mecanicos = logicaPersonal.obtenerMecanicos();
        mantencionSemiremolque = new MmeMantencionSemirremolque();
        return "flowEditarSemiRemolque";
    }

    public String irEditarTracto() {
        tractos = logicaEquipos.obtenerTractos();
        mecanicos = logicaPersonal.obtenerMecanicos();
        bateas = logicaEquipos.obtenerBateas();
        mantencionTracto = new MmeMantencionTracto();
        mantencionSemiremolque = new MmeMantencionSemirremolque();
        return "flowEditarTracto";
    }

    public String irEditarMaquinaria(MmeMantencionMaquina mantencionMaquina) {
        mecanicos = logicaPersonal.obtenerMecanicos();
        this.mantencionMaquina = mantencionMaquina;
        tiposMantencionMaquina = logicaMantenciones.obtenerTiposMantencionMaquina();

        obtenerMantencionMaquinaPrevia(mantencionMaquina);

        tiposMantencionMaquinaSeleccionadas = new ArrayList<>();
        for (MmeCheckMaquina check : mantencionMaquina.getCheckeoRealizado()) {
            if (check.getHrasAnotadas().equals(mantencionMaquina.getHrasAnotadas())) {
                tiposMantencionMaquinaSeleccionadas.add(check.getTareaMaquina());
            }
        }
        return "flowEditarMaquinaria";
    }
    // fin se debe borrar

    public void initAsistente() {
        asistenteOt = new AsistenteOt();
        if (ot.getAsistentes() == null) {
            ot.setAsistentes(new HashSet<AsistenteOt>());
        }
        asistenteOt.setOt(this.ot);
        ot.getAsistentes().add(asistenteOt);
    }

    public void removeAsistente(AsistenteOt item) {
        Iterator<AsistenteOt> iterator = ot.getAsistentes().iterator();
        while (iterator.hasNext()) {
            AsistenteOt current = iterator.next();
            if (current.getRowKey().equals(item.getRowKey())) {
                iterator.remove();
                break;
            }
        }
    }

    public void initRepuesto() {
        repuestoOt = new RepuestoOt();
        if (ot.getRepuestos() == null) {
            ot.setRepuestos(new HashSet<RepuestoOt>());
        }
        repuestoOt.setOt(ot);
        ot.getRepuestos().add(repuestoOt);
    }

    public void removeRepuesto(RepuestoOt item) {
        Iterator<RepuestoOt> iterator = ot.getRepuestos().iterator();
        while (iterator.hasNext()) {
            RepuestoOt current = iterator.next();
            if (current.getRowKey().equals(item.getRowKey())) {
                iterator.remove();
                break;
            }
        }
    }

    public void findOtById() {
        ot = logicaOt.findByIdAndStatus(ot.getId(), constantes.getESTADO_OT_ASIGNADA());
        if (ot == null) {
            FacesUtil.mostrarMensajeError("Error", "No se ha encontrado una OT vigente con el ID especificado");
            ot = new OrdenTrabajo();
            return;
        }
        ot = logicaOt.findWithMantenimientos(ot.getId());
        personal = logicaPersonal.obtenerMecanicos();
        productos = logicaStock.findAll();
        comprobantesMantencion = new HashMap<>();
    }

    public void save() {
        TrazabilidadOt state = new TrazabilidadOt();
        state.setAutor(sesionCliente.getUsuario().getRut());
        state.setOtId(this.ot);
        state.setFecha(new Date());
        state.setEstadoId(constantes.getESTADO_OT_FINALIZADA());
        this.ot.getTrazabilidad().add(state);
        if (comprobantesMantencion.get(sesionCliente.getUsuario().getRut() + "|mantencion")==null) {
            FacesUtil.mostrarMensajeError("Operación erronea", "Debes adjuntar al menos un archivo");
            return;
        }
        logicaOt.updateOt(this.ot);
        for (Documento d : comprobantesMantencion.get(sesionCliente.getUsuario().getRut() + "|mantencion")) {
            logicaDocumentos.asociarDocumento(d, ot);
        }
        FacesUtil.mostrarMensajeInformativo("Operación exitosa", "El mantenimiento se ha guardado correctamente");
        ot = new OrdenTrabajo();
    }

    public String irListar() {
        inicio();
        return "flowListar";
    }

    public String irEditarMaquinaria() {
        mecanicos = logicaPersonal.obtenerMecanicos();
        mantencionMaquina = new MmeMantencionMaquina();
        tiposMantencionMaquina = logicaMantenciones.obtenerTiposMantencionMaquina();
        tiposMantencionMaquinaSeleccionadas = new ArrayList<>();
        return "flowEditarMaquinaria";
    }

    public void obtenerUltimaMantencionMaquina(AjaxBehaviorEvent event) {
        Equipo maquina = (Equipo) ((HtmlSelectOneMenu) event.getSource()).getValue();
        ultimaMantencionMaquina = logicaMantenciones.obtenerUltimaMantencionMaquina(maquina);
        if (ultimaMantencionMaquina == null) {
            ultimaMantencionMaquina = new MmeMantencionMaquina();
            ultimaMantencionMaquina.setCheckeoRealizado(new TreeSet<MmeCheckMaquina>());
            ultimaMantencionMaquina.setHrasAnotadas(0);
        }
    }

    public void obtenerMantencionMaquinaPrevia(MmeMantencionMaquina mantencion) {
        ultimaMantencionMaquina = logicaMantenciones.obtenerMantencionMaquinaPrevia(mantencion);
    }

    public boolean dibujarSemaforoRojoSemiremolque(MmeMantencionSemirremolque m) {
        Calendar fechaMantencion = Calendar.getInstance();
        fechaMantencion.setTime(m.getFecha());
        fechaMantencion.add(Calendar.DAY_OF_MONTH, m.getCriterioSiguiente());

        return new Date().after(fechaMantencion.getTime());
    }

    public boolean dibujarSemaforoAmarilloSemiremolque(MmeMantencionSemirremolque m) {
        Calendar fechaMantencion = Calendar.getInstance();
        fechaMantencion.setTime(m.getFecha());
        fechaMantencion.add(Calendar.DAY_OF_MONTH, m.getCriterioSiguiente());

        if (new Date().before(fechaMantencion.getTime())) {
            fechaMantencion.add(Calendar.DAY_OF_MONTH, -5);
            return new Date().after(fechaMantencion.getTime());
        }
        return false;
    }

    public boolean dibujarSemaforoVerdeSemiremolque(MmeMantencionSemirremolque m) {
        Calendar fechaMantencion = Calendar.getInstance();
        fechaMantencion.setTime(m.getFecha());
        fechaMantencion.add(Calendar.DAY_OF_MONTH, m.getCriterioSiguiente());

        if (new Date().before(fechaMantencion.getTime())) {
            fechaMantencion.add(Calendar.DAY_OF_MONTH, -5);
            return new Date().before(fechaMantencion.getTime());
        }
        return false;
    }

    public Integer obtenerHoraComponente(MmeTareaMaquina mmeTareaMaquina) {
        if (ultimaMantencionMaquina == null) {
            return null;
        }
        for (MmeCheckMaquina mmeCheckMaquina : ultimaMantencionMaquina.getCheckeoRealizado()) {
            if (mmeTareaMaquina.equals(mmeCheckMaquina.getTareaMaquina())) {
                return mmeCheckMaquina.getHrasAnotadas();
            }
        }
        return null;
    }

    public Integer obtenerKmSiguienteMantencion(MmeMantencionTracto mt) {
        return mt.getKilometraje() + (mt.getCiclo() == 0 || mt.getCiclo() < ciclos ? tiposMantencion.get(0).getCotaKilometraje() : tiposMantencion.get(1).getCotaKilometraje());
    }



    public Date obtenerFechaSiguienteMantencionSemiremolque(MmeMantencionSemirremolque ms) {
        Calendar c = Calendar.getInstance();
        c.setTime(ms.getFecha());
        c.add(Calendar.DAY_OF_MONTH, 30);
        return c.getTime();
    }

    public void guardarMantMaquina() {
        if (comprobantesMantencion == null || comprobantesMantencion.get(sesionCliente.getUsuario().getRut() + "|mantencion") == null) {
            FacesUtil.mostrarMensajeError("Operación fallida", "Debes adjuntar al menos un respaldo de la mantención");
            return;
        }
        if (mantencionMaquina.getMaquina() == null) {
            FacesUtil.mostrarMensajeError("Operación fallida", "Debes indicar a que máquina se le realizó la mantención");
            return;
        }

        mantencionMaquina.setCheckeoRealizado(new TreeSet<MmeCheckMaquina>());
        for (MmeTareaMaquina tipoMantencion : tiposMantencionMaquinaSeleccionadas) {
            MmeCheckMaquina checkMaquina = new MmeCheckMaquina();
            checkMaquina.setHrasAnotadas(mantencionMaquina.getHrasAnotadas());
            checkMaquina.setMantencionMaquina(mantencionMaquina);
            checkMaquina.setTareaMaquina(tipoMantencion);
            mantencionMaquina.getCheckeoRealizado().add(checkMaquina);
        }

        for (MmeTareaMaquina tipoMantencion : tiposMantencionMaquina) {
            if (!tiposMantencionMaquinaSeleccionadas.contains(tipoMantencion)) {
                MmeCheckMaquina checkMaquina = new MmeCheckMaquina();
                checkMaquina.setHrasAnotadas(ultimaMantencionMaquina.getHrasAnotadas());
                checkMaquina.setMantencionMaquina(mantencionMaquina);
                checkMaquina.setTareaMaquina(tipoMantencion);
                mantencionMaquina.getCheckeoRealizado().add(checkMaquina);
            }
        }

        mantencionMaquina = logicaMantenciones.guardar(mantencionMaquina);
        for (Documento d : comprobantesMantencion.get(sesionCliente.getUsuario().getRut() + "|mantencion")) {
            if (mantencionMaquina.getMaquina() != null) {
                logicaDocumentos.asociarDocumento(d, mantencionMaquina);
            }
        }
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Se ha guardado la mantención");
        mantencionMaquina = new MmeMantencionMaquina();
    }

    public void guardar() {
        if (comprobantesMantencion == null || comprobantesMantencion.get(sesionCliente.getUsuario().getRut() + "|mantencion") == null) {
            FacesUtil.mostrarMensajeError("Operación fallida", "Debes adjuntar al menos un respaldo de la mantención");
            return;
        }
        if (mantencionTracto.getTracto() == null && mantencionSemiremolque.getSemiRremolque() == null) {
            FacesUtil.mostrarMensajeError("Operación fallida", "La mantención se realiza sobre un equipo al menos");
            return;
        }
        if (mantencionTracto.getTracto() != null) {
            // es + 2 debido a que a los ciclos de por si se le quita uno
            int cicloActual = (logicaMantenciones.obtenerUltimoCiclo(mantencionTracto.getTracto()) + 2) % ciclos;
            mantencionTracto.setCiclo(cicloActual);
            logicaMantenciones.guardar(mantencionTracto);
        }

        if (mantencionSemiremolque.getSemiRremolque() != null) {
            mantencionSemiremolque.setMecanicoResponsable(mantencionTracto.getMecanicoResponsable());
            mantencionSemiremolque.setFecha(mantencionTracto.getFecha());
            mantencionSemiremolque.setCriterioSiguiente(30);
            logicaMantenciones.guardar(mantencionSemiremolque);
        }
        for (Documento d : comprobantesMantencion.get(sesionCliente.getUsuario().getRut() + "|mantencion")) {
            if (mantencionTracto.getTracto() != null) {
                logicaDocumentos.asociarDocumento(d, mantencionTracto);
            }
            if (mantencionSemiremolque.getSemiRremolque() != null) {
                logicaDocumentos.asociarDocumento(d, mantencionSemiremolque);
            }
        }

        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Se ha guardado la mantención");
        comprobantesMantencion = null;
    }

    public StreamedContent obtenerDescargableSemiremolque(MmeMantencionSemirremolque m) {
        List<Documento> docs = logicaDocumentos.obtenerDocumentosAsociados(m);
        Documento doc = docs.get(docs.size() - 1);
        Descargable d = new Descargable(new File(constantes.getPathArchivos() + doc.getId()));
        d.setNombre(doc.getNombreOriginal());
        return d.getStreamedContent();
    }

    public StreamedContent obtenerDescargableTracto(MmeMantencionTracto m) {
        List<Documento> docs = logicaDocumentos.obtenerDocumentosAsociados(m);
        Documento doc = docs.get(docs.size() - 1);
        Descargable d = new Descargable(new File(constantes.getPathArchivos() + doc.getId()));
        d.setNombre(doc.getNombreOriginal());
        return d.getStreamedContent();
    }

    public StreamedContent obtenerDescargableMaquina(MmeMantencionMaquina m) {
        List<Documento> docs = logicaDocumentos.obtenerDocumentosAsociados(m);
        Documento doc = docs.get(docs.size() - 1);
        Descargable d = new Descargable(new File(constantes.getPathArchivos() + doc.getId()));
        d.setNombre(doc.getNombreOriginal());
        return d.getStreamedContent();
    }

    public StreamedContent obtenerDescargablePanne(Equipo e) {
        panne = logicaMantenciones.obtenerUltimaPanne(e);
        return obtenerDescargableTracto(panne);
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

    public MmeMantencionTracto getMantencionTracto() {
        return mantencionTracto;
    }

    public void setMantencionTracto(MmeMantencionTracto mantencionTracto) {
        this.mantencionTracto = mantencionTracto;
    }

    public MmeMantencionSemirremolque getMantencionSemiremolque() {
        return mantencionSemiremolque;
    }

    public void setMantencionSemiremolque(MmeMantencionSemirremolque mantencionSemiremolque) {
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

    public MmeMantencionMaquina getMantencionMaquina() {
        return mantencionMaquina;
    }

    public void setMantencionMaquina(MmeMantencionMaquina mantencionMaquina) {
        this.mantencionMaquina = mantencionMaquina;
    }

    public List<MmeTareaMaquina> getTiposMantencionMaquina() {
        return tiposMantencionMaquina;
    }

    public void setTiposMantencionMaquina(List<MmeTareaMaquina> tiposMantencionMaquina) {
        this.tiposMantencionMaquina = tiposMantencionMaquina;
    }

    public List<MmeTareaMaquina> getTiposMantencionMaquinaSeleccionadas() {
        return tiposMantencionMaquinaSeleccionadas;
    }

    public void setTiposMantencionMaquinaSeleccionadas(List<MmeTareaMaquina> tiposMantencionMaquinaSeleccionadas) {
        this.tiposMantencionMaquinaSeleccionadas = tiposMantencionMaquinaSeleccionadas;
    }

    public MmeMantencionMaquina getUltimaMantencionMaquina() {
        return ultimaMantencionMaquina;
    }

    public void setUltimaMantencionMaquina(MmeMantencionMaquina ultimaMantencionMaquina) {
        this.ultimaMantencionMaquina = ultimaMantencionMaquina;
    }

    public OrdenTrabajo getOt() {
        return ot;
    }

    public void setOt(OrdenTrabajo otId) {
        this.ot = otId;
    }

    public AsistenteOt getAsistenteOt() {
        return asistenteOt;
    }

    public void setAsistenteOt(AsistenteOt asistenteOt) {
        this.asistenteOt = asistenteOt;
    }

    public RepuestoOt getRepuestoOt() {
        return repuestoOt;
    }

    public void setRepuestoOt(RepuestoOt repuestoOt) {
        this.repuestoOt = repuestoOt;
    }

    public List<FactProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<FactProducto> productos) {
        this.productos = productos;
    }

    public List<Personal> getPersonal() {
        return personal;
    }

    public void setPersonal(List<Personal> personal) {
        this.personal = personal;
    }

    public int getCiclos() {
        return ciclos;
    }

    public void setCiclos(int ciclos) {
        this.ciclos = ciclos;
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
        if (comprobantesMantencion == null) {
            comprobantesMantencion = new HashMap<>();
        }
        if (comprobantesMantencion.get(sesionCliente.getUsuario().getRut() + "|mantencion") == null) {
            List<Documento> docs = new ArrayList<>();
            docs.add(d);
            comprobantesMantencion.put(sesionCliente.getUsuario().getRut() + "|mantencion", docs);
        } else {
            List<Documento> docs = comprobantesMantencion.get(sesionCliente.getUsuario().getRut() + "|mantencion");
            docs.add(d);
            comprobantesMantencion.put(sesionCliente.getUsuario().getRut() + "|mantencion", docs);
        }
    }
}
