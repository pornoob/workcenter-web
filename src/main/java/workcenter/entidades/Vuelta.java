/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "vueltas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vuelta.findAll", query = "SELECT v FROM Vuelta v"),
    @NamedQuery(name = "Vuelta.findByOrdenDeCarga", query = "SELECT v FROM Vuelta v WHERE v.ordenDeCarga = :ordenDeCarga"),
    @NamedQuery(name = "Vuelta.findByFecha", query = "SELECT v FROM Vuelta v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "Vuelta.findByTracto", query = "SELECT v FROM Vuelta v WHERE v.tracto = :tracto"),
    @NamedQuery(name = "Vuelta.findByConductor", query = "SELECT v FROM Vuelta v WHERE v.conductor = :conductor"),
    @NamedQuery(name = "Vuelta.findByIngresadoPor", query = "SELECT v FROM Vuelta v WHERE v.ingresadoPor = :ingresadoPor"),
    @NamedQuery(name = "Vuelta.findByPeaje", query = "SELECT v FROM Vuelta v WHERE v.peaje = :peaje"),
    @NamedQuery(name = "Vuelta.findByViatico", query = "SELECT v FROM Vuelta v WHERE v.viatico = :viatico"),
    @NamedQuery(name = "Vuelta.findByOtrosGastos", query = "SELECT v FROM Vuelta v WHERE v.otrosGastos = :otrosGastos"),
    @NamedQuery(name = "Vuelta.findByTotalCombustible", query = "SELECT v FROM Vuelta v WHERE v.totalCombustible = :totalCombustible"),
    @NamedQuery(name = "Vuelta.findByTotalLitros", query = "SELECT v FROM Vuelta v WHERE v.totalLitros = :totalLitros"),
    @NamedQuery(name = "Vuelta.findByKmInicial", query = "SELECT v FROM Vuelta v WHERE v.kmInicial = :kmInicial"),
    @NamedQuery(name = "Vuelta.findByKmFinal", query = "SELECT v FROM Vuelta v WHERE v.kmFinal = :kmFinal"),
    @NamedQuery(name = "Vuelta.findByDineroEntregado", query = "SELECT v FROM Vuelta v WHERE v.dineroEntregado = :dineroEntregado"),
    @NamedQuery(name = "Vuelta.findByBatea", query = "SELECT v FROM Vuelta v WHERE v.batea = :batea")})
public class Vuelta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ordendecarga")
    private Integer ordenDeCarga;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "tracto")
    private String tracto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "conductor")
    private int conductor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ingresadopor")
    private int ingresadoPor;
    @Column(name = "peaje")
    private Integer peaje;
    @Column(name = "viatico")
    private Integer viatico;
    @Column(name = "otrosgastos")
    private Integer otrosGastos;
    @Column(name = "totalcombustible")
    private Integer totalCombustible;
    @Column(name = "totallitros")
    private Integer totalLitros;
    @Column(name = "kminicial")
    private Integer kmInicial;
    @Column(name = "kmfinal")
    private Integer kmFinal;
    @Column(name = "dineroentregado")
    private Integer dineroEntregado;
    @Size(max = 7)
    @Column(name = "batea")
    private String batea;
    @OneToMany(mappedBy = "ordencarga", fetch = FetchType.EAGER)
    private List<Producto> productosList;

    public Vuelta() {
    }

    public Integer getOrdenDeCarga() {
        return ordenDeCarga;
    }

    public void setOrdenDeCarga(Integer ordenDeCarga) {
        this.ordenDeCarga = ordenDeCarga;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTracto() {
        return tracto;
    }

    public void setTracto(String tracto) {
        this.tracto = tracto;
    }

    public int getConductor() {
        return conductor;
    }

    public void setConductor(int conductor) {
        this.conductor = conductor;
    }

    public int getIngresadoPor() {
        return ingresadoPor;
    }

    public void setIngresadoPor(int ingresadoPor) {
        this.ingresadoPor = ingresadoPor;
    }

    public Integer getPeaje() {
        return peaje;
    }

    public void setPeaje(Integer peaje) {
        this.peaje = peaje;
    }

    public Integer getViatico() {
        return viatico;
    }

    public void setViatico(Integer viatico) {
        this.viatico = viatico;
    }

    public Integer getOtrosGastos() {
        return otrosGastos;
    }

    public void setOtrosGastos(Integer otrosGastos) {
        this.otrosGastos = otrosGastos;
    }

    public Integer getTotalCombustible() {
        return totalCombustible;
    }

    public void setTotalCombustible(Integer totalCombustible) {
        this.totalCombustible = totalCombustible;
    }

    public Integer getTotalLitros() {
        return totalLitros;
    }

    public void setTotalLitros(Integer totalLitros) {
        this.totalLitros = totalLitros;
    }

    public Integer getKmInicial() {
        return kmInicial;
    }

    public void setKmInicial(Integer kmInicial) {
        this.kmInicial = kmInicial;
    }

    public Integer getKmFinal() {
        return kmFinal;
    }

    public void setKmFinal(Integer kmFinal) {
        this.kmFinal = kmFinal;
    }

    public Integer getDineroEntregado() {
        return dineroEntregado;
    }

    public void setDineroEntregado(Integer dineroEntregado) {
        this.dineroEntregado = dineroEntregado;
    }

    public String getBatea() {
        return batea;
    }

    public void setBatea(String batea) {
        this.batea = batea;
    }

    public List<Producto> getProductosList() {
        return productosList;
    }

    public void setProductosList(List<Producto> productosList) {
        this.productosList = productosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordenDeCarga != null ? ordenDeCarga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vuelta)) {
            return false;
        }
        Vuelta other = (Vuelta) object;
        if ((this.ordenDeCarga == null && other.ordenDeCarga != null) || (this.ordenDeCarga != null && !this.ordenDeCarga.equals(other.ordenDeCarga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Vuelta[ ordendecarga=" + ordenDeCarga + " ]";
    }
    
}
