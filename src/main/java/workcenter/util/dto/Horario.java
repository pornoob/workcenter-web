package workcenter.util.dto;

import java.io.Serializable;

/**
 *
 * @author colivares
 */
public class Horario implements Serializable {
    private Integer id;
    private String hora;

    public Horario(Integer id, String hora) {
        this.id = id;
        this.hora = hora;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
