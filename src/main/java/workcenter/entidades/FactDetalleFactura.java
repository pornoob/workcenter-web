/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.entidades;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

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
    @Column(name = "detalle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detalleId;
    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "factura_id", referencedColumnName = "factura_id")
    private FactFactura factura;
    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "producto_id")
    private FactProducto producto;
    @Column(name = "precio_unitario")
    private Integer precioUnitario;
    @Column(name = "precio_total")
    private Integer precioTotal;
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Transient
    private String rowKey;

    public FactDetalleFactura() {
        this.rowKey = "RK" + UUID.randomUUID();
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

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.rowKey);
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
        return Objects.equals(this.rowKey, other.rowKey);
    }

    @Override
    public String toString() {
        return "workcenter.entidades.FactDetalleFactura[ detalleId=" + detalleId + " ]";
    }

}
