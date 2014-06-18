package workcenter.util.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author colivares
 */
public class Dia implements Serializable {

    private Integer id;
    private String nombre;
    private Date fecha;
    private SimpleDateFormat sdf;

    public Dia(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        sdf = new SimpleDateFormat("dd-MM-yyyy");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Dia)) {
            return false;
        }
        Dia otro = (Dia) o;
        if (otro.getId() == null || this.getId() == null) {
            return false;
        } else if (!otro.getId().equals(this.getId())) {
            return false;
        } else if (!this.getFecha().equals(otro.getFecha())) {
            return false;
        }
        return true;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getFechaStr() {
        try {
            return sdf.format(fecha);
        } catch (Exception e) {
            System.out.println("Fecha: "+fecha);
            return "";
        }
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
