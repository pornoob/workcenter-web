/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author colivares
 */
@Entity
@Table(name = "alarmas_gps")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlarmaGps.findAll", query = "SELECT a FROM AlarmaGps a"),
    @NamedQuery(name = "AlarmaGps.findById", query = "SELECT a FROM AlarmaGps a WHERE a.id = :id"),
    @NamedQuery(name = "AlarmaGps.findByNumero", query = "SELECT a FROM AlarmaGps a WHERE a.numero = :numero"),
    @NamedQuery(name = "AlarmaGps.findByPatente", query = "SELECT a FROM AlarmaGps a WHERE a.patente = :patente"),
    @NamedQuery(name = "AlarmaGps.findByChofer", query = "SELECT a FROM AlarmaGps a WHERE a.chofer = :chofer"),
    @NamedQuery(name = "AlarmaGps.findByFecha", query = "SELECT a FROM AlarmaGps a WHERE a.fecha = :fecha"),
    @NamedQuery(name = "AlarmaGps.findByAlarma", query = "SELECT a FROM AlarmaGps a WHERE a.alarma = :alarma"),
    @NamedQuery(name = "AlarmaGps.findByVelocidad", query = "SELECT a FROM AlarmaGps a WHERE a.velocidad = :velocidad"),
    @NamedQuery(name = "AlarmaGps.findByRuta", query = "SELECT a FROM AlarmaGps a WHERE a.ruta = :ruta"),
    @NamedQuery(name = "AlarmaGps.findByUbicacion", query = "SELECT a FROM AlarmaGps a WHERE a.ubicacion = :ubicacion"),
    @NamedQuery(name = "AlarmaGps.findByCliente", query = "SELECT a FROM AlarmaGps a WHERE a.cliente = :cliente")})
public class AlarmaGps implements Serializable {
    @OneToMany(mappedBy = "idAlarma", fetch = FetchType.EAGER)
    private Collection<GestionAlarmaGps> gestionAlarmasGpsCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "numero")
    private Integer numero;
    @Size(max = 20)
    @Column(name = "patente")
    private String patente;
    @Size(max = 200)
    @Column(name = "chofer")
    private String chofer;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 200)
    @Column(name = "alarma")
    private String alarma;
    @Column(name = "velocidad")
    private Integer velocidad;
    @Size(max = 200)
    @Column(name = "ruta")
    private String ruta;
    @Size(max = 300)
    @Column(name = "ubicacion")
    private String ubicacion;
    @Size(max = 100)
    @Column(name = "cliente")
    private String cliente;

    public AlarmaGps() {
    }

    public AlarmaGps(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getChofer() {
        return chofer;
    }

    public void setChofer(String chofer) {
        this.chofer = chofer;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAlarma() {
        return alarma;
    }

    public void setAlarma(String alarma) {
        this.alarma = alarma;
    }

    public Integer getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Integer velocidad) {
        this.velocidad = velocidad;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
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
        if (!(object instanceof AlarmaGps)) {
            return false;
        }
        AlarmaGps other = (AlarmaGps) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.AlarmaGps[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<GestionAlarmaGps> getGestionAlarmasGpsCollection() {
        return gestionAlarmasGpsCollection;
    }

    public void setGestionAlarmasGpsCollection(Collection<GestionAlarmaGps> gestionAlarmasGpsCollection) {
        this.gestionAlarmasGpsCollection = gestionAlarmasGpsCollection;
    }

}
