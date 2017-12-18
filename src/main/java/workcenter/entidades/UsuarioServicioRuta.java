/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author colivares
 */
@Entity
@Table(name = "usuario_servicio_ruta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioServicioRuta.findAll", query = "SELECT u FROM UsuarioServicioRuta u"),
    @NamedQuery(name = "UsuarioServicioRuta.findById", query = "SELECT u FROM UsuarioServicioRuta u WHERE u.id = :id"),
    @NamedQuery(name = "UsuarioServicioRuta.findByRut", query = "SELECT u FROM UsuarioServicioRuta u WHERE u.rut = :rut"),
    @NamedQuery(name = "UsuarioServicioRuta.findByIdServicio", query = "SELECT u FROM UsuarioServicioRuta u WHERE u.idServicio = :idServicio")})
public class UsuarioServicioRuta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "rut")
    private Long rut;
    @Column(name = "id_servicio")
    private Integer idServicio;

    public UsuarioServicioRuta() {
    }

    public UsuarioServicioRuta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getRut() {
        return rut;
    }

    public void setRut(Long rut) {
        this.rut = rut;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
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
        if (!(object instanceof UsuarioServicioRuta)) {
            return false;
        }
        UsuarioServicioRuta other = (UsuarioServicioRuta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.UsuarioServicioRuta[ id=" + id + " ]";
    }

}
