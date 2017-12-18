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
@Table(name = "clasificacionesdocumentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClasificacionDocumento.findAll", query = "SELECT c FROM ClasificacionDocumento c"),
    @NamedQuery(name = "ClasificacionDocumento.findById", query = "SELECT c FROM ClasificacionDocumento c WHERE c.id = :id"),
    @NamedQuery(name = "ClasificacionDocumento.findByNombre", query = "SELECT c FROM ClasificacionDocumento c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ClasificacionDocumento.findByDescripcion", query = "SELECT c FROM ClasificacionDocumento c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "ClasificacionDocumento.findByNombrecorto", query = "SELECT c FROM ClasificacionDocumento c WHERE c.nombrecorto = :nombrecorto")})
public class ClasificacionDocumento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 15)
    @Column(name = "nombrecorto")
    private String nombrecorto;

    public ClasificacionDocumento() {
    }

    public ClasificacionDocumento(Integer id) {
        this.id = id;
    }

    public ClasificacionDocumento(Integer id, String nombre) {
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

    public String getNombrecorto() {
        return nombrecorto;
    }

    public void setNombrecorto(String nombrecorto) {
        this.nombrecorto = nombrecorto;
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
        if (!(object instanceof ClasificacionDocumento)) {
            return false;
        }
        ClasificacionDocumento other = (ClasificacionDocumento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.ClasificacionDocumento[ id=" + id + " ]";
    }
    
}
