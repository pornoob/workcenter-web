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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "tipodocsportipoequipos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDocTipoEquipo.findAll", query = "SELECT t FROM TipoDocTipoEquipo t"),
    @NamedQuery(name = "TipoDocTipoEquipo.findById", query = "SELECT t FROM TipoDocTipoEquipo t WHERE t.id = :id"),
    @NamedQuery(name = "TipoDocTipoEquipo.findByDocumento", query = "SELECT t FROM TipoDocTipoEquipo t WHERE t.documento = :documento"),
    @NamedQuery(name = "TipoDocTipoEquipo.findByTipoequipo", query = "SELECT t FROM TipoDocTipoEquipo t WHERE t.tipoequipo = :tipoequipo")})
public class TipoDocTipoEquipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "documento")
    private int documento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipoequipo")
    private int tipoequipo;

    public TipoDocTipoEquipo() {
    }

    public TipoDocTipoEquipo(Integer id) {
        this.id = id;
    }

    public TipoDocTipoEquipo(Integer id, int documento, int tipoequipo) {
        this.id = id;
        this.documento = documento;
        this.tipoequipo = tipoequipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public int getTipoequipo() {
        return tipoequipo;
    }

    public void setTipoequipo(int tipoequipo) {
        this.tipoequipo = tipoequipo;
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
        if (!(object instanceof TipoDocTipoEquipo)) {
            return false;
        }
        TipoDocTipoEquipo other = (TipoDocTipoEquipo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.TipoDocTipoEquipo[ id=" + id + " ]";
    }
    
}
