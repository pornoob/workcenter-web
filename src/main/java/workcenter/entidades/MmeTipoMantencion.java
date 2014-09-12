package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by claudio on 09-09-14.
 */
@Entity
@Table(name = "mme_tipos_mantenciones", schema = "")
@NamedQueries(
        @NamedQuery(
                name = "MmeTipoMantencion.findAll",
                query = "select t from MmeTipoMantencion t order by t.cotaKilometraje asc"
        )
)
public class MmeTipoMantencion implements Serializable {
    private Integer id;
    private String nombre;
    private Integer cotaKilometraje;

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
    @Column(name = "nombre", nullable = false, insertable = true, updatable = true, length = 300)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "cota_kilometraje", nullable = true, insertable = true, updatable = true)
    public Integer getCotaKilometraje() {
        return cotaKilometraje;
    }

    public void setCotaKilometraje(Integer cotaKilometraje) {
        this.cotaKilometraje = cotaKilometraje;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MmeTipoMantencion that = (MmeTipoMantencion) o;

        if (this.getId() == null || that.getId() == null) return false;
        else if (this.getId().intValue() != that.getId().intValue()) return false;
        else return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
}
