package workcenter.util.pojo;

import java.io.Serializable;

/**
 *
 * @author colivares
 */
public class DynamicColumnDataTable implements Serializable {
    private String id;
    private String header;

    public DynamicColumnDataTable(String id, String header) {
        this.id = id;
        this.header = header;
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

    @Override
    public String toString() {
        return "workcenter.util.pojo.DynamicColumnDataTable[ id=" + id + " ]";
    }

}
