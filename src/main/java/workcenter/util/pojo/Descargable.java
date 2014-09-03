package workcenter.util.pojo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author colivares
 */
public class Descargable implements Serializable {

    private File archivo;
    private String nombre;

    public Descargable(File archivo) {
        this.archivo = archivo;
    }

    public String getNombre() {
        if (nombre == null) {
            return archivo.getName();
        } else {
            return archivo.getName() + "_" + nombre;
        }
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public StreamedContent getStreamedContent() {
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(archivo));
            DefaultStreamedContent dsc = new DefaultStreamedContent(is);
            System.err.println("INTENTO: "+dsc);
            dsc.setName(getNombre());
            return dsc;
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe.getMessage());
            return null;
        }
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }
}
