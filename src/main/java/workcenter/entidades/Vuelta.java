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
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "vueltas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vuelta.findAll", query = "SELECT v FROM Vuelta v"),
    @NamedQuery(name = "Vuelta.findByOrdenDeCarga", query = "SELECT v FROM Vuelta v WHERE v.id = :ordenDeCarga"),
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
    @Column(name = "ordendecarga")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @NotNull
    @Size(min = 1, max = 7)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tracto", referencedColumnName = "patente")
    private Equipo tracto;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conductor", referencedColumnName = "rut")
    private Personal conductor;
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
    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "batea", referencedColumnName = "patente")
    private Equipo batea;
    @OneToMany(mappedBy = "ordencarga", fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<Producto> productosList;
    
    @OneToMany(mappedBy = "ordendecarga", fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<GuiaPetroleo> guiasPetroleo;

    public Vuelta() {
        this.tracto = new Equipo();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Equipo getTracto() {
        return tracto;
    }

    public void setTracto(Equipo tracto) {
        this.tracto = tracto;
    }

    public Personal getConductor() {
        return conductor;
    }

    public void setConductor(Personal conductor) {
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

    public Equipo getBatea() {
        return batea;
    }

    public void setBatea(Equipo batea) {
        this.batea = batea;
    }

    public List<Producto> getProductosList() {
        return productosList;
    }

    public void setProductosList(List<Producto> productosList) {
        this.productosList = productosList;
    }

    public List<GuiaPetroleo> getGuiasPetroleo() {
        return guiasPetroleo;
    }

    public void setGuiasPetroleo(List<GuiaPetroleo> guiasPetroleo) {
        this.guiasPetroleo = guiasPetroleo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final Vuelta other = (Vuelta) obj;
        if (this.id == null || other.id == null) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.Vuelta[ ordendecarga=" + id + " ]";
    }
    
}
