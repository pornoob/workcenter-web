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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "maestroGuias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Remuneracion.findAll", query = "SELECT m FROM Remuneracion m"),
    @NamedQuery(name = "Remuneracion.findByIdMaestro", query = "SELECT m FROM Remuneracion m WHERE m.idMaestro = :idMaestro"),
    @NamedQuery(name = "Remuneracion.findBySueldoBase", query = "SELECT m FROM Remuneracion m WHERE m.sueldoBase = :sueldoBase"),
    @NamedQuery(name = "Remuneracion.findByGratificacion", query = "SELECT m FROM Remuneracion m WHERE m.gratificacion = :gratificacion"),
    @NamedQuery(name = "Remuneracion.findByHoraEspera", query = "SELECT m FROM Remuneracion m WHERE m.horaEspera = :horaEspera"),
    @NamedQuery(name = "Remuneracion.findBySemanaCorrida", query = "SELECT m FROM Remuneracion m WHERE m.semanaCorrida = :semanaCorrida"),
    @NamedQuery(name = "Remuneracion.findByTotalViajes", query = "SELECT m FROM Remuneracion m WHERE m.totalViajes = :totalViajes"),
    @NamedQuery(name = "Remuneracion.findByHorasExtras", query = "SELECT m FROM Remuneracion m WHERE m.horasExtras = :horasExtras"),
    @NamedQuery(name = "Remuneracion.findByTotalImponible", query = "SELECT m FROM Remuneracion m WHERE m.totalImponible = :totalImponible"),
    @NamedQuery(name = "Remuneracion.findByViatico", query = "SELECT m FROM Remuneracion m WHERE m.viatico = :viatico"),
    @NamedQuery(name = "Remuneracion.findByRentaAfecta", query = "SELECT m FROM Remuneracion m WHERE m.rentaAfecta = :rentaAfecta"),
    @NamedQuery(name = "Remuneracion.findByImposiciones", query = "SELECT m FROM Remuneracion m WHERE m.imposiciones = :imposiciones"),
    @NamedQuery(name = "Remuneracion.findByTotalHaberes", query = "SELECT m FROM Remuneracion m WHERE m.totalHaberes = :totalHaberes"),
    @NamedQuery(name = "Remuneracion.findByImpUnico", query = "SELECT m FROM Remuneracion m WHERE m.impUnico = :impUnico"),
    @NamedQuery(name = "Remuneracion.findBySeguroCesantia", query = "SELECT m FROM Remuneracion m WHERE m.seguroCesantia = :seguroCesantia"),
    @NamedQuery(name = "Remuneracion.findByTotalDctos", query = "SELECT m FROM Remuneracion m WHERE m.totalDctos = :totalDctos"),
    @NamedQuery(name = "Remuneracion.findByAlcanceLiquido", query = "SELECT m FROM Remuneracion m WHERE m.alcanceLiquido = :alcanceLiquido"),
    @NamedQuery(name = "Remuneracion.findByDifCaja", query = "SELECT m FROM Remuneracion m WHERE m.difCaja = :difCaja"),
    @NamedQuery(name = "Remuneracion.findByAnticipoViatico", query = "SELECT m FROM Remuneracion m WHERE m.anticipoViatico = :anticipoViatico"),
    @NamedQuery(name = "Remuneracion.findByAnticipoSueldo", query = "SELECT m FROM Remuneracion m WHERE m.anticipoSueldo = :anticipoSueldo"),
    @NamedQuery(name = "Remuneracion.findByPagoPrestamo", query = "SELECT m FROM Remuneracion m WHERE m.pagoPrestamo = :pagoPrestamo"),
    @NamedQuery(name = "Remuneracion.findByLiqPagar", query = "SELECT m FROM Remuneracion m WHERE m.liqPagar = :liqPagar"),
    @NamedQuery(name = "Remuneracion.findByNomAFP", query = "SELECT m FROM Remuneracion m WHERE m.nomAFP = :nomAFP"),
    @NamedQuery(name = "Remuneracion.findByNomPrevision", query = "SELECT m FROM Remuneracion m WHERE m.nomPrevision = :nomPrevision"),
    @NamedQuery(name = "Remuneracion.findByPorcentajeAFP", query = "SELECT m FROM Remuneracion m WHERE m.porcentajeAFP = :porcentajeAFP"),
    @NamedQuery(name = "Remuneracion.findByPorcentajePrevision", query = "SELECT m FROM Remuneracion m WHERE m.porcentajePrevision = :porcentajePrevision"),
    @NamedQuery(name = "Remuneracion.findByDectoAFP", query = "SELECT m FROM Remuneracion m WHERE m.dectoAFP = :dectoAFP"),
    @NamedQuery(name = "Remuneracion.findByDctoPrevision", query = "SELECT m FROM Remuneracion m WHERE m.dctoPrevision = :dctoPrevision"),
    @NamedQuery(name = "Remuneracion.findByAporteMontoEmpresa", query = "SELECT m FROM Remuneracion m WHERE m.aporteMontoEmpresa = :aporteMontoEmpresa"),
    @NamedQuery(name = "Remuneracion.findByAporteMontoTrabajador", query = "SELECT m FROM Remuneracion m WHERE m.aporteMontoTrabajador = :aporteMontoTrabajador"),
    @NamedQuery(name = "Remuneracion.findByAportePorcEmpresa", query = "SELECT m FROM Remuneracion m WHERE m.aportePorcEmpresa = :aportePorcEmpresa"),
    @NamedQuery(name = "Remuneracion.findByAportePorcTrabajador", query = "SELECT m FROM Remuneracion m WHERE m.aportePorcTrabajador = :aportePorcTrabajador"),
    @NamedQuery(name = "Remuneracion.findByTotalesAFC", query = "SELECT m FROM Remuneracion m WHERE m.totalesAFC = :totalesAFC"),
    @NamedQuery(name = "Remuneracion.findByTotalMasHorasExtras", query = "SELECT m FROM Remuneracion m WHERE m.totalMasHorasExtras = :totalMasHorasExtras"),
    @NamedQuery(name = "Remuneracion.findByTotalNumeroViajes", query = "SELECT m FROM Remuneracion m WHERE m.totalNumeroViajes = :totalNumeroViajes"),
    @NamedQuery(name = "Remuneracion.findByTotalPeajes", query = "SELECT m FROM Remuneracion m WHERE m.totalPeajes = :totalPeajes"),
    @NamedQuery(name = "Remuneracion.findByTotalViaticos", query = "SELECT m FROM Remuneracion m WHERE m.totalViaticos = :totalViaticos"),
    @NamedQuery(name = "Remuneracion.findByTotalOtrosGastos", query = "SELECT m FROM Remuneracion m WHERE m.totalOtrosGastos = :totalOtrosGastos"),
    @NamedQuery(name = "Remuneracion.findByTotalTotalGastos", query = "SELECT m FROM Remuneracion m WHERE m.totalTotalGastos = :totalTotalGastos"),
    @NamedQuery(name = "Remuneracion.findByTotalDinerosEntregados", query = "SELECT m FROM Remuneracion m WHERE m.totalDinerosEntregados = :totalDinerosEntregados"),
    @NamedQuery(name = "Remuneracion.findByTotalSaldoViajes", query = "SELECT m FROM Remuneracion m WHERE m.totalSaldoViajes = :totalSaldoViajes"),
    @NamedQuery(name = "Remuneracion.findByEmpleador", query = "SELECT m FROM Remuneracion m WHERE m.empleador = :empleador"),
    @NamedQuery(name = "Remuneracion.findByRutEmpleador", query = "SELECT m FROM Remuneracion m WHERE m.rutEmpleador = :rutEmpleador"),
    @NamedQuery(name = "Remuneracion.findByHrsTrabajadas", query = "SELECT m FROM Remuneracion m WHERE m.hrsTrabajadas = :hrsTrabajadas"),
    @NamedQuery(name = "Remuneracion.findByDiasTrabajados", query = "SELECT m FROM Remuneracion m WHERE m.diasTrabajados = :diasTrabajados"),
    @NamedQuery(name = "Remuneracion.findBySueldoBaseTemp", query = "SELECT m FROM Remuneracion m WHERE m.sueldoBaseTemp = :sueldoBaseTemp"),
    @NamedQuery(name = "Remuneracion.findByOtrosDescuentosTemp", query = "SELECT m FROM Remuneracion m WHERE m.otrosDescuentosTemp = :otrosDescuentosTemp"),
    @NamedQuery(name = "Remuneracion.findByTotalProducido", query = "SELECT m FROM Remuneracion m WHERE m.totalProducido = :totalProducido"),
    @NamedQuery(name = "Remuneracion.findByAporteTrabajador", query = "SELECT m FROM Remuneracion m WHERE m.aporteTrabajador = :aporteTrabajador"),
    @NamedQuery(name = "Remuneracion.findByAporteEmpresa", query = "SELECT m FROM Remuneracion m WHERE m.aporteEmpresa = :aporteEmpresa"),
    @NamedQuery(name = "Remuneracion.findByPorcAporteTrabajador", query = "SELECT m FROM Remuneracion m WHERE m.porcAporteTrabajador = :porcAporteTrabajador"),
    @NamedQuery(name = "Remuneracion.findByPorcAporteEmpresa", query = "SELECT m FROM Remuneracion m WHERE m.porcAporteEmpresa = :porcAporteEmpresa"),
    @NamedQuery(name = "Remuneracion.findByDevolucionPrestamos", query = "SELECT m FROM Remuneracion m WHERE m.devolucionPrestamos = :devolucionPrestamos"),
    @NamedQuery(name = "Remuneracion.findByCantHorasTrabajadas", query = "SELECT m FROM Remuneracion m WHERE m.cantHorasTrabajadas = :cantHorasTrabajadas"),
    @NamedQuery(name = "Remuneracion.findByCantHorasEspera", query = "SELECT m FROM Remuneracion m WHERE m.cantHorasEspera = :cantHorasEspera"),
    @NamedQuery(name = "Remuneracion.findByCantHorasDescanso", query = "SELECT m FROM Remuneracion m WHERE m.cantHorasDescanso = :cantHorasDescanso"),
    @NamedQuery(name = "Remuneracion.findByPorcSis", query = "SELECT m FROM Remuneracion m WHERE m.porcSis = :porcSis"),
    @NamedQuery(name = "Remuneracion.findBySis", query = "SELECT m FROM Remuneracion m WHERE m.sis = :sis"),
    @NamedQuery(name = "Remuneracion.findByUf", query = "SELECT m FROM Remuneracion m WHERE m.uf = :uf"),
    @NamedQuery(name = "Remuneracion.findByUtm", query = "SELECT m FROM Remuneracion m WHERE m.utm = :utm"),
    @NamedQuery(name = "Remuneracion.findByFechaLiquidacion", query = "SELECT m FROM Remuneracion m WHERE m.fechaLiquidacion = :fechaLiquidacion"),
    @NamedQuery(name = "Remuneracion.findByPorcentaje", query = "SELECT m FROM Remuneracion m WHERE m.porcentaje = :porcentaje"),
    @NamedQuery(name = "Remuneracion.findByNombreArchivo", query = "SELECT m FROM Remuneracion m WHERE m.nombreArchivo = :nombreArchivo"),
    @NamedQuery(name = "Remuneracion.findByExtension", query = "SELECT m FROM Remuneracion m WHERE m.extension = :extension")})
