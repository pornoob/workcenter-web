package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by claudio on 26-11-14.
 */
@Entity
@Table(name = "mfs_encuentas", schema = "")
@NamedQueries({
        @NamedQuery(
                name = "MfsEncuesta.findByDateRange",
                query = "select e from MfsEncuesta e where e.fecha >= :inicio and e.fecha <= :fin"
        )
})
public class MfsEncuesta implements Serializable {
    private Integer id;
    private Personal encuestado;
    private Personal encargado;
    private Date fecha;

    private List<MfsRespuesta> respuestas;
    private List<MfsTest> tests;

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
    @JoinColumn(name = "encuestado", referencedColumnName = "rut")
    public Personal getEncuestado() {
        return encuestado;
    }

    public void setEncuestado(Personal encuestado) {
        this.encuestado = encuestado;
    }

    @ManyToOne
    @JoinColumn(name = "encargado", referencedColumnName = "rut")
    public Personal getEncargado() {
        return encargado;
    }

    public void setEncargado(Personal encargado) {
        this.encargado = encargado;
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha")
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @OneToMany(mappedBy = "encuesta", cascade = CascadeType.ALL)
    public List<MfsRespuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<MfsRespuesta> respuestas) {
        this.respuestas = respuestas;
    }

    @OneToMany(mappedBy = "idEncuesta", cascade = CascadeType.ALL)
    public List<MfsTest> getTests() {
        return tests;
    }

    public void setTests(List<MfsTest> tests) {
        this.tests = tests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MfsEncuesta that = (MfsEncuesta) o;

        if (this.getId() == null || that.getId() == null) return false;
        else if (this.getId().intValue() != that.getId().intValue()) return false;
        else return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
