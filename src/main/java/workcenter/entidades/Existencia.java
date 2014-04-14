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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "existencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Existencia.findAll", query = "SELECT e FROM Existencia e"),
    @NamedQuery(name = "Existencia.findById", query = "SELECT e FROM Existencia e WHERE e.id = :id"),
    @NamedQuery(name = "Existencia.findByCantidad", query = "SELECT e FROM Existencia e WHERE e.cantidad = :cantidad"),
    @NamedQuery(name = "Existencia.findByValorunitario", query = "SELECT e FROM Existencia e WHERE e.valorunitario = :valorunitario"),
    @NamedQuery(name = "Existencia.findByTipo", query = "SELECT e FROM Existencia e WHERE e.tipo = :tipo"),
    @NamedQuery(name = "Existencia.findByFactura", query = "SELECT e FROM Existencia e WHERE e.factura = :factura"),
    @NamedQuery(name = "Existencia.findByIngreso", query = "SELECT e FROM Existencia e WHERE e.ingreso = :ingreso")})
public class Existencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valorunitario")
    private double valorunitario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo")
    private int tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "factura")
    private int factura;
    @Column(name = "ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ingreso;

    public Existencia() {
    }

    public Existencia(Integer id) {
        this.id = id;
    }

    public Existencia(Integer id, int cantidad, double valorunitario, int tipo, int factura) {
        this.id = id;
        this.cantidad = cantidad;
        this.valorunitario = valorunitario;
        this.tipo = tipo;
        this.factura = factura;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getValorunitario() {
        return valorunitario;
    }

    public void setValorunitario(double valorunitario) {
        this.valorunitario = valorunitario;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getFactura() {
        return factura;
    }

    public void setFactura(int factura) {
        this.factura = factura;
    }

    public Date getIngreso() {
        return ingreso;
    }

    public void setIngreso(Date ingreso) {
        this.ingreso = ingreso;
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
        if (!(object instanceof Existencia)) {
            return false;
        }
        Existencia other = (Existencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Existencia[ id=" + id + " ]";
    }
    
}
