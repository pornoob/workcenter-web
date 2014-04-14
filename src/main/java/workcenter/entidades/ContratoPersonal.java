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
@Table(name = "contratospersonal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContratoPersonal.findAll", query = "SELECT c FROM ContratoPersonal c"),
    @NamedQuery(name = "ContratoPersonal.findByNumero", query = "SELECT c FROM ContratoPersonal c WHERE c.numero = :numero"),
    @NamedQuery(name = "ContratoPersonal.findByRut", query = "SELECT c FROM ContratoPersonal c WHERE c.rut = :rut"),
    @NamedQuery(name = "ContratoPersonal.findBySueldoBase", query = "SELECT c FROM ContratoPersonal c WHERE c.sueldoBase = :sueldoBase"),
    @NamedQuery(name = "ContratoPersonal.findByVencimiento", query = "SELECT c FROM ContratoPersonal c WHERE c.vencimiento = :vencimiento"),
    @NamedQuery(name = "ContratoPersonal.findByCargo", query = "SELECT c FROM ContratoPersonal c WHERE c.cargo = :cargo"),
    @NamedQuery(name = "ContratoPersonal.findByVigente", query = "SELECT c FROM ContratoPersonal c WHERE c.vigente = :vigente"),
    @NamedQuery(name = "ContratoPersonal.findByInicio", query = "SELECT c FROM ContratoPersonal c WHERE c.inicio = :inicio"),
    @NamedQuery(name = "ContratoPersonal.findByEmpleador", query = "SELECT c FROM ContratoPersonal c WHERE c.empleador = :empleador"),
    @NamedQuery(name = "ContratoPersonal.findBySubcontrato", query = "SELECT c FROM ContratoPersonal c WHERE c.subcontrato = :subcontrato"),
    @NamedQuery(name = "ContratoPersonal.findByPorcentaje", query = "SELECT c FROM ContratoPersonal c WHERE c.porcentaje = :porcentaje"),
    @NamedQuery(name = "ContratoPersonal.findByFecha", query = "SELECT c FROM ContratoPersonal c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "ContratoPersonal.findByColacion", query = "SELECT c FROM ContratoPersonal c WHERE c.colacion = :colacion"),
    @NamedQuery(name = "ContratoPersonal.findByLocomocion", query = "SELECT c FROM ContratoPersonal c WHERE c.locomocion = :locomocion"),
    @NamedQuery(name = "ContratoPersonal.findByBonopactado", query = "SELECT c FROM ContratoPersonal c WHERE c.bonopactado = :bonopactado")})
public class ContratoPersonal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numero")
    private Integer numero;
    @Basic(optional = false)
    @NotNull
    @ManyToOne(optional = false)
    private Personal rut;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sueldoBase")
    private int sueldoBase;
    @Column(name = "vencimiento")
    @Temporal(TemporalType.DATE)
    private Date vencimiento;
    @JoinColumn(name = "cargo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cargo cargo;
    @Column(name = "vigente")
    private Boolean vigente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "inicio")
    @Temporal(TemporalType.DATE)
    private Date inicio;
    @JoinColumn(name = "empleador", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empresa empleador;
    @Column(name = "subcontrato")
    private Integer subcontrato;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "porcentaje")
    private Float porcentaje;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "colacion")
    private Integer colacion;
    @Column(name = "locomocion")
    private Integer locomocion;
    @Column(name = "bonopactado")
    private Integer bonopactado;

    public ContratoPersonal() {
    }

    public ContratoPersonal(Integer numero) {
        this.numero = numero;
    }
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Personal getRut() {
        return rut;
    }

    public void setRut(Personal rut) {
        this.rut = rut;
    }

    public int getSueldoBase() {
        return sueldoBase;
    }

    public void setSueldoBase(int sueldoBase) {
        this.sueldoBase = sueldoBase;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Boolean getVigente() {
        return vigente;
    }

    public void setVigente(Boolean vigente) {
        this.vigente = vigente;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Empresa getEmpleador() {
        return empleador;
    }

    public void setEmpleador(Empresa empleador) {
        this.empleador = empleador;
    }

    public Integer getSubcontrato() {
        return subcontrato;
    }

    public void setSubcontrato(Integer subcontrato) {
        this.subcontrato = subcontrato;
    }

    public Float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getColacion() {
        return colacion;
    }

    public void setColacion(Integer colacion) {
        this.colacion = colacion;
    }

    public Integer getLocomocion() {
        return locomocion;
    }

    public void setLocomocion(Integer locomocion) {
        this.locomocion = locomocion;
    }

    public Integer getBonopactado() {
        return bonopactado;
    }

    public void setBonopactado(Integer bonopactado) {
        this.bonopactado = bonopactado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numero != null ? numero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContratoPersonal)) {
            return false;
        }
        ContratoPersonal other = (ContratoPersonal) object;
        if ((this.numero == null && other.numero != null) || (this.numero != null && !this.numero.equals(other.numero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.ContratoPersonal[ numero=" + numero + " ]";
    }
    
}
