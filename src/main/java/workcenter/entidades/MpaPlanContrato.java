package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by claudio on 04-12-14.
 */
@Entity
@Table(name = "mpa_plan_contrato", schema = "")
@IdClass(MpaPlanContratoPK.class)
public class MpaPlanContrato implements Serializable {
    private MpaPlanPrograma idPlan;
    private MpaContrato idContrato;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "id_plan", referencedColumnName = "id")
    public MpaPlanPrograma getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(MpaPlanPrograma idPlan) {
        this.idPlan = idPlan;
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "id_contrato", referencedColumnName = "id")
    public MpaContrato getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(MpaContrato idContrato) {
        this.idContrato = idContrato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MpaPlanContrato that = (MpaPlanContrato) o;

        return this.getIdPlan().equals(that.getIdPlan()) && this.getIdContrato().equals(that.getIdContrato());
    }

    @Override
    public int hashCode() {
        return this.getIdPlan().hashCode() + this.getIdContrato().hashCode();
    }
}
