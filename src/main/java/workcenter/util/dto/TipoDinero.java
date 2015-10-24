package workcenter.util.dto;

import java.io.Serializable;

/**
 * Created by renholders on 20-10-2015.
 */
public class TipoDinero implements Serializable {

    private Integer id;
    private String  descripcion;

    public TipoDinero(Integer id, String descripcion){
        this.id = id;
        this.descripcion = descripcion;
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getdescripcion() {
        return descripcion;
    }

    public void setdescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
