/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "mme_check_maquina")
@XmlRootElement
@NamedQueries({
})
@IdClass(MmeCheckMaquinaPK.class)
public class MmeCheckMaquina implements Serializable {
    
    private static final long serialVersionUID = 5252750644329354248L;
    
    @Id
    private Integer mantencionMaquinaId;
    
    @Id
    private Integer tareaMaquinaId;
    
    @ManyToOne
    @JoinColumn(name = "maquina_id", insertable = false, updatable = false)
    private MmeMantencionMaquina mantencionMaquina;
    
    @ManyToOne
    @JoinColumn(name = "tarea_id", insertable = false, updatable = false)
    private MmeTareaMaquina tareaMaquina;
    
    @Column(name = "hras_anotadas")
    private Integer hrasAnotadas;

    public Integer getTareaMaquinaId() {
        return tareaMaquinaId;
    }

    public void setTareaMaquinaId(Integer tareaMaquinaId) {
        this.tareaMaquinaId = tareaMaquinaId;
    }

    public Integer getHrasAnotadas() {
        return hrasAnotadas;
    }

    public void setHrasAnotadas(Integer hrasAnotadas) {
        this.hrasAnotadas = hrasAnotadas;
    }

    public Integer getMantencionMaquinaId() {
        return mantencionMaquinaId;
    }

    public void setMantencionMaquinaId(Integer mantencionMaquinaId) {
        this.mantencionMaquinaId = mantencionMaquinaId;
    }

    public MmeMantencionMaquina getMantencionMaquina() {
        return mantencionMaquina;
    }

    public void setMantencionMaquina(MmeMantencionMaquina mantencionMaquina) {
        this.mantencionMaquinaId = mantencionMaquina.getId();
        this.mantencionMaquina = mantencionMaquina;
    }

    public MmeTareaMaquina getTareaMaquina() {
        return tareaMaquina;
    }

    public void setTareaMaquina(MmeTareaMaquina tareaMaquina) {
        this.tareaMaquinaId = tareaMaquina.getId();
        this.tareaMaquina = tareaMaquina;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.mantencionMaquinaId);
        hash = 29 * hash + Objects.hashCode(this.tareaMaquinaId);
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
        final MmeCheckMaquina other = (MmeCheckMaquina) obj;
        if (!Objects.equals(this.mantencionMaquinaId, other.mantencionMaquinaId)) {
            return false;
        }
        if (!Objects.equals(this.tareaMaquinaId, other.tareaMaquinaId)) {
            return false;
        }
        return true;
    }
}
