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
@Table(name = "camposremuneraciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CampoRemuneracion.findAll", query = "SELECT c FROM CampoRemuneracion c"),
    @NamedQuery(name = "CampoRemuneracion.findById", query = "SELECT c FROM CampoRemuneracion c WHERE c.id = :id"),
    @NamedQuery(name = "CampoRemuneracion.findByRemuneracion", query = "SELECT c FROM CampoRemuneracion c WHERE c.remuneracion = :remuneracion"),
    @NamedQuery(name = "CampoRemuneracion.findByValue", query = "SELECT c FROM CampoRemuneracion c WHERE c.value = :value"),
    @NamedQuery(name = "CampoRemuneracion.findByNombre", query = "SELECT c FROM CampoRemuneracion c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CampoRemuneracion.findByDetalle", query = "SELECT c FROM CampoRemuneracion c WHERE c.detalle = :detalle")})
public class CampoRemuneracion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "remuneracion")
    private int remuneracion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "value")
    private String value;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 300)
    @Column(name = "detalle")
    private String detalle;

    public CampoRemuneracion() {
    }

    public CampoRemuneracion(Integer id) {
        this.id = id;
    }

    public CampoRemuneracion(Integer id, int remuneracion, String value, String nombre) {
        this.id = id;
        this.remuneracion = remuneracion;
        this.value = value;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRemuneracion() {
        return remuneracion;
    }

    public void setRemuneracion(int remuneracion) {
        this.remuneracion = remuneracion;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
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
        if (!(object instanceof CampoRemuneracion)) {
            return false;
        }
        CampoRemuneracion other = (CampoRemuneracion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.CampoRemuneracion[ id=" + id + " ]";
    }
    
}
