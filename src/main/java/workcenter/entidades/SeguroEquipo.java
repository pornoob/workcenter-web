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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "segurosequipos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SeguroEquipo.findAll", query = "SELECT s FROM SeguroEquipo s"),
    @NamedQuery(name = "SeguroEquipo.findById", query = "SELECT s FROM SeguroEquipo s WHERE s.id = :id"),
    @NamedQuery(name = "SeguroEquipo.findByEquipo", query = "SELECT s FROM SeguroEquipo s WHERE s.equipo = :equipo"),
    @NamedQuery(name = "SeguroEquipo.findByTenedor", query = "SELECT s FROM SeguroEquipo s WHERE s.tenedor = :tenedor"),
    @NamedQuery(name = "SeguroEquipo.findByContratante", query = "SELECT s FROM SeguroEquipo s WHERE s.contratante = :contratante"),
    @NamedQuery(name = "SeguroEquipo.findByAseguradora", query = "SELECT s FROM SeguroEquipo s WHERE s.aseguradora = :aseguradora"),
    @NamedQuery(name = "SeguroEquipo.findByNumero", query = "SELECT s FROM SeguroEquipo s WHERE s.numero = :numero"),
    @NamedQuery(name = "SeguroEquipo.findByCobertura", query = "SELECT s FROM SeguroEquipo s WHERE s.cobertura = :cobertura"),
    @NamedQuery(name = "SeguroEquipo.findByVencimiento", query = "SELECT s FROM SeguroEquipo s WHERE s.vencimiento = :vencimiento"),
    @NamedQuery(name = "SeguroEquipo.findByArchivo", query = "SELECT s FROM SeguroEquipo s WHERE s.archivo = :archivo")})
public class SeguroEquipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "equipo")
    private String equipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tenedor")
    private int tenedor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contratante")
    private int contratante;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aseguradora")
    private int aseguradora;
    @Size(max = 70)
    @Column(name = "numero")
    private String numero;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cobertura")
    private Double cobertura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vencimiento")
    @Temporal(TemporalType.DATE)
    private Date vencimiento;
    @Size(max = 300)
    @Column(name = "archivo")
    private String archivo;

    public SeguroEquipo() {
    }

    public SeguroEquipo(Integer id) {
        this.id = id;
    }

    public SeguroEquipo(Integer id, String equipo, int tenedor, int contratante, int aseguradora, Date vencimiento) {
        this.id = id;
        this.equipo = equipo;
        this.tenedor = tenedor;
        this.contratante = contratante;
        this.aseguradora = aseguradora;
        this.vencimiento = vencimiento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public int getTenedor() {
        return tenedor;
    }

    public void setTenedor(int tenedor) {
        this.tenedor = tenedor;
    }

    public int getContratante() {
        return contratante;
    }

    public void setContratante(int contratante) {
        this.contratante = contratante;
    }

    public int getAseguradora() {
        return aseguradora;
    }

    public void setAseguradora(int aseguradora) {
        this.aseguradora = aseguradora;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Double getCobertura() {
        return cobertura;
    }

    public void setCobertura(Double cobertura) {
        this.cobertura = cobertura;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
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
        if (!(object instanceof SeguroEquipo)) {
            return false;
        }
        SeguroEquipo other = (SeguroEquipo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.SeguroEquipo[ id=" + id + " ]";
    }
    
}
