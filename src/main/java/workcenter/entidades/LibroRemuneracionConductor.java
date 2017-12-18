/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "libroremuneracionesconductores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LibroRemuneracionConductor.findAll", query = "SELECT l FROM LibroRemuneracionConductor l"),
    @NamedQuery(name = "LibroRemuneracionConductor.findById", query = "SELECT l FROM LibroRemuneracionConductor l WHERE l.id = :id"),
    @NamedQuery(name = "LibroRemuneracionConductor.findByConductor", query = "SELECT l FROM LibroRemuneracionConductor l WHERE l.conductor = :conductor"),
    @NamedQuery(name = "LibroRemuneracionConductor.findByMes", query = "SELECT l FROM LibroRemuneracionConductor l WHERE l.mes = :mes")})
public class LibroRemuneracionConductor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "conductor")
    private int conductor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mes")
    @Temporal(TemporalType.DATE)
    private Date mes;

    public LibroRemuneracionConductor() {
    }

    public LibroRemuneracionConductor(Integer id) {
        this.id = id;
    }

    public LibroRemuneracionConductor(Integer id, int conductor, Date mes) {
        this.id = id;
        this.conductor = conductor;
        this.mes = mes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getConductor() {
        return conductor;
    }

    public void setConductor(int conductor) {
        this.conductor = conductor;
    }

    public Date getMes() {
        return mes;
    }

    public void setMes(Date mes) {
        this.mes = mes;
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
        if (!(object instanceof LibroRemuneracionConductor)) {
            return false;
        }
        LibroRemuneracionConductor other = (LibroRemuneracionConductor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.LibroRemuneracionConductor[ id=" + id + " ]";
    }
    
}
