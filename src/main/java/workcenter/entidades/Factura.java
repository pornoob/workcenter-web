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
@Table(name = "facturas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findById", query = "SELECT f FROM Factura f WHERE f.id = :id"),
    @NamedQuery(name = "Factura.findByEmisor", query = "SELECT f FROM Factura f WHERE f.emisor = :emisor"),
    @NamedQuery(name = "Factura.findByFecha", query = "SELECT f FROM Factura f WHERE f.fecha = :fecha"),
    @NamedQuery(name = "Factura.findByMescontable", query = "SELECT f FROM Factura f WHERE f.mescontable = :mescontable"),
    @NamedQuery(name = "Factura.findByClasificacion", query = "SELECT f FROM Factura f WHERE f.clasificacion = :clasificacion"),
    @NamedQuery(name = "Factura.findByNumerofactura", query = "SELECT f FROM Factura f WHERE f.numerofactura = :numerofactura"),
    @NamedQuery(name = "Factura.findByReceptor", query = "SELECT f FROM Factura f WHERE f.receptor = :receptor"),
    @NamedQuery(name = "Factura.findByImpespecifico", query = "SELECT f FROM Factura f WHERE f.impespecifico = :impespecifico"),
    @NamedQuery(name = "Factura.findByIva", query = "SELECT f FROM Factura f WHERE f.iva = :iva"),
    @NamedQuery(name = "Factura.findByOtrosimp", query = "SELECT f FROM Factura f WHERE f.otrosimp = :otrosimp"),
    @NamedQuery(name = "Factura.findByNeto", query = "SELECT f FROM Factura f WHERE f.neto = :neto"),
    @NamedQuery(name = "Factura.findByBruto", query = "SELECT f FROM Factura f WHERE f.bruto = :bruto"),
    @NamedQuery(name = "Factura.findByLitros", query = "SELECT f FROM Factura f WHERE f.litros = :litros")})
public class Factura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "emisor")
    private int emisor;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mescontable")
    @Temporal(TemporalType.DATE)
    private Date mescontable;
    @Column(name = "clasificacion")
    private Integer clasificacion;
    @Column(name = "numerofactura")
    private Integer numerofactura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "receptor")
    private int receptor;
    @Column(name = "impespecifico")
    private Integer impespecifico;
    @Column(name = "iva")
    private Integer iva;
    @Column(name = "otrosimp")
    private Integer otrosimp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "neto")
    private int neto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bruto")
    private int bruto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "litros")
    private Double litros;

    public Factura() {
    }

    public Factura(Integer id) {
        this.id = id;
    }

    public Factura(Integer id, int emisor, Date mescontable, int receptor, int neto, int bruto) {
        this.id = id;
        this.emisor = emisor;
        this.mescontable = mescontable;
        this.receptor = receptor;
        this.neto = neto;
        this.bruto = bruto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEmisor() {
        return emisor;
    }

    public void setEmisor(int emisor) {
        this.emisor = emisor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getMescontable() {
        return mescontable;
    }

    public void setMescontable(Date mescontable) {
        this.mescontable = mescontable;
    }

    public Integer getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Integer clasificacion) {
        this.clasificacion = clasificacion;
    }

    public Integer getNumerofactura() {
        return numerofactura;
    }

    public void setNumerofactura(Integer numerofactura) {
        this.numerofactura = numerofactura;
    }

    public int getReceptor() {
        return receptor;
    }

    public void setReceptor(int receptor) {
        this.receptor = receptor;
    }

    public Integer getImpespecifico() {
        return impespecifico;
    }

    public void setImpespecifico(Integer impespecifico) {
        this.impespecifico = impespecifico;
    }

    public Integer getIva() {
        return iva;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }

    public Integer getOtrosimp() {
        return otrosimp;
    }

    public void setOtrosimp(Integer otrosimp) {
        this.otrosimp = otrosimp;
    }

    public int getNeto() {
        return neto;
    }

    public void setNeto(int neto) {
        this.neto = neto;
    }

    public int getBruto() {
        return bruto;
    }

    public void setBruto(int bruto) {
        this.bruto = bruto;
    }

    public Double getLitros() {
        return litros;
    }

    public void setLitros(Double litros) {
        this.litros = litros;
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
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Factura[ id=" + id + " ]";
    }
    
}
