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
@Table(name = "tiposunidades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoUnidad.findAll", query = "SELECT t FROM TipoUnidad t"),
    @NamedQuery(name = "TipoUnidad.findById", query = "SELECT t FROM TipoUnidad t WHERE t.id = :id"),
    @NamedQuery(name = "TipoUnidad.findByCaracter", query = "SELECT t FROM TipoUnidad t WHERE t.caracter = :caracter"),
    @NamedQuery(name = "TipoUnidad.findByEtiqueta", query = "SELECT t FROM TipoUnidad t WHERE t.etiqueta = :etiqueta")})
public class TipoUnidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 5)
    @Column(name = "caracter")
    private String caracter;
    @Size(max = 15)
    @Column(name = "etiqueta")
    private String etiqueta;

    public TipoUnidad() {
    }

    public TipoUnidad(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaracter() {
        return caracter;
    }

    public void setCaracter(String caracter) {
        this.caracter = caracter;
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
        if (!(object instanceof TipoUnidad)) {
            return false;
        }
        TipoUnidad other = (TipoUnidad) object;
        if (this.getId() == null || other.getId() == null) {
            return false;
        } else if (this.getId().intValue() != other.getId().intValue()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.TipoUnidad[ id=" + id + " ]";
    }

}
