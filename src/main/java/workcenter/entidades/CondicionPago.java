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
@Table(name = "condicionesdepago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CondicionPago.findAll", query = "SELECT c FROM CondicionPago c"),
    @NamedQuery(name = "CondicionPago.findById", query = "SELECT c FROM CondicionPago c WHERE c.id = :id"),
    @NamedQuery(name = "CondicionPago.findByNombre", query = "SELECT c FROM CondicionPago c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CondicionPago.findByDescripcion", query = "SELECT c FROM CondicionPago c WHERE c.descripcion = :descripcion")})
public class CondicionPago implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;

    public CondicionPago() {
    }

    public CondicionPago(Integer id) {
        this.id = id;
    }

    public CondicionPago(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CondicionPago)) {
            return false;
        }
        CondicionPago other = (CondicionPago) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.CondicionPago[ id=" + id + " ]";
    }
    
}
