/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "detallesactividades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleActividad.findAll", query = "SELECT d FROM DetalleActividad d"),
    @NamedQuery(name = "DetalleActividad.findById", query = "SELECT d FROM DetalleActividad d WHERE d.id = :id"),
    @NamedQuery(name = "DetalleActividad.findByActividad", query = "SELECT d FROM DetalleActividad d WHERE d.actividad = :actividad"),
    @NamedQuery(name = "DetalleActividad.findByDetalle", query = "SELECT d FROM DetalleActividad d WHERE d.detalle = :detalle")})
public class DetalleActividad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "actividad")
    private Integer actividad;
    @Size(max = 1000)
    @Column(name = "detalle")
    private String detalle;

    public DetalleActividad() {
    }

    public DetalleActividad(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActividad() {
        return actividad;
    }

    public void setActividad(Integer actividad) {
        this.actividad = actividad;
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
        if (!(object instanceof DetalleActividad)) {
            return false;
        }
        DetalleActividad other = (DetalleActividad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.DetalleActividad[ id=" + id + " ]";
    }
    
}
