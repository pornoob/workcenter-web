package workcenter.util.pojo;

import java.io.Serializable;

/**
 *
 * @author colivares
 */
public class DynamicColumnDataTable implements Serializable {
    private String id;
    private String header;
    private String ancho;

    public DynamicColumnDataTable(String id, String header) {
        this.id = id;
        this.header = header;
    }

    public DynamicColumnDataTable(String id, String header, String ancho) {
        this.id = id;
        this.header = header;
        this.ancho = ancho;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getAncho() {
        return ancho;
    }

    public void setAncho(String ancho) {
        this.ancho = ancho;
    }

    @Override
    public String toString() {
        return "workcenter.util.pojo.DynamicColumnDataTable[ id=" + id + " ]";
    }

}
