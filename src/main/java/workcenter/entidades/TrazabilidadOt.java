/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "trazabilidad_ot")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrazabilidadOt.findAll", query = "SELECT t FROM TrazabilidadOt t")})
public class TrazabilidadOt implements Serializable, Comparable<TrazabilidadOt> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "estado_id")
    private Integer estadoId;
    @Column(name = "autor")
    private Integer autor;
    @Column(name = "ejecutor")
    private Integer ejecutor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ot_id", referencedColumnName = "id")
    private OrdenTrabajo ot;

    public TrazabilidadOt() {
    }

    public TrazabilidadOt(Integer id) {
        this.id = id;
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

    public Integer getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }

    public Integer getAutor() {
        return autor;
    }

    public void setAutor(Integer autor) {
        this.autor = autor;
    }

    public OrdenTrabajo getOtId() {
        return ot;
    }

    public void setOtId(OrdenTrabajo otId) {
        this.ot = otId;
    }

    public Integer getEjecutor() {
        return ejecutor;
    }

    public void setEjecutor(Integer ejecutor) {
        this.ejecutor = ejecutor;
    }

    public OrdenTrabajo getOt() {
        return ot;
    }

    public void setOt(OrdenTrabajo ot) {
        this.ot = ot;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TrazabilidadOt other = (TrazabilidadOt) obj;
        if (this.id == null || other.id == null) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int compareTo(TrazabilidadOt o) {
        return o.fecha.compareTo(this.fecha);
    }

    @Override
    public String toString() {
        return "workcenter.entidades.TrazabilidadOt[ id=" + id + " ]";
    }
    
}
