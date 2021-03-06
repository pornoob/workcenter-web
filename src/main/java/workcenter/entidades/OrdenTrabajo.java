/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.entidades;

import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "ordenes_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenTrabajo.findById", query = "SELECT DISTINCT o FROM OrdenTrabajo o WHERE o.id = :id"),
    @NamedQuery(name = "OrdenTrabajo.findAll", query = "SELECT o FROM OrdenTrabajo o"),
    @NamedQuery(name = "OrdenTrabajo.findByIdAndStatus", query = "SELECT DISTINCT ot FROM OrdenTrabajo ot INNER JOIN FETCH ot.solicitante s INNER JOIN FETCH ot.trazabilidad tot LEFT JOIN FETCH tot.ejecutor WHERE EXISTS ( SELECT o FROM OrdenTrabajo o INNER JOIN o.trazabilidad to WHERE to.fecha = (SELECT MAX(tmo.fecha) FROM TrazabilidadOt tmo WHERE tmo.ot = o) AND to.estadoId = :status AND ot.id = o.id AND ot.id = :id )")
})
public class OrdenTrabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
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

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "ot", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private MmeMantencionTracto mantencionTracto;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "ot", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private MmeMantencionSemirremolque mantencionSemirremolque;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "ot", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private MmeMantencionMaquina mantencionMaquina;


    @OneToMany(mappedBy = "ot", cascade = CascadeType.ALL)
    private Set<AsistenteOt> asistentes;
    @OneToMany(mappedBy = "ot", cascade = CascadeType.ALL)
    private Set<RepuestoOt> repuestos;

    public OrdenTrabajo() {
    }

    public OrdenTrabajo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Set<AsistenteOt> getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(Set<AsistenteOt> asistentes) {
        this.asistentes = asistentes;
    }

    public Set<RepuestoOt> getRepuestos() {
        return repuestos;
    }

    public void setRepuestos(Set<RepuestoOt> repuestos) {
        this.repuestos = repuestos;
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
