/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "reportesdecarga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReporteDecarga.findAll", query = "SELECT r FROM ReporteDecarga r"),
    @NamedQuery(name = "ReporteDecarga.findById", query = "SELECT r FROM ReporteDecarga r WHERE r.id = :id"),
    @NamedQuery(name = "ReporteDecarga.findByProducto", query = "SELECT r FROM ReporteDecarga r WHERE r.producto = :producto"),
    @NamedQuery(name = "ReporteDecarga.findByFechacarga", query = "SELECT r FROM ReporteDecarga r WHERE r.fechacarga = :fechacarga"),
    @NamedQuery(name = "ReporteDecarga.findByGuia", query = "SELECT r FROM ReporteDecarga r WHERE r.guia = :guia"),
    @NamedQuery(name = "ReporteDecarga.findByTonelaje", query = "SELECT r FROM ReporteDecarga r WHERE r.tonelaje = :tonelaje")})
public class ReporteDecarga implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "producto")
    private int producto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechacarga")
    @Temporal(TemporalType.DATE)
    private Date fechacarga;
    @Column(name = "guia")
    private Integer guia;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tonelaje")
    private Double tonelaje;

    public ReporteDecarga() {
    }

    public ReporteDecarga(Integer id) {
        this.id = id;
    }

    public ReporteDecarga(Integer id, int producto, Date fechacarga) {
        this.id = id;
        this.producto = producto;
        this.fechacarga = fechacarga;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getProducto() {
        return producto;
    }

    public void setProducto(int producto) {
        this.producto = producto;
    }

    public Date getFechacarga() {
        return fechacarga;
    }

    public void setFechacarga(Date fechacarga) {
        this.fechacarga = fechacarga;
    }

    public Integer getGuia() {
        return guia;
    }

    public void setGuia(Integer guia) {
        this.guia = guia;
    }

    public Double getTonelaje() {
        return tonelaje;
    }

    public void setTonelaje(Double tonelaje) {
        this.tonelaje = tonelaje;
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
        if (!(object instanceof ReporteDecarga)) {
            return false;
        }
        ReporteDecarga other = (ReporteDecarga) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.ReporteDecarga[ id=" + id + " ]";
    }
    
}
