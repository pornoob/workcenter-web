package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Claudio Olivares
 */
@Entity
@Table(name = "mme_mantenciones_tareas_maquinaria")
@NamedQueries({
    @NamedQuery(name = "MmeTareaMaquina.findAll", query = "SELECT t FROM MmeTareaMaquina t")
})
public class MmeTareaMaquina implements Serializable, Comparable<MmeTareaMaquina> {

    private static final long serialVersionUID = -2362490414765102501L;
    
    @Id
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "cantidad_horas")
    private Integer cantHoras;
    
    @Column(name = "orden")
    private Integer orden;

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

    public Integer getCantHoras() {
        return cantHoras;
    }

    public void setCantHoras(Integer cantHoras) {
        this.cantHoras = cantHoras;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MmeTareaMaquina other = (MmeTareaMaquina) obj;
        if (this.getId() == null || other.getId() == null) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(MmeTareaMaquina o) {
        return this.orden.compareTo(o.orden);
    }
}
