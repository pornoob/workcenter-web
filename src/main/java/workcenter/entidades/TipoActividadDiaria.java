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
 * @author colivares
 */
@Entity
@Table(name = "tipo_actividad_diaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoActividadDiaria.findAll", query = "SELECT t FROM TipoActividadDiaria t"),
    @NamedQuery(name = "TipoActividadDiaria.findById", query = "SELECT t FROM TipoActividadDiaria t WHERE t.id = :id"),
    @NamedQuery(name = "TipoActividadDiaria.findByNombre", query = "SELECT t FROM TipoActividadDiaria t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoActividadDiaria.findByIcono", query = "SELECT t FROM TipoActividadDiaria t WHERE t.icono = :icono")})
public class TipoActividadDiaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "icono")
    private String icono;

    public TipoActividadDiaria() {
    }

    public TipoActividadDiaria(Integer id) {
        this.id = id;
    }

    public TipoActividadDiaria(Integer id, String nombre, String icono) {
        this.id = id;
        this.nombre = nombre;
        this.icono = icono;
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

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
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
        if (!(object instanceof TipoActividadDiaria)) {
            return false;
        }
        TipoActividadDiaria other = (TipoActividadDiaria) object;
        if (this.getId() == null || other.getId() == null) {
            return false;
        } else if (this.getId().intValue() != other.getId().intValue()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.TipoActividadDiaria[ id=" + id + " ]";
    }

}
