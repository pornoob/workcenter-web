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
@Table(name = "camposlibretasconductores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CampoLibretaConductor.findAll", query = "SELECT c FROM CampoLibretaConductor c"),
    @NamedQuery(name = "CampoLibretaConductor.findById", query = "SELECT c FROM CampoLibretaConductor c WHERE c.id = :id"),
    @NamedQuery(name = "CampoLibretaConductor.findByFecha", query = "SELECT c FROM CampoLibretaConductor c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "CampoLibretaConductor.findByLibreta", query = "SELECT c FROM CampoLibretaConductor c WHERE c.libreta = :libreta"),
    @NamedQuery(name = "CampoLibretaConductor.findByMconduccion", query = "SELECT c FROM CampoLibretaConductor c WHERE c.mconduccion = :mconduccion"),
    @NamedQuery(name = "CampoLibretaConductor.findByMdescanso", query = "SELECT c FROM CampoLibretaConductor c WHERE c.mdescanso = :mdescanso"),
    @NamedQuery(name = "CampoLibretaConductor.findByMespera", query = "SELECT c FROM CampoLibretaConductor c WHERE c.mespera = :mespera")})
public class CampoLibretaConductor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "libreta")
    private int libreta;
    @Column(name = "mconduccion")
    private Integer mconduccion;
    @Column(name = "mdescanso")
    private Integer mdescanso;
    @Column(name = "mespera")
    private Integer mespera;

    public CampoLibretaConductor() {
    }

    public CampoLibretaConductor(Integer id) {
        this.id = id;
    }

    public CampoLibretaConductor(Integer id, int libreta) {
        this.id = id;
        this.libreta = libreta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getLibreta() {
        return libreta;
    }

    public void setLibreta(int libreta) {
        this.libreta = libreta;
    }

    public Integer getMconduccion() {
        return mconduccion;
    }

    public void setMconduccion(Integer mconduccion) {
        this.mconduccion = mconduccion;
    }

    public Integer getMdescanso() {
        return mdescanso;
    }

    public void setMdescanso(Integer mdescanso) {
        this.mdescanso = mdescanso;
    }

    public Integer getMespera() {
        return mespera;
    }

    public void setMespera(Integer mespera) {
        this.mespera = mespera;
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
        if (!(object instanceof CampoLibretaConductor)) {
            return false;
        }
        CampoLibretaConductor other = (CampoLibretaConductor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.CampoLibretaConductor[ id=" + id + " ]";
    }
    
}
