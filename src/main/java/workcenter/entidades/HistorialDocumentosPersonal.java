package workcenter.entidades;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by claudio on 22-09-14.
 */
@Entity
@Table(name = "history_documentospersonal", schema = "")
public class HistorialDocumentosPersonal {
    private Integer id;
    private String numero;
    private Date vencimiento;
    private int tipo;
    private Long personal;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "numero", nullable = true, insertable = true, updatable = true, length = 45)
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "vencimiento", nullable = true, insertable = true, updatable = true)
    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    @Basic
    @Column(name = "tipo", nullable = false, insertable = true, updatable = true)
    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Basic
    @Column(name = "personal", nullable = false, insertable = true, updatable = true)
    public Long getPersonal() {
        return personal;
    }

    public void setPersonal(Long personal) {
        this.personal = personal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistorialDocumentosPersonal that = (HistorialDocumentosPersonal) o;

        if (id != that.id) return false;
        if (personal != that.personal) return false;
        if (tipo != that.tipo) return false;
        if (numero != null ? !numero.equals(that.numero) : that.numero != null) return false;
        if (vencimiento != null ? !vencimiento.equals(that.vencimiento) : that.vencimiento != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id * 31;
        return result;
    }
}
