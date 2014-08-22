package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by colivares on 04-08-14.
 */
@Entity
@Table(name = "mar_actividad", schema = "")
public class MarActividad implements Serializable {
    private Integer id;
    private String descripcion;
    private String horaInicio;
    private String horaFin;
    private Date fecha;
    private Personal encargado;
    private MarTipoActividad tipoActividad;
    private List<MarParticipantesAct> participantes;

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
    @Column(name = "descripcion", nullable = false, insertable = true, updatable = true, length = 1000)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Basic
    @Column(name = "hora_inicio", nullable = false, insertable = true, updatable = true, length = 5)
    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    @Basic
    @Column(name = "hora_fin", nullable = false, insertable = true, updatable = true, length = 5)
    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha", nullable = false, insertable = true, updatable = true)
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @JoinColumn(name = "rut_encargado", referencedColumnName = "rut", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    public Personal getEncargado() {
        return encargado;
    }

    public void setEncargado(Personal encargado) {
        this.encargado = encargado;
    }

    @JoinColumn(name = "id_tipo_actividad", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER, cascade =
            {
                    CascadeType.REFRESH, CascadeType.DETACH
            }
    )
    public MarTipoActividad getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(MarTipoActividad tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    @OneToMany(mappedBy = "marActividadByIdActividad", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    public List<MarParticipantesAct> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<MarParticipantesAct> participantes) {
        this.participantes = participantes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarActividad that = (MarActividad) o;

        if (this.getId() == null || that.getId() == null) return false;
        else if (this.getId().intValue() != that.getId().intValue()) return false;
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
        return this.getClass().getName() + "[ id = " + id + " ]";
    }
}
