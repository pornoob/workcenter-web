/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "cargos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cargo.findAll", query = "SELECT c FROM Cargo c"),
    @NamedQuery(name = "Cargo.findById", query = "SELECT c FROM Cargo c WHERE c.id = :id"),
    @NamedQuery(name = "Cargo.findByNombrecargo", query = "SELECT c FROM Cargo c WHERE c.nombrecargo = :nombrecargo"),
    @NamedQuery(name = "Cargo.findByResponsabilidades", query = "SELECT c FROM Cargo c WHERE c.responsabilidades = :responsabilidades"),
    @NamedQuery(name = "Cargo.findByDependencia", query = "SELECT c FROM Cargo c WHERE c.dependencia = :dependencia"),
    @NamedQuery(name = "Cargo.findByEducacion", query = "SELECT c FROM Cargo c WHERE c.educacion = :educacion"),
    @NamedQuery(name = "Cargo.findByFormacion", query = "SELECT c FROM Cargo c WHERE c.formacion = :formacion"),
    @NamedQuery(name = "Cargo.findByExperiencia", query = "SELECT c FROM Cargo c WHERE c.experiencia = :experiencia"),
    @NamedQuery(name = "Cargo.findByExposicion", query = "SELECT c FROM Cargo c WHERE c.exposicion = :exposicion")})
public class Cargo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 70)
    @Column(name = "nombrecargo")
    private String nombrecargo;
    @Size(max = 500)
    @Column(name = "responsabilidades")
    private String responsabilidades;
    @Size(max = 100)
    @Column(name = "dependencia")
    private String dependencia;
    @Size(max = 100)
    @Column(name = "educacion")
    private String educacion;
    @Size(max = 100)
    @Column(name = "formacion")
    private String formacion;
    @Size(max = 200)
    @Column(name = "experiencia")
    private String experiencia;
    @Column(name = "exposicion")
    private Integer exposicion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargo")
    private Collection<ContratoPersonal> contratospersonalCollection;

    public Cargo() {
    }

    public Cargo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombrecargo() {
        return nombrecargo;
    }

    public void setNombrecargo(String nombrecargo) {
        this.nombrecargo = nombrecargo;
    }

    public String getResponsabilidades() {
        return responsabilidades;
    }

    public void setResponsabilidades(String responsabilidades) {
        this.responsabilidades = responsabilidades;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public String getEducacion() {
        return educacion;
    }

    public void setEducacion(String educacion) {
        this.educacion = educacion;
    }

    public String getFormacion() {
        return formacion;
    }

    public void setFormacion(String formacion) {
        this.formacion = formacion;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public Integer getExposicion() {
        return exposicion;
    }

    public void setExposicion(Integer exposicion) {
        this.exposicion = exposicion;
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
        if (!(object instanceof Cargo)) {
            return false;
        }
        Cargo other = (Cargo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Cargo[ id=" + id + " ]";
    }
    
}
