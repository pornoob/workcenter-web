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
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author colivares
 */
@Entity
@Table(name = "mpa_actividades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MpaActividad.findAll", query = "SELECT m FROM MpaActividad m"),
    @NamedQuery(name = "MpaActividad.findById", query = "SELECT m FROM MpaActividad m WHERE m.id = :id"),
    @NamedQuery(name = "MpaActividad.findByNombre", query = "SELECT m FROM MpaActividad m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "MpaActividad.findByPrograma", query = "SELECT m FROM MpaActividad m WHERE m.idPrograma = :programa")
})
public class MpaActividad implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idActividad")
    private Collection<MpaEjecucionPlan> mpaEjecucionPlanCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idActividad")
    private Collection<MpaPlanPrograma> mpaPlanProgramaCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "id_programa", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)    private MpaPrograma idPrograma;

    public MpaActividad() {
    }

    public MpaActividad(Integer id) {
        this.id = id;
    }

    public MpaActividad(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public MpaPrograma getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(MpaPrograma idPrograma) {
        this.idPrograma = idPrograma;
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
        if (!(object instanceof MpaActividad)) {
            return false;
        }
        MpaActividad other = (MpaActividad) object;
        if (this.getId() == null || other.getId() == null) return false;
        else if (this.getId().intValue() != other.getId().intValue()) return false;
        else return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.MpaActividades[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<MpaPlanPrograma> getMpaPlanProgramaCollection() {
        return mpaPlanProgramaCollection;
    }

    public void setMpaPlanProgramaCollection(Collection<MpaPlanPrograma> mpaPlanProgramaCollection) {
        this.mpaPlanProgramaCollection = mpaPlanProgramaCollection;
    }

    @XmlTransient
    public Collection<MpaEjecucionPlan> getMpaEjecucionPlanCollection() {
        return mpaEjecucionPlanCollection;
    }

    public void setMpaEjecucionPlanCollection(Collection<MpaEjecucionPlan> mpaEjecucionPlanCollection) {
        this.mpaEjecucionPlanCollection = mpaEjecucionPlanCollection;
    }

}
