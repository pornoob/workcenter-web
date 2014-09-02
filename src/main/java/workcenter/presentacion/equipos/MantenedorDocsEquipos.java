package workcenter.presentacion.equipos;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.DocumentoEquipo;
import workcenter.entidades.Equipo;
import workcenter.negocio.LogicaEquipos;
import workcenter.util.components.Constantes;
import workcenter.util.components.SesionCliente;
import workcenter.util.pojo.Descargable;

import java.io.File;
import java.io.Serializable;
import java.util.List;

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

    private Equipo equipo;
    private List<DocumentoEquipo> documentos;

    public void inicio(Equipo e) {
        this.equipo = e;
        documentos = logicaEquipos.obtenerDocumentosActualizados(equipo);
    }

    public String irEquipo() {
        return "flowFin";
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
}
