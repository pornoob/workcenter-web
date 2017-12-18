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
import java.util.Date;

/**
 * @author claudio
 */
@Entity
@Table(name = "variables")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Variable.findAll", query = "SELECT v FROM Variable v"),
        @NamedQuery(name = "Variable.findById", query = "SELECT v FROM Variable v WHERE v.id = :id"),
        @NamedQuery(name = "Variable.findByLlave", query = "SELECT v FROM Variable v WHERE v.llave = :llave"),
        @NamedQuery(name = "Variable.findActualByLlave", query = "SELECT v FROM Variable v WHERE v.llave = :llave ORDER BY v.fecha DESC"),
        @NamedQuery(
                name = "Variable.findByLlaveMesAnio",
                query = "SELECT v FROM Variable v WHERE v.llave=:llave and YEAR(v.fecha) = :anio and MONTH(v.fecha) = :mes"
        )
})
public class Variable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "llave")
    private String llave;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "valor")
    private String valor;
    @Size(max = 70)
    @Column(name = "ambito")
    private String ambito;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rut")
    private int rut;

    public Variable() {
    }

    public Variable(Integer id) {
        this.id = id;
    }

    public Variable(Integer id, String llave, String valor, int rut) {
        this.id = id;
        this.llave = llave;
        this.valor = valor;
        this.rut = rut;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Variable)) {
            return false;
        }
        Variable other = (Variable) object;
        if (this.getId() == null || other.getId() == null) return false;
        else return this.getId().intValue() == other.getId().intValue();
    }

    @Override
    public String toString() {
        return "workcenter.entities.Variable[ id=" + id + " ]";
    }

}
