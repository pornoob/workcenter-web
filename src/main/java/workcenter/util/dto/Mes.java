package workcenter.util.dto;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * @author colivares
 */
public class Mes implements Serializable, Comparator {

    private String id;
    private String nombre;
    private List<Semana> semanas;

    public Mes(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Mes(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "workcenter.util.pojo.Mes[id=" + getId() + "]";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Mes)) {
            return false;
        }
        Mes otro = (Mes) o;
        if (otro.getId() == null || this.getId() == null) {
            return false;
        } else if (!otro.getId().equals(this.getId())) {
            return false;
        }
        return true;
    }

    public int compare(Object o1, Object o2) {
        Mes mes1 = (Mes) o1;
        Mes mes2 = (Mes) o2;
        if (mes1.equals(mes2)) {
            return 0;
        }
        return -1;
    }

    public List<Semana> getSemanas() {
        return semanas;
    }

    public void setSemanas(List<Semana> semanas) {
        this.semanas = semanas;
    }
}
