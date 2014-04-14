/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "bonosdescuentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BonoDescuento.findAll", query = "SELECT b FROM BonoDescuento b"),
    @NamedQuery(name = "BonoDescuento.findById", query = "SELECT b FROM BonoDescuento b WHERE b.id = :id"),
    @NamedQuery(name = "BonoDescuento.findByDescripcion", query = "SELECT b FROM BonoDescuento b WHERE b.descripcion = :descripcion"),
    @NamedQuery(name = "BonoDescuento.findByMonto", query = "SELECT b FROM BonoDescuento b WHERE b.monto = :monto"),
    @NamedQuery(name = "BonoDescuento.findByIdTipoUnidad", query = "SELECT b FROM BonoDescuento b WHERE b.idTipoUnidad = :idTipoUnidad"),
    @NamedQuery(name = "BonoDescuento.findByIdTipoBonodescuento", query = "SELECT b FROM BonoDescuento b WHERE b.idTipoBonodescuento = :idTipoBonodescuento"),
    @NamedQuery(name = "BonoDescuento.findByIndefinido", query = "SELECT b FROM BonoDescuento b WHERE b.indefinido = :indefinido"),
    @NamedQuery(name = "BonoDescuento.findByDuracion", query = "SELECT b FROM BonoDescuento b WHERE b.duracion = :duracion"),
    @NamedQuery(name = "BonoDescuento.findByPorPersona", query = "SELECT b FROM BonoDescuento b WHERE b.porPersona = :porPersona"),
    @NamedQuery(name = "BonoDescuento.findByImponible", query = "SELECT b FROM BonoDescuento b WHERE b.imponible = :imponible")})
public class BonoDescuento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto")
    private long monto;
    @JoinColumn(name = "id_tipo_unidad", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoUnidad idTipoUnidad;
    @JoinColumn(name = "id_tipo_bonodescuento", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoBonoDescuento idTipoBonodescuento;
    @Column(name = "indefinido")
    private Boolean indefinido;
    @Column(name = "duracion")
    private Integer duracion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "por_persona")
    private boolean porPersona;
    @Basic(optional = false)
    @NotNull
    @Column(name = "imponible")
    private boolean imponible;

    public BonoDescuento() {
    }

    public BonoDescuento(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }

    public TipoUnidad getIdTipoUnidad() {
        return idTipoUnidad;
    }

    public void setIdTipoUnidad(TipoUnidad idTipoUnidad) {
        this.idTipoUnidad = idTipoUnidad;
    }

    public TipoBonoDescuento getIdTipoBonodescuento() {
        return idTipoBonodescuento;
    }

    public void setIdTipoBonodescuento(TipoBonoDescuento idTipoBonodescuento) {
        this.idTipoBonodescuento = idTipoBonodescuento;
    }

    public Boolean getIndefinido() {
        return indefinido;
    }

    public void setIndefinido(Boolean indefinido) {
        this.indefinido = indefinido;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public boolean getPorPersona() {
        return porPersona;
    }

    public void setPorPersona(boolean porPersona) {
        this.porPersona = porPersona;
    }

    public boolean getImponible() {
        return imponible;
    }

    public void setImponible(boolean imponible) {
        this.imponible = imponible;
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
        if (!(object instanceof BonoDescuento)) {
            return false;
        }
        BonoDescuento other = (BonoDescuento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.BonoDescuento[ id=" + id + " ]";
    }
    
}
