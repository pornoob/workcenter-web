package workcenter.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Claudio Olivares
 */
public class MmeMantencionMaquina implements Serializable {
    @Id
    @Column(name = "id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_tarea", referencedColumnName = "id")
    private MmeTareaMaquina tareaMantencion;
    
    @Column(name = "kilometraje_anotado")
    private Integer kmAnotado;
    
    @Column(name = "kilometraje_prox_mantencion")
    private Integer kmProxMantencion;
    
    @Column(name = "kilometraje_diferencia")
    private Integer kmDiff;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha")
    private Date fecha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MmeTareaMaquina getTareaMantencion() {
        return tareaMantencion;
    }

    public void setTareaMantencion(MmeTareaMaquina tareaMantencion) {
        this.tareaMantencion = tareaMantencion;
    }

    public Integer getKmAnotado() {
        return kmAnotado;
    }

    public void setKmAnotado(Integer kmAnotado) {
        this.kmAnotado = kmAnotado;
    }

    public Integer getKmDiff() {
        return kmDiff;
    }

    public void setKmDiff(Integer kmDiff) {
        this.kmDiff = kmDiff;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getKmProxMantencion() {
        return kmProxMantencion;
    }

    public void setKmProxMantencion(Integer kmProxMantencion) {
        this.kmProxMantencion = kmProxMantencion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MmeMantencionMaquina other = (MmeMantencionMaquina) obj;
        if (this.getId() == null || other.getId() == null) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
