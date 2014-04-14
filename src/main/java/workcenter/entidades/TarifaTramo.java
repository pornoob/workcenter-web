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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "tarifastramos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TarifaTramo.findAll", query = "SELECT t FROM TarifaTramo t"),
    @NamedQuery(name = "TarifaTramo.findById", query = "SELECT t FROM TarifaTramo t WHERE t.id = :id"),
    @NamedQuery(name = "TarifaTramo.findByTarifapago", query = "SELECT t FROM TarifaTramo t WHERE t.tarifapago = :tarifapago"),
    @NamedQuery(name = "TarifaTramo.findByTarifacobro", query = "SELECT t FROM TarifaTramo t WHERE t.tarifacobro = :tarifacobro"),
    @NamedQuery(name = "TarifaTramo.findByFechavigencia", query = "SELECT t FROM TarifaTramo t WHERE t.fechavigencia = :fechavigencia"),
    @NamedQuery(name = "TarifaTramo.findByTipotarifa", query = "SELECT t FROM TarifaTramo t WHERE t.tipoTarifa = :tipoTarifa"),
    @NamedQuery(name = "TarifaTramo.findByTramo", query = "SELECT t FROM TarifaTramo t WHERE t.tramo = :tramo")})
public class TarifaTramo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tarifapago")
    private int tarifapago;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tarifacobro")
    private int tarifacobro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechavigencia")
    @Temporal(TemporalType.DATE)
    private Date fechavigencia;
    @JoinColumn(name = "tipoTarifa", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoTarifa tipoTarifa;
    @JoinColumn(name = "tramo", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TramoContrato tramo;
    

    public TarifaTramo() {
    }

    public TarifaTramo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTarifapago() {
        return tarifapago;
    }

    public void setTarifapago(int tarifapago) {
        this.tarifapago = tarifapago;
    }

    public int getTarifacobro() {
        return tarifacobro;
    }

    public void setTarifacobro(int tarifacobro) {
        this.tarifacobro = tarifacobro;
    }

    public Date getFechavigencia() {
        return fechavigencia;
    }

    public void setFechavigencia(Date fechavigencia) {
        this.fechavigencia = fechavigencia;
    }

    public TipoTarifa getTipoTarifa() {
        return tipoTarifa;
    }

    public void setTipoTarifa(TipoTarifa tipoTarifa) {
        this.tipoTarifa = tipoTarifa;
    }

    public TramoContrato getTramo() {
        return tramo;
    }

    public void setTramo(TramoContrato tramo) {
        this.tramo = tramo;
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
        if (!(object instanceof TarifaTramo)) {
            return false;
        }
        TarifaTramo other = (TarifaTramo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.TarifaTramo[ id=" + id + " ]";
    }
    
}
