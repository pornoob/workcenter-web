/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "estacionesdeservicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstacionServicio.findAll", query = "SELECT e FROM EstacionServicio e"),
    @NamedQuery(name = "EstacionServicio.findById", query = "SELECT e FROM EstacionServicio e WHERE e.id = :id"),
    @NamedQuery(name = "EstacionServicio.findByNombre", query = "SELECT e FROM EstacionServicio e WHERE e.nombre = :nombre")})
public class EstacionServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 70)
    @Column(name = "nombre")
    private String nombre;

    public EstacionServicio() {
    }

    public EstacionServicio(Integer id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstacionServicio)) {
            return false;
        }
        EstacionServicio other = (EstacionServicio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.EstacionServicio[ id=" + id + " ]";
    }
    
}
