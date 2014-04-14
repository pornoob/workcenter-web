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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "inputsformularios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InputFormulario.findAll", query = "SELECT i FROM InputFormulario i"),
    @NamedQuery(name = "InputFormulario.findById", query = "SELECT i FROM InputFormulario i WHERE i.id = :id"),
    @NamedQuery(name = "InputFormulario.findByTipodato", query = "SELECT i FROM InputFormulario i WHERE i.tipodato = :tipodato"),
    @NamedQuery(name = "InputFormulario.findByObligatorio", query = "SELECT i FROM InputFormulario i WHERE i.obligatorio = :obligatorio"),
    @NamedQuery(name = "InputFormulario.findByFormulario", query = "SELECT i FROM InputFormulario i WHERE i.formulario = :formulario"),
    @NamedQuery(name = "InputFormulario.findByName", query = "SELECT i FROM InputFormulario i WHERE i.name = :name")})
public class InputFormulario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipodato")
    private String tipodato;
    @Basic(optional = false)
    @NotNull
    @Column(name = "obligatorio")
    private boolean obligatorio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "formulario")
    private int formulario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "name")
    private String name;

    public InputFormulario() {
    }

    public InputFormulario(Integer id) {
        this.id = id;
    }

    public InputFormulario(Integer id, String tipodato, boolean obligatorio, int formulario, String name) {
        this.id = id;
        this.tipodato = tipodato;
        this.obligatorio = obligatorio;
        this.formulario = formulario;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipodato() {
        return tipodato;
    }

    public void setTipodato(String tipodato) {
        this.tipodato = tipodato;
    }

    public boolean getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public int getFormulario() {
        return formulario;
    }

    public void setFormulario(int formulario) {
        this.formulario = formulario;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(object instanceof InputFormulario)) {
            return false;
        }
        InputFormulario other = (InputFormulario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.InputFormulario[ id=" + id + " ]";
    }
    
}
