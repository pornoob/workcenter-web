/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

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
public class TramoContrato implements Serializable {
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tramo", fetch = FetchType.LAZY,orphanRemoval = true)
    private List<TarifaTramo> tarifasTramosList;

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

    public List<TarifaTramo> getTarifasTramosList() {
        return tarifasTramosList;
    }

    public void setTarifasTramosList(List<TarifaTramo> tarifasTramosList) {
        this.tarifasTramosList = tarifasTramosList;
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
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TramoContrato)) {
            return false;
        }
        TramoContrato other = (TramoContrato) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.TramoContrato[ id=" + id + " ]";
    }
    
}
