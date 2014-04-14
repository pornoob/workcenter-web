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
@Table(name = "tareas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tarea.findAll", query = "SELECT t FROM Tarea t"),
    @NamedQuery(name = "Tarea.findById", query = "SELECT t FROM Tarea t WHERE t.id = :id"),
    @NamedQuery(name = "Tarea.findByTicket", query = "SELECT t FROM Tarea t WHERE t.ticket = :ticket"),
    @NamedQuery(name = "Tarea.findByTicketrecargado", query = "SELECT t FROM Tarea t WHERE t.ticketrecargado = :ticketrecargado"),
    @NamedQuery(name = "Tarea.findByDuracion", query = "SELECT t FROM Tarea t WHERE t.duracion = :duracion"),
    @NamedQuery(name = "Tarea.findByNombre", query = "SELECT t FROM Tarea t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Tarea.findByDetalle", query = "SELECT t FROM Tarea t WHERE t.detalle = :detalle"),
    @NamedQuery(name = "Tarea.findByPrioridad", query = "SELECT t FROM Tarea t WHERE t.prioridad = :prioridad"),
    @NamedQuery(name = "Tarea.findByTareade", query = "SELECT t FROM Tarea t WHERE t.tareade = :tareade"),
    @NamedQuery(name = "Tarea.findByInicio", query = "SELECT t FROM Tarea t WHERE t.inicio = :inicio"),
    @NamedQuery(name = "Tarea.findByIniciolocal", query = "SELECT t FROM Tarea t WHERE t.iniciolocal = :iniciolocal"),
    @NamedQuery(name = "Tarea.findByEntrega", query = "SELECT t FROM Tarea t WHERE t.entrega = :entrega"),
    @NamedQuery(name = "Tarea.findByEntregalocal", query = "SELECT t FROM Tarea t WHERE t.entregalocal = :entregalocal")})
public class Tarea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ticket")
    private int ticket;
    @Column(name = "ticketrecargado")
    private Integer ticketrecargado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "duracion")
    private Double duracion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2000)
    @Column(name = "detalle")
    private String detalle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prioridad")
    private int prioridad;
    @Column(name = "tareade")
    private Integer tareade;
    @Column(name = "inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicio;
    @Column(name = "iniciolocal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iniciolocal;
    @Column(name = "entrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entrega;
    @Column(name = "entregalocal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entregalocal;

    public Tarea() {
    }

    public Tarea(Integer id) {
        this.id = id;
    }

    public Tarea(Integer id, int ticket, String nombre, int prioridad) {
        this.id = id;
        this.ticket = ticket;
        this.nombre = nombre;
        this.prioridad = prioridad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public Integer getTicketrecargado() {
        return ticketrecargado;
    }

    public void setTicketrecargado(Integer ticketrecargado) {
        this.ticketrecargado = ticketrecargado;
    }

    public Double getDuracion() {
        return duracion;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public Integer getTareade() {
        return tareade;
    }

    public void setTareade(Integer tareade) {
        this.tareade = tareade;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getIniciolocal() {
        return iniciolocal;
    }

    public void setIniciolocal(Date iniciolocal) {
        this.iniciolocal = iniciolocal;
    }

    public Date getEntrega() {
        return entrega;
    }

    public void setEntrega(Date entrega) {
        this.entrega = entrega;
    }

    public Date getEntregalocal() {
        return entregalocal;
    }

    public void setEntregalocal(Date entregalocal) {
        this.entregalocal = entregalocal;
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
        if (!(object instanceof Tarea)) {
            return false;
        }
        Tarea other = (Tarea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Tarea[ id=" + id + " ]";
    }
    
}
