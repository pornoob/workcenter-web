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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "relacionesclasificacionesdocumentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelacionClasificacionDocumento.findAll", query = "SELECT r FROM RelacionClasificacionDocumento r"),
    @NamedQuery(name = "RelacionClasificacionDocumento.findById", query = "SELECT r FROM RelacionClasificacionDocumento r WHERE r.id = :id"),
    @NamedQuery(name = "RelacionClasificacionDocumento.findByPadre", query = "SELECT r FROM RelacionClasificacionDocumento r WHERE r.padre = :padre"),
    @NamedQuery(name = "RelacionClasificacionDocumento.findByHijo", query = "SELECT r FROM RelacionClasificacionDocumento r WHERE r.hijo = :hijo")})
public class RelacionClasificacionDocumento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "padre")
    private int padre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hijo")
    private int hijo;

    public RelacionClasificacionDocumento() {
    }

    public RelacionClasificacionDocumento(Integer id) {
        this.id = id;
    }

    public RelacionClasificacionDocumento(Integer id, int padre, int hijo) {
        this.id = id;
        this.padre = padre;
        this.hijo = hijo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPadre() {
        return padre;
    }

    public void setPadre(int padre) {
        this.padre = padre;
    }

    public int getHijo() {
        return hijo;
    }

    public void setHijo(int hijo) {
        this.hijo = hijo;
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
        if (!(object instanceof RelacionClasificacionDocumento)) {
            return false;
        }
        RelacionClasificacionDocumento other = (RelacionClasificacionDocumento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.RelacionClasificacionDocumento[ id=" + id + " ]";
    }
    
}
