package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by colivares on 19-08-14.
 */
@Entity
@Table(name = "mia_inspeccion_avanzada", schema = "")
@NamedQueries(
        @NamedQuery(
                name = "MiaInspeccionAvanzada.findAll",
                query = "SELECT i FROM MiaInspeccionAvanzada i ORDER BY i.fecha DESC"
        )
)
public class MiaInspeccionAvanzada implements Serializable {
    private Integer id;
    private Date fecha;
    private String observacion;
    private Equipo tracto;
    private Equipo batea;
    private Personal conductor;
    private Personal ejecutor;
    private Integer kilometraje;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha", nullable = true, insertable = true, updatable = true)
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Basic
    @Column(name = "observacion", nullable = true, insertable = true, updatable = true, length = 1000)
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @ManyToOne
    @JoinColumn(name = "tracto", referencedColumnName = "patente")
    public Equipo getTracto() {
        return tracto;
    }

    public void setTracto(Equipo tracto) {
        this.tracto = tracto;
    }

    @ManyToOne
    @JoinColumn(name = "batea", referencedColumnName = "patente")
    public Equipo getBatea() {
        return batea;
    }

    public void setBatea(Equipo batea) {
        this.batea = batea;
    }

    @ManyToOne
    @JoinColumn(name = "rut_conductor", referencedColumnName = "rut")
    public Personal getConductor() {
        return conductor;
    }

    public void setConductor(Personal conductor) {
        this.conductor = conductor;
    }

    @ManyToOne
    @JoinColumn(name = "rut_ejecutor", referencedColumnName = "rut")
    public Personal getEjecutor() {
        return ejecutor;
    }

    public void setEjecutor(Personal ejecutor) {
        this.ejecutor = ejecutor;
    }

    @Basic
    @Column(name = "kilometraje")
    public Integer getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MiaInspeccionAvanzada that = (MiaInspeccionAvanzada) o;

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
