package workcenter.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 *
 * @author colivares
 */
@Entity
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByRut", query = "SELECT u FROM Usuario u WHERE u.rut = :rut"),
    @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password"),
    @NamedQuery(name = "Usuario.findByRutAndPassword", query = "SELECT u FROM Usuario u WHERE u.rut=:rut and u.password = :password")
})
public class Usuario implements Serializable {
    @OneToMany(mappedBy = "idUsuario", fetch = FetchType.LAZY)
    private Collection<GestionAlarmaGps> gestionAlarmaGpsCollection;
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "rut")
    private Long rut;
    @Size(max = 45)
    @Column(name = "password")
    private String password;
    @JoinColumn(name = "rut", referencedColumnName = "rut")
    @OneToOne(fetch = FetchType.LAZY)
    private Personal personal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Permiso> permisos;
    @Column(name = "habilitado")
    private Boolean habilitado;

    public Usuario() {
        habilitado = false;
    }

    public Usuario(Long rut) {
        this.rut = rut;
    }

    public Long getRut() {
        return rut;
    }

    public void setRut(Long rut) {
        this.rut = rut;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(Set<Permiso> permisos) {
        this.permisos = permisos;
    }

    public Boolean isHabilitado() {
        return habilitado;
    }
    
    public Boolean getHabilitado() {
        // solo por compatibilidad
        return isHabilitado();
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rut != null ? rut.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if (this.getRut() == null || other.getRut() == null) {
            return false;
        } else if (this.getRut().intValue() != other.getRut().intValue()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Usuario[ rut=" + rut + " ]";
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    @XmlTransient
    public Collection<GestionAlarmaGps> getGestionAlarmaGpsCollection() {
        return gestionAlarmaGpsCollection;
    }

    public void setGestionAlarmaGpsCollection(Collection<GestionAlarmaGps> gestionAlarmaGpsCollection) {
        this.gestionAlarmaGpsCollection = gestionAlarmaGpsCollection;
    }
}
