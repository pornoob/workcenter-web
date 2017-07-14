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
@Table(name = "fact_detalle_facturas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FactDetalleFactura.findAll", query = "SELECT f FROM FactDetalleFactura f")})
public class FactDetalleFactura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "detalle_id")
    private Long detalleId;
    @ManyToOne
    @JoinColumn(name = "factura_id", referencedColumnName = "factura_id")
    private FactFactura factura;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private FactProducto producto;
    @Column(name = "precio_unitario")
    private Integer precioUnitario;
    @Column(name = "precio_total")
    private Integer precioTotal;
    @Column(name = "cantidad")
    private Integer cantidad;

    public FactDetalleFactura() {
    }

    public FactDetalleFactura(Long detalleId) {
        this.detalleId = detalleId;
    }

    public Long getDetalleId() {
        return detalleId;
    }

    public void setDetalleId(Long detalleId) {
        this.detalleId = detalleId;
    }

    public FactFactura getFactura() {
        return factura;
    }

    public void setFactura(FactFactura factura) {
        this.factura = factura;
    }

    public FactProducto getProducto() {
        return producto;
    }

    public void setProducto(FactProducto producto) {
        this.producto = producto;
    }

    public Integer getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Integer precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Integer precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.detalleId);
        hash = 79 * hash + Objects.hashCode(this.factura);
        hash = 79 * hash + Objects.hashCode(this.producto);
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
        final FactDetalleFactura other = (FactDetalleFactura) obj;
        if (this.detalleId == null || other.detalleId == null) {
            return false;
        }
        if (!Objects.equals(this.detalleId, other.detalleId)) {
            return false;
        }
        if (!Objects.equals(this.factura, other.factura)) {
            return false;
        }
        if (!Objects.equals(this.producto, other.producto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.FactDetalleFactura[ detalleId=" + detalleId + " ]";
    }

}
