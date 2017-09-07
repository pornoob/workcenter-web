package workcenter.entidades;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Claudio Olivares
 */
@Entity
@Table(name = "ot_asistentes")
@IdClass(AsistenteOtPK.class)
public class AsistenteOt implements Serializable  {
    @Id
    private Long otId;
    @Id
    private Long personalId;
    
    @ManyToOne
    @MapsId(value = "otId")
    @JoinColumn(name = "ot_id")
    private OrdenTrabajo ot;

    @ManyToOne
    @MapsId(value = "personalId")
    @JoinColumn(name = "personal_id")
    private Personal personal;
    
    @Column(name = "hh")
    private Float horasHombre;
    
    @Transient
    private String rowKey;

    public AsistenteOt() {
        this.rowKey = "RK" + UUID.randomUUID();
    }

    public AsistenteOt(OrdenTrabajo ot, Personal personal) {
        this.ot = ot;
        this.personal = personal;
        this.rowKey = "RK" + UUID.randomUUID();
    }

    public Long getOtId() {
        return otId;
    }

    public void setOtId(Long otId) {
        this.otId = otId;
    }

    public Long getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Long personalId) {
        this.personalId = personalId;
    }

    public Float getHorasHombre() {
        return horasHombre;
    }

    public void setHorasHombre(Float horasHombre) {
        this.horasHombre = horasHombre;
    }

    public OrdenTrabajo getOt() {
        return ot;
    }

    public void setOt(OrdenTrabajo ot) {
        this.ot = ot;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.ot);
        hash = 53 * hash + Objects.hashCode(this.personal);
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
        final AsistenteOt other = (AsistenteOt) obj;
        if (!Objects.equals(this.ot, other.ot)) {
            return false;
        }
        return Objects.equals(this.personal, other.personal);
    }
}