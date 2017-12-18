/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "mantencionesequipos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MantencionEquipo.findAll", query = "SELECT m FROM MantencionEquipo m"),
    @NamedQuery(name = "MantencionEquipo.findById", query = "SELECT m FROM MantencionEquipo m WHERE m.id = :id"),
    @NamedQuery(name = "MantencionEquipo.findByHh", query = "SELECT m FROM MantencionEquipo m WHERE m.hh = :hh"),
    @NamedQuery(name = "MantencionEquipo.findByFechacomienzo", query = "SELECT m FROM MantencionEquipo m WHERE m.fechacomienzo = :fechacomienzo"),
    @NamedQuery(name = "MantencionEquipo.findByFechafinal", query = "SELECT m FROM MantencionEquipo m WHERE m.fechafinal = :fechafinal")})
public class MantencionEquipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "hh")
    private Integer hh;
    @Column(name = "fechacomienzo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacomienzo;
    @Column(name = "fechafinal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;

    public MantencionEquipo() {
    }

    public MantencionEquipo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHh() {
        return hh;
    }

    public void setHh(Integer hh) {
        this.hh = hh;
    }

    public Date getFechacomienzo() {
        return fechacomienzo;
    }

    public void setFechacomienzo(Date fechacomienzo) {
        this.fechacomienzo = fechacomienzo;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
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
        if (!(object instanceof MantencionEquipo)) {
            return false;
        }
        MantencionEquipo other = (MantencionEquipo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.MantencionEquipo[ id=" + id + " ]";
    }
    
}
