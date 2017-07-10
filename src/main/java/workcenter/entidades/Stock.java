/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "stock")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stock.findAll", query = "SELECT s FROM Stock s")})
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "stock_id")
    private Long stockId;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private FactProducto producto;
    @ManyToOne
    @JoinColumn(name = "detalle_id")
    private FactDetalleFactura detalle;

    public Stock() {
    }

    public Stock(Long stockId) {
        this.stockId = stockId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public FactProducto getProducto() {
        return producto;
    }

    public void setProducto(FactProducto producto) {
        this.producto = producto;
    }

    public FactDetalleFactura getDetalle() {
        return detalle;
    }

    public void setDetalle(FactDetalleFactura detalle) {
        this.detalle = detalle;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.stockId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Stock other = (Stock) obj;
        if (this.stockId == null || other.stockId == null) { 
            return false;
        }
        return Objects.equals(this.stockId, other.stockId);
    }

    @Override
    public String toString() {
        return "workcenter.entidades.Stock[ stockId=" + stockId + " ]";
    }
    
}
