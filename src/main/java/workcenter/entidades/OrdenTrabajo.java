/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.entidades;

import java.io.Serializable;
import java.util.SortedSet;
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
import javax.persistence.OneToMany;
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
    @NamedQuery(name = "OrdenTrabajo.findAll", query = "SELECT o FROM OrdenTrabajo o")
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
    private Integer tipoTrabajo;
    @Size(max = 3000)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "ot", fetch = FetchType.LAZY)
    @SortNatural
    private SortedSet<TrazabilidadOt> trazabilidad;

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

    public Integer getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(Integer tipoTrabajo) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenTrabajo)) {
            return false;
        }
        OrdenTrabajo other = (OrdenTrabajo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.OrdenTrabajo[ id=" + id + " ]";
    }
    
}
