/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa", referencedColumnName = "id")
    private Empresa empresa;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "contacto", referencedColumnName = "id")
    private Contacto contacto;

    public ContactoEmpresa() {
    }

    public ContactoEmpresa(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactoEmpresa that = (ContactoEmpresa) o;
        if (this.id == null || that.id == null) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        int hash = 123;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "workcenter.entities.ContactoEmpresa[ id=" + id + " ]";
    }
    
}
