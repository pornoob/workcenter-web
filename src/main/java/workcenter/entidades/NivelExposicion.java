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
@Table(name = "nivelesexposicion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NivelExposicion.findAll", query = "SELECT n FROM NivelExposicion n"),
    @NamedQuery(name = "NivelExposicion.findById", query = "SELECT n FROM NivelExposicion n WHERE n.id = :id"),
    @NamedQuery(name = "NivelExposicion.findByPeligro", query = "SELECT n FROM NivelExposicion n WHERE n.peligro = :peligro"),
    @NamedQuery(name = "NivelExposicion.findByIncidente", query = "SELECT n FROM NivelExposicion n WHERE n.incidente = :incidente"),
    @NamedQuery(name = "NivelExposicion.findByMr", query = "SELECT n FROM NivelExposicion n WHERE n.mr = :mr"),
    @NamedQuery(name = "NivelExposicion.findByNivel", query = "SELECT n FROM NivelExposicion n WHERE n.nivel = :nivel")})
public class NivelExposicion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "peligro")
    private String peligro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "incidente")
    private String incidente;
    @Column(name = "mr")
    private Integer mr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nivel")
    private int nivel;

    public NivelExposicion() {
    }

    public NivelExposicion(Integer id) {
        this.id = id;
    }

    public NivelExposicion(Integer id, String peligro, String incidente, int nivel) {
        this.id = id;
        this.peligro = peligro;
        this.incidente = incidente;
        this.nivel = nivel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPeligro() {
        return peligro;
    }

    public void setPeligro(String peligro) {
        this.peligro = peligro;
    }

    public String getIncidente() {
        return incidente;
    }

    public void setIncidente(String incidente) {
        this.incidente = incidente;
    }

    public Integer getMr() {
        return mr;
    }

    public void setMr(Integer mr) {
        this.mr = mr;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
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
        if (!(object instanceof NivelExposicion)) {
            return false;
        }
        NivelExposicion other = (NivelExposicion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.NivelExposicion[ id=" + id + " ]";
    }
    
}
