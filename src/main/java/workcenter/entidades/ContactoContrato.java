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
@Table(name = "contactoscontratos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContactoContrato.findAll", query = "SELECT c FROM ContactoContrato c"),
    @NamedQuery(name = "ContactoContrato.findById", query = "SELECT c FROM ContactoContrato c WHERE c.id = :id"),
    @NamedQuery(name = "ContactoContrato.findByContrato", query = "SELECT c FROM ContactoContrato c WHERE c.contrato = :contrato"),
    @NamedQuery(name = "ContactoContrato.findByContacto", query = "SELECT c FROM ContactoContrato c WHERE c.contacto = :contacto")})
public class ContactoContrato implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contrato")
    private int contrato;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contacto")
    private int contacto;

    public ContactoContrato() {
    }

    public ContactoContrato(Integer id) {
        this.id = id;
    }

    public ContactoContrato(Integer id, int contrato, int contacto) {
        this.id = id;
        this.contrato = contrato;
        this.contacto = contacto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getContrato() {
        return contrato;
    }

    public void setContrato(int contrato) {
        this.contrato = contrato;
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
        if (!(object instanceof ContactoContrato)) {
            return false;
        }
        ContactoContrato other = (ContactoContrato) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.ContactoContrato[ id=" + id + " ]";
    }
    
}
