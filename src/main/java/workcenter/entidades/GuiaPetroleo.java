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
    @Basic(optional = false)
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
    @Column(name = "ordendecarga")
    private Integer ordendecarga;
    @Column(name = "estaciondeservicio")
    private Integer estaciondeservicio;

    public GuiaPetroleo() {
    }

    public GuiaPetroleo(Integer id) {
        this.id = id;
    }

    public GuiaPetroleo(Integer id, int numeroguia, int conductor, String equipo) {
        this.id = id;
        this.numeroguia = numeroguia;
        this.conductor = conductor;
        this.equipo = equipo;
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

    public Integer getOrdendecarga() {
        return ordendecarga;
    }

    public void setOrdendecarga(Integer ordendecarga) {
        this.ordendecarga = ordendecarga;
    }

    public Integer getEstaciondeservicio() {
        return estaciondeservicio;
    }

    public void setEstaciondeservicio(Integer estaciondeservicio) {
        this.estaciondeservicio = estaciondeservicio;
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
        if (!(object instanceof GuiaPetroleo)) {
            return false;
        }
        GuiaPetroleo other = (GuiaPetroleo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.GuiaPetroleo[ id=" + id + " ]";
    }
    
}
