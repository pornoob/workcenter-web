package workcenter.entidades;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Claudio Olivares
 */
@Entity
@Table(name = "ot_repuestos")
@IdClass(RepuestoOtPK.class)
public class RepuestoOt implements Serializable {
    @Id
    private Long otId;
    @Id
    private Long productoId;
    
    @ManyToOne
    @MapsId(value = "otId")
    @JoinColumn(name = "ot_id")
    private OrdenTrabajo ot;

    @ManyToOne
    @MapsId(value = "productoId")
    @JoinColumn(name = "producto_id")
    private FactProducto producto;
    
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Transient
    private String rowKey;

    public RepuestoOt() {
        this.rowKey = "RK" + UUID.randomUUID();
    }

    public RepuestoOt(OrdenTrabajo ot, FactProducto producto) {
        this.ot = ot;
        this.producto = producto;
        this.rowKey = "RK" + UUID.randomUUID();
    }

    public Long getOtId() {
        return otId;
    }

    public void setOtId(Long otId) {
        this.otId = otId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public OrdenTrabajo getOt() {
        return ot;
    }

    public void setOt(OrdenTrabajo ot) {
        this.ot = ot;
    }

    public FactProducto getProducto() {
        return producto;
    }

    public void setProducto(FactProducto producto) {
        this.producto = producto;
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
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.ot);
        hash = 41 * hash + Objects.hashCode(this.producto);
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
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final RepuestoOt other = (RepuestoOt) obj;
        if (!Objects.equals(this.ot, other.ot)) {
            return false;
        }
        return Objects.equals(this.producto, other.producto);
    }
}
