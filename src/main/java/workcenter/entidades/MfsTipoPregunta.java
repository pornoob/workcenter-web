package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by claudio on 19-11-14.
 */
@Entity
@Table(name = "mfs_tipo_pregunta", schema = "")
public class MfsTipoPregunta implements Serializable {
    private Integer id;
    private String nombre;
    private List<MfsPregunta> preguntas;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nombre", nullable = false, insertable = true, updatable = true, length = 100)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @OneToMany(mappedBy = "tipoPregunta")
    public List<MfsPregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<MfsPregunta> preguntas) {
        this.preguntas = preguntas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MfsTipoPregunta that = (MfsTipoPregunta) o;

        if (this.getId() == null || that.getId() == null) return false;
        else if (this.getId().intValue() != that.getId().intValue()) return false;
        else return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
