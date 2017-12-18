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
@Table(name = "tipobonosdescuentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoBonoDescuento.findAll", query = "SELECT t FROM TipoBonoDescuento t"),
    @NamedQuery(name = "TipoBonoDescuento.findById", query = "SELECT t FROM TipoBonoDescuento t WHERE t.id = :id"),
    @NamedQuery(name = "TipoBonoDescuento.findByDescripcion", query = "SELECT t FROM TipoBonoDescuento t WHERE t.descripcion = :descripcion")})
public class TipoBonoDescuento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descripcion")
    private String descripcion;

    public TipoBonoDescuento() {
    }

    public TipoBonoDescuento(Integer id) {
        this.id = id;
    }

    public TipoBonoDescuento(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof TipoBonoDescuento)) {
            return false;
        }
        TipoBonoDescuento other = (TipoBonoDescuento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.TipoBonoDescuento[ id=" + id + " ]";
    }
    
}
