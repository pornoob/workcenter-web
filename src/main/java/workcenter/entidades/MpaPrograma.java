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
 * @author colivares
 */
@Entity
@Table(name = "mpa_programas")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "MpaPrograma.findAll", query = "SELECT m FROM MpaPrograma m"),
        @NamedQuery(name = "MpaPrograma.findById", query = "SELECT m FROM MpaPrograma m WHERE m.id = :id"),
        @NamedQuery(name = "MpaPrograma.findByNombre", query = "SELECT m FROM MpaPrograma m WHERE m.nombre = :nombre")
})
public class MpaPrograma implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPrograma")
    private Collection<MpaEjecucionPlan> mpaEjecucionPlanCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPrograma")
    private Collection<MpaPlanPrograma> mpaPlanProgramaCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idPrograma")
    private Collection<MpaActividad> mpaActividadesCollection;

    public MpaPrograma() {
    }

    public MpaPrograma(Integer id) {
        this.id = id;
    }

    public MpaPrograma(Integer id, String nombre) {
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

    @XmlTransient
    public Collection<MpaActividad> getMpaActividadesCollection() {
        return mpaActividadesCollection;
    }

    public void setMpaActividadesCollection(Collection<MpaActividad> mpaActividadesCollection) {
        this.mpaActividadesCollection = mpaActividadesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MpaPrograma)) {
            return false;
        }
        MpaPrograma other = (MpaPrograma) object;
        if (this.getId() == null || other.getId() == null) {
            return false;
        } else if (this.getId().intValue() != other.getId().intValue()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.MpaProgramas[ id=" + id + " ]";
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
