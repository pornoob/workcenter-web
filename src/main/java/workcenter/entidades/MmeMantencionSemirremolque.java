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
                name = "MmeMantencionSemirremolque.findBySemiremolque",
                query = "select m from MmeMantencionSemirremolque m where m.semiRremolque=:semiremolque order by m.fecha desc"
        )
)
public class MmeMantencionSemirremolque implements Serializable {
    private Integer id;
    private Date fecha;
    private Personal mecanicoResponsable;
    private Integer criterioSiguiente;
    private Equipo semiRremolque;
    private OrdenTrabajo ot;

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
    public Equipo getSemiRremolque() {
        return semiRremolque;
    }

    public void setSemiRremolque(Equipo tracto) {
        this.semiRremolque = tracto;
    }
    
    @JoinColumn(name = "ot_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public OrdenTrabajo getOt() {
        return ot;
    }

    public void setOt(OrdenTrabajo ot) {
        this.ot = ot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MmeMantencionSemirremolque that = (MmeMantencionSemirremolque) o;

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
