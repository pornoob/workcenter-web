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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "facturasasociadas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaAsociada.findAll", query = "SELECT f FROM FacturaAsociada f"),
    @NamedQuery(name = "FacturaAsociada.findById", query = "SELECT f FROM FacturaAsociada f WHERE f.id = :id"),
    @NamedQuery(name = "FacturaAsociada.findByClasfactura", query = "SELECT f FROM FacturaAsociada f WHERE f.clasfactura = :clasfactura"),
    @NamedQuery(name = "FacturaAsociada.findByBodega", query = "SELECT f FROM FacturaAsociada f WHERE f.bodega = :bodega")})
public class FacturaAsociada implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "clasfactura")
    private int clasfactura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bodega")
    private int bodega;

    public FacturaAsociada() {
    }

    public FacturaAsociada(Integer id) {
        this.id = id;
    }

    public FacturaAsociada(Integer id, int clasfactura, int bodega) {
        this.id = id;
        this.clasfactura = clasfactura;
        this.bodega = bodega;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getClasfactura() {
        return clasfactura;
    }

    public void setClasfactura(int clasfactura) {
        this.clasfactura = clasfactura;
    }

    public int getBodega() {
        return bodega;
    }

    public void setBodega(int bodega) {
        this.bodega = bodega;
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
        if (!(object instanceof FacturaAsociada)) {
            return false;
        }
        FacturaAsociada other = (FacturaAsociada) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.FacturaAsociada[ id=" + id + " ]";
    }
    
}
