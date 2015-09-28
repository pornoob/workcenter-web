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
import java.util.List;

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
})
public class ContratoPersonal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero")
    private Integer numero;
    @JoinColumn(name = "rut", referencedColumnName = "rut")
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
    @ManyToOne
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contrato", fetch = FetchType.LAZY)
    private List<PrevisionContrato> previsiones;
    @Column(name = "sin_tope")
    private Boolean sinTope;

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

    public Boolean getSinTope() {
        return sinTope;
    }

    public void setSinTope(Boolean sinTope) {
        this.sinTope = sinTope;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numero != null ? numero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ContratoPersonal)) {
            return false;
        }
        ContratoPersonal other = (ContratoPersonal) object;
        if (this.getNumero() == null || other.getNumero() == null) {
            return false;
        } else if (this.getNumero().intValue() != other.getNumero().intValue()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.ContratoPersonal[ numero=" + numero + " ]";
    }

    public List<PrevisionContrato> getPrevisiones() {
        return previsiones;
    }

    public void setPrevisiones(List<PrevisionContrato> previsiones) {
        this.previsiones = previsiones;
    }
}
