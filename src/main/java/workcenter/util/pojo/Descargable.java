package workcenter.util.pojo;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author colivares
 */
public class Descargable implements Serializable {

    private File archivo;
    private String contentType;
    private String nombre;

    public Descargable(File archivo) {
        this.archivo = archivo;
        obtenerContentType();
        System.err.println("Creando descargable: "+archivo.getName()+" del tipo: "+contentType);
    }

    private void obtenerContentType() {
        try {
            this.contentType = Files.probeContentType(Paths.get(archivo.getAbsolutePath()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            this.contentType = "";
        }
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
            DefaultStreamedContent dsc = new DefaultStreamedContent(is, contentType);
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
