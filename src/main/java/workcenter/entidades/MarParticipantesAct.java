package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by claudio on 04-08-14.
 */
@Entity
@Table(name = "mar_participantes_act", schema = "", catalog = "")
@NamedQueries(
        @NamedQuery(
                name = "MarParticipantesAct.findByActividad",
                query = "SELECT m FROM MarParticipantesAct m INNER JOIN FETCH m.participante WHERE m.marActividadByIdActividad = :actividad"
        )
)
public class MarParticipantesAct implements Serializable {
    private Integer id;
    private MarActividad marActividadByIdActividad;
    private Personal participante;
    private Float nota;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarParticipantesAct that = (MarParticipantesAct) o;

        if (id == null || that.id == null) return false;
        else if (id.intValue() != that.id.intValue()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[ id = "+id+" ]";
    }

    @ManyToOne
    @JoinColumn(name = "id_actividad", referencedColumnName = "id", nullable = false)
    public MarActividad getMarActividadByIdActividad() {
        return marActividadByIdActividad;
    }

    public void setMarActividadByIdActividad(MarActividad marActividadByIdActividad) {
        this.marActividadByIdActividad = marActividadByIdActividad;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rut_participante", referencedColumnName = "rut")
    public Personal getParticipante() {
        return participante;
    }

    public void setParticipante(Personal participante) {
        this.participante = participante;
    }

    @Basic
    @Column(name = "nota", insertable = true, updatable = true, nullable = true)
    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }
}
