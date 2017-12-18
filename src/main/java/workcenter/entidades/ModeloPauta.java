/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "modelospautas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ModeloPauta.findAll", query = "SELECT m FROM ModeloPauta m"),
    @NamedQuery(name = "ModeloPauta.findById", query = "SELECT m FROM ModeloPauta m WHERE m.id = :id"),
    @NamedQuery(name = "ModeloPauta.findByModelo", query = "SELECT m FROM ModeloPauta m WHERE m.modelo = :modelo"),
    @NamedQuery(name = "ModeloPauta.findByPauta", query = "SELECT m FROM ModeloPauta m WHERE m.pauta = :pauta")})
public class ModeloPauta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "modelo")
    private Integer modelo;
    @Column(name = "pauta")
    private Integer pauta;

    public ModeloPauta() {
    }

    public ModeloPauta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModelo() {
        return modelo;
    }

    public void setModelo(Integer modelo) {
        this.modelo = modelo;
    }

    public Integer getPauta() {
        return pauta;
    }

    public void setPauta(Integer pauta) {
        this.pauta = pauta;
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
        if (!(object instanceof ModeloPauta)) {
            return false;
        }
        ModeloPauta other = (ModeloPauta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.ModeloPauta[ id=" + id + " ]";
    }
    
}
