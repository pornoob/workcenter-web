package workcenter.entidades;

import javax.persistence.*;

/**
 * Created by colivares on 27-06-15.
 */
@Entity
@Table(name = "valores_impuesto_unico", schema = "")
@NamedQueries({
        @NamedQuery(
                name = "ValorImpuestoUnico.findVigentes",
                query = "SELECT viu From ValorImpuestoUnico viu WHERE viu.anioVigencia = (" +
                        "SELECT MAX(vigente.anioVigencia) FROM ValorImpuestoUnico vigente ORDER BY vigente.cotaMin ASC)"
        )
})
public class ValorImpuestoUnico {
    private Integer id;
    private Integer anioVigencia;
    private Float cotaMin;
    private Float cotaMax;
    private Float factor;
    private Float substraendo;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "anio_vigencia")
    public Integer getAnioVigencia() {
        return anioVigencia;
    }

    public void setAnioVigencia(Integer anioVigencia) {
        this.anioVigencia = anioVigencia;
    }

    @Basic
    @Column(name = "cota_min")
    public Float getCotaMin() {
        return cotaMin;
    }

    public void setCotaMin(Float cotaMin) {
        this.cotaMin = cotaMin;
    }

    @Basic
    @Column(name = "cota_max")
    public Float getCotaMax() {
        return cotaMax;
    }

    public void setCotaMax(Float cotaMax) {
        this.cotaMax = cotaMax;
    }

    @Basic
    @Column(name = "factor")
    public Float getFactor() {
        return factor;
    }

    public void setFactor(Float factor) {
        this.factor = factor;
    }

    @Basic
    @Column(name = "substraendo")
    public Float getSubstraendo() {
        return substraendo;
    }

    public void setSubstraendo(Float substraendo) {
        this.substraendo = substraendo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValorImpuestoUnico that = (ValorImpuestoUnico) o;
        if (this.getId() == null || that.getId() == null) return false;
        else return this.getId().intValue() == that.getId().intValue();
    }

    @Override
    public int hashCode() {
        return 31 * (id == null ? 0 : id.hashCode());
    }

    @Override
    public String toString() {
        return "ValorImpuestoUnico{" +
                "id=" + id +
                ", anioVigencia=" + anioVigencia +
                ", cotaMin=" + cotaMin +
                ", cotaMax=" + cotaMax +
                ", factor=" + factor +
                ", substraendo=" + substraendo +
                '}';
    }
}
