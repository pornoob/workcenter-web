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
import java.util.Date;

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
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "numero")
    private String numero;
    @Column(name = "vencimiento")
    @Temporal(TemporalType.DATE)
    private Date vencimiento;
    @JoinColumn(name = "tipo", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TipoDocPersonal tipo;
    @JoinColumn(name = "personal", referencedColumnName = "rut")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Personal personal;
    @Size(max = 300)
    @Column(name = "archivo")
    private String archivo;

    public DocumentoPersonal() {
    }

    public DocumentoPersonal(Integer id) {
        this.id = id;
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

    public TipoDocPersonal getTipo() {
        return tipo;
    }

    public void setTipo(TipoDocPersonal tipo) {
        this.tipo = tipo;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
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
        if (!(object instanceof DocumentoPersonal)) {
            return false;
        }
        DocumentoPersonal other = (DocumentoPersonal) object;
        if (this.getId() == null || other.getId() == null) {
            return false;
        } else if (this.getId().intValue() != other.getId().intValue()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entities.DocumentoPersonal[ id=" + id + " ]";
    }

}
