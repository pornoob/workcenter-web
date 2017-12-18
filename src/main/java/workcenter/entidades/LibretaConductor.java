/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "libretasconductores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LibretaConductor.findAll", query = "SELECT l FROM LibretaConductor l"),
    @NamedQuery(name = "LibretaConductor.findById", query = "SELECT l FROM LibretaConductor l WHERE l.id = :id"),
    @NamedQuery(name = "LibretaConductor.findByArchivo", query = "SELECT l FROM LibretaConductor l WHERE l.archivo = :archivo"),
    @NamedQuery(name = "LibretaConductor.findByConductor", query = "SELECT l FROM LibretaConductor l WHERE l.conductor = :conductor")})
public class LibretaConductor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 300)
    @Column(name = "archivo")
    private String archivo;
    @JoinColumn(name = "conductor", referencedColumnName = "rut")
    @ManyToOne(fetch = FetchType.LAZY)
    private Personal conductor;

    public LibretaConductor() {
    }

    public LibretaConductor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public Personal getConductor() {
        return conductor;
    }

    public void setConductor(Personal conductor) {
        this.conductor = conductor;
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
        if (!(object instanceof LibretaConductor)) {
            return false;
        }
        LibretaConductor other = (LibretaConductor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.LibretaConductor[ id=" + id + " ]";
    }
    
}
