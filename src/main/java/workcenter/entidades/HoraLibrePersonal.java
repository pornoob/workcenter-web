/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "horaslibrespersonal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HoraLibrePersonal.findAll", query = "SELECT h FROM HoraLibrePersonal h"),
    @NamedQuery(name = "HoraLibrePersonal.findById", query = "SELECT h FROM HoraLibrePersonal h WHERE h.id = :id"),
    @NamedQuery(name = "HoraLibrePersonal.findByFecha", query = "SELECT h FROM HoraLibrePersonal h WHERE h.fecha = :fecha"),
    @NamedQuery(name = "HoraLibrePersonal.findByDesde", query = "SELECT h FROM HoraLibrePersonal h WHERE h.desde = :desde"),
    @NamedQuery(name = "HoraLibrePersonal.findByHasta", query = "SELECT h FROM HoraLibrePersonal h WHERE h.hasta = :hasta"),
    @NamedQuery(name = "HoraLibrePersonal.findByPersonal", query = "SELECT h FROM HoraLibrePersonal h WHERE h.personal = :personal"),
    @NamedQuery(name = "HoraLibrePersonal.findByTipo", query = "SELECT h FROM HoraLibrePersonal h WHERE h.tipo = :tipo")})
public class HoraLibrePersonal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "desde")
    @Temporal(TemporalType.TIMESTAMP)
    private Date desde;
    @Column(name = "hasta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hasta;
    @Column(name = "personal")
    private Integer personal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipo")
    private String tipo;

    public HoraLibrePersonal() {
    }

    public HoraLibrePersonal(Integer id) {
        this.id = id;
    }

    public HoraLibrePersonal(Integer id, Date fecha, String tipo) {
        this.id = id;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Integer getPersonal() {
        return personal;
    }

    public void setPersonal(Integer personal) {
        this.personal = personal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        if (!(object instanceof HoraLibrePersonal)) {
            return false;
        }
        HoraLibrePersonal other = (HoraLibrePersonal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.HoraLibrePersonal[ id=" + id + " ]";
    }
    
}
