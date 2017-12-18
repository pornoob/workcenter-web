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
@Table(name = "tiposequipos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEquipo.findAll", query = "SELECT t FROM TipoEquipo t"),
    @NamedQuery(name = "TipoEquipo.findById", query = "SELECT t FROM TipoEquipo t WHERE t.id = :id"),
    @NamedQuery(name = "TipoEquipo.findByEtiqueta", query = "SELECT t FROM TipoEquipo t WHERE t.etiqueta = :etiqueta"),
    @NamedQuery(name = "TipoEquipo.findByDescripcion", query = "SELECT t FROM TipoEquipo t WHERE t.descripcion = :descripcion")})
public class TipoEquipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "etiqueta")
    private String etiqueta;
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;

    public TipoEquipo() {
    }

    public TipoEquipo(Integer id) {
        this.id = id;
    }

    public TipoEquipo(Integer id, String etiqueta) {
        this.id = id;
        this.etiqueta = etiqueta;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TipoEquipo)) {
            return false;
        }
        TipoEquipo that = (TipoEquipo) object;
        if (this.getId() == null || that.getId() == null) return false;
        else if (this.getId().intValue() != that.getId().intValue()) return false;
        else return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.TipoEquipo[ id=" + id + " ]";
    }
    
}
