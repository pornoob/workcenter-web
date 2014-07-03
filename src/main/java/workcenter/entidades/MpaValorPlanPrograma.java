/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.entidades;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import workcenter.util.dto.Mes;

/**
 *
 * @author colivares
 */
@Entity
@Table(name = "mpa_valor_plan_programa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MpaValorPlanPrograma.findAll", query = "SELECT m FROM MpaValorPlanPrograma m"),
    @NamedQuery(name = "MpaValorPlanPrograma.findById", query = "SELECT m FROM MpaValorPlanPrograma m WHERE m.id = :id"),
    @NamedQuery(name = "MpaValorPlanPrograma.findByEnero", query = "SELECT m FROM MpaValorPlanPrograma m WHERE m.enero = :enero"),
    @NamedQuery(name = "MpaValorPlanPrograma.findByFebrero", query = "SELECT m FROM MpaValorPlanPrograma m WHERE m.febrero = :febrero"),
    @NamedQuery(name = "MpaValorPlanPrograma.findByMarzo", query = "SELECT m FROM MpaValorPlanPrograma m WHERE m.marzo = :marzo"),
    @NamedQuery(name = "MpaValorPlanPrograma.findByAbril", query = "SELECT m FROM MpaValorPlanPrograma m WHERE m.abril = :abril"),
    @NamedQuery(name = "MpaValorPlanPrograma.findByMayo", query = "SELECT m FROM MpaValorPlanPrograma m WHERE m.mayo = :mayo"),
    @NamedQuery(name = "MpaValorPlanPrograma.findByJunio", query = "SELECT m FROM MpaValorPlanPrograma m WHERE m.junio = :junio"),
    @NamedQuery(name = "MpaValorPlanPrograma.findByJulio", query = "SELECT m FROM MpaValorPlanPrograma m WHERE m.julio = :julio"),
    @NamedQuery(name = "MpaValorPlanPrograma.findByAgosto", query = "SELECT m FROM MpaValorPlanPrograma m WHERE m.agosto = :agosto"),
    @NamedQuery(name = "MpaValorPlanPrograma.findBySeptiembre", query = "SELECT m FROM MpaValorPlanPrograma m WHERE m.septiembre = :septiembre"),
    @NamedQuery(name = "MpaValorPlanPrograma.findByOctubre", query = "SELECT m FROM MpaValorPlanPrograma m WHERE m.octubre = :octubre"),
    @NamedQuery(name = "MpaValorPlanPrograma.findByNoviembre", query = "SELECT m FROM MpaValorPlanPrograma m WHERE m.noviembre = :noviembre"),
    @NamedQuery(name = "MpaValorPlanPrograma.findByDiciembre", query = "SELECT m FROM MpaValorPlanPrograma m WHERE m.diciembre = :diciembre")})
public class MpaValorPlanPrograma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "enero")
    private int enero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "febrero")
    private int febrero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "marzo")
    private int marzo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "abril")
    private int abril;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mayo")
    private int mayo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "junio")
    private int junio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "julio")
    private int julio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "agosto")
    private int agosto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "septiembre")
    private int septiembre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "octubre")
    private int octubre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "noviembre")
    private int noviembre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "diciembre")
    private int diciembre;
    @JoinColumn(name = "id_plan", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MpaPlanPrograma idPlan;

    public MpaValorPlanPrograma() {
    }

    public MpaValorPlanPrograma(Integer id) {
        this.id = id;
    }

    public MpaValorPlanPrograma(Integer id, int enero, int febrero, int marzo, int abril, int mayo, int junio, int julio, int agosto, int septiembre, int octubre, int noviembre, int diciembre) {
        this.id = id;
        this.enero = enero;
        this.febrero = febrero;
        this.marzo = marzo;
        this.abril = abril;
        this.mayo = mayo;
        this.junio = junio;
        this.julio = julio;
        this.agosto = agosto;
        this.septiembre = septiembre;
        this.octubre = octubre;
        this.noviembre = noviembre;
        this.diciembre = diciembre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEnero() {
        return enero;
    }

    public void setEnero(int enero) {
        this.enero = enero;
    }

    public int getFebrero() {
        return febrero;
    }

    public void setFebrero(int febrero) {
        this.febrero = febrero;
    }

    public int getMarzo() {
        return marzo;
    }

    public void setMarzo(int marzo) {
        this.marzo = marzo;
    }

    public int getAbril() {
        return abril;
    }

    public void setAbril(int abril) {
        this.abril = abril;
    }

    public int getMayo() {
        return mayo;
    }

    public void setMayo(int mayo) {
        this.mayo = mayo;
    }

    public int getJunio() {
        return junio;
    }

    public void setJunio(int junio) {
        this.junio = junio;
    }

    public int getJulio() {
        return julio;
    }

    public void setJulio(int julio) {
        this.julio = julio;
    }

    public int getAgosto() {
        return agosto;
    }

    public void setAgosto(int agosto) {
        this.agosto = agosto;
    }

    public int getSeptiembre() {
        return septiembre;
    }

    public void setSeptiembre(int septiembre) {
        this.septiembre = septiembre;
    }

    public int getOctubre() {
        return octubre;
    }

    public void setOctubre(int octubre) {
        this.octubre = octubre;
    }

    public int getNoviembre() {
        return noviembre;
    }

    public void setNoviembre(int noviembre) {
        this.noviembre = noviembre;
    }

    public int getDiciembre() {
        return diciembre;
    }

    public void setDiciembre(int diciembre) {
        this.diciembre = diciembre;
    }

    public MpaPlanPrograma getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(MpaPlanPrograma idPlan) {
        this.idPlan = idPlan;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MpaValorPlanPrograma)) {
            return false;
        }
        MpaValorPlanPrograma other = (MpaValorPlanPrograma) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.MpaValorPlanPrograma[ id=" + id + " ]";
    }

    public void setear(Map<Mes, Integer> cantActividades) {
        for (Entry e : cantActividades.entrySet()) {
            Mes m = (Mes) e.getKey();
            if (m.getId().equals("01")) {
                enero = (Integer) e.getValue();
            } else if (m.getId().equals("02")) {
                febrero = (Integer) e.getValue();
            } else if (m.getId().equals("03")) {
                marzo = (Integer) e.getValue();
            } else if (m.getId().equals("04")) {
                abril = (Integer) e.getValue();
            } else if (m.getId().equals("05")) {
                mayo = (Integer) e.getValue();
            } else if (m.getId().equals("06")) {
                junio = (Integer) e.getValue();
            } else if (m.getId().equals("07")) {
                julio = (Integer) e.getValue();
            } else if (m.getId().equals("08")) {
                agosto = (Integer) e.getValue();
            } else if (m.getId().equals("09")) {
                septiembre = (Integer) e.getValue();
            } else if (m.getId().equals("10")) {
                octubre = (Integer) e.getValue();
            } else if (m.getId().equals("11")) {
                noviembre = (Integer) e.getValue();
            } else if (m.getId().equals("12")) {
                diciembre = (Integer) e.getValue();
            }
        }
    }

}
