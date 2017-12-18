/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "gestionalertas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GestionAlerta.findAll", query = "SELECT g FROM GestionAlerta g"),
    @NamedQuery(name = "GestionAlerta.findById", query = "SELECT g FROM GestionAlerta g WHERE g.id = :id"),
    @NamedQuery(name = "GestionAlerta.findByPatente", query = "SELECT g FROM GestionAlerta g WHERE g.patente = :patente"),
    @NamedQuery(name = "GestionAlerta.findByFecha", query = "SELECT g FROM GestionAlerta g WHERE g.fecha = :fecha"),
    @NamedQuery(name = "GestionAlerta.findByTipo", query = "SELECT g FROM GestionAlerta g WHERE g.tipo = :tipo"),
    @NamedQuery(name = "GestionAlerta.findByRuta", query = "SELECT g FROM GestionAlerta g WHERE g.ruta = :ruta"),
    @NamedQuery(name = "GestionAlerta.findByUbicacion", query = "SELECT g FROM GestionAlerta g WHERE g.ubicacion = :ubicacion"),
    @NamedQuery(name = "GestionAlerta.findByVelocidad", query = "SELECT g FROM GestionAlerta g WHERE g.velocidad = :velocidad"),
    @NamedQuery(name = "GestionAlerta.findByGestion", query = "SELECT g FROM GestionAlerta g WHERE g.gestion = :gestion")})
public class GestionAlerta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "patente")
    private String patente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 100)
    @Column(name = "ruta")
    private String ruta;
    @Size(max = 150)
    @Column(name = "ubicacion")
    private String ubicacion;
    @Column(name = "velocidad")
    private Integer velocidad;
    @Size(max = 500)
    @Column(name = "gestion")
    private String gestion;

    public GestionAlerta() {
    }

    public GestionAlerta(Integer id) {
        this.id = id;
    }

    public GestionAlerta(Integer id, String patente, Date fecha, String tipo) {
        this.id = id;
        this.patente = patente;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public Integer getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Integer velocidad) {
        this.velocidad = velocidad;
    }

    public String getGestion() {
        return gestion;
    }

    public void setGestion(String gestion) {
        this.gestion = gestion;
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
        if (!(object instanceof GestionAlerta)) {
            return false;
        }
        GestionAlerta other = (GestionAlerta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.GestionAlerta[ id=" + id + " ]";
    }
    
}
