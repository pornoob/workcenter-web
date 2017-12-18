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
import java.util.Objects;

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
    private Long autor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ejecutor", referencedColumnName = "rut")
    private Personal ejecutor;
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

    public Long getAutor() {
        return autor;
    }

    public void setAutor(Long autor) {
        this.autor = autor;
    }

    public OrdenTrabajo getOtId() {
        return ot;
    }

    public void setOtId(OrdenTrabajo otId) {
        this.ot = otId;
    }

    public Personal getEjecutor() {
        return ejecutor;
    }

    public void setEjecutor(Personal ejecutor) {
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
        if (this.fecha == null || o.fecha == null)
            return -1;
        else if (this.fecha == null)
            return 1;
        else if (o.fecha == null)
            return -1;
        else
            return o.fecha.compareTo(this.fecha);
    }

    @Override
    public String toString() {
        return "workcenter.entidades.TrazabilidadOt[ id=" + id + " ]";
    }
    
}
