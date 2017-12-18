package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by colivares on 04-08-14.
 */
@Entity
@Table(name = "mar_tipo_actividad", schema = "")
@NamedQueries(
        value = {
            @NamedQuery(
                    name = "MarTipoActividad.findByIdRegistro",
                    query = "SELECT m FROM MarTipoActividad m WHERE m.marRegistrosByIdRegistro.id = :idRegistro " +
                            "AND m.marTipoActividadByIdTipoPadre IS NULL ORDER BY m.nombre"
            ),
            @NamedQuery(
                    name = "MarTipoActividad.findSubTiposByActividad",
                    query = "SELECT m FROM MarTipoActividad  m WHERE m.marTipoActividadByIdTipoPadre = :tipoActividad " +
                            "ORDER BY m.nombre"
            )
        }
)
public class MarTipoActividad implements Serializable {
    private Integer id;
    private String nombre;
    private Boolean requiereNota;
    private MarRegistro marRegistrosByIdRegistro;
    private String duracion;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = true, insertable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nombre", nullable = false, insertable = true, updatable = true, length = 200)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "requiere_nota", nullable = false, insertable = true, updatable = true)
    public Boolean getRequiereNota() {
        return requiereNota;
    }

    public void setRequiereNota(Boolean requiereNota) {
        this.requiereNota = requiereNota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarTipoActividad that = (MarTipoActividad) o;

        if (this.getId() == null || that.getId() == null) return false;
        else if (this.getId().intValue() != that.getId().intValue()) return false;
        return true;
    }

    @Override
    public int hashCode() {
       return id * 31;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[ id = "+id+" ]";
    }

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "id_registro", referencedColumnName = "id", nullable = false)
    public MarRegistro getMarRegistrosByIdRegistro() {
        return marRegistrosByIdRegistro;
    }

    public void setMarRegistrosByIdRegistro(MarRegistro marRegistrosByIdRegistro) {
        this.marRegistrosByIdRegistro = marRegistrosByIdRegistro;
    }

    @Basic
    @Column(name = "duracion", insertable = true, updatable = true, nullable = true, length = 5)
    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }    private MarTipoActividad marTipoActividadByIdTipoPadre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_padre", referencedColumnName = "id")
    public MarTipoActividad getMarTipoActividadByIdTipoPadre() {
        return marTipoActividadByIdTipoPadre;
    }

    public void setMarTipoActividadByIdTipoPadre(MarTipoActividad marTipoActividadByIdTipoPadre) {
        this.marTipoActividadByIdTipoPadre = marTipoActividadByIdTipoPadre;
    }
}
