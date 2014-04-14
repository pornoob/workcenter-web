/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "equipossancionados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EquipoSancionado.findAll", query = "SELECT e FROM EquipoSancionado e"),
    @NamedQuery(name = "EquipoSancionado.findById", query = "SELECT e FROM EquipoSancionado e WHERE e.id = :id"),
    @NamedQuery(name = "EquipoSancionado.findBySancionado", query = "SELECT e FROM EquipoSancionado e WHERE e.sancionado = :sancionado"),
    @NamedQuery(name = "EquipoSancionado.findByNivel", query = "SELECT e FROM EquipoSancionado e WHERE e.nivel = :nivel"),
    @NamedQuery(name = "EquipoSancionado.findByMotivo", query = "SELECT e FROM EquipoSancionado e WHERE e.motivo = :motivo"),
    @NamedQuery(name = "EquipoSancionado.findByFecha", query = "SELECT e FROM EquipoSancionado e WHERE e.fecha = :fecha")})
public class EquipoSancionado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "sancionado")
    private String sancionado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nivel")
    private int nivel;
    @Size(max = 200)
    @Column(name = "motivo")
    private String motivo;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public EquipoSancionado() {
    }

    public EquipoSancionado(Integer id) {
        this.id = id;
    }

    public EquipoSancionado(Integer id, String sancionado, int nivel) {
        this.id = id;
        this.sancionado = sancionado;
        this.nivel = nivel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSancionado() {
        return sancionado;
    }

    public void setSancionado(String sancionado) {
        this.sancionado = sancionado;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        if (!(object instanceof EquipoSancionado)) {
            return false;
        }
        EquipoSancionado other = (EquipoSancionado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.EquipoSancionado[ id=" + id + " ]";
    }
    
}
