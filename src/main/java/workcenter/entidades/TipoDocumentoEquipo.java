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
 * @author claudio
 */
@Entity
@Table(name = "tiposdocumentosequipos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDocumentoEquipo.findAll", query = "SELECT t FROM TipoDocumentoEquipo t"),
    @NamedQuery(name = "TipoDocumentoEquipo.findById", query = "SELECT t FROM TipoDocumentoEquipo t WHERE t.id = :id"),
    @NamedQuery(name = "TipoDocumentoEquipo.findByEtiqueta", query = "SELECT t FROM TipoDocumentoEquipo t WHERE t.etiqueta = :etiqueta"),
    @NamedQuery(name = "TipoDocumentoEquipo.findByDescripcion", query = "SELECT t FROM TipoDocumentoEquipo t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TipoDocumentoEquipo.findByDiasalerta", query = "SELECT t FROM TipoDocumentoEquipo t WHERE t.diasalerta = :diasalerta")})
public class TipoDocumentoEquipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 120)
    @Column(name = "etiqueta")
    private String etiqueta;
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "diasalerta")
    private Integer diasalerta;

    public TipoDocumentoEquipo() {
    }

    public TipoDocumentoEquipo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDiasalerta() {
        return diasalerta;
    }

    public void setDiasalerta(Integer diasalerta) {
        this.diasalerta = diasalerta;
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
        if (!(object instanceof TipoDocumentoEquipo)) {
            return false;
        }
        TipoDocumentoEquipo other = (TipoDocumentoEquipo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.TipoDocumentoEquipo[ id=" + id + " ]";
    }
    
}
