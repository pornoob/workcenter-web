package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by claudio on 30-09-14.
 */
@Entity
@Table(name = "mir_incidencia", schema = "")
@NamedQueries({
        @NamedQuery(
                name = "MirIncidencia.findAll",
                query = "select i from MirIncidencia i order by i.fecha desc"
        ),
        @NamedQuery(
                name = "MirIncidencia.findByInformador",
                query = "select i from MirIncidencia i where i.rutInformador.rut = :rut"
        ),
        @NamedQuery(
                name = "MirIncidencia.findByApoyo",
                query = "select i from MirIncidencia i where i.idApoyo.idSocio.rut = :rut"
        )
})
public class MirIncidencia implements Serializable {
    private Integer id;
    private Personal rutInformador;
    private MirApoyo idApoyo;
    private Date fecha;
    private Date resolucionProgramada;
    private MirSeveridad severidad;
    private MirPrioridad prioridad;

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
    @JoinColumn(name = "rut_informador", referencedColumnName = "rut")
    public Personal getRutInformador() {
        return rutInformador;
    }

    public void setRutInformador(Personal rutInformador) {
        this.rutInformador = rutInformador;
    }

    @ManyToOne
    @JoinColumn(name = "id_apoyo", referencedColumnName = "id")
    public MirApoyo getIdApoyo() {
        return idApoyo;
    }

    public void setIdApoyo(MirApoyo idApoyo) {
        this.idApoyo = idApoyo;
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
    @Temporal(TemporalType.DATE)
    @Column(name = "resolucion_programada", nullable = false, insertable = true, updatable = true)
    public Date getResolucionProgramada() {
        return resolucionProgramada;
    }

    public void setResolucionProgramada(Date resolucionProgramada) {
        this.resolucionProgramada = resolucionProgramada;
    }

    @ManyToOne
    @JoinColumn(name = "severidad", referencedColumnName = "id")
    public MirSeveridad getSeveridad() {
        return severidad;
    }

    public void setSeveridad(MirSeveridad severidad) {
        this.severidad = severidad;
    }

    @ManyToOne
    @JoinColumn(name = "prioridad", referencedColumnName = "id")
    public MirPrioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(MirPrioridad prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MirIncidencia that = (MirIncidencia) o;

        if (that.getId() == null || this.getId() == null) return false;
        else if (that.getId().intValue() != this.getId().intValue()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
