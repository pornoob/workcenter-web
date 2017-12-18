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
import java.io.Serializable;
import java.util.Date;

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
    @NamedQuery(name = "EquipoSancionado.findByEquipo", query = "SELECT e FROM EquipoSancionado e WHERE e.sancionado = :equipo ORDER BY e.fecha ASC"),
    @NamedQuery(name = "EquipoSancionado.findByNivel", query = "SELECT e FROM EquipoSancionado e WHERE e.nivel = :nivel"),
    @NamedQuery(name = "EquipoSancionado.findByMotivo", query = "SELECT e FROM EquipoSancionado e WHERE e.motivo = :motivo"),
    @NamedQuery(name = "EquipoSancionado.findByFecha", query = "SELECT e FROM EquipoSancionado e WHERE e.fecha = :fecha")})
public class EquipoSancionado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sancionado", referencedColumnName = "patente")
    private Equipo sancionado;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Equipo getSancionado() {
        return sancionado;
    }

    public void setSancionado(Equipo sancionado) {
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
