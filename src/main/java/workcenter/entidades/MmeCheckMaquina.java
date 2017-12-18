/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.entidades;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "mme_check_maquina")
@XmlRootElement
@NamedQueries({
})
public class MmeCheckMaquina implements Serializable, Comparable<MmeCheckMaquina> {
    
    private static final long serialVersionUID = 5252750644329354248L;
    
    @EmbeddedId
    private MmeCheckMaquinaPK id;
    
    @ManyToOne(fetch = FetchType.LAZY)    @MapsId("mantencionMaquinaId")
    @JoinColumn(name = "mantencion_id", referencedColumnName = "id")
    private MmeMantencionMaquina mantencionMaquina;
    
    @ManyToOne(fetch = FetchType.LAZY)    @MapsId("tareaMaquinaId")
    @JoinColumn(name = "tarea_id", referencedColumnName = "id")
    private MmeTareaMaquina tareaMaquina;
    
    @Column(name = "hras_anotadas")
    private Integer hrasAnotadas;

    public MmeCheckMaquina() {
        this.id = new MmeCheckMaquinaPK();
    }

    public MmeCheckMaquinaPK getId() {
        return id;
    }

    public void setId(MmeCheckMaquinaPK id) {
        this.id = id;
    }

    public MmeMantencionMaquina getMantencionMaquina() {
        return mantencionMaquina;
    }

    public void setMantencionMaquina(MmeMantencionMaquina mantencionMaquina) {
        this.mantencionMaquina = mantencionMaquina;
        this.id.setMantencionMaquinaId(mantencionMaquina.getId());
    }

    public MmeTareaMaquina getTareaMaquina() {
        return tareaMaquina;
    }

    public void setTareaMaquina(MmeTareaMaquina tareaMaquina) {
        this.tareaMaquina = tareaMaquina;
        this.id.setTareaMaquinaId(tareaMaquina.getId());
    }

    public Integer getHrasAnotadas() {
        return hrasAnotadas;
    }

    public void setHrasAnotadas(Integer hrasAnotadas) {
        this.hrasAnotadas = hrasAnotadas;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(MmeCheckMaquina o) {
        if (this.tareaMaquina == null && o.tareaMaquina != null)
            return 1;
        else if (this.tareaMaquina != null && o.tareaMaquina == null)
            return -1;
        else if (this.tareaMaquina == null && o.tareaMaquina == null)
            return 0;
        else
            return this.tareaMaquina.compareTo(o.tareaMaquina);
    }
}
