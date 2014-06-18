/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author colivares
 */
@Entity
@Table(name = "asociacion_documento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsociacionDocumento.findAll", query = "SELECT a FROM AsociacionDocumento a"),
    @NamedQuery(name = "AsociacionDocumento.findById", query = "SELECT a FROM AsociacionDocumento a WHERE a.id = :id"),
    @NamedQuery(name = "AsociacionDocumento.findByNombreTabla", query = "SELECT a FROM AsociacionDocumento a WHERE a.nombreTabla = :nombreTabla"),
    @NamedQuery(name = "AsociacionDocumento.findByIdEnTabla", query = "SELECT a FROM AsociacionDocumento a WHERE a.idEnTabla = :idEnTabla")})
public class AsociacionDocumento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nombre_tabla")
    private String nombreTabla;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_en_tabla")
    private int idEnTabla;
    @JoinColumn(name = "id_documento", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Documento idDocumento;

    public AsociacionDocumento() {
    }

    public AsociacionDocumento(Integer id) {
        this.id = id;
    }

    public AsociacionDocumento(Integer id, String nombreTabla, int idEnTabla) {
        this.id = id;
        this.nombreTabla = nombreTabla;
        this.idEnTabla = idEnTabla;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public int getIdEnTabla() {
        return idEnTabla;
    }

    public void setIdEnTabla(int idEnTabla) {
        this.idEnTabla = idEnTabla;
    }

    public Documento getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Documento idDocumento) {
        this.idDocumento = idDocumento;
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
        if (!(object instanceof AsociacionDocumento)) {
            return false;
        }
        AsociacionDocumento other = (AsociacionDocumento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.AsociacionDocumento[ id=" + id + " ]";
    }

}
