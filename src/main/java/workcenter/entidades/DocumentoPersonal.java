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
@Table(name = "documentospersonal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentoPersonal.findAll", query = "SELECT d FROM DocumentoPersonal d"),
    @NamedQuery(name = "DocumentoPersonal.findById", query = "SELECT d FROM DocumentoPersonal d WHERE d.id = :id"),
    @NamedQuery(name = "DocumentoPersonal.findByNumero", query = "SELECT d FROM DocumentoPersonal d WHERE d.numero = :numero"),
    @NamedQuery(name = "DocumentoPersonal.findByVencimiento", query = "SELECT d FROM DocumentoPersonal d WHERE d.vencimiento = :vencimiento"),
    @NamedQuery(name = "DocumentoPersonal.findByTipo", query = "SELECT d FROM DocumentoPersonal d WHERE d.tipo = :tipo"),
    @NamedQuery(name = "DocumentoPersonal.findByPersonal", query = "SELECT d FROM DocumentoPersonal d WHERE d.personal = :personal"),
    @NamedQuery(name = "DocumentoPersonal.findByArchivo", query = "SELECT d FROM DocumentoPersonal d WHERE d.archivo = :archivo")})
public class DocumentoPersonal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "numero")
    private String numero;
    @Column(name = "vencimiento")
    @Temporal(TemporalType.DATE)
    private Date vencimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo")
    private int tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "personal")
    private int personal;
    @Size(max = 300)
    @Column(name = "archivo")
    private String archivo;

    public DocumentoPersonal() {
    }

    public DocumentoPersonal(Integer id) {
        this.id = id;
    }

    public DocumentoPersonal(Integer id, int tipo, int personal) {
        this.id = id;
        this.tipo = tipo;
        this.personal = personal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getPersonal() {
        return personal;
    }

    public void setPersonal(int personal) {
        this.personal = personal;
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
        if (!(object instanceof DocumentoPersonal)) {
            return false;
        }
        DocumentoPersonal other = (DocumentoPersonal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.DocumentoPersonal[ id=" + id + " ]";
    }
    
}
