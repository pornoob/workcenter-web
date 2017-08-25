/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.entidades;

import java.io.Serializable;
import java.util.Objects;
import java.util.SortedSet;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.SortNatural;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "ordenes_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenTrabajo.findById", query = "SELECT o FROM OrdenTrabajo o WHERE o.id = :id"),
    @NamedQuery(name = "OrdenTrabajo.findAll", query = "SELECT o FROM OrdenTrabajo o"),
    @NamedQuery(name = "OrdenTrabajo.findByStatus", query = "SELECT o FROM OrdenTrabajo o INNER JOIN FETCH o.solicitante s INNER JOIN FETCH o.trazabilidad to WHERE to.fecha = (SELECT MAX(tmo.fecha) FROM TrazabilidadOt tmo WHERE tmo.ot = o) AND to.estadoId = :status"),
    @NamedQuery(name = "OrdenTrabajo.findByIdAndStatus", query = "SELECT o FROM OrdenTrabajo o INNER JOIN FETCH o.solicitante s INNER JOIN FETCH o.trazabilidad to WHERE o.id = :id to.fecha = (SELECT MAX(tmo.fecha) FROM TrazabilidadOt tmo WHERE tmo.ot = o) AND to.estadoId = :status")
})
public class OrdenTrabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitante", referencedColumnName = "id")
    private SolicitanteOt solicitante;
    @Column(name = "tipo_trabajo")
    private String tipoTrabajo;
    @Size(max = 3000)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "ot", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @SortNatural
    private SortedSet<TrazabilidadOt> trazabilidad;
    
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "ot")
    private MmeMantencionTracto mantencionTracto;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "ot")
    private MmeMantencionSemirremolque mantencionSemirremolque;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "ot")
    private MmeMantencionMaquina mantencionMaquina;

    public OrdenTrabajo() {
    }

    public OrdenTrabajo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SolicitanteOt getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(SolicitanteOt solicitante) {
        this.solicitante = solicitante;
    }

    public String getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(String tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public SortedSet<TrazabilidadOt> getTrazabilidad() {
        return trazabilidad;
    }

    public void setTrazabilidad(SortedSet<TrazabilidadOt> trazabilidad) {
        this.trazabilidad = trazabilidad;
    }

    public MmeMantencionTracto getMantencionTracto() {
        return mantencionTracto;
    }

    public void setMantencionTracto(MmeMantencionTracto mantencionTracto) {
        this.mantencionTracto = mantencionTracto;
    }

    public MmeMantencionSemirremolque getMantencionSemirremolque() {
        return mantencionSemirremolque;
    }

    public void setMantencionSemirremolque(MmeMantencionSemirremolque mantencionSemirremolque) {
        this.mantencionSemirremolque = mantencionSemirremolque;
    }

    public MmeMantencionMaquina getMantencionMaquina() {
        return mantencionMaquina;
    }

    public void setMantencionMaquina(MmeMantencionMaquina mantencionMaquina) {
        this.mantencionMaquina = mantencionMaquina;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final OrdenTrabajo other = (OrdenTrabajo) obj;
        if (this.id == null || other.id == null) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "workcenter.entidades.OrdenTrabajo[ id=" + id + " ]";
    }
    
}
