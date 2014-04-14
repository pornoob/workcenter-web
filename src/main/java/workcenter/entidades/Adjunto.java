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
@Table(name = "adjuntos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adjunto.findAll", query = "SELECT a FROM Adjunto a"),
    @NamedQuery(name = "Adjunto.findById", query = "SELECT a FROM Adjunto a WHERE a.id = :id"),
    @NamedQuery(name = "Adjunto.findByTabla", query = "SELECT a FROM Adjunto a WHERE a.tabla = :tabla"),
    @NamedQuery(name = "Adjunto.findByFila", query = "SELECT a FROM Adjunto a WHERE a.fila = :fila"),
    @NamedQuery(name = "Adjunto.findByUrl", query = "SELECT a FROM Adjunto a WHERE a.url = :url"),
    @NamedQuery(name = "Adjunto.findByPeso", query = "SELECT a FROM Adjunto a WHERE a.peso = :peso"),
    @NamedQuery(name = "Adjunto.findByDescripcion", query = "SELECT a FROM Adjunto a WHERE a.descripcion = :descripcion")})
public class Adjunto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tabla")
    private String tabla;
    @Size(max = 15)
    @Column(name = "fila")
    private String fila;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "url")
    private String url;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "peso")
    private Double peso;
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;

    public Adjunto() {
    }

    public Adjunto(Integer id) {
        this.id = id;
    }

    public Adjunto(Integer id, String tabla, String url) {
        this.id = id;
        this.tabla = tabla;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof Adjunto)) {
            return false;
        }
        Adjunto other = (Adjunto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Adjunto[ id=" + id + " ]";
    }
    
}
