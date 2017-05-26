/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "finiquitos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Finiquito.findAll", query = "SELECT f FROM Finiquito f")
    , @NamedQuery(name = "Finiquito.findById", query = "SELECT f FROM Finiquito f WHERE f.id = :id")
    , @NamedQuery(name = "Finiquito.findByPersonMonthYear", query = "SELECT f FROM Finiquito f WHERE f.trabajador = :person AND FUNCTION('YEAR', f.fechaFiniquito) = :year AND FUNCTION('MONTH', f.fechaFiniquito) = :month")
    , @NamedQuery(name = "Finiquito.findByPersonAndYear", query = "SELECT f FROM Finiquito f WHERE f.trabajador = :person AND FUNCTION('YEAR', f.fechaFiniquito) = :year")
    , @NamedQuery(name = "Finiquito.findByFactoryAndYear", query = "SELECT f FROM Finiquito f WHERE f.empleador = :factory and FUNCTION('YEAR', f.fechaFiniquito) = :year")
    , @NamedQuery(name = "Finiquito.findByMonthAndYear", query = "SELECT f FROM Finiquito f WHERE FUNCTION('MONTH', f.fechaFiniquito) = :month and FUNCTION('YEAR', f.fechaFiniquito) = :year ORDER BY f.fechaIngreso DESC")
})
public class Finiquito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fecha_ingreso", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_finiquito")
    @Temporal(TemporalType.DATE)
    private Date fechaFiniquito;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto")
    private int monto;
    
    @OneToOne
    @NotNull
    @JoinColumn(name = "rut_empleador", referencedColumnName = "rut")
    private Empresa empleador;
    
    @OneToOne
    @NotNull
    @JoinColumn(name = "rut_trabajador", referencedColumnName = "rut")
    private Personal trabajador;

    public Finiquito() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaFiniquito() {
        return fechaFiniquito;
    }

    public void setFechaFiniquito(Date fechaFiniquito) {
        this.fechaFiniquito = fechaFiniquito;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Empresa getEmpleador() {
        return empleador;
    }

    public void setEmpleador(Empresa empleador) {
        this.empleador = empleador;
    }

    public Personal getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Personal trabajador) {
        this.trabajador = trabajador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Finiquito other = (Finiquito) obj;
        
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.Finiquito[ id=" + id + " ]";
    }
    
}
