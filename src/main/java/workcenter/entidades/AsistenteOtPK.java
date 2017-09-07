package workcenter.entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;

/**
 *
 * @author Claudio Olivares
 */
public class AsistenteOtPK implements Serializable {

    @Column(name = "ot_id")
    private Long otId;

    @Column(name = "personal_id")
    private Long personalId;

    public AsistenteOtPK() {
    }

    public AsistenteOtPK(Long otId, Long personalId) {
        this.otId = otId;
        this.personalId = personalId;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.otId);
        hash = 79 * hash + Objects.hashCode(this.personalId);
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
        final AsistenteOtPK other = (AsistenteOtPK) obj;
        if (!Objects.equals(this.otId, other.otId)) {
            return false;
        }
        if (!Objects.equals(this.personalId, other.personalId)) {
            return false;
        }
        return true;
    }
}
