/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "bonosdescuentospersonal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BonoDescuentoPersonal.findAll", query = "SELECT b FROM BonoDescuentoPersonal b"),
    @NamedQuery(name = "BonoDescuentoPersonal.findById", query = "SELECT b FROM BonoDescuentoPersonal b WHERE b.id = :id"),
    @NamedQuery(name = "BonoDescuentoPersonal.findByIdBonodescuento", query = "SELECT b FROM BonoDescuentoPersonal b WHERE b.idBonodescuento = :idBonodescuento"),
    @NamedQuery(name = "BonoDescuentoPersonal.findByIdPersonal", query = "SELECT b FROM BonoDescuentoPersonal b WHERE b.idPersonal = :idPersonal"),
    @NamedQuery(name = "BonoDescuentoPersonal.findByFecha", query = "SELECT b FROM BonoDescuentoPersonal b WHERE b.fecha = :fecha"),
    @NamedQuery(name = "BonoDescuentoPersonal.findByMonto", query = "SELECT b FROM BonoDescuentoPersonal b WHERE b.monto = :monto"),
    @NamedQuery(name = "BonoDescuentoPersonal.findByFechadesde", query = "SELECT b FROM BonoDescuentoPersonal b WHERE b.fechadesde = :fechadesde"),
    @NamedQuery(name = "BonoDescuentoPersonal.findByFechahasta", query = "SELECT b FROM BonoDescuentoPersonal b WHERE b.fechahasta = :fechahasta")})
public class BonoDescuentoPersonal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_bonodescuento")
    private long idBonodescuento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_personal")
    private int idPersonal;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "monto")
    private BigInteger monto;
    @Column(name = "fechadesde")
    @Temporal(TemporalType.DATE)
    private Date fechadesde;
    @Column(name = "fechahasta")
    @Temporal(TemporalType.DATE)
    private Date fechahasta;

    public BonoDescuentoPersonal() {
    }

    public BonoDescuentoPersonal(Long id) {
        this.id = id;
    }

    public BonoDescuentoPersonal(Long id, long idBonodescuento, int idPersonal) {
        this.id = id;
        this.idBonodescuento = idBonodescuento;
        this.idPersonal = idPersonal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdBonodescuento() {
        return idBonodescuento;
    }

    public void setIdBonodescuento(long idBonodescuento) {
        this.idBonodescuento = idBonodescuento;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigInteger getMonto() {
        return monto;
    }

    public void setMonto(BigInteger monto) {
        this.monto = monto;
    }

    public Date getFechadesde() {
        return fechadesde;
    }

    public void setFechadesde(Date fechadesde) {
        this.fechadesde = fechadesde;
    }

    public Date getFechahasta() {
        return fechahasta;
    }

    public void setFechahasta(Date fechahasta) {
        this.fechahasta = fechahasta;
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
        if (!(object instanceof BonoDescuentoPersonal)) {
            return false;
        }
        BonoDescuentoPersonal other = (BonoDescuentoPersonal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.BonoDescuentoPersonal[ id=" + id + " ]";
    }
    
}
