/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;

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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empresa empresa;
    @NotNull
    @Column(name = "fechainicio")
    @Temporal(TemporalType.DATE)
    private Date fechainicio;
    @Column(name = "fechatermino")
    @Temporal(TemporalType.DATE)
    private Date fechatermino;
    @Lob
    @Column(name = "detalle")
    private String detalle;
    @NotNull
    @Column(name = "escliente")
    private Boolean escliente;
    @Column(name = "indefinido")
    private Boolean indefinido;
    @Column(name = "ordendecompra")
    private Integer ordendecompra;
    @SortNatural
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contrato",fetch = FetchType.LAZY)
    private SortedSet<TramoContrato> tramos;
    @JoinTable(name = "contactoscontratos", joinColumns = {
            @JoinColumn(name = "contrato", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "contacto", referencedColumnName = "id")
    })
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @SortNatural
    private SortedSet<Contacto> contactos;

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

    public Boolean getEscliente() {
        return escliente;
    }

    public void setEscliente(Boolean escliente) {
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

    public SortedSet<TramoContrato> getTramos() {
        return tramos;
    }

    public void setTramos(SortedSet<TramoContrato> tramosporcontratos) {
        this.tramos = tramosporcontratos;
    }

    public SortedSet<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(SortedSet<Contacto> contactos) {
        this.contactos = contactos;
    }

    @Override
    public int hashCode() {
        int hash = 21312;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContratoEmpresa that = (ContratoEmpresa) o;
        if (this.id == null || that.id == null) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return "workcenter.entities.ContratoEmpresa[ id=" + id + " ]";
    }
    
}
