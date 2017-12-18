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
@Table(name = "prestamoscancelados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrestamoCancelado.findAll", query = "SELECT p FROM PrestamoCancelado p"),
    @NamedQuery(name = "PrestamoCancelado.findById", query = "SELECT p FROM PrestamoCancelado p WHERE p.id = :id"),
    @NamedQuery(name = "PrestamoCancelado.findByPrestamo", query = "SELECT p FROM PrestamoCancelado p WHERE p.prestamo = :prestamo"),
    @NamedQuery(name = "PrestamoCancelado.findByDevolucion", query = "SELECT p FROM PrestamoCancelado p WHERE p.devolucion = :devolucion"),
    @NamedQuery(name = "PrestamoCancelado.findByMontoexcedente", query = "SELECT p FROM PrestamoCancelado p WHERE p.montoexcedente = :montoexcedente"),
    @NamedQuery(name = "PrestamoCancelado.findByDetalle", query = "SELECT p FROM PrestamoCancelado p WHERE p.detalle = :detalle")})
public class PrestamoCancelado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "prestamo")
    private Integer prestamo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "devolucion")
    private int devolucion;
    @Column(name = "montoexcedente")
    private Integer montoexcedente;
    @Size(max = 150)
    @Column(name = "detalle")
    private String detalle;

    public PrestamoCancelado() {
    }

    public PrestamoCancelado(Integer id) {
        this.id = id;
    }

    public PrestamoCancelado(Integer id, Integer prestamo, int devolucion) {
        this.id = id;
        this.prestamo = prestamo;
        this.devolucion = devolucion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Integer prestamo) {
        this.prestamo = prestamo;
    }

    public int getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(int devolucion) {
        this.devolucion = devolucion;
    }

    public Integer getMontoexcedente() {
        return montoexcedente;
    }

    public void setMontoexcedente(Integer montoexcedente) {
        this.montoexcedente = montoexcedente;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
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
        if (!(object instanceof PrestamoCancelado)) {
            return false;
        }
        PrestamoCancelado other = (PrestamoCancelado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.PrestamoCancelado[ id=" + id + " ]";
    }
    
}
