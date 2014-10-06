package workcenter.presentacion.equipos;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.LogicaDocumentos;
import workcenter.negocio.equipos.LogicaEquipos;
import workcenter.util.components.Constantes;
import workcenter.util.components.SesionCliente;
import workcenter.util.pojo.Descargable;
import workcenter.util.components.FacesUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import static org.springframework.util.FileCopyUtils.BUFFER_SIZE;

/**
 * Created by claudio on 01-09-14.
 */
@Component
@Scope("flow")
public class MantenedorDocsEquipos implements Serializable {
    @Autowired
    private SesionCliente sesionCliente;

    @Autowired
    private LogicaEquipos logicaEquipos;

    @Autowired
    private Constantes constantes;

    @Autowired
    private LogicaDocumentos logicaDocumentos;

    private Equipo equipo;
    private List<DocumentoEquipo> documentos;
    private List<TipoDocumentoEquipo> tiposPendientes;
    private TipoDocumentoEquipo documentoSeleccionado;
    private UploadedFile archivo;
    private DocumentoEquipo documentoEquipo;

    private enum Operacion {
        EDITAR, ACTUALIZAR, CREAR
    }

    private Operacion operacion;

    public void inicio(Equipo e) {
        this.equipo = e;
        documentos = logicaEquipos.obtenerDocumentosActualizados(equipo);
    }

    public String irListar() {
        inicio(equipo);
        return "flowListar";
    }

    public String irActualizar(TipoDocumentoEquipo t) {
        operacion = Operacion.ACTUALIZAR;
        documentoSeleccionado = t;
        documentoEquipo = new DocumentoEquipo();
        documentoEquipo.setTipo(documentoSeleccionado);
        return "flowEditar";
    }

    public String irEditar(TipoDocumentoEquipo t) {
        operacion = Operacion.EDITAR;
        documentoSeleccionado = t;
        documentoEquipo = new DocumentoEquipo();
        documentoEquipo.setTipo(documentoSeleccionado);
        return "flowEditar";
    }

    public String irAgregar() {
        operacion = Operacion.CREAR;
        documentoEquipo = new DocumentoEquipo();
        tiposPendientes = logicaEquipos.obtenerTiposPendientes(equipo);
        if (tiposPendientes == null || tiposPendientes.isEmpty()) {
            FacesUtil.mostrarMensajeError("Operación fallida", "Este equipo ya tiene asociado todas la documentación requerida");
            return null;
        }
        return "flowEditar";
    }

    public String irEquipo() {
        return "flowFin";
    }

    public boolean esIngreso() {
        return operacion == Operacion.CREAR;
    }

    public boolean esEdicion() {
        return operacion == Operacion.EDITAR;
    }

    public boolean esActualizacion() {
        return operacion == Operacion.ACTUALIZAR;
    }

    public void subir() {
        String path = constantes.getPathArchivos() + "Documentos/equipos/" + equipo.getPatente();
        DocumentoEquipo existente = null;
        try {
            switch (operacion) {
                case ACTUALIZAR:
                    // respaldamos existente antes de subir
                    for (DocumentoEquipo de : documentos) {
                        if (de.getTipo().equals(documentoSeleccionado)) {
                            existente = de;
                            break;
                        }
                    }
                    if (new File(constantes.getPathArchivos() + existente.getArchivo()).exists()) {
                        Documento d = new Documento();
                        d.setFecha(new Date());
                        d.setNombreOriginal(existente.getArchivo().substring(existente.getArchivo().lastIndexOf('/') + 1));
                        logicaDocumentos.guardarDocumento(d);

                        HistorialDocumentoEquipo respaldo = new HistorialDocumentoEquipo();
                        respaldo.setNumero(existente.getNumero());
                        respaldo.setPatente(existente.getPatente());
                        respaldo.setVencimiento(existente.getVencimiento());
                        respaldo.setTipo(existente.getTipo().getId());
                        logicaEquipos.guardarHistorialDocumento(respaldo);
                    }
                    break;
                case EDITAR:
                    // sobreescribir el existente
                    for (DocumentoEquipo de : documentos) {
                        if (de.getTipo().equals(documentoSeleccionado)) {
                            existente = de;
                            break;
                        }
                    }
                    if (new File(constantes.getPathArchivos() + existente.getArchivo()).exists()) {
                        Files.delete(Paths.get(constantes.getPathArchivos() + existente.getArchivo()));
                    }
                    break;
            }
            path += "/" + documentoSeleccionado.getEtiqueta() + archivo.getFileName().substring(archivo.getFileName().lastIndexOf('.'));
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bulk;
            InputStream inputStream = archivo.getInputstream();
            while (true) {
                bulk = inputStream.read(buffer);
                if (bulk < 0) {
                    break;
                }
                fileOutputStream.write(buffer, 0, bulk);
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            inputStream.close();
            if (existente == null) {
                documentoEquipo.setArchivo("Documentos/equipos/" + equipo.getPatente() + "/" + documentoSeleccionado.getEtiqueta() + archivo.getFileName().substring(archivo.getFileName().lastIndexOf('.')));
                documentoEquipo.setPatente(equipo.getPatente());
                documentoEquipo.setTipo(documentoSeleccionado);
                logicaEquipos.guardarDocumento(documentoEquipo);
                documentoEquipo = new DocumentoEquipo();
                FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Se ha creado/actualizado el documento");
            } else {
                existente.setArchivo("Documentos/equipos/" + equipo.getPatente() + "/" + documentoSeleccionado.getEtiqueta() + archivo.getFileName().substring(archivo.getFileName().lastIndexOf('.')));
                existente.setVencimiento(documentoEquipo.getVencimiento());
                existente.setNumero(documentoEquipo.getNumero());
                existente.setPatente(equipo.getPatente());
                logicaEquipos.guardarDocumento(existente);
                documentoEquipo = existente;
                FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Se ha creado una nueva versión del documento");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            FacesUtil.mostrarMensajeError("Operación fallida", "Ha ocurrido un error interno");
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Operación fallida", "Ha ocurrido un error interno");
            e.printStackTrace();
        }
        tiposPendientes = logicaEquipos.obtenerTiposPendientes(equipo);
    }

    public StreamedContent generaDescargable(DocumentoEquipo de) {
        return new Descargable(new File(constantes.getPathArchivos() + de.getArchivo())).getStreamedContent();
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public List<DocumentoEquipo> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoEquipo> documentos) {
        this.documentos = documentos;
    }

    public List<TipoDocumentoEquipo> getTiposPendientes() {
        return tiposPendientes;
    }

    public void setTiposPendientes(List<TipoDocumentoEquipo> tiposPendientes) {
        this.tiposPendientes = tiposPendientes;
    }

    public TipoDocumentoEquipo getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(TipoDocumentoEquipo documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public DocumentoEquipo getDocumentoEquipo() {
        return documentoEquipo;
    }

    public void setDocumentoEquipo(DocumentoEquipo documentoEquipo) {
        this.documentoEquipo = documentoEquipo;
    }
}
