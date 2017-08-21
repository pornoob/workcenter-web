package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by claudio on 09-09-14.
 */
@Entity
@Table(name = "mme_mantenciones_tractos", schema = "")
@NamedQueries(
        @NamedQuery(
                name = "MmeMantencionTracto.findByTracto",
                query = "select m from MmeMantencionTracto  m where m.tracto = :tracto order by m.fecha desc"
        )
)
public class MmeMantencionTracto implements Serializable {
    private Integer id;
    private MmeTipoMantencion tipo;
    private Date fecha;
    private Personal mecanicoResponsable;
    private Equipo tracto;
    private Integer kilometraje;
    private Integer ciclo;
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

    @OneToOne
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    public MmeTipoMantencion getTipo() {
        return tipo;
    }

    public void setTipo(MmeTipoMantencion idTipo) {
        this.tipo = idTipo;
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

    @ManyToOne
    @JoinColumn(name = "equipo", referencedColumnName = "patente")
    public Equipo getTracto() {
        return tracto;
    }

    public void setTracto(Equipo tracto) {
        this.tracto = tracto;
    }

    @Basic
    @Column(name = "kilometraje")
    public Integer getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
    }

    @Basic
    @Column(name = "ciclo")
    public Integer getCiclo() {
        return ciclo;
    }

    public void setCiclo(Integer ciclo) {
        this.ciclo = ciclo;
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

        MmeMantencionTracto that = (MmeMantencionTracto) o;

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

    @Override
    public String toString() {
        return "workcenter.entidades.MmeMantencionTracto{" +
                "id=" + id +
                '}';
    }
}
