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

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "reembolso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reembolso.findAll", query = "SELECT r FROM Reembolso r"),
    @NamedQuery(name = "Reembolso.findById", query = "SELECT r FROM Reembolso r WHERE r.id = :id"),
    @NamedQuery(name = "Reembolso.findByPersona", query = "SELECT r FROM Reembolso r WHERE r.persona = :persona"),
    @NamedQuery(name = "Reembolso.findByNombre", query = "SELECT r FROM Reembolso r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "Reembolso.findByMonto", query = "SELECT r FROM Reembolso r WHERE r.monto = :monto"),
    @NamedQuery(name = "Reembolso.findByFecha", query = "SELECT r FROM Reembolso r WHERE r.fecha = :fecha"),
    @NamedQuery(name = "Reembolso.findByDescripcion", query = "SELECT r FROM Reembolso r WHERE r.descripcion = :descripcion"),
    @NamedQuery(name = "Reembolso.findByPagado", query = "SELECT r FROM Reembolso r WHERE r.pagado = :pagado"),
    @NamedQuery(name = "Reembolso.findByMotivo", query = "SELECT r FROM Reembolso r WHERE r.motivo = :motivo"),
    @NamedQuery(name = "Reembolso.findByImponible", query = "SELECT r FROM Reembolso r WHERE r.imponible = :imponible")})
public class Reembolso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "persona")
    private int persona;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto")
    private int monto;
    @Size(max = 45)
    @Column(name = "fecha")
    private String fecha;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "pagado")
    private Boolean pagado;
    @Column(name = "motivo")
    private Integer motivo;
    @Column(name = "imponible")
    private Boolean imponible;

    public Reembolso() {
    }

    public Reembolso(Integer id) {
        this.id = id;
    }

    public Reembolso(Integer id, int persona, int monto) {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getPagado() {
        return pagado;
    }

    public void setPagado(Boolean pagado) {
        this.pagado = pagado;
    }

    public Integer getMotivo() {
        return motivo;
    }

    public void setMotivo(Integer motivo) {
        this.motivo = motivo;
    }

    public Boolean getImponible() {
        return imponible;
    }

    public void setImponible(Boolean imponible) {
        this.imponible = imponible;
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
        if (!(object instanceof Reembolso)) {
            return false;
        }
        Reembolso other = (Reembolso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Reembolso[ id=" + id + " ]";
    }
    
}
