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
@Table(name = "cotizaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cotizacion.findAll", query = "SELECT c FROM Cotizacion c"),
    @NamedQuery(name = "Cotizacion.findById", query = "SELECT c FROM Cotizacion c WHERE c.id = :id"),
    @NamedQuery(name = "Cotizacion.findByEmpresa", query = "SELECT c FROM Cotizacion c WHERE c.empresa = :empresa"),
    @NamedQuery(name = "Cotizacion.findByFecha", query = "SELECT c FROM Cotizacion c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Cotizacion.findByDuracion", query = "SELECT c FROM Cotizacion c WHERE c.duracion = :duracion"),
    @NamedQuery(name = "Cotizacion.findByOrigen", query = "SELECT c FROM Cotizacion c WHERE c.origen = :origen"),
    @NamedQuery(name = "Cotizacion.findByDestino", query = "SELECT c FROM Cotizacion c WHERE c.destino = :destino"),
    @NamedQuery(name = "Cotizacion.findByCliente", query = "SELECT c FROM Cotizacion c WHERE c.cliente = :cliente"),
    @NamedQuery(name = "Cotizacion.findByEmitidopor", query = "SELECT c FROM Cotizacion c WHERE c.emitidopor = :emitidopor"),
    @NamedQuery(name = "Cotizacion.findByCondventa", query = "SELECT c FROM Cotizacion c WHERE c.condventa = :condventa"),
    @NamedQuery(name = "Cotizacion.findByRepresentante", query = "SELECT c FROM Cotizacion c WHERE c.representante = :representante")})
public class Cotizacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "empresa")
    private int empresa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 45)
    @Column(name = "duracion")
    private String duracion;
    @Size(max = 45)
    @Column(name = "origen")
    private String origen;
    @Size(max = 45)
    @Column(name = "destino")
    private String destino;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cliente")
    private int cliente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "emitidopor")
    private int emitidopor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "condventa")
    private int condventa;
    @Size(max = 200)
    @Column(name = "representante")
    private String representante;

    public Cotizacion() {
    }

    public Cotizacion(Integer id) {
        this.id = id;
    }

    public Cotizacion(Integer id, int empresa, Date fecha, int cliente, int emitidopor, int condventa) {
        this.id = id;
        this.empresa = empresa;
        this.fecha = fecha;
        this.cliente = cliente;
        this.emitidopor = emitidopor;
        this.condventa = condventa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getEmitidopor() {
        return emitidopor;
    }

    public void setEmitidopor(int emitidopor) {
        this.emitidopor = emitidopor;
    }

    public int getCondventa() {
        return condventa;
    }

    public void setCondventa(int condventa) {
        this.condventa = condventa;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
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
        if (!(object instanceof Cotizacion)) {
            return false;
        }
        Cotizacion other = (Cotizacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Cotizacion[ id=" + id + " ]";
    }
    
}
