/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "consumo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consumo.findAll", query = "SELECT c FROM Consumo c"),
    @NamedQuery(name = "Consumo.findById", query = "SELECT c FROM Consumo c WHERE c.id = :id"),
    @NamedQuery(name = "Consumo.findBySalida", query = "SELECT c FROM Consumo c WHERE c.salida = :salida"),
    @NamedQuery(name = "Consumo.findByExistencia", query = "SELECT c FROM Consumo c WHERE c.existencia = :existencia"),
    @NamedQuery(name = "Consumo.findByCantidad", query = "SELECT c FROM Consumo c WHERE c.cantidad = :cantidad"),
    @NamedQuery(name = "Consumo.findByEntregadoa", query = "SELECT c FROM Consumo c WHERE c.entregadoa = :entregadoa"),
    @NamedQuery(name = "Consumo.findByEntregadopor", query = "SELECT c FROM Consumo c WHERE c.entregadopor = :entregadopor"),
    @NamedQuery(name = "Consumo.findByPermisoespecial", query = "SELECT c FROM Consumo c WHERE c.permisoespecial = :permisoespecial")})
public class Consumo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "salida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date salida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "existencia")
    private int existencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "entregadoa")
    private String entregadoa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "entregadopor")
    private int entregadopor;
    @Column(name = "permisoespecial")
    private Integer permisoespecial;

    public Consumo() {
    }

    public Consumo(Integer id) {
        this.id = id;
    }

    public Consumo(Integer id, int existencia, int cantidad, String entregadoa, int entregadopor) {
        this.id = id;
        this.existencia = existencia;
        this.cantidad = cantidad;
        this.entregadoa = entregadoa;
        this.entregadopor = entregadopor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getSalida() {
        return salida;
    }

    public void setSalida(Date salida) {
        this.salida = salida;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEntregadoa() {
        return entregadoa;
    }

    public void setEntregadoa(String entregadoa) {
        this.entregadoa = entregadoa;
    }

    public int getEntregadopor() {
        return entregadopor;
    }

    public void setEntregadopor(int entregadopor) {
        this.entregadopor = entregadopor;
    }

    public Integer getPermisoespecial() {
        return permisoespecial;
    }

    public void setPermisoespecial(Integer permisoespecial) {
        this.permisoespecial = permisoespecial;
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
        if (!(object instanceof Consumo)) {
            return false;
        }
        Consumo other = (Consumo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Consumo[ id=" + id + " ]";
    }
    
}
