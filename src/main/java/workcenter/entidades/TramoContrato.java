/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;
import java.util.SortedSet;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "tramosporcontrato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TramoContrato.findAll", query = "SELECT t FROM TramoContrato t"),
    @NamedQuery(name = "TramoContrato.findById", query = "SELECT t FROM TramoContrato t WHERE t.id = :id"),
    @NamedQuery(name = "TramoContrato.findByContrato", query = "SELECT t FROM TramoContrato t WHERE t.contrato = :contrato"),
    @NamedQuery(name = "TramoContrato.findByOrigen", query = "SELECT t FROM TramoContrato t WHERE t.origen = :origen"),
    @NamedQuery(name = "TramoContrato.findByDestino", query = "SELECT t FROM TramoContrato t WHERE t.destino = :destino")})
public class TramoContrato implements Serializable, Comparable<TramoContrato> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "contrato", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)    private ContratoEmpresa contrato;
    @JoinColumn(name = "origen", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)    private OrigenDestino origen;
    @JoinColumn(name = "destino", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)    private OrigenDestino destino;
    @JoinColumn(name = "tipoproducto", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)    private TipoProducto tipoProducto;
    @SortNatural
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tramo", fetch = FetchType.LAZY,orphanRemoval = true)
    private SortedSet<TarifaTramo> tarifas;

    // read only
    @Column(name = "origen", insertable = false, updatable = false)
    private Integer origenId;
    @Column(name = "destino", insertable = false, updatable = false)
    private Integer destinoId;
    @Column(name = "tipoproducto", insertable = false, updatable = false)
    private Integer productoId;

    public TramoContrato() {
    }

    public TramoContrato(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public SortedSet<TarifaTramo> getTarifas() {
        return tarifas;
    }

    public void setTarifas(SortedSet<TarifaTramo> tarifasTramosList) {
        this.tarifas = tarifasTramosList;
    }

    public ContratoEmpresa getContrato() {
        return contrato;
    }

    public void setContrato(ContratoEmpresa contrato) {
        this.contrato = contrato;
    }

    public OrigenDestino getOrigen() {
        return origen;
    }

    public void setOrigen(OrigenDestino origen) {
        this.origen = origen;
    }

    public OrigenDestino getDestino() {
        return destino;
    }

    public void setDestino(OrigenDestino destino) {
        this.destino = destino;
    }

    @Override
    public int compareTo(TramoContrato o) {
        int diff = origenId.compareTo(o.origenId);
        diff = diff == 0 ? destinoId.compareTo(o.destinoId) : diff;
        diff = diff == 0 ? productoId.compareTo(o.productoId) : diff;
        diff = diff == 0 ? id.compareTo(o.id) : diff;
        return diff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TramoContrato that = (TramoContrato) o;
        if (this.id == null || that.id == null) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        int hash = 897213;
        hash += id != null ? Objects.hashCode(id) : 0;
        return Objects.hash(hash);
    }

    @Override
    public String toString() {
        return "workcenter.entities.TramoContrato[ id=" + id + " ]";
    }
    
}