public class Remuneracion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMaestro")
    private Long idMaestro;
    @Column(name = "sueldoBase")
    private Integer sueldoBase;
    @Column(name = "gratificacion")
    private Integer gratificacion;
    @Column(name = "horaEspera")
    private Integer horaEspera;
    @Column(name = "semanaCorrida")
    private Integer semanaCorrida;
    @Column(name = "totalViajes")
    private Integer totalViajes;
    @Column(name = "horasExtras")
    private Integer horasExtras;
    @Column(name = "totalImponible")
    private Integer totalImponible;
    @Column(name = "viatico")
    private Integer viatico;
    @Column(name = "rentaAfecta")
    private Integer rentaAfecta;
    @Column(name = "imposiciones")
    private Integer imposiciones;
    @Column(name = "totalHaberes")
    private Integer totalHaberes;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "impUnico")
    private Double impUnico;
    @Column(name = "seguroCesantia")
    private Integer seguroCesantia;
    @Column(name = "totalDctos")
    private Integer totalDctos;
    @Column(name = "alcanceLiquido")
    private Integer alcanceLiquido;
    @Column(name = "difCaja")
    private Integer difCaja;
    @Column(name = "anticipoViatico")
    private Integer anticipoViatico;
    @Column(name = "anticipoSueldo")
    private Integer anticipoSueldo;
    @Column(name = "pagoPrestamo")
    private Integer pagoPrestamo;
    @Column(name = "liqPagar")
    private Integer liqPagar;
    @Size(max = 5000)
    @Column(name = "nomAFP")
    private String nomAFP;
    @Size(max = 5000)
    @Column(name = "nomPrevision")
    private String nomPrevision;
    @Column(name = "porcentajeAFP")
    private Double porcentajeAFP;
    @Column(name = "porcentajePrevision")
    private Double porcentajePrevision;
    @Column(name = "dectoAFP")
    private Integer dectoAFP;
    @Column(name = "dctoPrevision")
    private Integer dctoPrevision;
    @Column(name = "aporteMontoEmpresa")
    private Integer aporteMontoEmpresa;
    @Column(name = "aporteMontoTrabajador")
    private Integer aporteMontoTrabajador;
    @Column(name = "aportePorcEmpresa")
    private Double aportePorcEmpresa;
    @Column(name = "aportePorcTrabajador")
    private Double aportePorcTrabajador;
    @Column(name = "totalesAFC")
    private Integer totalesAFC;
    @Column(name = "totalMasHorasExtras")
    private Integer totalMasHorasExtras;
    @Column(name = "totalNumeroViajes")
    private Integer totalNumeroViajes;
    @Column(name = "totalPeajes")
    private Integer totalPeajes;
    @Column(name = "totalViaticos")
    private Integer totalViaticos;
    @Column(name = "totalOtrosGastos")
    private Integer totalOtrosGastos;
    @Column(name = "totalTotalGastos")
    private Integer totalTotalGastos;
    @Column(name = "totalDinerosEntregados")
    private Integer totalDinerosEntregados;
    @Column(name = "totalSaldoViajes")
    private Integer totalSaldoViajes;
    @Size(max = 5000)
    @Column(name = "empleador")
    private String empleador;
    @Size(max = 5000)
    @Column(name = "rutEmpleador")
    private String rutEmpleador;
    @Column(name = "hrsTrabajadas")
    private Integer hrsTrabajadas;
    @Column(name = "diasTrabajados")
    private Integer diasTrabajados;
    @Column(name = "sueldoBaseTemp")
    private Integer sueldoBaseTemp;
    @Column(name = "otrosDescuentosTemp")
    private Integer otrosDescuentosTemp;
    @Column(name = "totalProducido")
    private Integer totalProducido;
    @Column(name = "aporteTrabajador")
    private Double aporteTrabajador;
    @Column(name = "aporteEmpresa")
    private Double aporteEmpresa;
    @Column(name = "porcAporteTrabajador")
    private Double porcAporteTrabajador;
    @Column(name = "porcAporteEmpresa")
    private Double porcAporteEmpresa;
    @Column(name = "devolucionPrestamos")
    private Integer devolucionPrestamos;
    @Column(name = "cantHorasTrabajadas")
    private Integer cantHorasTrabajadas;
    @Column(name = "cantHorasEspera")
    private Integer cantHorasEspera;
    @Column(name = "cantHorasDescanso")
    private Integer cantHorasDescanso;
    @Column(name = "porcSis")
    private Double porcSis;
    @Column(name = "sis")
    private Integer sis;
    @Size(max = 5000)
    @Column(name = "uf")
    private String uf;
    @Column(name = "utm")
    private Integer utm;
    @Column(name = "fechaLiquidacion")
    @Temporal(TemporalType.DATE)
    private Date fechaLiquidacion;
    @Column(name = "porcentaje")
    private Float porcentaje;
    @Lob
    @Column(name = "archivo")
    private byte[] archivo;
    @Size(max = 5000)
    @Column(name = "nombreArchivo")
    private String nombreArchivo;
    @Size(max = 500)
    @Column(name = "extension")
    private String extension;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMaestroGuia", fetch = FetchType.EAGER)
    private List<BonoDescuentoRemuneracion> remuneracionBonoDescuentoList;
    @JoinColumn(name = "idPersonal", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private Personal idPersonal;
    @Basic
    @Column(name = "generica")
    private Boolean esGenerica;

    public Remuneracion() {
    }

    public Remuneracion(Long idMaestro) {
        this.idMaestro = idMaestro;
    }

    public Long getIdMaestro() {
        return idMaestro;
    }

    public void setIdMaestro(Long idMaestro) {
        this.idMaestro = idMaestro;
    }

    public Integer getSueldoBase() {
        return sueldoBase;
    }

    public void setSueldoBase(Integer sueldoBase) {
        this.sueldoBase = sueldoBase;
    }

    public Integer getGratificacion() {
        return gratificacion;
    }

    public void setGratificacion(Integer gratificacion) {
        this.gratificacion = gratificacion;
    }

    public Integer getHoraEspera() {
        return horaEspera;
    }

    public void setHoraEspera(Integer horaEspera) {
        this.horaEspera = horaEspera;
    }

    public Integer getSemanaCorrida() {
        return semanaCorrida;
    }

    public void setSemanaCorrida(Integer semanaCorrida) {
        this.semanaCorrida = semanaCorrida;
    }

    public Integer getTotalViajes() {
        return totalViajes;
    }

    public void setTotalViajes(Integer totalViajes) {
        this.totalViajes = totalViajes;
    }

    public Integer getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(Integer horasExtras) {
        this.horasExtras = horasExtras;
    }

    public Integer getTotalImponible() {
        return totalImponible;
    }

    public void setTotalImponible(Integer totalImponible) {
        this.totalImponible = totalImponible;
    }

    public Integer getViatico() {
        return viatico;
    }

    public void setViatico(Integer viatico) {
        this.viatico = viatico;
    }

    public Integer getRentaAfecta() {
        return rentaAfecta;
    }

    public void setRentaAfecta(Integer rentaAfecta) {
        this.rentaAfecta = rentaAfecta;
    }

    public Integer getImposiciones() {
        return imposiciones;
    }

    public void setImposiciones(Integer imposiciones) {
        this.imposiciones = imposiciones;
    }

    public Integer getTotalHaberes() {
        return totalHaberes;
    }

    public void setTotalHaberes(Integer totalHaberes) {
        this.totalHaberes = totalHaberes;
    }

    public Double getImpUnico() {
        return impUnico;
    }

    public void setImpUnico(Double impUnico) {
        this.impUnico = impUnico;
    }

    public Integer getSeguroCesantia() {
        return seguroCesantia;
    }

    public void setSeguroCesantia(Integer seguroCesantia) {
        this.seguroCesantia = seguroCesantia;
    }

    public Integer getTotalDctos() {
        return totalDctos;
    }

    public void setTotalDctos(Integer totalDctos) {
        this.totalDctos = totalDctos;
    }

    public Integer getAlcanceLiquido() {
        return alcanceLiquido;
    }

    public void setAlcanceLiquido(Integer alcanceLiquido) {
        this.alcanceLiquido = alcanceLiquido;
    }

    public Integer getDifCaja() {
        return difCaja;
    }

    public void setDifCaja(Integer difCaja) {
        this.difCaja = difCaja;
    }

    public Integer getAnticipoViatico() {
        return anticipoViatico;
    }

    public void setAnticipoViatico(Integer anticipoViatico) {
        this.anticipoViatico = anticipoViatico;
    }

    public Integer getAnticipoSueldo() {
        return anticipoSueldo;
    }

    public void setAnticipoSueldo(Integer anticipoSueldo) {
        this.anticipoSueldo = anticipoSueldo;
    }

    public Integer getPagoPrestamo() {
        return pagoPrestamo;
    }

    public void setPagoPrestamo(Integer pagoPrestamo) {
        this.pagoPrestamo = pagoPrestamo;
    }

    public Integer getLiqPagar() {
        return liqPagar;
    }

    public void setLiqPagar(Integer liqPagar) {
        this.liqPagar = liqPagar;
    }

    public String getNomAFP() {
        return nomAFP;
    }

    public void setNomAFP(String nomAFP) {
        this.nomAFP = nomAFP;
    }

    public String getNomPrevision() {
        return nomPrevision;
    }

    public void setNomPrevision(String nomPrevision) {
        this.nomPrevision = nomPrevision;
    }

    public Double getPorcentajeAFP() {
        return porcentajeAFP;
    }

    public void setPorcentajeAFP(Double porcentajeAFP) {
        this.porcentajeAFP = porcentajeAFP;
    }

    public Double getPorcentajePrevision() {
        return porcentajePrevision;
    }

    public void setPorcentajePrevision(Double porcentajePrevision) {
        this.porcentajePrevision = porcentajePrevision;
    }

    public Integer getDectoAFP() {
        return dectoAFP;
    }

    public void setDectoAFP(Integer dectoAFP) {
        this.dectoAFP = dectoAFP;
    }

    public Integer getDctoPrevision() {
        return dctoPrevision;
    }

    public void setDctoPrevision(Integer dctoPrevision) {
        this.dctoPrevision = dctoPrevision;
    }

    public Integer getAporteMontoEmpresa() {
        return aporteMontoEmpresa;
    }

    public void setAporteMontoEmpresa(Integer aporteMontoEmpresa) {
        this.aporteMontoEmpresa = aporteMontoEmpresa;
    }

    public Integer getAporteMontoTrabajador() {
        return aporteMontoTrabajador;
    }

    public void setAporteMontoTrabajador(Integer aporteMontoTrabajador) {
        this.aporteMontoTrabajador = aporteMontoTrabajador;
    }

    public Double getAportePorcEmpresa() {
        return aportePorcEmpresa;
    }

    public void setAportePorcEmpresa(Double aportePorcEmpresa) {
        this.aportePorcEmpresa = aportePorcEmpresa;
    }

    public Double getAportePorcTrabajador() {
        return aportePorcTrabajador;
    }

    public void setAportePorcTrabajador(Double aportePorcTrabajador) {
        this.aportePorcTrabajador = aportePorcTrabajador;
    }

    public Integer getTotalesAFC() {
        return totalesAFC;
    }

    public void setTotalesAFC(Integer totalesAFC) {
        this.totalesAFC = totalesAFC;
    }

    public Integer getTotalMasHorasExtras() {
        return totalMasHorasExtras;
    }

    public void setTotalMasHorasExtras(Integer totalMasHorasExtras) {
        this.totalMasHorasExtras = totalMasHorasExtras;
    }

    public Integer getTotalNumeroViajes() {
        return totalNumeroViajes;
    }

    public void setTotalNumeroViajes(Integer totalNumeroViajes) {
        this.totalNumeroViajes = totalNumeroViajes;
    }

    public Integer getTotalPeajes() {
        return totalPeajes;
    }

    public void setTotalPeajes(Integer totalPeajes) {
        this.totalPeajes = totalPeajes;
    }

    public Integer getTotalViaticos() {
        return totalViaticos;
    }

    public void setTotalViaticos(Integer totalViaticos) {
        this.totalViaticos = totalViaticos;
    }

    public Integer getTotalOtrosGastos() {
        return totalOtrosGastos;
    }

    public void setTotalOtrosGastos(Integer totalOtrosGastos) {
        this.totalOtrosGastos = totalOtrosGastos;
    }

    public Integer getTotalTotalGastos() {
        return totalTotalGastos;
    }

    public void setTotalTotalGastos(Integer totalTotalGastos) {
        this.totalTotalGastos = totalTotalGastos;
    }

    public Integer getTotalDinerosEntregados() {
        return totalDinerosEntregados;
    }

    public void setTotalDinerosEntregados(Integer totalDinerosEntregados) {
        this.totalDinerosEntregados = totalDinerosEntregados;
    }

    public Integer getTotalSaldoViajes() {
        return totalSaldoViajes;
    }

    public void setTotalSaldoViajes(Integer totalSaldoViajes) {
        this.totalSaldoViajes = totalSaldoViajes;
    }

    public String getEmpleador() {
        return empleador;
    }

    public void setEmpleador(String empleador) {
        this.empleador = empleador;
    }

    public String getRutEmpleador() {
        return rutEmpleador;
    }

    public void setRutEmpleador(String rutEmpleador) {
        this.rutEmpleador = rutEmpleador;
    }

    public Integer getHrsTrabajadas() {
        return hrsTrabajadas;
    }

    public void setHrsTrabajadas(Integer hrsTrabajadas) {
        this.hrsTrabajadas = hrsTrabajadas;
    }

    public Integer getDiasTrabajados() {
        return diasTrabajados;
    }

    public void setDiasTrabajados(Integer diasTrabajados) {
        this.diasTrabajados = diasTrabajados;
    }

    public Integer getSueldoBaseTemp() {
        return sueldoBaseTemp;
    }

    public void setSueldoBaseTemp(Integer sueldoBaseTemp) {
        this.sueldoBaseTemp = sueldoBaseTemp;
    }

    public Integer getOtrosDescuentosTemp() {
        return otrosDescuentosTemp;
    }

    public void setOtrosDescuentosTemp(Integer otrosDescuentosTemp) {
        this.otrosDescuentosTemp = otrosDescuentosTemp;
    }

    public Integer getTotalProducido() {
        return totalProducido;
    }

    public void setTotalProducido(Integer totalProducido) {
        this.totalProducido = totalProducido;
    }

    public Double getAporteTrabajador() {
        return aporteTrabajador;
    }

    public void setAporteTrabajador(Double aporteTrabajador) {
        this.aporteTrabajador = aporteTrabajador;
    }

    public Double getAporteEmpresa() {
        return aporteEmpresa;
    }

    public void setAporteEmpresa(Double aporteEmpresa) {
        this.aporteEmpresa = aporteEmpresa;
    }

    public Double getPorcAporteTrabajador() {
        return porcAporteTrabajador;
    }

    public void setPorcAporteTrabajador(Double porcAporteTrabajador) {
        this.porcAporteTrabajador = porcAporteTrabajador;
    }

    public Double getPorcAporteEmpresa() {
        return porcAporteEmpresa;
    }

    public void setPorcAporteEmpresa(Double porcAporteEmpresa) {
        this.porcAporteEmpresa = porcAporteEmpresa;
    }

    public Integer getDevolucionPrestamos() {
        return devolucionPrestamos;
    }

    public void setDevolucionPrestamos(Integer devolucionPrestamos) {
        this.devolucionPrestamos = devolucionPrestamos;
    }

    public Integer getCantHorasTrabajadas() {
        return cantHorasTrabajadas;
    }

    public void setCantHorasTrabajadas(Integer cantHorasTrabajadas) {
        this.cantHorasTrabajadas = cantHorasTrabajadas;
    }

    public Integer getCantHorasEspera() {
        return cantHorasEspera;
    }

    public void setCantHorasEspera(Integer cantHorasEspera) {
        this.cantHorasEspera = cantHorasEspera;
    }

    public Integer getCantHorasDescanso() {
        return cantHorasDescanso;
    }

    public void setCantHorasDescanso(Integer cantHorasDescanso) {
        this.cantHorasDescanso = cantHorasDescanso;
    }

    public Double getPorcSis() {
        return porcSis;
    }

    public void setPorcSis(Double porcSis) {
        this.porcSis = porcSis;
    }

    public Integer getSis() {
        return sis;
    }

    public void setSis(Integer sis) {
        this.sis = sis;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Integer getUtm() {
        return utm;
    }

    public void setUtm(Integer utm) {
        this.utm = utm;
    }

    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public Float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @XmlTransient
    public List<BonoDescuentoRemuneracion> getRemuneracionBonoDescuentoList() {
        return remuneracionBonoDescuentoList;
    }

    public void setRemuneracionBonoDescuentoList(List<BonoDescuentoRemuneracion> maestroGuiaBonoDescuentoList) {
        this.remuneracionBonoDescuentoList = maestroGuiaBonoDescuentoList;
    }

    public Personal getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(Personal idPersonal) {
        this.idPersonal = idPersonal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMaestro != null ? idMaestro.hashCode() : 0);
        return hash;
    }

    public Boolean getEsGenerica() {
        return esGenerica;
    }

    public void setEsGenerica(Boolean esGenerica) {
        this.esGenerica = esGenerica;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Remuneracion)) {
            return false;
        }
        Remuneracion other = (Remuneracion) object;
        if (this.getIdMaestro() == null || other.getIdMaestro() == null) return false;
        else return this.getIdMaestro().intValue() == other.getIdMaestro().intValue();
    }

    @Override
    public String toString() {
        return "workcenter.entities.Remuneracion[ idMaestro=" + idMaestro + " ]";
    }
    
}
