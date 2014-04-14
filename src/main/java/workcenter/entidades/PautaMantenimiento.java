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
@Table(name = "pautasmantenimientos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PautaMantenimiento.findAll", query = "SELECT p FROM PautaMantenimiento p"),
    @NamedQuery(name = "PautaMantenimiento.findById", query = "SELECT p FROM PautaMantenimiento p WHERE p.id = :id"),
    @NamedQuery(name = "PautaMantenimiento.findByNombre", query = "SELECT p FROM PautaMantenimiento p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PautaMantenimiento.findByDocumento", query = "SELECT p FROM PautaMantenimiento p WHERE p.documento = :documento"),
    @NamedQuery(name = "PautaMantenimiento.findByKms", query = "SELECT p FROM PautaMantenimiento p WHERE p.kms = :kms"),
    @NamedQuery(name = "PautaMantenimiento.findByAdicional", query = "SELECT p FROM PautaMantenimiento p WHERE p.adicional = :adicional")})
public class PautaMantenimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "documento")
    private String documento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kms")
    private int kms;
    @Column(name = "adicional")
    private Integer adicional;

    public PautaMantenimiento() {
    }

    public PautaMantenimiento(Integer id) {
        this.id = id;
    }

    public PautaMantenimiento(Integer id, String nombre, String documento, int kms) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.kms = kms;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getKms() {
        return kms;
    }

    public void setKms(int kms) {
        this.kms = kms;
    }

    public Integer getAdicional() {
        return adicional;
    }

    public void setAdicional(Integer adicional) {
        this.adicional = adicional;
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
        if (!(object instanceof PautaMantenimiento)) {
            return false;
        }
        PautaMantenimiento other = (PautaMantenimiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.PautaMantenimiento[ id=" + id + " ]";
    }
    
}
