/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;
import java.util.SortedSet;

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
        @NamedQuery(name = "Equipo.findByTipo", query = "SELECT DISTINCT e FROM Equipo e WHERE e.tipo = :tipo"),
        @NamedQuery(name = "Equipo.findBySubtipo", query = "SELECT e FROM Equipo e WHERE e.subtipo = :subtipo"),
        @NamedQuery(name = "Equipo.findByMarca", query = "SELECT e FROM Equipo e WHERE e.marca = :marca"),
        @NamedQuery(name = "Equipo.findByModelo", query = "SELECT e FROM Equipo e WHERE e.modelo = :modelo"),
        @NamedQuery(name = "Equipo.findByAnio", query = "SELECT e FROM Equipo e WHERE e.anio = :anio"),
        @NamedQuery(name = "Equipo.findByNumero", query = "SELECT e FROM Equipo e WHERE e.numero = :numero"),
        @NamedQuery(name = "Equipo.findByMotor", query = "SELECT e FROM Equipo e WHERE e.motor = :motor"),
        @NamedQuery(name = "Equipo.findByChasis", query = "SELECT e FROM Equipo e WHERE e.chasis = :chasis"),
        @NamedQuery(name = "Equipo.findByColor", query = "SELECT e FROM Equipo e WHERE e.color = :color"),
        @NamedQuery(name = "Equipo.findByKilometraje", query = "SELECT e FROM Equipo e WHERE e.kilometraje = :kilometraje"),
        @NamedQuery(name = "Equipo.findByBollo", query = "SELECT e FROM Equipo e WHERE e.bollo = :bollo"),
        @NamedQuery(name = "Equipo.findByNumeroTipo", query = "SELECT e FROM Equipo e WHERE e.numero = :numero and e.tipo = :tipo")
})
public class Equipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "patente")
    private String patente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "duenio", referencedColumnName = "id")
    private Empresa duenio;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo", referencedColumnName = "id")
    private TipoEquipo tipo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subtipo", referencedColumnName = "id")
    private SubtipoEquipo subtipo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca", referencedColumnName = "id")
    private MarcaEquipo marca;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modelo", referencedColumnName = "id")
    private ModeloEquipo modelo;
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
    @OneToMany(
            mappedBy = "equipo",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.REMOVE , CascadeType.DETACH, CascadeType.REFRESH
            }
    )
    private List<FotoEquipo> fotos;
    @OneToMany(
            mappedBy = "tracto",
            fetch = FetchType.LAZY
    )
    @SortNatural
    private SortedSet<MmeMantencionTracto> mantenimientos;
    @OneToMany(
            mappedBy = "maquina",
            fetch = FetchType.LAZY
    )
    @SortNatural
    private SortedSet<MmeMantencionMaquina> mantenimientosMaquina;

    public Equipo() {
    }

    public Equipo(String patente) {
        this.patente = patente != null ? patente.toUpperCase() : null;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente != null ? patente.toUpperCase() : null;
    }

    public Empresa getDuenio() {
        return duenio;
    }

    public void setDuenio(Empresa duenio) {
        this.duenio = duenio;
    }

    public TipoEquipo getTipo() {
        return tipo;
    }

    public void setTipo(TipoEquipo tipo) {
        this.tipo = tipo;
    }

    public SubtipoEquipo getSubtipo() {
        return subtipo;
    }

    public void setSubtipo(SubtipoEquipo subtipo) {
        this.subtipo = subtipo;
    }

    public MarcaEquipo getMarca() {
        return marca;
    }

    public void setMarca(MarcaEquipo marca) {
        this.marca = marca;
    }

    public ModeloEquipo getModelo() {
        return modelo;
    }

    public void setModelo(ModeloEquipo modelo) {
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
        this.color = color != null ? color.toLowerCase() : color;
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
        if (!(object instanceof Equipo)) {
            return false;
        }
        Equipo other = (Equipo) object;
        if (this.getPatente() == null || other.getPatente() == null) return false;
        else if (!this.getPatente().equals(other.getPatente())) return false;
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Equipo[ patente=" + patente + " ]";
    }

    public List<FotoEquipo> getFotos() {
        return fotos;
    }

    public void setFotos(List<FotoEquipo> fotos) {
        this.fotos = fotos;
    }

    public SortedSet<MmeMantencionTracto> getMantenimientos() {
        return mantenimientos;
    }

    public void setMantenimientos(SortedSet<MmeMantencionTracto> mantenimientos) {
        this.mantenimientos = mantenimientos;
    }

    public SortedSet<MmeMantencionMaquina> getMantenimientosMaquina() {
        return mantenimientosMaquina;
    }

    public void setMantenimientosMaquina(SortedSet<MmeMantencionMaquina> mantenimientosMaquina) {
        this.mantenimientosMaquina = mantenimientosMaquina;
    }
}

