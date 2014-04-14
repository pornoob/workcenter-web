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
@Table(name = "documentospauta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentoPauta.findAll", query = "SELECT d FROM DocumentoPauta d"),
    @NamedQuery(name = "DocumentoPauta.findById", query = "SELECT d FROM DocumentoPauta d WHERE d.id = :id"),
    @NamedQuery(name = "DocumentoPauta.findByPauta", query = "SELECT d FROM DocumentoPauta d WHERE d.pauta = :pauta"),
    @NamedQuery(name = "DocumentoPauta.findByDocumento", query = "SELECT d FROM DocumentoPauta d WHERE d.documento = :documento")})
public class DocumentoPauta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pauta")
    private int pauta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "documento")
    private int documento;

    public DocumentoPauta() {
    }

    public DocumentoPauta(Integer id) {
        this.id = id;
    }

    public DocumentoPauta(Integer id, int pauta, int documento) {
        this.id = id;
        this.pauta = pauta;
        this.documento = documento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPauta() {
        return pauta;
    }

    public void setPauta(int pauta) {
        this.pauta = pauta;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
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
        if (!(object instanceof DocumentoPauta)) {
            return false;
        }
        DocumentoPauta other = (DocumentoPauta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.DocumentoPauta[ id=" + id + " ]";
    }
    
}
