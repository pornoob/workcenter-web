/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "contactosempresas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContactoEmpresa.findAll", query = "SELECT c FROM ContactoEmpresa c"),
    @NamedQuery(name = "ContactoEmpresa.findById", query = "SELECT c FROM ContactoEmpresa c WHERE c.id = :id"),
    @NamedQuery(name = "ContactoEmpresa.findByEmpresa", query = "SELECT c FROM ContactoEmpresa c WHERE c.empresa = :empresa"),
    @NamedQuery(name = "ContactoEmpresa.findByContacto", query = "SELECT c FROM ContactoEmpresa c WHERE c.contacto = :contacto")})
public class ContactoEmpresa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "empresa")
    private int empresa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contacto")
    private int contacto;

    public ContactoEmpresa() {
    }

    public ContactoEmpresa(Integer id) {
        this.id = id;
    }

    public ContactoEmpresa(Integer id, int empresa, int contacto) {
        this.id = id;
        this.empresa = empresa;
        this.contacto = contacto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }

    public int getContacto() {
        return contacto;
    }

    public void setContacto(int contacto) {
        this.contacto = contacto;
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
        if (!(object instanceof ContactoEmpresa)) {
            return false;
        }
        ContactoEmpresa other = (ContactoEmpresa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.ContactoEmpresa[ id=" + id + " ]";
    }
    
}
