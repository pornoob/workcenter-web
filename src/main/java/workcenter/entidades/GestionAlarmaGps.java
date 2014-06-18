/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author colivares
 */
@Entity
@Table(name = "gestiones_alarmas_gps")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GestionAlarmaGps.findAll", query = "SELECT g FROM GestionAlarmaGps g"),
    @NamedQuery(name = "GestionAlarmaGps.findByIdGestion", query = "SELECT g FROM GestionAlarmaGps g WHERE g.idGestion = :idGestion")})
public class GestionAlarmaGps implements Serializable {
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "idUsuario", referencedColumnName = "rut")
    @ManyToOne
    private Usuario idUsuario;
    @Size(max = 1000)
    @Column(name = "detalle")
    private String detalle;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gestion")
    private Integer idGestion;
    @OneToMany(mappedBy = "idGestion")
    private Collection<DocGestionAlarmaGps> docGestionAlarmaGpsCollection;
    @JoinColumn(name = "id_alarma", referencedColumnName = "id")
    @ManyToOne
    private AlarmaGps idAlarma;

    public GestionAlarmaGps() {
    }

    public GestionAlarmaGps(Integer idGestion) {
        this.idGestion = idGestion;
    }

    public Integer getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(Integer idGestion) {
        this.idGestion = idGestion;
    }

    @XmlTransient
    public Collection<DocGestionAlarmaGps> getDocGestionAlarmaGpsCollection() {
        return docGestionAlarmaGpsCollection;
    }

    public void setDocGestionAlarmaGpsCollection(Collection<DocGestionAlarmaGps> docGestionAlarmaGpsCollection) {
        this.docGestionAlarmaGpsCollection = docGestionAlarmaGpsCollection;
    }

    public AlarmaGps getIdAlarma() {
        return idAlarma;
    }

    public void setIdAlarma(AlarmaGps idAlarma) {
        this.idAlarma = idAlarma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGestion != null ? idGestion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GestionAlarmaGps)) {
            return false;
        }
        GestionAlarmaGps other = (GestionAlarmaGps) object;
        if ((this.idGestion == null && other.idGestion != null) || (this.idGestion != null && !this.idGestion.equals(other.idGestion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.GestionAlarmaGps[ idGestion=" + idGestion + " ]";
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

}
