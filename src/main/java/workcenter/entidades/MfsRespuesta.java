package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by claudio on 26-11-14.
 */
@Entity
@Table(name = "mfs_respuestas_encuesta", schema = "")
@NamedQueries({
        @NamedQuery(
                name = "MfsRespuesta.findByEncuesta",
                query = "select r from MfsRespuesta r where r.encuesta = :encuesta"
        )
})
public class MfsRespuesta implements Serializable {
    private Integer id;
    private MfsPregunta idPregunta;
    private Integer valorRespuesta;
    private MfsEncuesta encuesta;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "id_pregunta", referencedColumnName = "id")
    public MfsPregunta getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(MfsPregunta idPregunta) {
        this.idPregunta = idPregunta;
    }

    @Basic
    @Column(name = "valor_respuesta")
    public Integer getValorRespuesta() {
        return valorRespuesta;
    }

    public void setValorRespuesta(Integer valorRespuesta) {
        this.valorRespuesta = valorRespuesta;
    }

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "encuesta", referencedColumnName = "id")
    public MfsEncuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(MfsEncuesta encuenta) {
        this.encuesta = encuenta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MfsRespuesta that = (MfsRespuesta) o;

        if (this.getId() == null || that.getId() == null) return false;
        else if (this.getId().intValue() != that.getId().intValue()) return false;
        else return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
