/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @author claudio
 */
@Entity
@Table(name = "productos")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
        @NamedQuery(name = "Producto.findById", query = "SELECT p FROM Producto p WHERE p.id = :id"),
        @NamedQuery(name = "Producto.findByOrdencarga", query = "SELECT p FROM Producto p WHERE p.ordencarga = :ordencarga"),
        @NamedQuery(name = "Producto.findByNumeroguia", query = "SELECT p FROM Producto p WHERE p.numeroguia = :numeroguia"),
        @NamedQuery(name = "Producto.findByFechacarga", query = "SELECT p FROM Producto p WHERE p.fechacarga = :fechacarga"),
        @NamedQuery(name = "Producto.findByFechadescarga", query = "SELECT p FROM Producto p WHERE p.fechadescarga = :fechadescarga"),
        @NamedQuery(name = "Producto.findByTonsalida", query = "SELECT p FROM Producto p WHERE p.tonsalida = :tonsalida"),
        @NamedQuery(name = "Producto.findByTonentrega", query = "SELECT p FROM Producto p WHERE p.tonentrega = :tonentrega"),
        @NamedQuery(name = "Producto.findByTramo", query = "SELECT p FROM Producto p WHERE p.tramo = :tramo")})
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "ordencarga", referencedColumnName = "ordendecarga")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vuelta ordencarga;
    @Column(name = "numeroguia")
    private Integer numeroguia;
    @Column(name = "fechacarga")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacarga;
    @Column(name = "fechadescarga")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechadescarga;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tonsalida")
    private Double tonsalida;
    @Column(name = "tonentrega")
    private Double tonentrega;
    @JoinColumn(name = "tramo", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TramoContrato tramo;

    @Transient
    private String rowKey;

    public Producto() {
        rowKey = "RK" + UUID.randomUUID();
    }

    public Producto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Vuelta getOrdencarga() {
        return ordencarga;
    }

    public void setOrdencarga(Vuelta ordencarga) {
        this.ordencarga = ordencarga;
    }

    public Integer getNumeroguia() {
        return numeroguia;
    }

    public void setNumeroguia(Integer numeroguia) {
        this.numeroguia = numeroguia;
    }

    public Date getFechacarga() {
        return fechacarga;
    }

    public void setFechacarga(Date fechacarga) {
        this.fechacarga = fechacarga;
    }

    public Date getFechadescarga() {
        return fechadescarga;
    }

    public void setFechadescarga(Date fechadescarga) {
        this.fechadescarga = fechadescarga;
    }

    public Double getTonsalida() {
        return tonsalida;
    }

    public void setTonsalida(Double tonsalida) {
        this.tonsalida = tonsalida;
    }

    public Double getTonentrega() {
        return tonentrega;
    }

    public void setTonentrega(Double tonentrega) {
        this.tonentrega = tonentrega;
    }

    public TramoContrato getTramo() {
        return tramo;
    }

    public void setTramo(TramoContrato tramo) {
        this.tramo = tramo;
    }

    @Override
    public int hashCode() {
        int hash = 123125;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto that = (Producto) o;
        if (id == null || that.id == null) return Objects.equals(rowKey, that.rowKey);
        return Objects.equals(id, that.getId());
    }

    @Override
    public String toString() {
        return "workcenter.entities.Producto[ id=" + id + " ]";
    }

}
