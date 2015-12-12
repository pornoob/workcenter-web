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
@Table(name = "previsionescontratos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrevisionContrato.findAll", query = "SELECT p FROM PrevisionContrato p"),
    @NamedQuery(name = "PrevisionContrato.findById", query = "SELECT p FROM PrevisionContrato p WHERE p.id = :id"),
    @NamedQuery(name = "PrevisionContrato.findByContrato", query = "SELECT p FROM PrevisionContrato p WHERE p.contrato = :contrato"),
    @NamedQuery(name = "PrevisionContrato.findByPrevision", query = "SELECT p FROM PrevisionContrato p WHERE p.prevision = :prevision"),
    @NamedQuery(name = "PrevisionContrato.findByFechainicio", query = "SELECT p FROM PrevisionContrato p WHERE p.fechainicio = :fechainicio"),
    @NamedQuery(name = "PrevisionContrato.findByFechatermino", query = "SELECT p FROM PrevisionContrato p WHERE p.fechatermino = :fechatermino")})
public class PrevisionContrato implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "contrato", referencedColumnName = "numero")
    @ManyToOne
    private ContratoPersonal contrato;
    @JoinColumn(name = "prevision", referencedColumnName = "id")
    @OneToOne
    private Prevision prevision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechainicio")
    @Temporal(TemporalType.DATE)
    private Date fechainicio;
    @Column(name = "fechatermino")
    @Temporal(TemporalType.DATE)
    private Date fechatermino;

    public PrevisionContrato() {
    }

    public PrevisionContrato(Integer id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrevisionContrato)) {
            return false;
        }
        PrevisionContrato other = (PrevisionContrato) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.PrevisionContrato[ id=" + id + " ]";
    }
    
}
