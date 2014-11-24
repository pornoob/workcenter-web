package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by claudio on 19-11-14.
 */
@Entity
@Table(name = "mfs_preguntas", schema = "")
@NamedQueries({
        @NamedQuery(name = "MfsPregunta.findBySeccion", query = "select p from MfsPregunta p where p.seccion = :seccion order by p.numero")
})
public class MfsPregunta implements Serializable {
    private Integer id;
    private Integer numero;
    private Integer seccion;
    private MfsTipoPregunta tipoPregunta;
    private Integer respuesta;
    private String pregunta;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "numero", nullable = false, insertable = true, updatable = true)
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Basic
    @Column(name = "seccion", nullable = false, insertable = true, updatable = true)
    public Integer getSeccion() {
        return seccion;
    }

    public void setSeccion(Integer seccion) {
        this.seccion = seccion;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_pregunta", referencedColumnName = "id")
    public MfsTipoPregunta getTipoPregunta() {
        return tipoPregunta;
    }

    public void setTipoPregunta(MfsTipoPregunta tipoPregunta) {
        this.tipoPregunta = tipoPregunta;
    }

    @Basic
    @Column(name = "respuesta", nullable = true, insertable = true, updatable = true)
    public Integer getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Integer respuesta) {
        this.respuesta = respuesta;
    }

    @Basic
    @Column(name = "pregunta", nullable = true, insertable = true, updatable = true, length = 300)
    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MfsPregunta that = (MfsPregunta) o;

        if (this.getId() == null || that.getId() == null) return false;
        else if (this.getId().intValue() != that.getId().intValue()) return false;
        else return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
