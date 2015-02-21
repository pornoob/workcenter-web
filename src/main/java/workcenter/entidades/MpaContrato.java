package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by claudio on 04-12-14.
 */
@Entity
@Table(name = "mpa_contrato", schema = "")
@NamedQueries({
        @NamedQuery(name = "MpaContrato.findAll", query = "select c from MpaContrato c order by c.idServicio.id, c.nombre"),
        @NamedQuery(name = "MpaContrato.findByServicio", query = "select c from MpaContrato c where c.idServicio=:servicio order by c.nombre")
})
public class MpaContrato implements Serializable {
    private Integer id;
    private String nombre;
    private Servicio idServicio;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @ManyToOne
    @JoinColumn(name = "id_servicio", referencedColumnName = "id")
    public Servicio getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Servicio idServicio) {
        this.idServicio = idServicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MpaContrato that = (MpaContrato) o;

        if (this.getId() == null || that.getId() == null) return false;
        else if (this.getId().intValue() != that.getId().intValue()) return false;
        else return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
