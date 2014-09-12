package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by claudio on 09-09-14.
 */
@Entity
@Table(name = "mme_mantenciones_semiremolque", schema = "")
@NamedQueries(
        @NamedQuery(
                name = "MmeMantencionSemiremolque.findBySemiremolque",
                query = "select m from MmeMantencionSemiremolque m where m.semiRemolque=:semiremolque order by m.fecha desc"
        )
)
public class MmeMantencionSemiremolque implements Serializable {
    private Integer id;
    private Date fecha;
    private Personal mecanicoResponsable;
    private Integer criterioSiguiente;
    private Equipo semiRemolque;

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
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha", nullable = true, insertable = true, updatable = true)
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @ManyToOne
    @JoinColumn(name = "mecanico_responsable", referencedColumnName = "rut")
    public Personal getMecanicoResponsable() {
        return mecanicoResponsable;
    }

    public void setMecanicoResponsable(Personal mecanicoResponsable) {
        this.mecanicoResponsable = mecanicoResponsable;
    }

    @Basic
    @Column(name = "criterio_siguiente", nullable = true, insertable = true, updatable = true)
    public Integer getCriterioSiguiente() {
        return criterioSiguiente;
    }

    public void setCriterioSiguiente(Integer criterioSiguiente) {
        this.criterioSiguiente = criterioSiguiente;
    }

    @ManyToOne
    @JoinColumn(name = "equipo", referencedColumnName = "patente")
    public Equipo getSemiRemolque() {
        return semiRemolque;
    }

    public void setSemiRemolque(Equipo tracto) {
        this.semiRemolque = tracto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MmeMantencionSemiremolque that = (MmeMantencionSemiremolque) o;

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
