/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "descuentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Descuento.findAll", query = "SELECT d FROM Descuento d"),
    @NamedQuery(name = "Descuento.findById", query = "SELECT d FROM Descuento d WHERE d.id = :id"),
    @NamedQuery(name = "Descuento.findByPersona", query = "SELECT d FROM Descuento d WHERE d.persona = :persona"),
    @NamedQuery(name = "Descuento.findByMonto", query = "SELECT d FROM Descuento d WHERE d.monto = :monto"),
    @NamedQuery(name = "Descuento.findByFecha", query = "SELECT d FROM Descuento d WHERE d.fecha = :fecha"),
    @NamedQuery(name = "Descuento.findByNombre", query = "SELECT d FROM Descuento d WHERE d.nombre = :nombre"),
    @NamedQuery(name = "Descuento.findByDescripcion", query = "SELECT d FROM Descuento d WHERE d.descripcion = :descripcion"),
    @NamedQuery(name = "Descuento.findByMotivo", query = "SELECT d FROM Descuento d WHERE d.motivo = :motivo")})
public class Descuento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "persona")
    private int persona;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto")
    private int monto;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motivo", referencedColumnName = "id")
    private Dinero motivo;

    public Descuento() {
    }

    public Descuento(Integer id) {
        this.id = id;
    }

    public Descuento(Integer id, int persona, int monto) {
        this.id = id;
        this.persona = persona;
        this.monto = monto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPersona() {
        return persona;
    }

    public void setPersona(int persona) {
        this.persona = persona;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Dinero getMotivo() {
        return motivo;
    }

    public void setMotivo(Dinero motivo) {
        this.motivo = motivo;
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
        if (!(object instanceof Descuento)) {
            return false;
        }
        Descuento other = (Descuento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Descuento[ id=" + id + " ]";
    }
    
}
