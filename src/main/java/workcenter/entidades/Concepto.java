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
import java.util.Collection;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "conceptos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Concepto.findAll", query = "SELECT c FROM Concepto c ORDER BY c.etiqueta"),
    @NamedQuery(name = "Concepto.findById", query = "SELECT c FROM Concepto c WHERE c.id = :id"),
    @NamedQuery(name = "Concepto.findByEtiqueta", query = "SELECT c FROM Concepto c WHERE c.etiqueta = :etiqueta"),
    @NamedQuery(name = "Concepto.findByDescripcion", query = "SELECT c FROM Concepto c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Concepto.findBySalida", query = "SELECT c FROM Concepto c WHERE c.salida = :salida")})
public class Concepto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "etiqueta")
    private String etiqueta;
    @Size(max = 300)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "salida")
    private boolean salida;
    @OneToMany(mappedBy = "concepto")
    private Collection<Dinero> dinerosCollection;

    public Concepto() {
    }

    public Concepto(Integer id) {
        this.id = id;
    }

    public Concepto(Integer id, String etiqueta, boolean salida) {
        this.id = id;
        this.etiqueta = etiqueta;
        this.salida = salida;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getSalida() {
        return salida;
    }

    public void setSalida(boolean salida) {
        this.salida = salida;
    }

    public Collection<Dinero> getDinerosCollection() {
        return dinerosCollection;
    }

    public void setDinerosCollection(Collection<Dinero> dinerosCollection) {
        this.dinerosCollection = dinerosCollection;
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
        if (!(object instanceof Concepto)) {
            return false;
        }
        Concepto other = (Concepto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Concepto[ id=" + id + " ]";
    }
    
}
