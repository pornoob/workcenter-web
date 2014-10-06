package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by claudio on 01-10-14.
 */
@Entity
@Table(name = "mir_apoyos", schema = "")
@NamedQueries(
        @NamedQuery(
                name = "MirApoyo.findAll",
                query = "SELECT a FROM MirApoyo a ORDER BY a.orden ASC"
        )
)
public class MirApoyo implements Serializable {
    private Integer id;
    private Empresa idSocio;
    private int orden;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "id_socio", referencedColumnName = "id")
    public Empresa getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(Empresa idSocio) {
        this.idSocio = idSocio;
    }

    @Basic
    @Column(name = "orden")
    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MirApoyo that = (MirApoyo) o;

        if (that.getId() == null || this.getId() == null) return false;
        else if (that.getId().intValue() != this.getId().intValue()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
