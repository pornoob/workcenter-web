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
@Table(name = "tipodocporcargo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDocCargo.findAll", query = "SELECT t FROM TipoDocCargo t"),
    @NamedQuery(name = "TipoDocCargo.findById", query = "SELECT t FROM TipoDocCargo t WHERE t.id = :id"),
    @NamedQuery(name = "TipoDocCargo.findByDocumento", query = "SELECT t FROM TipoDocCargo t WHERE t.documento = :documento"),
    @NamedQuery(name = "TipoDocCargo.findByCargo", query = "SELECT t FROM TipoDocCargo t WHERE t.cargo = :cargo"),
    @NamedQuery(name = "TipoDocCargo.findByObligatorio", query = "SELECT t FROM TipoDocCargo t WHERE t.obligatorio = :obligatorio")})
public class TipoDocCargo implements Serializable {
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
    @Column(name = "cargo")
    private int cargo;
    @Column(name = "obligatorio")
    private Boolean obligatorio;

    public TipoDocCargo() {
    }

    public TipoDocCargo(Integer id) {
        this.id = id;
    }

    public TipoDocCargo(Integer id, int documento, int cargo) {
        this.id = id;
        this.documento = documento;
        this.cargo = cargo;
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

    public int getCargo() {
        return cargo;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }

    public Boolean getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(Boolean obligatorio) {
        this.obligatorio = obligatorio;
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
        if (!(object instanceof TipoDocCargo)) {
            return false;
        }
        TipoDocCargo other = (TipoDocCargo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.TipoDocCargo[ id=" + id + " ]";
    }
    
}
