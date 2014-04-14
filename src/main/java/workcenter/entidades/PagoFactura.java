/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "pagosfacturas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoFactura.findAll", query = "SELECT p FROM PagoFactura p"),
    @NamedQuery(name = "PagoFactura.findById", query = "SELECT p FROM PagoFactura p WHERE p.id = :id"),
    @NamedQuery(name = "PagoFactura.findByFactura", query = "SELECT p FROM PagoFactura p WHERE p.factura = :factura"),
    @NamedQuery(name = "PagoFactura.findByDetalle", query = "SELECT p FROM PagoFactura p WHERE p.detalle = :detalle"),
    @NamedQuery(name = "PagoFactura.findByFechapago", query = "SELECT p FROM PagoFactura p WHERE p.fechapago = :fechapago"),
    @NamedQuery(name = "PagoFactura.findByMonto", query = "SELECT p FROM PagoFactura p WHERE p.monto = :monto"),
    @NamedQuery(name = "PagoFactura.findByPagado", query = "SELECT p FROM PagoFactura p WHERE p.pagado = :pagado")})
public class PagoFactura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "factura")
    private int factura;
    @Size(max = 200)
    @Column(name = "detalle")
    private String detalle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechapago")
    @Temporal(TemporalType.DATE)
    private Date fechapago;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto")
    private int monto;
    @Column(name = "pagado")
    private Boolean pagado;

    public PagoFactura() {
    }

    public PagoFactura(Integer id) {
        this.id = id;
    }

    public PagoFactura(Integer id, int factura, Date fechapago, int monto) {
        this.id = id;
        this.factura = factura;
        this.fechapago = fechapago;
        this.monto = monto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFactura() {
        return factura;
    }

    public void setFactura(int factura) {
        this.factura = factura;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Boolean getPagado() {
        return pagado;
    }

    public void setPagado(Boolean pagado) {
        this.pagado = pagado;
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
        if (!(object instanceof PagoFactura)) {
            return false;
        }
        PagoFactura other = (PagoFactura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.PagoFactura[ id=" + id + " ]";
    }
    
}
