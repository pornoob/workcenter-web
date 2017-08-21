package workcenter.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    @NamedQuery(name = "MmeMantencionMaquina.findAll", query = "SELECT m FROM MmeMantencionMaquina m ORDER BY m.fecha DESC")
    ,
    @NamedQuery(name = "MmeMantencionMaquina.findLastByMaquina", query = "SELECT m FROM MmeMantencionMaquina m WHERE m.maquina = :maquina ORDER BY m.fecha DESC")
    ,
    @NamedQuery(name = "MmeMantencionMaquina.findPreviousByFechaAndMaquina", query = "SELECT m FROM MmeMantencionMaquina m WHERE m.fecha < :fecha and m.maquina = :maquina ORDER BY m.fecha DESC")
    ,
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

    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo maquina;

    @ManyToOne
    @JoinColumn(name = "mecanico_id")
    private Personal mecanicoResponsable;

    @OneToMany(mappedBy = "mantencionMaquina", cascade = CascadeType.ALL)
    private List<MmeCheckMaquina> checkeoRealizado;
    
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

    public List<MmeCheckMaquina> getCheckeoRealizado() {
        return checkeoRealizado;
    }

    public void setCheckeoRealizado(List<MmeCheckMaquina> checkeoRealizado) {
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
        
        if (this.fecha == null || o.fecha == null) {
            return -2;
        }
        if (this.fecha.before(o.fecha)) {
            return 1;
        } else if (this.fecha.after(o.fecha)) {
            return -1;
        } else if (this.fecha.equals(o.fecha)) {
            return 0;
        } else {
            return -2;
        }
    }
}
