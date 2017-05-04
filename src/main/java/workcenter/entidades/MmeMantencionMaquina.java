package workcenter.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Claudio Olivares
 */
@Entity
@Table(name = "mme_mantenciones_maquinaria")
@NamedQueries({
    @NamedQuery(name = "MmeMantencionMaquina.findAll", query = "SELECT m FROM MmeMantencionMaquina m ORDER BY m.fecha DESC"),
    @NamedQuery(name = "MmeMantencionMaquina.findLastByMaquina", query = "SELECT m FROM MmeMantencionMaquina m WHERE m.maquina = :maquina ORDER BY m.fecha DESC")
})
public class MmeMantencionMaquina implements Serializable {

    private static final long serialVersionUID = -7971947037702767059L;
    @Id
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "horas_anotadas")
    private Integer hrasAnotadas;
    
    @Column(name = "horas_diferencia")
    private Integer hrasDiff;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha")
    private Date fecha;
    
    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo maquina;
    
    @ManyToOne
    @JoinColumn(name = "mecanico_id")
    private Personal mecanicoResponsable;
    
    @OneToMany(mappedBy = "mantencionMaquina", cascade = CascadeType.ALL)
    private List<MmeCheckMaquina> checkeoRealizado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHrasAnotadas() {
        return hrasAnotadas;
    }

    public void setHrasAnotadas(Integer kmAnotado) {
        this.hrasAnotadas = kmAnotado;
    }

    public Integer getHrasDiff() {
        return hrasDiff;
    }

    public void setHrasDiff(Integer kmDiff) {
        this.hrasDiff = kmDiff;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Equipo getMaquina() {
        return maquina;
    }

    public void setMaquina(Equipo maquina) {
        this.maquina = maquina;
    }

    public Personal getMecanicoResponsable() {
        return mecanicoResponsable;
    }

    public void setMecanicoResponsable(Personal mecanicoResponsable) {
        this.mecanicoResponsable = mecanicoResponsable;
    }

    public List<MmeCheckMaquina> getCheckeoRealizado() {
        return checkeoRealizado;
    }

    public void setCheckeoRealizado(List<MmeCheckMaquina> checkeoRealizado) {
        this.checkeoRealizado = checkeoRealizado;
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
