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
@Table(name = "valoresprevisionpersonal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValorPrevisionPersonal.findAll", query = "SELECT v FROM ValorPrevisionPersonal v"),
    @NamedQuery(name = "ValorPrevisionPersonal.findById", query = "SELECT v FROM ValorPrevisionPersonal v WHERE v.id = :id"),
    @NamedQuery(name = "ValorPrevisionPersonal.findByContrato", query = "SELECT v FROM ValorPrevisionPersonal v WHERE v.contrato = :contrato"),
    @NamedQuery(name = "ValorPrevisionPersonal.findByPrevision", query = "SELECT v FROM ValorPrevisionPersonal v WHERE v.prevision = :prevision"),
    @NamedQuery(name = "ValorPrevisionPersonal.findByUnidad", query = "SELECT v FROM ValorPrevisionPersonal v WHERE v.unidad = :unidad"),
    @NamedQuery(name = "ValorPrevisionPersonal.findByValor", query = "SELECT v FROM ValorPrevisionPersonal v WHERE v.valor = :valor"),
    @NamedQuery(name = "ValorPrevisionPersonal.findByFechaVigencia", query = "SELECT v FROM ValorPrevisionPersonal v WHERE v.fechaVigencia = :fechaVigencia")})
public class ValorPrevisionPersonal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "contrato", referencedColumnName = "numero")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ContratoPersonal contrato;
    @JoinColumn(name = "prevision", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Prevision prevision;
    @Basic(optional = false)
    @JoinColumn(name = "unidad", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TipoUnidad unidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private double valor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechavigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaVigencia;

    public ValorPrevisionPersonal() {
    }

    public ValorPrevisionPersonal(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ContratoPersonal getContrato() {
        return contrato;
    }

    public void setContrato(ContratoPersonal contrato) {
        this.contrato = contrato;
    }

    public Prevision getPrevision() {
        return prevision;
    }

    public void setPrevision(Prevision prevision) {
        this.prevision = prevision;
    }

    public TipoUnidad getUnidad() {
        return unidad;
    }

    public void setUnidad(TipoUnidad unidad) {
        this.unidad = unidad;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechavigencia) {
        this.fechaVigencia = fechavigencia;
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
        if (!(object instanceof ValorPrevisionPersonal)) {
            return false;
        }
        ValorPrevisionPersonal other = (ValorPrevisionPersonal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.ValorPrevisionPersonal[ id=" + id + " ]";
    }
    
}
