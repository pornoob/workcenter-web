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
@Table(name = "extrasimponibles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExtraImponible.findAll", query = "SELECT e FROM ExtraImponible e"),
    @NamedQuery(name = "ExtraImponible.findById", query = "SELECT e FROM ExtraImponible e WHERE e.id = :id"),
    @NamedQuery(name = "ExtraImponible.findByEtiqueta", query = "SELECT e FROM ExtraImponible e WHERE e.etiqueta = :etiqueta"),
    @NamedQuery(name = "ExtraImponible.findByPersona", query = "SELECT e FROM ExtraImponible e WHERE e.persona = :persona"),
    @NamedQuery(name = "ExtraImponible.findByMonto", query = "SELECT e FROM ExtraImponible e WHERE e.monto = :monto"),
    @NamedQuery(name = "ExtraImponible.findByFecha", query = "SELECT e FROM ExtraImponible e WHERE e.fecha = :fecha"),
    @NamedQuery(name = "ExtraImponible.findByDescripcion", query = "SELECT e FROM ExtraImponible e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "ExtraImponible.findByPagado", query = "SELECT e FROM ExtraImponible e WHERE e.pagado = :pagado")})
public class ExtraImponible implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "etiqueta")
    private String etiqueta;
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
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "pagado")
    private Boolean pagado;

    public ExtraImponible() {
    }

    public ExtraImponible(Integer id) {
        this.id = id;
    }

    public ExtraImponible(Integer id, String etiqueta, int persona, int monto) {
        this.id = id;
        this.etiqueta = etiqueta;
        this.persona = persona;
        this.monto = monto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExtraImponible)) {
            return false;
        }
        ExtraImponible other = (ExtraImponible) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.ExtraImponible[ id=" + id + " ]";
    }
    
}
