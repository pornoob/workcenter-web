package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by claudio on 30-09-14.
 */
@Entity
@Table(name = "mir_trazabilidad_incidencia", schema = "")
@NamedQueries({
        @NamedQuery(
                name = "MirTrazabilidadIncidencia.findByIncidencia",
                query = "select t from MirTrazabilidadIncidencia t where t.idIncidencia = :incidencia order by t.id asc"
        )
})
public class MirTrazabilidadIncidencia implements Serializable {
    private Integer id;
    private MirIncidencia idIncidencia;
    private MirEstadoIncidencia idEstado;
    private Date fecha;
    private String detalle;
    private Personal creador;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "id_incidencia", referencedColumnName = "id")
    public MirIncidencia getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(MirIncidencia idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    @ManyToOne
    @JoinColumn(name = "id_estado", referencedColumnName = "id")
    public MirEstadoIncidencia getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(MirEstadoIncidencia idEstado) {
        this.idEstado = idEstado;
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false, insertable = true, updatable = true)
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Basic
    @Column(name = "detalle", nullable = false, insertable = true, updatable = true, length = 65535)
    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MirTrazabilidadIncidencia that = (MirTrazabilidadIncidencia) o;

        if (that.getId() == null || this.getId() == null) return false;
        else if (that.getId().intValue() != this.getId().intValue()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @ManyToOne
    @JoinColumn(name = "creador", referencedColumnName = "rut")
    public Personal getCreador() {
        return creador;
    }

    public void setCreador(Personal creador) {
        this.creador = creador;
    }
}
