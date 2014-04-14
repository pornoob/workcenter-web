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
@Table(name = "subtipoequipos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubtipoEquipo.findAll", query = "SELECT s FROM SubtipoEquipo s"),
    @NamedQuery(name = "SubtipoEquipo.findById", query = "SELECT s FROM SubtipoEquipo s WHERE s.id = :id"),
    @NamedQuery(name = "SubtipoEquipo.findByTipo", query = "SELECT s FROM SubtipoEquipo s WHERE s.tipo = :tipo"),
    @NamedQuery(name = "SubtipoEquipo.findByEtiqueta", query = "SELECT s FROM SubtipoEquipo s WHERE s.etiqueta = :etiqueta"),
    @NamedQuery(name = "SubtipoEquipo.findByDescripcion", query = "SELECT s FROM SubtipoEquipo s WHERE s.descripcion = :descripcion")})
public class SubtipoEquipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo")
    private int tipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "etiqueta")
    private String etiqueta;
    @Size(max = 45)
    @Column(name = "descripcion")
    private String descripcion;

    public SubtipoEquipo() {
    }

    public SubtipoEquipo(Integer id) {
        this.id = id;
    }

    public SubtipoEquipo(Integer id, int tipo, String etiqueta) {
        this.id = id;
        this.tipo = tipo;
        this.etiqueta = etiqueta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
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
        if (!(object instanceof SubtipoEquipo)) {
            return false;
        }
        SubtipoEquipo other = (SubtipoEquipo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.SubtipoEquipo[ id=" + id + " ]";
    }
    
}
