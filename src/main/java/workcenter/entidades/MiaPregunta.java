package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by colivares on 19-08-14.
 */
@Entity
@Table(name = "mia_preguntas", schema = "")
@NamedQueries(
        @NamedQuery(
                name = "MiaPregunta.findAll",
                query = "SELECT m FROM MiaPregunta m ORDER BY m.numero ASC"
        )
)
public class MiaPregunta implements Serializable {
    private Integer id;
    private String pregunta;
    private Integer numero;
    private Boolean bloqueante;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pregunta", nullable = true, insertable = true, updatable = true, length = 1000)
    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    @Basic
    @Column(name = "numero", nullable = true, insertable = true, updatable = true)
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Basic
    @Column(name = "bloqueante", nullable = false)
    public Boolean getBloqueante() {
        return bloqueante;
    }

    public void setBloqueante(Boolean bloqueante) {
        this.bloqueante = bloqueante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MiaPregunta that = (MiaPregunta) o;

        if (this.getId() == null || that.getId() == null) return false;
        else if (this.getId().intValue() != that.getId().intValue()) return false;
        else return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
}
