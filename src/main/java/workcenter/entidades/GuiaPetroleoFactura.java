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
@Table(name = "guiasdepetroleoporfactura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GuiaPetroleoFactura.findAll", query = "SELECT g FROM GuiaPetroleoFactura g"),
    @NamedQuery(name = "GuiaPetroleoFactura.findById", query = "SELECT g FROM GuiaPetroleoFactura g WHERE g.id = :id"),
    @NamedQuery(name = "GuiaPetroleoFactura.findByGuiapetr", query = "SELECT g FROM GuiaPetroleoFactura g WHERE g.guiapetr = :guiapetr"),
    @NamedQuery(name = "GuiaPetroleoFactura.findByFactura", query = "SELECT g FROM GuiaPetroleoFactura g WHERE g.factura = :factura")})
public class GuiaPetroleoFactura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "guiapetr")
    private Integer guiapetr;
    @Column(name = "factura")
    private Integer factura;

    public GuiaPetroleoFactura() {
    }

    public GuiaPetroleoFactura(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGuiapetr() {
        return guiapetr;
    }

    public void setGuiapetr(Integer guiapetr) {
        this.guiapetr = guiapetr;
    }

    public Integer getFactura() {
        return factura;
    }

    public void setFactura(Integer factura) {
        this.factura = factura;
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
        if (!(object instanceof GuiaPetroleoFactura)) {
            return false;
        }
        GuiaPetroleoFactura other = (GuiaPetroleoFactura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.GuiaPetroleoFactura[ id=" + id + " ]";
    }
    
}
