package workcenter.entidades;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Claudio Olivares
 */
public class RepuestoOtPK implements Serializable {
    @Column(name = "ot_id")
    private Long otId;
    @Column(name = "producto_id")
    private Long productoId;

    public RepuestoOtPK() {
    }

    public RepuestoOtPK(Long otId, Long productoId) {
        this.otId = otId;
        this.productoId = productoId;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.otId);
        hash = 97 * hash + Objects.hashCode(this.productoId);
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
        final RepuestoOtPK other = (RepuestoOtPK) obj;
        if (!Objects.equals(this.otId, other.otId)) {
            return false;
        }
        if (!Objects.equals(this.productoId, other.productoId)) {
            return false;
        }
        return true;
    }
}
