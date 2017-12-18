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
 *
 * @author claudio
 */
@Entity
@Table(name = "tickets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ticket.findAll", query = "SELECT t FROM Ticket t"),
    @NamedQuery(name = "Ticket.findById", query = "SELECT t FROM Ticket t WHERE t.id = :id"),
    @NamedQuery(name = "Ticket.findByTitulo", query = "SELECT t FROM Ticket t WHERE t.titulo = :titulo"),
    @NamedQuery(name = "Ticket.findByEmisor", query = "SELECT t FROM Ticket t WHERE t.emisor = :emisor"),
    @NamedQuery(name = "Ticket.findByFecha", query = "SELECT t FROM Ticket t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "Ticket.findByTicketvinculado", query = "SELECT t FROM Ticket t WHERE t.ticketvinculado = :ticketvinculado"),
    @NamedQuery(name = "Ticket.findByEstado", query = "SELECT t FROM Ticket t WHERE t.estado = :estado"),
    @NamedQuery(name = "Ticket.findByFechalocal", query = "SELECT t FROM Ticket t WHERE t.fechalocal = :fechalocal")})
public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "emisor")
    private int emisor;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "ticketvinculado")
    private Integer ticketvinculado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private int estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechalocal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechalocal;

    public Ticket() {
    }

    public Ticket(Integer id) {
        this.id = id;
    }

    public Ticket(Integer id, String titulo, int emisor, int estado, Date fechalocal) {
        this.id = id;
        this.titulo = titulo;
        this.emisor = emisor;
        this.estado = estado;
        this.fechalocal = fechalocal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getEmisor() {
        return emisor;
    }

    public void setEmisor(int emisor) {
        this.emisor = emisor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getTicketvinculado() {
        return ticketvinculado;
    }

    public void setTicketvinculado(Integer ticketvinculado) {
        this.ticketvinculado = ticketvinculado;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFechalocal() {
        return fechalocal;
    }

    public void setFechalocal(Date fechalocal) {
        this.fechalocal = fechalocal;
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
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Ticket[ id=" + id + " ]";
    }
    
}
