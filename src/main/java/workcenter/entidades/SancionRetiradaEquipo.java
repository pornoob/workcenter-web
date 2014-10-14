/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "sancionesretiradasequipos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SancionRetiradaEquipo.findAll", query = "SELECT s FROM SancionRetiradaEquipo s"),
    @NamedQuery(name = "SancionRetiradaEquipo.findById", query = "SELECT s FROM SancionRetiradaEquipo s WHERE s.id = :id"),
    @NamedQuery(name = "SancionRetiradaEquipo.findBySancionado", query = "SELECT s FROM SancionRetiradaEquipo s WHERE s.sancionado = :sancionado"),
    @NamedQuery(name = "SancionRetiradaEquipo.findByPerdonadopor", query = "SELECT s FROM SancionRetiradaEquipo s WHERE s.perdonadopor = :perdonadopor"),
    @NamedQuery(name = "SancionRetiradaEquipo.findByNivel", query = "SELECT s FROM SancionRetiradaEquipo s WHERE s.nivel = :nivel"),
    @NamedQuery(name = "SancionRetiradaEquipo.findByMotivo", query = "SELECT s FROM SancionRetiradaEquipo s WHERE s.motivo = :motivo"),
    @NamedQuery(name = "SancionRetiradaEquipo.findByMotivoretirosancion", query = "SELECT s FROM SancionRetiradaEquipo s WHERE s.motivoretirosancion = :motivoretirosancion"),
    @NamedQuery(name = "SancionRetiradaEquipo.findByFechasancion", query = "SELECT s FROM SancionRetiradaEquipo s WHERE s.fechasancion = :fechasancion"),
    @NamedQuery(name = "SancionRetiradaEquipo.findByFecha", query = "SELECT s FROM SancionRetiradaEquipo s WHERE s.fecha = :fecha")})
public class SancionRetiradaEquipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "sancionado", referencedColumnName = "patente")
    private Equipo sancionado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "perdonadopor")
    private int perdonadopor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nivel")
    private int nivel;
    @Size(max = 200)
    @Column(name = "motivo")
    private String motivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "motivoretirosancion")
    private String motivoretirosancion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechasancion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasancion;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public SancionRetiradaEquipo() {
    }

    public SancionRetiradaEquipo(Integer id) {
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

    public int getPerdonadopor() {
        return perdonadopor;
    }

    public void setPerdonadopor(int perdonadopor) {
        this.perdonadopor = perdonadopor;
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

    public String getMotivoretirosancion() {
        return motivoretirosancion;
    }

    public void setMotivoretirosancion(String motivoretirosancion) {
        this.motivoretirosancion = motivoretirosancion;
    }

    public Date getFechasancion() {
        return fechasancion;
    }

    public void setFechasancion(Date fechasancion) {
        this.fechasancion = fechasancion;
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
        if (!(object instanceof SancionRetiradaEquipo)) {
            return false;
        }
        SancionRetiradaEquipo other = (SancionRetiradaEquipo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.SancionRetiradaEquipo[ id=" + id + " ]";
    }
    
}
