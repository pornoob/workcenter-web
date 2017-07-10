/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "fact_facturas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FactFactura.findAll", query = "SELECT f FROM FactFactura f")})
public class FactFactura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "factura_id")
    private Long facturaId;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @ManyToOne
    @JoinColumn(name = "emisor", referencedColumnName = "id")
    private Empresa emisor;
    @ManyToOne
    @JoinColumn(name = "receptor", referencedColumnName = "id")
    private Empresa receptor;
    
    private Set<FactDetalleFactura> items;

    public FactFactura() {
    }

    public FactFactura(Long facturaId) {
        this.facturaId = facturaId;
    }

    public Long getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Long facturaId) {
        this.facturaId = facturaId;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Empresa getEmisor() {
        return emisor;
    }

    public void setEmisor(Empresa emisor) {
        this.emisor = emisor;
    }

    public Empresa getReceptor() {
        return receptor;
    }

    public void setReceptor(Empresa receptor) {
        this.receptor = receptor;
    }

    public Set<FactDetalleFactura> getItems() {
        return items;
    }

    public void setItems(Set<FactDetalleFactura> items) {
        this.items = items;
    }
    
    /**
     * Agrega un detalle a la factura, siempre y cuando el producto especificado
     * no exista con anterioridad.
     * 
     * @param item item que se desea agregar
     * @return boolean, true en caso de haber agregado el producto y false
     * en caso contrario.
     */
    public boolean addItem(FactDetalleFactura item) {
        boolean retorno = false;
        if (this.items == null) {
            this.items = new HashSet<>();
        }
        Iterator<FactDetalleFactura> iterator = items.iterator();
        while(iterator.hasNext()) {
            FactDetalleFactura current = iterator.next();
            if (current.getProducto().equals(item.getProducto())) {
                return retorno;
            }
        }
        this.items.add(item);
        retorno = true;
        return retorno;
    }
    
    /**
     * Quita un detalle a la factura en base al producto que se esté detallando.
     * 
     * @param item que se desea quitar
     * @return boolean, true en caso de eliminar el producto especificado, false
     * en caso contrario.
     */
    public boolean removeItem(FactDetalleFactura item) {
        boolean retorno = false;
        if (this.items == null) {
            return retorno;
        }
        Iterator<FactDetalleFactura> iterator = items.iterator();
        while(iterator.hasNext()) {
            FactDetalleFactura current = iterator.next();
            if (current.getProducto().equals(item.getProducto())) {
                iterator.remove();
                break;
            }
        }
        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.facturaId);
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
        final FactFactura other = (FactFactura) obj;
        if (this.facturaId == null || other.facturaId == null) {
            return false;
        }
        return Objects.equals(this.facturaId, other.facturaId);
    }

    @Override
    public String toString() {
        return "workcenter.entidades.FactFactura[ facturaId=" + facturaId + " ]";
    }
    
}
