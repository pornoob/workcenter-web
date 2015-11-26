/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "contratosempresas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContratoEmpresa.findAll", query = "SELECT c FROM ContratoEmpresa c"),
    @NamedQuery(name = "ContratoEmpresa.findById", query = "SELECT c FROM ContratoEmpresa c WHERE c.id = :id"),
    @NamedQuery(name = "ContratoEmpresa.findByNumerocontrato", query = "SELECT c FROM ContratoEmpresa c WHERE c.numerocontrato = :numerocontrato"),
    @NamedQuery(name = "ContratoEmpresa.findByNombrecontrato", query = "SELECT c FROM ContratoEmpresa c WHERE c.nombrecontrato = :nombrecontrato"),
    @NamedQuery(name = "ContratoEmpresa.findByEmpresa", query = "SELECT c FROM ContratoEmpresa c WHERE c.empresa = :empresa"),
    @NamedQuery(name = "ContratoEmpresa.findByFechainicio", query = "SELECT c FROM ContratoEmpresa c WHERE c.fechainicio = :fechainicio"),
    @NamedQuery(name = "ContratoEmpresa.findByFechatermino", query = "SELECT c FROM ContratoEmpresa c WHERE c.fechatermino = :fechatermino"),
    @NamedQuery(name = "ContratoEmpresa.findByEscliente", query = "SELECT c FROM ContratoEmpresa c WHERE c.escliente = :escliente"),
    @NamedQuery(name = "ContratoEmpresa.findByIndefinido", query = "SELECT c FROM ContratoEmpresa c WHERE c.indefinido = :indefinido"),
    @NamedQuery(name = "ContratoEmpresa.findByOrdendecompra", query = "SELECT c FROM ContratoEmpresa c WHERE c.ordendecompra = :ordendecompra")})
public class ContratoEmpresa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "numerocontrato")
    private String numerocontrato;
    @Size(max = 70)
    @Column(name = "nombrecontrato")
    private String nombrecontrato;
    @JoinColumn(name = "empresa", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Empresa empresa;
    @NotNull
    @Column(name = "fechainicio")
    @Temporal(TemporalType.DATE)
    private Date fechainicio;
    @Column(name = "fechatermino")
    @Temporal(TemporalType.DATE)
    private Date fechatermino;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "detalle")
    private String detalle;
    @NotNull
    @Column(name = "escliente")
    private boolean escliente;
    @Column(name = "indefinido")
    private Boolean indefinido;
    @Column(name = "ordendecompra")
    private Integer ordendecompra;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contrato",fetch = FetchType.LAZY)
    private List<TramoContrato> tramosporcontratos;

    public ContratoEmpresa() {
    }

    public ContratoEmpresa(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumerocontrato() {
        return numerocontrato;
    }

    public void setNumerocontrato(String numerocontrato) {
        this.numerocontrato = numerocontrato;
    }

    public String getNombrecontrato() {
        return nombrecontrato;
    }

    public void setNombrecontrato(String nombrecontrato) {
        this.nombrecontrato = nombrecontrato;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechatermino() {
        return fechatermino;
    }

    public void setFechatermino(Date fechatermino) {
        this.fechatermino = fechatermino;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public boolean getEscliente() {
        return escliente;
    }

    public void setEscliente(boolean escliente) {
        this.escliente = escliente;
    }

    public Boolean getIndefinido() {
        return indefinido;
    }

    public void setIndefinido(Boolean indefinido) {
        this.indefinido = indefinido;
    }

    public Integer getOrdendecompra() {
        return ordendecompra;
    }

    public void setOrdendecompra(Integer ordendecompra) {
        this.ordendecompra = ordendecompra;
    }

    public List<TramoContrato> getTramosporcontratos() {
        return tramosporcontratos;
    }

    public void setTramosporcontratos(List<TramoContrato> tramosporcontratos) {
        this.tramosporcontratos = tramosporcontratos;
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
        if (!(object instanceof ContratoEmpresa)) {
            return false;
        }
        ContratoEmpresa other = (ContratoEmpresa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.ContratoEmpresa[ id=" + id + " ]";
    }
    
}
