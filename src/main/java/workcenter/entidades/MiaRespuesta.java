package workcenter.entidades;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by colivares on 19-08-14.
 */
@Entity
@Table(name = "mia_respuestas", schema = "")
@NamedQueries(
        @NamedQuery(
                name = "MiaRespuesta.findByInspeccion",
                query = "SELECT r FROM MiaRespuesta r WHERE r.miaInspeccionAvanzadaByIdInspeccion = :inspeccion"
        )
)
public class MiaRespuesta implements Serializable {
    private Integer id;
    private Boolean cumple;
    private MiaPregunta miaPreguntasByIdPregunta;
    private MiaInspeccionAvanzada miaInspeccionAvanzadaByIdInspeccion;

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
    @Column(name = "cumple", nullable = true, insertable = true, updatable = true)
    public Boolean getCumple() {
        return cumple;
    }

    public void setCumple(Boolean cumple) {
        this.cumple = cumple;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MiaRespuesta that = (MiaRespuesta) o;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pregunta", referencedColumnName = "id", insertable = true, updatable = false)
    public MiaPregunta getMiaPreguntasByIdPregunta() {
        return miaPreguntasByIdPregunta;
    }

    public void setMiaPreguntasByIdPregunta(MiaPregunta miaPreguntasByIdPregunta) {
        this.miaPreguntasByIdPregunta = miaPreguntasByIdPregunta;
    }

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "id_inspeccion", referencedColumnName = "id", insertable = true, updatable = false)
    public MiaInspeccionAvanzada getMiaInspeccionAvanzadaByIdInspeccion() {
        return miaInspeccionAvanzadaByIdInspeccion;
    }

    public void setMiaInspeccionAvanzadaByIdInspeccion(MiaInspeccionAvanzada miaInspeccionAvanzadaByIdInspeccion) {
        this.miaInspeccionAvanzadaByIdInspeccion = miaInspeccionAvanzadaByIdInspeccion;
    }
}
