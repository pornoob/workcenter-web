/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "dineros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dinero.findAll", query = "SELECT d FROM Dinero d ORDER BY d.id DESC"),
    @NamedQuery(name = "Dinero.findById", query = "SELECT d FROM Dinero d WHERE d.id = :id"),
    @NamedQuery(name = "Dinero.findByMonto", query = "SELECT d FROM Dinero d WHERE d.monto = :monto"),
    @NamedQuery(name = "Dinero.findByConcepto", query = "SELECT d FROM Dinero d WHERE d.concepto = :concepto"),
    @NamedQuery(name = "Dinero.findByReceptor", query = "SELECT d FROM Dinero d WHERE d.receptor = :receptor"),
    @NamedQuery(name = "Dinero.findByOrdendecarga", query = "SELECT d FROM Dinero d WHERE d.ordendecarga = :ordendecarga"),
    @NamedQuery(name = "Dinero.findByConceptoFecha", query = "SELECT d FROM Dinero d "
    		+ "WHERE MONTH(d.fechaactivo) = :mes and YEAR(d.fechaactivo) = :anio and d.receptor = :receptor"),
    @NamedQuery(name = "Dinero.findDineroWithDescuento", query = "SELECT d FROM Dinero d INNER JOIN FETCH d.receptor "
            + "INNER JOIN FETCH d.lstDescuentos des WHERE des.monto > 0 ORDER BY d.id DESC")})
public class Dinero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto")
    private Integer monto;
    @JoinColumn(name = "concepto", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
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
    @JoinColumn(name = "receptor", referencedColumnName = "rut")
    @ManyToOne(fetch = FetchType.LAZY)
    private Personal receptor;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordendecarga", referencedColumnName = "ordendecarga")
    private Vuelta ordendecarga;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "motivo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Descuento> lstDescuentos;

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

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
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

    public Personal getReceptor() {
        return receptor;
    }

    public void setReceptor(Personal receptor) {
        this.receptor = receptor;
    }

    public Vuelta getOrdendecarga() {
        return ordendecarga;
    }

    public void setOrdendecarga(Vuelta ordendecarga) {
        this.ordendecarga = ordendecarga;
    }

    public List<Descuento> getLstDescuentos() {
        return lstDescuentos;
    }

    public void setLstDescuentos(List<Descuento> lstDescuentos) {
        this.lstDescuentos = lstDescuentos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dinero other = (Dinero) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Dinero[ id=" + id + " ]";
    }
    
}
