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
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "guiasdepetroleo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GuiaPetroleo.findAll", query = "SELECT g FROM GuiaPetroleo g"),
    @NamedQuery(name = "GuiaPetroleo.findById", query = "SELECT g FROM GuiaPetroleo g WHERE g.id = :id"),
    @NamedQuery(name = "GuiaPetroleo.findByNumeroguia", query = "SELECT g FROM GuiaPetroleo g WHERE g.numeroguia = :numeroguia"),
    @NamedQuery(name = "GuiaPetroleo.findByConductor", query = "SELECT g FROM GuiaPetroleo g WHERE g.conductor = :conductor"),
    @NamedQuery(name = "GuiaPetroleo.findByEquipo", query = "SELECT g FROM GuiaPetroleo g WHERE g.equipo = :equipo"),
    @NamedQuery(name = "GuiaPetroleo.findByLitros", query = "SELECT g FROM GuiaPetroleo g WHERE g.litros = :litros"),
    @NamedQuery(name = "GuiaPetroleo.findByPreciolitro", query = "SELECT g FROM GuiaPetroleo g WHERE g.preciolitro = :preciolitro"),
    @NamedQuery(name = "GuiaPetroleo.findByFecha", query = "SELECT g FROM GuiaPetroleo g WHERE g.fecha = :fecha"),
    @NamedQuery(name = "GuiaPetroleo.findByOrdendecarga", query = "SELECT g FROM GuiaPetroleo g WHERE g.ordendecarga = :ordendecarga"),
    @NamedQuery(name = "GuiaPetroleo.findByEstaciondeservicio", query = "SELECT g FROM GuiaPetroleo g WHERE g.estaciondeservicio = :estaciondeservicio")})
public class GuiaPetroleo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numeroguia")
    private Integer numeroguia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "conductor")
    private int conductor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "equipo")
    private String equipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "litros")
    private Float litros;
    @Column(name = "preciolitro")
    private Integer preciolitro;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordendecarga", referencedColumnName = "ordendecarga")
    private Vuelta ordendecarga;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="estaciondeservicio", referencedColumnName = "id")
    private EstacionServicio estaciondeservicio;

    @Transient
    private String rowKey;

    public GuiaPetroleo() {
        rowKey = "RK" + UUID.randomUUID();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumeroguia() {
        return numeroguia;
    }

    public void setNumeroguia(Integer numeroguia) {
        this.numeroguia = numeroguia;
    }

    public int getConductor() {
        return conductor;
    }

    public void setConductor(int conductor) {
        this.conductor = conductor;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public Float getLitros() {
        return litros;
    }

    public void setLitros(Float litros) {
        this.litros = litros;
    }

    public Integer getPreciolitro() {
        return preciolitro;
    }

    public void setPreciolitro(Integer preciolitro) {
        this.preciolitro = preciolitro;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Vuelta getOrdendecarga() {
        return ordendecarga;
    }

    public void setOrdendecarga(Vuelta ordendecarga) {
        this.ordendecarga = ordendecarga;
    }

    public EstacionServicio getEstaciondeservicio() {
        return estaciondeservicio;
    }

    public void setEstaciondeservicio(EstacionServicio estaciondeservicio) {
        this.estaciondeservicio = estaciondeservicio;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    @Override
    public int hashCode() {
        int hash = 143030;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuiaPetroleo that = (GuiaPetroleo) o;
        if (id == null || that.id == null) return Objects.equals(rowKey, that.rowKey);
        return Objects.equals(id, that.getId());
    }

    @Override
    public String toString() {
        return "workcenter.entities.GuiaPetroleo[ id=" + id + " ]";
    }
    
}
