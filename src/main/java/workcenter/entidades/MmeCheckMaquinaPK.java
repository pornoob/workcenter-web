/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.entidades;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author claudio
 */
@Embeddable
public class MmeCheckMaquinaPK implements Serializable {

    private static final long serialVersionUID = 270251934939613522L;

    @Column(name = "mantencion_id")
    private Integer mantencionMaquinaId;
    @Column(name = "tarea_id")
    private Integer tareaMaquinaId;

    public MmeCheckMaquinaPK() {
    }

    public Integer getMantencionMaquinaId() {
        return mantencionMaquinaId;
    }

    public void setMantencionMaquinaId(Integer mantencionMaquinaId) {
        this.mantencionMaquinaId = mantencionMaquinaId;
    }

    public Integer getTareaMaquinaId() {
        return tareaMaquinaId;
    }

    public void setTareaMaquinaId(Integer tareaMaquinaId) {
        this.tareaMaquinaId = tareaMaquinaId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.mantencionMaquinaId);
        hash = 89 * hash + Objects.hashCode(this.tareaMaquinaId);
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
        final MmeCheckMaquinaPK other = (MmeCheckMaquinaPK) obj;
        if (this.getMantencionMaquinaId() == null || other.getMantencionMaquinaId() == null) {
            return false;
        }
        if (!Objects.equals(this.getMantencionMaquinaId(), other.getMantencionMaquinaId())) {
            return false;
        }
        if (this.getTareaMaquinaId() == null || other.getTareaMaquinaId() == null) {
            return false;
        }
        if (!Objects.equals(this.getTareaMaquinaId(), other.getTareaMaquinaId())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "workcenter.entidades.MmeCheckMaquinaPK[ mantencionId=" + mantencionMaquinaId + ", tareaId=" + tareaMaquinaId + " ]";
    }
    
}
