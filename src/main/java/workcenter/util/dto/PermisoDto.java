package workcenter.util.dto;

import java.io.Serializable;

/**
 * Created by claudio on 25-09-14.
 */
public class PermisoDto implements Serializable {
    private String modulo;
    private String acceso;

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getAcceso() {
        return acceso;
    }

    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }
}
