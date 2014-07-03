/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import workcenter.util.dto.Mes;

/**
 *
 * @author colivares
 */
@Entity
@Table(name = "mpa_plan_programa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MpaPlanPrograma.findAll", query = "SELECT m FROM MpaPlanPrograma m"),
    @NamedQuery(name = "MpaPlanPrograma.findById", query = "SELECT m FROM MpaPlanPrograma m WHERE m.id = :id"),
    @NamedQuery(name = "MpaPlanPrograma.findByFecha", query = "SELECT m FROM MpaPlanPrograma m WHERE m.fecha = :fecha")})
public class MpaPlanPrograma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "rut_creador", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private Personal rutCreador;
    @JoinColumn(name = "rut_responsable", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private Personal rutResponsable;
    @JoinColumn(name = "id_actividad", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MpaActividad idActividad;
    @JoinColumn(name = "id_programa", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MpaPrograma idPrograma;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPlan", orphanRemoval = true, fetch = FetchType.EAGER)
    private Collection<MpaValorPlanPrograma> mpaValorPlanProgramaCollection;

    public MpaPlanPrograma() {
    }

    public MpaPlanPrograma(Integer id) {
        this.id = id;
    }

    public MpaPlanPrograma(Integer id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
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

    public Personal getRutCreador() {
        return rutCreador;
    }

    public void setRutCreador(Personal rutCreador) {
        this.rutCreador = rutCreador;
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

    @XmlTransient
    public Collection<MpaValorPlanPrograma> getMpaValorPlanProgramaCollection() {
        return mpaValorPlanProgramaCollection;
    }

    public void setMpaValorPlanProgramaCollection(Collection<MpaValorPlanPrograma> mpaValorPlanProgramaCollection) {
        this.mpaValorPlanProgramaCollection = mpaValorPlanProgramaCollection;
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
        if (!(object instanceof MpaPlanPrograma)) {
            return false;
        }
        MpaPlanPrograma other = (MpaPlanPrograma) object;
        if (this.getId() == null || other.getId() == null) {
            return false;
        } else if (this.getId().intValue() != other.getId().intValue()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.MpaPlanPrograma[ id=" + id + " ]";
    }

    public Integer obtenerPorMes(Mes m) {
        for (MpaValorPlanPrograma valor : getMpaValorPlanProgramaCollection()) {
            if (m.getId().equals("01")) {
                return valor.getEnero();
            } else if (m.getId().equals("02")) {
                return valor.getFebrero();
            } else if (m.getId().equals("03")) {
                return valor.getMarzo();
            } else if (m.getId().equals("04")) {
                return valor.getAbril();
            } else if (m.getId().equals("05")) {
                return valor.getMayo();
            } else if (m.getId().equals("06")) {
                return valor.getJunio();
            } else if (m.getId().equals("07")) {
                return valor.getJulio();
            } else if (m.getId().equals("08")) {
                return valor.getAgosto();
            } else if (m.getId().equals("09")) {
                return valor.getSeptiembre();
            } else if (m.getId().equals("10")) {
                return valor.getOctubre();
            } else if (m.getId().equals("11")) {
                return valor.getNoviembre();
            } else if (m.getId().equals("12")) {
                return valor.getDiciembre();
            }
        }
        return 0;
    }

}
