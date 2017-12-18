/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author colivares
 */
@Entity
@Table(name = "doc_gestion_alarma_gps")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocGestionAlarmaGps.findAll", query = "SELECT d FROM DocGestionAlarmaGps d"),
    @NamedQuery(name = "DocGestionAlarmaGps.findById", query = "SELECT d FROM DocGestionAlarmaGps d WHERE d.id = :id"),
    @NamedQuery(name = "DocGestionAlarmaGps.findByRutaArchivo", query = "SELECT d FROM DocGestionAlarmaGps d WHERE d.rutaArchivo = :rutaArchivo")})
public class DocGestionAlarmaGps implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 500)
    @Column(name = "rutaArchivo")
    private String rutaArchivo;
    @JoinColumn(name = "id_gestion", referencedColumnName = "id_gestion")
    @ManyToOne(fetch = FetchType.LAZY)    private GestionAlarmaGps idGestion;

    public DocGestionAlarmaGps() {
    }

    public DocGestionAlarmaGps(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public GestionAlarmaGps getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(GestionAlarmaGps idGestion) {
        this.idGestion = idGestion;
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
        if (!(object instanceof DocGestionAlarmaGps)) {
            return false;
        }
        DocGestionAlarmaGps other = (DocGestionAlarmaGps) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.DocGestionAlarmaGps[ id=" + id + " ]";
    }

}
