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
    @NamedQuery(name = "ValorPrevisionPersonal.findByContrato", query = "SELECT v FROM ValorPrevisionPersonal v WHERE v.contrato = :contrato order by fechaVigencia DESC"),
    @NamedQuery(name = "ValorPrevisionPersonal.findByPrevision", query = "SELECT v FROM ValorPrevisionPersonal v WHERE v.prevision = :prevision"),
    @NamedQuery(name = "ValorPrevisionPersonal.findByUnidad", query = "SELECT v FROM ValorPrevisionPersonal v WHERE v.unidad = :unidad"),
    @NamedQuery(name = "ValorPrevisionPersonal.findByValor", query = "SELECT v FROM ValorPrevisionPersonal v WHERE v.valor = :valor"),
    @NamedQuery(name = "ValorPrevisionPersonal.findByFechaVigencia", query = "SELECT v FROM ValorPrevisionPersonal v WHERE v.fechaVigencia = :fechaVigencia")})
public class ValorPrevisionPersonal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "contrato", referencedColumnName = "numero")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ContratoPersonal contrato;
    @JoinColumn(name = "prevision", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Prevision prevision;
    @JoinColumn(name = "unidad", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoUnidad unidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private Double valor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechavigencia")
    @Temporal(TemporalType.TIMESTAMP)
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
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
        if (!(object instanceof ValorPrevisionPersonal)) {
            return false;
        }
        ValorPrevisionPersonal other = (ValorPrevisionPersonal) object;
        if (this.getId() == null || other.getId() == null) return false;
        else return this.getId().equals(other.getId());
    }

    @Override
    public String toString() {
        return "workcenter.entities.ValorPrevisionPersonal[ id=" + id + " ]";
    }
    
}
