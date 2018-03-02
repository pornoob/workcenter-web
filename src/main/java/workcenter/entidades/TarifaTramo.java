/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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
public class TarifaTramo implements Serializable,Comparable<TarifaTramo> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TarifaTramo that = (TarifaTramo) o;
        if (this.id == null || that.id == null) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        int hash = 218937719;
        hash += id != null ? Objects.hash(id) : 0;
        return hash;
    }

    @Override
    public int compareTo(TarifaTramo tarifaTramo) {
        return this.fechavigencia.compareTo(tarifaTramo.fechavigencia) * -1;
    }

    @Override
    public String toString() {
        return "workcenter.entities.TarifaTramo[ id=" + id + " ]";
    }
    
}
