package workcenter.util.dto;

import java.io.Serializable;

/**
 * @author colivares
 */
public class UsuarioDto implements Serializable {
    private boolean externo;
    private Integer rut;
    private String usuario;
    private String nombres;
    private String apellidos;

    public boolean isExterno() {
        return externo;
    }
    
    public boolean getExterno() {
        return isExterno();
    }

    public void setExterno(boolean externo) {
        this.externo = externo;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Object getIdentificador() {
        return externo ? usuario : rut;
    }
}
