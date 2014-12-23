package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by claudio on 26-11-14.
 */
@Entity
@Table(name = "mfs_tests", schema = "")
@NamedQueries({
        @NamedQuery(
                name = "MfsTest.findByEncuesta",
                query = "select t from MfsTest t where t.idEncuesta = :encuesta"
        )
})
public class MfsTest implements Serializable {
    private Integer id;
    private MfsEncuesta idEncuesta;
    private Integer valorEsperado;
    private Integer valorEntregado;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "id_encuesta", referencedColumnName = "id")
    public MfsEncuesta getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(MfsEncuesta idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    @Basic
    @Column(name = "valor_esperado")
    public Integer getValorEsperado() {
        return valorEsperado;
    }

    public void setValorEsperado(Integer valorEsperado) {
        this.valorEsperado = valorEsperado;
    }

    @Basic
    @Column(name = "valor_entregado")
    public Integer getValorEntregado() {
        return valorEntregado;
    }

    public void setValorEntregado(Integer valorEntregado) {
        this.valorEntregado = valorEntregado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MfsTest that = (MfsTest) o;

        if (this.getId() == null || that.getId() == null) return false;
        else if (this.getId().intValue() != that.getId().intValue()) return false;
        else return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
