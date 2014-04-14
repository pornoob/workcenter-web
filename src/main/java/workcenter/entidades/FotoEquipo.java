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
@Table(name = "fotosequipos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FotoEquipo.findAll", query = "SELECT f FROM FotoEquipo f"),
    @NamedQuery(name = "FotoEquipo.findById", query = "SELECT f FROM FotoEquipo f WHERE f.id = :id"),
    @NamedQuery(name = "FotoEquipo.findByEquipo", query = "SELECT f FROM FotoEquipo f WHERE f.equipo = :equipo"),
    @NamedQuery(name = "FotoEquipo.findByFoto", query = "SELECT f FROM FotoEquipo f WHERE f.foto = :foto"),
    @NamedQuery(name = "FotoEquipo.findByEtiqueta", query = "SELECT f FROM FotoEquipo f WHERE f.etiqueta = :etiqueta")})
public class FotoEquipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "equipo")
    private String equipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "foto")
    private String foto;
    @Size(max = 45)
    @Column(name = "etiqueta")
    private String etiqueta;

    public FotoEquipo() {
    }

    public FotoEquipo(Integer id) {
        this.id = id;
    }

    public FotoEquipo(Integer id, String equipo, String foto) {
        this.id = id;
        this.equipo = equipo;
        this.foto = foto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
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
        if (!(object instanceof FotoEquipo)) {
            return false;
        }
        FotoEquipo other = (FotoEquipo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.FotoEquipo[ id=" + id + " ]";
    }

}
