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
@Table(name = "documentosadjuntos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentoAdjunto.findAll", query = "SELECT d FROM DocumentoAdjunto d"),
    @NamedQuery(name = "DocumentoAdjunto.findById", query = "SELECT d FROM DocumentoAdjunto d WHERE d.id = :id"),
    @NamedQuery(name = "DocumentoAdjunto.findByProducto", query = "SELECT d FROM DocumentoAdjunto d WHERE d.producto = :producto"),
    @NamedQuery(name = "DocumentoAdjunto.findByIdentificador", query = "SELECT d FROM DocumentoAdjunto d WHERE d.identificador = :identificador"),
    @NamedQuery(name = "DocumentoAdjunto.findByEtiqueta", query = "SELECT d FROM DocumentoAdjunto d WHERE d.etiqueta = :etiqueta")})
public class DocumentoAdjunto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "producto")
    private Integer producto;
    @Size(max = 45)
    @Column(name = "identificador")
    private String identificador;
    @Size(max = 100)
    @Column(name = "etiqueta")
    private String etiqueta;

    public DocumentoAdjunto() {
    }

    public DocumentoAdjunto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProducto() {
        return producto;
    }

    public void setProducto(Integer producto) {
        this.producto = producto;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
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
        if (!(object instanceof DocumentoAdjunto)) {
            return false;
        }
        DocumentoAdjunto other = (DocumentoAdjunto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.DocumentoAdjunto[ id=" + id + " ]";
    }
    
}
