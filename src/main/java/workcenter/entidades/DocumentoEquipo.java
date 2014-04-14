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
@Table(name = "documentosequipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentoEquipo.findAll", query = "SELECT d FROM DocumentoEquipo d"),
    @NamedQuery(name = "DocumentoEquipo.findById", query = "SELECT d FROM DocumentoEquipo d WHERE d.id = :id"),
    @NamedQuery(name = "DocumentoEquipo.findByPatente", query = "SELECT d FROM DocumentoEquipo d WHERE d.patente = :patente"),
    @NamedQuery(name = "DocumentoEquipo.findByNumero", query = "SELECT d FROM DocumentoEquipo d WHERE d.numero = :numero"),
    @NamedQuery(name = "DocumentoEquipo.findByTipo", query = "SELECT d FROM DocumentoEquipo d WHERE d.tipo = :tipo"),
    @NamedQuery(name = "DocumentoEquipo.findByVencimiento", query = "SELECT d FROM DocumentoEquipo d WHERE d.vencimiento = :vencimiento"),
    @NamedQuery(name = "DocumentoEquipo.findByArchivo", query = "SELECT d FROM DocumentoEquipo d WHERE d.archivo = :archivo")})
public class DocumentoEquipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "patente")
    private String patente;
    @Size(max = 25)
    @Column(name = "numero")
    private String numero;
    @Column(name = "tipo")
    private Integer tipo;
    @Column(name = "vencimiento")
    @Temporal(TemporalType.DATE)
    private Date vencimiento;
    @Size(max = 300)
    @Column(name = "archivo")
    private String archivo;

    public DocumentoEquipo() {
    }

    public DocumentoEquipo(Integer id) {
        this.id = id;
    }

    public DocumentoEquipo(Integer id, String patente) {
        this.id = id;
        this.patente = patente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
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
        if (!(object instanceof DocumentoEquipo)) {
            return false;
        }
        DocumentoEquipo other = (DocumentoEquipo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.DocumentoEquipo[ id=" + id + " ]";
    }
    
}
