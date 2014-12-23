package workcenter.entidades;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by claudio on 04-12-14.
 */
public class MpaPlanContratoPK implements Serializable {
    private Integer idPlan;
    private Integer idContrato;

    @Column(name = "id_plan")
    @Id
    public Integer getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Integer idPlan) {
        this.idPlan = idPlan;
    }

    @Column(name = "id_contrato")
    @Id
    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MpaPlanContratoPK that = (MpaPlanContratoPK) o;

        if (this.getIdContrato() != null && that.getIdContrato() != null &&
                this.getIdContrato().intValue() == that.getIdContrato().intValue() &&
                this.getIdPlan() != null && that.getIdPlan() != null &&
                this.getIdPlan().intValue() == that.getIdPlan().intValue())
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        int result = idPlan != null ? idPlan.hashCode() : 0;
        result += (idContrato != null ? idContrato.hashCode() : 0);
        return result;
    }
}
