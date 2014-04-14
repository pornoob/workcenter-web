/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "registrosformularios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistroFormulario.findAll", query = "SELECT r FROM RegistroFormulario r"),
    @NamedQuery(name = "RegistroFormulario.findById", query = "SELECT r FROM RegistroFormulario r WHERE r.id = :id"),
    @NamedQuery(name = "RegistroFormulario.findByFormulario", query = "SELECT r FROM RegistroFormulario r WHERE r.formulario = :formulario"),
    @NamedQuery(name = "RegistroFormulario.findByFechasubmit", query = "SELECT r FROM RegistroFormulario r WHERE r.fechasubmit = :fechasubmit"),
    @NamedQuery(name = "RegistroFormulario.findByUsuario", query = "SELECT r FROM RegistroFormulario r WHERE r.usuario = :usuario")})
public class RegistroFormulario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "formulario")
    private int formulario;
    @Column(name = "fechasubmit")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasubmit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usuario")
    private int usuario;

    public RegistroFormulario() {
    }

    public RegistroFormulario(Integer id) {
        this.id = id;
    }

    public RegistroFormulario(Integer id, int formulario, int usuario) {
        this.id = id;
        this.formulario = formulario;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFormulario() {
        return formulario;
    }

    public void setFormulario(int formulario) {
        this.formulario = formulario;
    }

    public Date getFechasubmit() {
        return fechasubmit;
    }

    public void setFechasubmit(Date fechasubmit) {
        this.fechasubmit = fechasubmit;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof RegistroFormulario)) {
            return false;
        }
        RegistroFormulario other = (RegistroFormulario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.RegistroFormulario[ id=" + id + " ]";
    }
    
}
