package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by claudio on 15-02-15.
 */
@Entity
@Table(name = "factor_actualizacion_sii", schema = "")
@NamedQueries({
        @NamedQuery(
                name = "FactorActualizacionSII.findAll",
                query = "select f from FactorActualizacionSII f order by f.idMes asc"
        )
})
public class FactorActualizacionSII implements Serializable {
    private Integer id;
    private Integer idMes;
    private Float valor;

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
    @Column(name = "id_mes", nullable = false, insertable = true, updatable = true)
    public Integer getIdMes() {
        return idMes;
    }

    public void setIdMes(Integer idMes) {
        this.idMes = idMes;
    }

    @Basic
    @Column(name = "valor", nullable = true, insertable = true, updatable = true, precision = 0)
    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FactorActualizacionSII that = (FactorActualizacionSII) o;

        if (this.getIdMes() == null || that.getIdMes() == null) return false;
        else if (this.getIdMes().intValue() != that.getIdMes().intValue()) return false;
        else return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idMes != null ? idMes.hashCode() : 0);
        result = 31 * result + (valor != null ? valor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FactorActualizacionSII{" +
                "id=" + id +
                '}';
    }
}
