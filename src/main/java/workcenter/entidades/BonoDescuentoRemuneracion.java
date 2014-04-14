/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "maestroGuiasBonosDescuentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BonoDescuentoRemuneracion.findAll", query = "SELECT m FROM BonoDescuentoRemuneracion m"),
    @NamedQuery(name = "BonoDescuentoRemuneracion.findByIdBonoDescuento", query = "SELECT m FROM BonoDescuentoRemuneracion m WHERE m.idBonoDescuento = :idBonoDescuento"),
    @NamedQuery(name = "BonoDescuentoRemuneracion.findByMonto", query = "SELECT m FROM BonoDescuentoRemuneracion m WHERE m.monto = :monto"),
    @NamedQuery(name = "BonoDescuentoRemuneracion.findByDescripcion", query = "SELECT m FROM BonoDescuentoRemuneracion m WHERE m.descripcion = :descripcion"),
    @NamedQuery(name = "BonoDescuentoRemuneracion.findByImponible", query = "SELECT m FROM BonoDescuentoRemuneracion m WHERE m.imponible = :imponible"),
    @NamedQuery(name = "BonoDescuentoRemuneracion.findByBono", query = "SELECT m FROM BonoDescuentoRemuneracion m WHERE m.bono = :bono")})
public class BonoDescuentoRemuneracion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBonoDescuento")
    private Long idBonoDescuento;
    @Column(name = "monto")
    private BigInteger monto;
    @Size(max = 5000)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "imponible")
    private Boolean imponible;
    @Column(name = "bono")
    private Boolean bono;
    @JoinColumn(name = "idMaestroGuia", referencedColumnName = "idMaestro")
    @ManyToOne(optional = false)
    private Remuneracion idMaestroGuia;

    public BonoDescuentoRemuneracion() {
    }

    public BonoDescuentoRemuneracion(Long idBonoDescuento) {
        this.idBonoDescuento = idBonoDescuento;
    }

    public Long getIdBonoDescuento() {
        return idBonoDescuento;
    }

    public void setIdBonoDescuento(Long idBonoDescuento) {
        this.idBonoDescuento = idBonoDescuento;
    }

    public BigInteger getMonto() {
        return monto;
    }

    public void setMonto(BigInteger monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getImponible() {
        return imponible;
    }

    public void setImponible(Boolean imponible) {
        this.imponible = imponible;
    }

    public Boolean getBono() {
        return bono;
    }

    public void setBono(Boolean bono) {
        this.bono = bono;
    }

    public Remuneracion getIdMaestroGuia() {
        return idMaestroGuia;
    }

    public void setIdMaestroGuia(Remuneracion idMaestroGuia) {
        this.idMaestroGuia = idMaestroGuia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBonoDescuento != null ? idBonoDescuento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BonoDescuentoRemuneracion)) {
            return false;
        }
        BonoDescuentoRemuneracion other = (BonoDescuentoRemuneracion) object;
        if ((this.idBonoDescuento == null && other.idBonoDescuento != null) || (this.idBonoDescuento != null && !this.idBonoDescuento.equals(other.idBonoDescuento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.BonoDescuentoRemuneracion[ idBonoDescuento=" + idBonoDescuento + " ]";
    }
    
}
