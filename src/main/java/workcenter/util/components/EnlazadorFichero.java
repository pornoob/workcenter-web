package workcenter.util.components;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Documento;
import workcenter.negocio.LogicaDocumentos;
import workcenter.util.pojo.Descargable;
import workcenter.util.pojo.FacesUtil;

/**
 *
 * @author colivares
 */
@Component
@Scope("view")
public class EnlazadorFichero implements Serializable {

    private boolean buscarExistente;
    private boolean cargarNuevo;
    private boolean puedeEnlazar;
    private boolean mostrarResultados;
    private String filtroArchivo;
    private List<Descargable> descargables;

    @Autowired
    Constantes constantes;

    @Autowired
    LogicaDocumentos logicaDocumentos;

    public void buscarArchivosCodigo() {
        Documento documento = logicaDocumentos.obtenerPorCodigo(filtroArchivo);
        descargables = new ArrayList<Descargable>();
        if (documento != null) {
            Descargable d = new Descargable(new File(System.getProperty("catalina.base") + "/static/workcenter/" + documento.getId()));
            d.setNombre(documento.getNombreOriginal());
            descargables.add(d);
        }
        if (descargables.isEmpty())
            FacesUtil.mostrarMensajeInformativo("Sin resultados", "No se han encontrado ficheros con el código brindado ["+filtroArchivo+"]");
        mostrarResultados = true;
    }

    public void reset() {
        buscarExistente = false;
        cargarNuevo = false;
        mostrarResultados = false;
        descargables = new ArrayList<Descargable>();
        puedeEnlazar = true;
    }

    public void seleccionExistente() {
        buscarExistente = true;
        mostrarResultados = false;
        cargarNuevo = false;
    }

    public void seleccionNuevo() {
        buscarExistente = false;
        cargarNuevo = true;
        mostrarResultados = false;
    }

    public boolean getBuscarExistente() {
        return isBuscarExistente();
    }

    public boolean isBuscarExistente() {
        return buscarExistente;
    }

    public void setBuscarExistente(boolean buscarExistente) {
        this.buscarExistente = buscarExistente;
    }

    public boolean getCargarNuevo() {
        return isCargarNuevo();
    }

    public boolean isCargarNuevo() {
        return cargarNuevo;
    }

    public void setCargarNuevo(boolean cargarNuevo) {
        this.cargarNuevo = cargarNuevo;
    }

    public boolean isPuedeEnlazar() {
        return puedeEnlazar;
    }

    public void setPuedeEnlazar(boolean puedeEnlazar) {
        this.puedeEnlazar = puedeEnlazar;
    }

    public boolean isMostrarResultados() {
        return mostrarResultados;
    }

    public void setMostrarResultados(boolean mostrarResultados) {
        this.mostrarResultados = mostrarResultados;
    }

    public String getFiltroArchivo() {
        return filtroArchivo;
    }

    public void setFiltroArchivo(String filtroArchivo) {
        this.filtroArchivo = filtroArchivo;
    }

    public List<Descargable> getDescargables() {
        return descargables;
    }

    public void setDescargables(List<Descargable> descargables) {
        this.descargables = descargables;
    }
}
