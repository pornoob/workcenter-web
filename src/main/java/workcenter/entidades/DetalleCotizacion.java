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

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "detallescotizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleCotizacion.findAll", query = "SELECT d FROM DetalleCotizacion d"),
    @NamedQuery(name = "DetalleCotizacion.findById", query = "SELECT d FROM DetalleCotizacion d WHERE d.id = :id"),
    @NamedQuery(name = "DetalleCotizacion.findByCantidad", query = "SELECT d FROM DetalleCotizacion d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DetalleCotizacion.findByUnidad", query = "SELECT d FROM DetalleCotizacion d WHERE d.unidad = :unidad"),
    @NamedQuery(name = "DetalleCotizacion.findByPreciounidad", query = "SELECT d FROM DetalleCotizacion d WHERE d.preciounidad = :preciounidad"),
    @NamedQuery(name = "DetalleCotizacion.findByDetalle", query = "SELECT d FROM DetalleCotizacion d WHERE d.detalle = :detalle"),
    @NamedQuery(name = "DetalleCotizacion.findByCotizacion", query = "SELECT d FROM DetalleCotizacion d WHERE d.cotizacion = :cotizacion")})
public class DetalleCotizacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "unidad")
    private int unidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "preciounidad")
    private int preciounidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "detalle")
    private String detalle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cotizacion")
    private int cotizacion;

    public DetalleCotizacion() {
    }

    public DetalleCotizacion(Integer id) {
        this.id = id;
    }

    public DetalleCotizacion(Integer id, int cantidad, int unidad, int preciounidad, String detalle, int cotizacion) {
        this.id = id;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.preciounidad = preciounidad;
        this.detalle = detalle;
        this.cotizacion = cotizacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getUnidad() {
        return unidad;
    }

    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }

    public int getPreciounidad() {
        return preciounidad;
    }

    public void setPreciounidad(int preciounidad) {
        this.preciounidad = preciounidad;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(int cotizacion) {
        this.cotizacion = cotizacion;
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
        if (!(object instanceof DetalleCotizacion)) {
            return false;
        }
        DetalleCotizacion other = (DetalleCotizacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.DetalleCotizacion[ id=" + id + " ]";
    }
    
}
