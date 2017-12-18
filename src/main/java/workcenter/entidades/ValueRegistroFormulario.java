/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "valuesregistrosformularios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValueRegistroFormulario.findAll", query = "SELECT v FROM ValueRegistroFormulario v"),
    @NamedQuery(name = "ValueRegistroFormulario.findById", query = "SELECT v FROM ValueRegistroFormulario v WHERE v.id = :id"),
    @NamedQuery(name = "ValueRegistroFormulario.findByRegistro", query = "SELECT v FROM ValueRegistroFormulario v WHERE v.registro = :registro"),
    @NamedQuery(name = "ValueRegistroFormulario.findByInput", query = "SELECT v FROM ValueRegistroFormulario v WHERE v.input = :input"),
    @NamedQuery(name = "ValueRegistroFormulario.findByValue", query = "SELECT v FROM ValueRegistroFormulario v WHERE v.value = :value")})
public class ValueRegistroFormulario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "registro")
    private int registro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "input")
    private int input;
    @Size(max = 1000)
    @Column(name = "value")
    private String value;

    public ValueRegistroFormulario() {
    }

    public ValueRegistroFormulario(Integer id) {
        this.id = id;
    }

    public ValueRegistroFormulario(Integer id, int registro, int input) {
        this.id = id;
        this.registro = registro;
        this.input = input;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRegistro() {
        return registro;
    }

    public void setRegistro(int registro) {
        this.registro = registro;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        if (!(object instanceof ValueRegistroFormulario)) {
            return false;
        }
        ValueRegistroFormulario other = (ValueRegistroFormulario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.ValueRegistroFormulario[ id=" + id + " ]";
    }
    
}
