package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "id_servicio", referencedColumnName = "id")
    public Servicio getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Servicio idServicio) {
        this.idServicio = idServicio;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final MpaContrato other = (MpaContrato) obj;
        if (this.id == null || other.id == null) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
