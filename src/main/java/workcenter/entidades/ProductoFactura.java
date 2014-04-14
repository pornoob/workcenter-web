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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "productosporfactura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductoFactura.findAll", query = "SELECT p FROM ProductoFactura p"),
    @NamedQuery(name = "ProductoFactura.findById", query = "SELECT p FROM ProductoFactura p WHERE p.id = :id"),
    @NamedQuery(name = "ProductoFactura.findByProducto", query = "SELECT p FROM ProductoFactura p WHERE p.producto = :producto"),
    @NamedQuery(name = "ProductoFactura.findByFactura", query = "SELECT p FROM ProductoFactura p WHERE p.factura = :factura")})
public class ProductoFactura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "producto")
    private Integer producto;
    @Column(name = "factura")
    private Integer factura;

    public ProductoFactura() {
    }

    public ProductoFactura(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProducto() {
        return producto;
    }

    public void setProducto(Integer producto) {
        this.producto = producto;
    }

    public Integer getFactura() {
        return factura;
    }

    public void setFactura(Integer factura) {
        this.factura = factura;
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
        if (!(object instanceof ProductoFactura)) {
            return false;
        }
        ProductoFactura other = (ProductoFactura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.ProductoFactura[ id=" + id + " ]";
    }
    
}
