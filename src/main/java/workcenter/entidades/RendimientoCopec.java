/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workcenter.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author colivares
 */
@Entity
@Table(name = "rendimientos_copec")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RendimientoCopec.findAll", query = "SELECT r FROM RendimientoCopec r"),
    @NamedQuery(name = "RendimientoCopec.findById", query = "SELECT r FROM RendimientoCopec r WHERE r.id = :id"),
    @NamedQuery(name = "RendimientoCopec.findByDepartamento", query = "SELECT r FROM RendimientoCopec r WHERE r.departamento = :departamento"),
    @NamedQuery(name = "RendimientoCopec.findByPatente", query = "SELECT r FROM RendimientoCopec r WHERE r.patente = :patente"),
    @NamedQuery(name = "RendimientoCopec.findByVehiculo", query = "SELECT r FROM RendimientoCopec r WHERE r.vehiculo = :vehiculo"),
    @NamedQuery(name = "RendimientoCopec.findByTarjeta", query = "SELECT r FROM RendimientoCopec r WHERE r.tarjeta = :tarjeta"),
    @NamedQuery(name = "RendimientoCopec.findByFecha", query = "SELECT r FROM RendimientoCopec r WHERE r.fecha = :fecha"),
    @NamedQuery(name = "RendimientoCopec.findByEstacionDeServicio", query = "SELECT r FROM RendimientoCopec r WHERE r.estacionDeServicio = :estacionDeServicio"),
    @NamedQuery(name = "RendimientoCopec.findByGuiaDespacho", query = "SELECT r FROM RendimientoCopec r WHERE r.guiaDespacho = :guiaDespacho"),
    @NamedQuery(name = "RendimientoCopec.findByPrecio", query = "SELECT r FROM RendimientoCopec r WHERE r.precio = :precio"),
    @NamedQuery(name = "RendimientoCopec.findByLitros", query = "SELECT r FROM RendimientoCopec r WHERE r.litros = :litros"),
    @NamedQuery(name = "RendimientoCopec.findByMonto", query = "SELECT r FROM RendimientoCopec r WHERE r.monto = :monto"),
    @NamedQuery(name = "RendimientoCopec.findByOdometro", query = "SELECT r FROM RendimientoCopec r WHERE r.odometro = :odometro"),
    @NamedQuery(name = "RendimientoCopec.findByKmLt", query = "SELECT r FROM RendimientoCopec r WHERE r.kmLt = :kmLt"),
    @NamedQuery(name = "RendimientoCopec.findByPesosKm", query = "SELECT r FROM RendimientoCopec r WHERE r.pesosKm = :pesosKm")})
public class RendimientoCopec implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "departamento")
    private Integer departamento;
    @Size(max = 7)
    @Column(name = "patente")
    private String patente;
    @Column(name = "vehiculo")
    private Integer vehiculo;
    @Size(max = 50)
    @Column(name = "tarjeta")
    private String tarjeta;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 300)
    @Column(name = "estacion_de_servicio")
    private String estacionDeServicio;
    @Column(name = "guia_despacho")
    private Integer guiaDespacho;
    @Column(name = "precio")
    private Integer precio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "litros")
    private Float litros;
    @Column(name = "monto")
    private Integer monto;
    @Column(name = "odometro")
    private Integer odometro;
    @Column(name = "km_lt")
    private Float kmLt;
    @Column(name = "pesos_km")
    private Float pesosKm;

    public RendimientoCopec() {
    }

    public RendimientoCopec(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Integer departamento) {
        this.departamento = departamento;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public Integer getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Integer vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstacionDeServicio() {
        return estacionDeServicio;
    }

    public void setEstacionDeServicio(String estacionDeServicio) {
        this.estacionDeServicio = estacionDeServicio;
    }

    public Integer getGuiaDespacho() {
        return guiaDespacho;
    }

    public void setGuiaDespacho(Integer guiaDespacho) {
        this.guiaDespacho = guiaDespacho;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Float getLitros() {
        return litros;
    }

    public void setLitros(Float litros) {
        this.litros = litros;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public Integer getOdometro() {
        return odometro;
    }

    public void setOdometro(Integer odometro) {
        this.odometro = odometro;
    }

    public Float getKmLt() {
        return kmLt;
    }

    public void setKmLt(Float kmLt) {
        this.kmLt = kmLt;
    }

    public Float getPesosKm() {
        return pesosKm;
    }

    public void setPesosKm(Float pesosKm) {
        this.pesosKm = pesosKm;
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
        if (!(object instanceof RendimientoCopec)) {
            return false;
        }
        RendimientoCopec other = (RendimientoCopec) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.RendimientoCopec[ id=" + id + " ]";
    }

}
