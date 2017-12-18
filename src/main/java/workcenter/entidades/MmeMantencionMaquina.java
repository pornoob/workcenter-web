package workcenter.entidades;

import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.SortedSet;

/**
 *
 * @author Claudio Olivares
 */
@Entity
@Table(name = "mme_mantenciones_maquinaria")
@NamedQueries({
    @NamedQuery(name = "MmeMantencionMaquina.findById", query = "SELECT m FROM MmeMantencionMaquina m WHERE m.id = :id"),
    @NamedQuery(name = "MmeMantencionMaquina.findAll", query = "SELECT m FROM MmeMantencionMaquina m ORDER BY m.fecha DESC"),
    @NamedQuery(name = "MmeMantencionMaquina.findLastByMaquina", query = "SELECT m FROM MmeMantencionMaquina m WHERE m.maquina = :maquina ORDER BY m.fecha DESC"),
    @NamedQuery(name = "MmeMantencionMaquina.findPreviousByFechaAndMaquina", query = "SELECT m FROM MmeMantencionMaquina m WHERE m.fecha < :fecha and m.maquina = :maquina ORDER BY m.fecha DESC"),
    @NamedQuery(name = "MmeMantencionMaquina.findByMesAndAnio", query = "SELECT m FROM MmeMantencionMaquina m WHERE YEAR(m.fecha) = :anio AND MONTH(m.fecha) = :mes ORDER BY m.fecha DESC")
})
public class MmeMantencionMaquina implements Serializable, Comparable<MmeMantencionMaquina> {

    private static final long serialVersionUID = -7971947037702767059L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "horas_anotadas")
    private Integer hrasAnotadas;

    @Column(name = "horas_diferencia")
    private Integer hrasDiff;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha")
    private Date fecha;

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "equipo_id")
    private Equipo maquina;

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "mecanico_id")
    private Personal mecanicoResponsable;

    @OneToMany(mappedBy = "mantencionMaquina", cascade = CascadeType.ALL)
    @SortNatural
    private SortedSet<MmeCheckMaquina> checkeoRealizado;
    
    @JoinColumn(name = "ot_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private OrdenTrabajo ot;

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

    public SortedSet<MmeCheckMaquina> getCheckeoRealizado() {
        return checkeoRealizado;
    }

    public void setCheckeoRealizado(SortedSet<MmeCheckMaquina> checkeoRealizado) {
        this.checkeoRealizado = checkeoRealizado;
    }
    
    public OrdenTrabajo getOt() {
        return ot;
    }

    public void setOt(OrdenTrabajo ot) {
        this.ot = ot;
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

    @Override
    public int compareTo(MmeMantencionMaquina o) {
        if (!this.maquina.equals(o.maquina)) {
            return this.maquina.getPatente().compareTo(o.maquina.getPatente());
        }
        
        if (this.fecha == null) {
            return 1;
        } else if (o.fecha == null) {
            return -1;
        } else {
            return this.fecha.compareTo(o.fecha) * -1;
        }
    }
}
