/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.entidades;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author rlabbe
 */
@Entity
@Table(name="documentos", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Documento.findAll", query = "SELECT d FROM Documento d"),
    @NamedQuery(name = "Documento.findById", query = "SELECT d FROM Documento d WHERE d.id = :id"),
})
public class Documento implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDocumento")
    private Collection<AsociacionDocumento> asociacionDocumentoCollection;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre_original")
        private String nombreOriginal;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public Documento() {
    }

    public Documento(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Documento)) {
            return false;
        }
        Documento other = (Documento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Documento(Integer id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.Documento[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<AsociacionDocumento> getAsociacionDocumentoCollection() {
        return asociacionDocumentoCollection;
    }

    public void setAsociacionDocumentoCollection(Collection<AsociacionDocumento> asociacionDocumentoCollection) {
        this.asociacionDocumentoCollection = asociacionDocumentoCollection;
    }
    
}
