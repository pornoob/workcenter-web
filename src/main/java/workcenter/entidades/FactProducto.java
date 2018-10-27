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
import java.util.Objects;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "fact_productos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FactProducto.findAll", query = "SELECT f FROM FactProducto f ORDER BY f.nombre ASC"),
    @NamedQuery(name = "FactProducto.findByName", query = "SELECT f FROM FactProducto f WHERE UPPER(f.nombre) = UPPER(:name)")
})
public class FactProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "producto_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productoId;
    @Basic(optional = false)
    @Column(name = "codigo")
    private Long codigo;
    @Size(max = 200)
    @Column(name = "nombre")
    private String nombre;

    public FactProducto() {
    }

    public FactProducto(Long productoId) {
        this.productoId = productoId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.productoId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FactProducto other = (FactProducto) obj;
        if (this.productoId == null || other.productoId == null) {
            return false;
        }
        return Objects.equals(this.productoId, other.productoId);
    }

    @Override
    public String toString() {
        return "workcenter.entidades.FactProducto[ productoId=" + productoId + " ]";
    }
}
