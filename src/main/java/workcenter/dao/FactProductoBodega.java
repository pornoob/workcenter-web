/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.dao;

import workcenter.entidades.FactProducto;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "FACT_PRODUCTO_BODEGA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FactProductoBodega.findAll", query = "SELECT f FROM FactProductoBodega f ORDER BY f.producto.nombre ASC ")})
public class FactProductoBodega implements Serializable {

    private static final long serialVersionUID = -5173357582492589643L;
    @Id
    @JoinColumn(name = "producto_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private FactProducto producto;
    @Size(max = 70)
    @Column(name = "proveedores")
    private String proveedores;
    @Column(name = "precio_unitario")
    private Long precioUnitario;
    @Column(name = "cantidad")
    private Integer cantidad;

    public FactProductoBodega() {
    }

    public String getProveedores() {
        return proveedores;
    }

    public void setProveedores(String proveedores) {
        this.proveedores = proveedores;
    }

    public Long getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Long precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public FactProducto getProducto() {
        return producto;
    }

    public void setProducto(FactProducto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
}
