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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "dineros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dinero.findAll", query = "SELECT d FROM Dinero d"),
    @NamedQuery(name = "Dinero.findById", query = "SELECT d FROM Dinero d WHERE d.id = :id"),
    @NamedQuery(name = "Dinero.findByMonto", query = "SELECT d FROM Dinero d WHERE d.monto = :monto"),
    @NamedQuery(name = "Dinero.findByConcepto", query = "SELECT d FROM Dinero d WHERE d.concepto = :concepto"),
    @NamedQuery(name = "Dinero.findByFechareal", query = "SELECT d FROM Dinero d WHERE d.fechareal = :fechareal"),
    @NamedQuery(name = "Dinero.findByFechaactivo", query = "SELECT d FROM Dinero d WHERE d.fechaactivo = :fechaactivo"),
    @NamedQuery(name = "Dinero.findByComentario", query = "SELECT d FROM Dinero d WHERE d.comentario = :comentario"),
    @NamedQuery(name = "Dinero.findByReceptor", query = "SELECT d FROM Dinero d WHERE d.receptor = :receptor"),
    @NamedQuery(name = "Dinero.findByOrdendecarga", query = "SELECT d FROM Dinero d WHERE d.ordendecarga = :ordendecarga")})
public class Dinero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto")
    private int monto;
    @JoinColumn(name = "concepto", referencedColumnName = "id")
    @ManyToOne
    private Concepto concepto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechareal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechareal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaactivo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaactivo;
    @Size(max = 200)
    @Column(name = "comentario")
    private String comentario;
    @Column(name = "receptor")
    private Integer receptor;
    @Column(name = "ordendecarga")
    private Integer ordendecarga;

    public Dinero() {
    }

    public Dinero(Integer id) {
        this.id = id;
    }

    public Dinero(Integer id, int monto, Date fechareal, Date fechaactivo) {
        this.id = id;
        this.monto = monto;
        this.fechareal = fechareal;
        this.fechaactivo = fechaactivo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Concepto getConcepto() {
        return concepto;
    }

    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }

    public Date getFechareal() {
        return fechareal;
    }

    public void setFechareal(Date fechareal) {
        this.fechareal = fechareal;
    }

    public Date getFechaactivo() {
        return fechaactivo;
    }

    public void setFechaactivo(Date fechaactivo) {
        this.fechaactivo = fechaactivo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getReceptor() {
        return receptor;
    }

    public void setReceptor(Integer receptor) {
        this.receptor = receptor;
    }

    public Integer getOrdendecarga() {
        return ordendecarga;
    }

    public void setOrdendecarga(Integer ordendecarga) {
        this.ordendecarga = ordendecarga;
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
        if (!(object instanceof Dinero)) {
            return false;
        }
        Dinero other = (Dinero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Dinero[ id=" + id + " ]";
    }
    
}
