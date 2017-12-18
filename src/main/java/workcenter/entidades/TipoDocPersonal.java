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
 * @author claudio
 */
@Entity
@Table(name = "tiposdocpersonal")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "TipoDocPersonal.findAll", query = "SELECT t FROM TipoDocPersonal t"),
        @NamedQuery(name = "TipoDocPersonal.findById", query = "SELECT t FROM TipoDocPersonal t WHERE t.id = :id"),
        @NamedQuery(name = "TipoDocPersonal.findByEtiqueta", query = "SELECT t FROM TipoDocPersonal t WHERE t.etiqueta = :etiqueta"),
        @NamedQuery(name = "TipoDocPersonal.findByDescripcion", query = "SELECT t FROM TipoDocPersonal t WHERE t.descripcion = :descripcion"),
        @NamedQuery(name = "TipoDocPersonal.findByDiasalerta", query = "SELECT t FROM TipoDocPersonal t WHERE t.diasalerta = :diasalerta")})
public class TipoDocPersonal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Size(max = 70)
    @Column(name = "etiqueta")
    private String etiqueta;
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "diasalerta")
    private Integer diasalerta;

    public TipoDocPersonal() {
    }

    public TipoDocPersonal(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDiasalerta() {
        return diasalerta;
    }

    public void setDiasalerta(Integer diasalerta) {
        this.diasalerta = diasalerta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TipoDocPersonal)) {
            return false;
        }
        TipoDocPersonal other = (TipoDocPersonal) object;
        if (this.getId() == null || other.getId() == null) return false;
        else return this.getId().intValue() == other.getId().intValue();
    }

    @Override
    public String toString() {
        return "workcenter.entities.TipoDocPersonal[ id=" + id + " ]";
    }

}
