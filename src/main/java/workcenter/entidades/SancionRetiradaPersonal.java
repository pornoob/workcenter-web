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
@Table(name = "sancionesretiradaspersonal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SancionRetiradaPersonal.findAll", query = "SELECT s FROM SancionRetiradaPersonal s"),
    @NamedQuery(name = "SancionRetiradaPersonal.findById", query = "SELECT s FROM SancionRetiradaPersonal s WHERE s.id = :id"),
    @NamedQuery(name = "SancionRetiradaPersonal.findBySancionado", query = "SELECT s FROM SancionRetiradaPersonal s WHERE s.sancionado = :sancionado"),
    @NamedQuery(name = "SancionRetiradaPersonal.findByPerdonadapor", query = "SELECT s FROM SancionRetiradaPersonal s WHERE s.perdonadapor = :perdonadapor"),
    @NamedQuery(name = "SancionRetiradaPersonal.findByMotivosancion", query = "SELECT s FROM SancionRetiradaPersonal s WHERE s.motivosancion = :motivosancion"),
    @NamedQuery(name = "SancionRetiradaPersonal.findByMotivolevantada", query = "SELECT s FROM SancionRetiradaPersonal s WHERE s.motivolevantada = :motivolevantada"),
    @NamedQuery(name = "SancionRetiradaPersonal.findByFechasancion", query = "SELECT s FROM SancionRetiradaPersonal s WHERE s.fechasancion = :fechasancion"),
    @NamedQuery(name = "SancionRetiradaPersonal.findByFecha", query = "SELECT s FROM SancionRetiradaPersonal s WHERE s.fecha = :fecha"),
    @NamedQuery(name = "SancionRetiradaPersonal.findByNivel", query = "SELECT s FROM SancionRetiradaPersonal s WHERE s.nivel = :nivel")})
public class SancionRetiradaPersonal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sancionado")
    private int sancionado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "perdonadapor")
    private int perdonadapor;
    @Size(max = 200)
    @Column(name = "motivosancion")
    private String motivosancion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "motivolevantada")
    private String motivolevantada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechasancion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasancion;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nivel")
    private int nivel;

    public SancionRetiradaPersonal() {
    }

    public SancionRetiradaPersonal(Integer id) {
        this.id = id;
    }

    public SancionRetiradaPersonal(Integer id, int sancionado, int perdonadapor, String motivolevantada, Date fechasancion, int nivel) {
        this.id = id;
        this.sancionado = sancionado;
        this.perdonadapor = perdonadapor;
        this.motivolevantada = motivolevantada;
        this.fechasancion = fechasancion;
        this.nivel = nivel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSancionado() {
        return sancionado;
    }

    public void setSancionado(int sancionado) {
        this.sancionado = sancionado;
    }

    public int getPerdonadapor() {
        return perdonadapor;
    }

    public void setPerdonadapor(int perdonadapor) {
        this.perdonadapor = perdonadapor;
    }

    public String getMotivosancion() {
        return motivosancion;
    }

    public void setMotivosancion(String motivosancion) {
        this.motivosancion = motivosancion;
    }

    public String getMotivolevantada() {
        return motivolevantada;
    }

    public void setMotivolevantada(String motivolevantada) {
        this.motivolevantada = motivolevantada;
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

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
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
        if (!(object instanceof SancionRetiradaPersonal)) {
            return false;
        }
        SancionRetiradaPersonal other = (SancionRetiradaPersonal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.SancionRetiradaPersonal[ id=" + id + " ]";
    }
    
}
