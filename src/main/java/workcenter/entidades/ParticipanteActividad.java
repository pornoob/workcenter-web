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
@Table(name = "participantesactividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParticipanteActividad.findAll", query = "SELECT p FROM ParticipanteActividad p"),
    @NamedQuery(name = "ParticipanteActividad.findById", query = "SELECT p FROM ParticipanteActividad p WHERE p.id = :id"),
    @NamedQuery(name = "ParticipanteActividad.findByActividad", query = "SELECT p FROM ParticipanteActividad p WHERE p.actividad = :actividad"),
    @NamedQuery(name = "ParticipanteActividad.findByRut", query = "SELECT p FROM ParticipanteActividad p WHERE p.rut = :rut")})
public class ParticipanteActividad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actividad")
    private int actividad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rut")
    private int rut;

    public ParticipanteActividad() {
    }

    public ParticipanteActividad(Integer id) {
        this.id = id;
    }

    public ParticipanteActividad(Integer id, int actividad, int rut) {
        this.id = id;
        this.actividad = actividad;
        this.rut = rut;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getActividad() {
        return actividad;
    }

    public void setActividad(int actividad) {
        this.actividad = actividad;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
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
        if (!(object instanceof ParticipanteActividad)) {
            return false;
        }
        ParticipanteActividad other = (ParticipanteActividad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.ParticipanteActividad[ id=" + id + " ]";
    }
    
}
