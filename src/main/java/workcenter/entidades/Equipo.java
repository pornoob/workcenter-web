/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "equipos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipo.findAll", query = "SELECT e FROM Equipo e"),
    @NamedQuery(name = "Equipo.findByPatente", query = "SELECT e FROM Equipo e WHERE e.patente = :patente"),
    @NamedQuery(name = "Equipo.findByDuenio", query = "SELECT e FROM Equipo e WHERE e.duenio = :duenio"),
    @NamedQuery(name = "Equipo.findByTipo", query = "SELECT e FROM Equipo e WHERE e.tipo = :tipo"),
    @NamedQuery(name = "Equipo.findBySubtipo", query = "SELECT e FROM Equipo e WHERE e.subtipo = :subtipo"),
    @NamedQuery(name = "Equipo.findByMarca", query = "SELECT e FROM Equipo e WHERE e.marca = :marca"),
    @NamedQuery(name = "Equipo.findByModelo", query = "SELECT e FROM Equipo e WHERE e.modelo = :modelo"),
    @NamedQuery(name = "Equipo.findByAnio", query = "SELECT e FROM Equipo e WHERE e.anio = :anio"),
    @NamedQuery(name = "Equipo.findByNumero", query = "SELECT e FROM Equipo e WHERE e.numero = :numero"),
    @NamedQuery(name = "Equipo.findByMotor", query = "SELECT e FROM Equipo e WHERE e.motor = :motor"),
    @NamedQuery(name = "Equipo.findByChasis", query = "SELECT e FROM Equipo e WHERE e.chasis = :chasis"),
    @NamedQuery(name = "Equipo.findByColor", query = "SELECT e FROM Equipo e WHERE e.color = :color"),
    @NamedQuery(name = "Equipo.findByKilometraje", query = "SELECT e FROM Equipo e WHERE e.kilometraje = :kilometraje"),
    @NamedQuery(name = "Equipo.findByBollo", query = "SELECT e FROM Equipo e WHERE e.bollo = :bollo")})
public class Equipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "patente")
    private String patente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "duenio")
    private int duenio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo")
    private int tipo;
    @Column(name = "subtipo")
    private Integer subtipo;
    @Column(name = "marca")
    private Integer marca;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modelo")
    private int modelo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio")
    private int anio;
    @Column(name = "numero")
    private Integer numero;
    @Size(max = 25)
    @Column(name = "motor")
    private String motor;
    @Size(max = 25)
    @Column(name = "chasis")
    private String chasis;
    @Size(max = 45)
    @Column(name = "color")
    private String color;
    @Column(name = "kilometraje")
    private Integer kilometraje;
    @Column(name = "bollo")
    private Boolean bollo;

    public Equipo() {
    }

    public Equipo(String patente) {
        this.patente = patente;
    }

    public Equipo(String patente, int duenio, int tipo, int modelo, int anio) {
        this.patente = patente;
        this.duenio = duenio;
        this.tipo = tipo;
        this.modelo = modelo;
        this.anio = anio;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public int getDuenio() {
        return duenio;
    }

    public void setDuenio(int duenio) {
        this.duenio = duenio;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Integer getSubtipo() {
        return subtipo;
    }

    public void setSubtipo(Integer subtipo) {
        this.subtipo = subtipo;
    }

    public Integer getMarca() {
        return marca;
    }

    public void setMarca(Integer marca) {
        this.marca = marca;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
    }

    public Boolean getBollo() {
        return bollo;
    }

    public void setBollo(Boolean bollo) {
        this.bollo = bollo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (patente != null ? patente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipo)) {
            return false;
        }
        Equipo other = (Equipo) object;
        if ((this.patente == null && other.patente != null) || (this.patente != null && !this.patente.equals(other.patente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Equipo[ patente=" + patente + " ]";
    }
    
}
