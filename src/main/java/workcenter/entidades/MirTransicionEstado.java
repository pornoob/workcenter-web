package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by claudio on 30-09-14.
 */
@Entity
@Table(name = "mir_transicion_estado", schema = "")
public class MirTransicionEstado implements Serializable {
    private Integer id;
    private MirEstadoIncidencia idEstadoOrigen;
    private MirEstadoIncidencia idEstadoDestino;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "id_estado_origen", referencedColumnName = "id")
    public MirEstadoIncidencia getIdEstadoOrigen() {
        return idEstadoOrigen;
    }

    public void setIdEstadoOrigen(MirEstadoIncidencia idEstadoOrigen) {
        this.idEstadoOrigen = idEstadoOrigen;
    }

    @ManyToOne
    @JoinColumn(name = "id_estado_destino", referencedColumnName = "id")
    public MirEstadoIncidencia getIdEstadoDestino() {
        return idEstadoDestino;
    }

    public void setIdEstadoDestino(MirEstadoIncidencia idEstadoDestino) {
        this.idEstadoDestino = idEstadoDestino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MirTransicionEstado that = (MirTransicionEstado) o;

        if (that.getId() == null || this.getId() == null) return false;
        else if (that.getId().intValue() != this.getId().intValue()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
