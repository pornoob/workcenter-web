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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author colivares
 */
@Entity
@Table(name = "mpa_ejecucion_plan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MpaEjecucionPlan.findAll", query = "SELECT m FROM MpaEjecucionPlan m"),
    @NamedQuery(name = "MpaEjecucionPlan.findById", query = "SELECT m FROM MpaEjecucionPlan m WHERE m.id = :id"),
    @NamedQuery(name = "MpaEjecucionPlan.findByIdPrograma", query = "SELECT m FROM MpaEjecucionPlan m WHERE m.idPrograma = :idPrograma"),
    @NamedQuery(name = "MpaEjecucionPlan.findByIdActividad", query = "SELECT m FROM MpaEjecucionPlan m WHERE m.idActividad = :idActividad"),
    @NamedQuery(name = "MpaEjecucionPlan.findByRutResponsable", query = "SELECT m FROM MpaEjecucionPlan m WHERE m.rutResponsable = :rutResponsable"),
    @NamedQuery(name = "MpaEjecucionPlan.findByIdMes", query = "SELECT m FROM MpaEjecucionPlan m WHERE m.idMes = :idMes")})
public class MpaEjecucionPlan implements Serializable {
    @JoinColumn(name = "rut_responsable", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private Personal rutResponsable;
    @JoinColumn(name = "id_actividad", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MpaActividad idActividad;
    @JoinColumn(name = "id_programa", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MpaPrograma idPrograma;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_mes")
    private int idMes;

    public MpaEjecucionPlan() {
    }

    public MpaEjecucionPlan(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdMes() {
        return idMes;
    }

    public void setIdMes(int idMes) {
        this.idMes = idMes;
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
        if (!(object instanceof MpaEjecucionPlan)) {
            return false;
        }
        MpaEjecucionPlan other = (MpaEjecucionPlan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.MpaEjecucionPlan[ id=" + id + " ]";
    }

    public Personal getRutResponsable() {
        return rutResponsable;
    }

    public void setRutResponsable(Personal rutResponsable) {
        this.rutResponsable = rutResponsable;
    }

    public MpaActividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(MpaActividad idActividad) {
        this.idActividad = idActividad;
    }

    public MpaPrograma getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(MpaPrograma idPrograma) {
        this.idPrograma = idPrograma;
    }

}
