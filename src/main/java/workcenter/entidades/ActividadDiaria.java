/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author colivares
 */
@Entity
@Table(name = "actividad_diaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadDiaria.findAll", query = "SELECT a FROM ActividadDiaria a"),
    @NamedQuery(name = "ActividadDiaria.findById", query = "SELECT a FROM ActividadDiaria a WHERE a.id = :id"),
    @NamedQuery(name = "ActividadDiaria.findByIdUsuario", query = "SELECT a FROM ActividadDiaria a WHERE a.idUsuario = :idUsuario"),
    @NamedQuery(name = "ActividadDiaria.findByHora", query = "SELECT a FROM ActividadDiaria a WHERE a.hora = :hora"),
    @NamedQuery(name = "ActividadDiaria.findByIdTipoActividad", query = "SELECT a FROM ActividadDiaria a WHERE a.idTipoActividad = :idTipoActividad"),
    @NamedQuery(name = "ActividadDiaria.findByFecha", query = "SELECT a FROM ActividadDiaria a WHERE a.fecha = :fecha"),
    @NamedQuery(name = "ActividadDiaria.findByDetalle", query = "SELECT a FROM ActividadDiaria a WHERE a.detalle = :detalle")})
public class ActividadDiaria implements Serializable {
    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "id_servicio", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Servicio idServicio;

    @JoinColumn(name = "id_contrato", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private MpaContrato contrato;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_usuario")
    private Long idUsuario;
    @Column(name = "hora")
    private Integer hora;
    @JoinColumn(name = "id_tipo_actividad", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoActividadDiaria idTipoActividad;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 1000)
    @Column(name = "detalle")
    private String detalle;
    
    @JoinColumn(name = "id_usuario", referencedColumnName = "rut", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.EAGER)
    private Personal persona;

    public ActividadDiaria() {
    }

    public ActividadDiaria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public TipoActividadDiaria getIdTipoActividad() {
        return idTipoActividad;
    }

    public void setIdTipoActividad(TipoActividadDiaria idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadDiaria)) {
            return false;
        }
        ActividadDiaria other = (ActividadDiaria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.ActividadDiaria[ id=" + id + ", fecha="+fecha+" ]";
    }

    public Servicio getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Servicio idServicio) {
        this.idServicio = idServicio;
    }

    public Personal getPersona() {
        return persona;
    }

    public void setPersona(Personal persona) {
        this.persona = persona;
    }

    public MpaContrato getContrato() {
        return contrato;
    }

    public void setContrato(MpaContrato contrato) {
        this.contrato = contrato;
    }
}
