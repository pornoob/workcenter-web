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
 * @author colivares
 */
@Entity
@Table(name = "servicio_ruta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServicioRuta.findAll", query = "SELECT s FROM ServicioRuta s"),
    @NamedQuery(name = "ServicioRuta.findById", query = "SELECT s FROM ServicioRuta s WHERE s.id = :id"),
    @NamedQuery(name = "ServicioRuta.findByPatron", query = "SELECT s FROM ServicioRuta s WHERE s.patron = :patron")})
public class ServicioRuta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "patron")
    private String patron;
    @JoinColumn(name = "id_servicio", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)    private Servicio idServicio;

    public ServicioRuta() {
    }

    public ServicioRuta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatron() {
        return patron;
    }

    public void setPatron(String patron) {
        this.patron = patron;
    }

    public Servicio getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Servicio idServicio) {
        this.idServicio = idServicio;
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
        if (!(object instanceof ServicioRuta)) {
            return false;
        }
        ServicioRuta other = (ServicioRuta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.ServicioRuta[ id=" + id + " ]";
    }

}
