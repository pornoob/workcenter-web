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

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "clasificacionproducto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClasificacionProducto.findAll", query = "SELECT c FROM ClasificacionProducto c"),
    @NamedQuery(name = "ClasificacionProducto.findById", query = "SELECT c FROM ClasificacionProducto c WHERE c.id = :id"),
    @NamedQuery(name = "ClasificacionProducto.findByEtiqueta", query = "SELECT c FROM ClasificacionProducto c WHERE c.etiqueta = :etiqueta"),
    @NamedQuery(name = "ClasificacionProducto.findByCodigo", query = "SELECT c FROM ClasificacionProducto c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "ClasificacionProducto.findByDescripcion", query = "SELECT c FROM ClasificacionProducto c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "ClasificacionProducto.findByPadre", query = "SELECT c FROM ClasificacionProducto c WHERE c.padre = :padre"),
    @NamedQuery(name = "ClasificacionProducto.findByDiasdevida", query = "SELECT c FROM ClasificacionProducto c WHERE c.diasdevida = :diasdevida"),
    @NamedQuery(name = "ClasificacionProducto.findByBodega", query = "SELECT c FROM ClasificacionProducto c WHERE c.bodega = :bodega")})
public class ClasificacionProducto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "etiqueta")
    private String etiqueta;
    @Size(max = 10)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "padre")
    private Integer padre;
    @Column(name = "diasdevida")
    private Integer diasdevida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bodega")
    private int bodega;

    public ClasificacionProducto() {
    }

    public ClasificacionProducto(Integer id) {
        this.id = id;
    }

    public ClasificacionProducto(Integer id, String etiqueta, int bodega) {
        this.id = id;
        this.etiqueta = etiqueta;
        this.bodega = bodega;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPadre() {
        return padre;
    }

    public void setPadre(Integer padre) {
        this.padre = padre;
    }

    public Integer getDiasdevida() {
        return diasdevida;
    }

    public void setDiasdevida(Integer diasdevida) {
        this.diasdevida = diasdevida;
    }

    public int getBodega() {
        return bodega;
    }

    public void setBodega(int bodega) {
        this.bodega = bodega;
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
        if (!(object instanceof ClasificacionProducto)) {
            return false;
        }
        ClasificacionProducto other = (ClasificacionProducto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.ClasificacionProducto[ id=" + id + " ]";
    }
    
}
