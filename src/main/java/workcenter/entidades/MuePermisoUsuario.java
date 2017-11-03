package workcenter.entidades;

import javax.persistence.*;

/**
 * Created by colivares on 27-08-14.
 */
@Entity
@Table(name = "mue_permisos_usuarios", schema = "")
@NamedQueries(
        @NamedQuery(
                name = "MuePermisoUsuario.findByUsuario",
                query = "SELECT p FROM MuePermisoUsuario p WHERE p.usuario.usuario = :usuario"
        )
)
public class MuePermisoUsuario {
    private Integer id;
    private MueUsuarioExterno usuario;
    private Proyecto modulo;
    private int nivel;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nivel", nullable = false)
    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    public MueUsuarioExterno getUsuario() {
        return usuario;
    }

    public void setUsuario(MueUsuarioExterno usuario) {
        this.usuario = usuario;
    }

    @ManyToOne
    @JoinColumn(name = "id_modulo", referencedColumnName = "id")
    public Proyecto getModulo() {
        return modulo;
    }

    public void setModulo(Proyecto modulo) {
        this.modulo = modulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MuePermisoUsuario that = (MuePermisoUsuario) o;

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
