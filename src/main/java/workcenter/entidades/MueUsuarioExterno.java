package workcenter.entidades;

import javax.persistence.*;

/**
 * Created by colivares on 27-08-14.
 */
@Entity
@Table(name = "mue_usuario_externo", schema = "")
@NamedQueries({
        @NamedQuery(
                name = "MueUsuarioExterno.findByUsuarioAndPass",
                query = "SELECT ue FROM MueUsuarioExterno ue WHERE ue.usuario = :usuario and ue.clave = :pass"
        ),
        @NamedQuery(
                name = "MueUsuarioExterno.findByUsuario",
                query = "SELECT ue FROM MueUsuarioExterno ue WHERE ue.usuario = :usuario"
        )
})
public class MueUsuarioExterno {
    private Integer id;
    private String usuario;
    private String clave;
    private String nombreCompleto;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "usuario", nullable = true, insertable = true, updatable = true, length = 50)
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Basic
    @Column(name = "clave", nullable = true, insertable = true, updatable = true, length = 45)
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Basic
    @Column(name = "nombre_completo", nullable = true, insertable = true, updatable = true, length = 100)
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MueUsuarioExterno that = (MueUsuarioExterno) o;

        if (this.getId() == null || that.getId() == null) return false;
        else if (this.getId().intValue() != that.getId().intValue()) return false;
        else return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
}
