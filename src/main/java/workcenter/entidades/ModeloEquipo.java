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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "modelosequipos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ModeloEquipo.findAll", query = "SELECT m FROM ModeloEquipo m"),
    @NamedQuery(name = "ModeloEquipo.findById", query = "SELECT m FROM ModeloEquipo m WHERE m.id = :id"),
    @NamedQuery(name = "ModeloEquipo.findByNombre", query = "SELECT m FROM ModeloEquipo m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "ModeloEquipo.findByDescripcion", query = "SELECT m FROM ModeloEquipo m WHERE m.descripcion = :descripcion")})
public class ModeloEquipo implements Serializable {
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

    public ModeloEquipo() {
    }

    public ModeloEquipo(Integer id) {
        this.id = id;
    }

    public ModeloEquipo(Integer id, String nombre) {
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
        if (!(object instanceof ModeloEquipo)) {
            return false;
        }
        ModeloEquipo other = (ModeloEquipo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.ModeloEquipo[ id=" + id + " ]";
    }
    
}
