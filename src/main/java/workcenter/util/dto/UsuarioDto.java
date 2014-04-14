package workcenter.util.dto;

/**
 * @author colivares
 */
public class UsuarioDto {
    private boolean externo;
    private Integer rut;
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
}
