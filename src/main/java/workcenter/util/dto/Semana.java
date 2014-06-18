package workcenter.util.dto;

import java.io.Serializable;

/**
 *
 * @author colivares
 */
public class Semana implements Serializable {

    private Dia[] dias;

    public Semana() {
        dias = new Dia[7];
        dias[0] = new Dia(1, "Lunes");
        dias[1] = new Dia(2, "Martes");
        dias[2] = new Dia(3, "Miércoles");
        dias[3] = new Dia(4, "Jueves");
        dias[4] = new Dia(5, "Viernes");
        dias[5] = new Dia(6, "Sábado");
        dias[6] = new Dia(7, "Domingo");
    }

    public Dia[] getDias() {
        return dias;
    }

    @Override
    public String toString() {
        return "Semana del " + dias[0].getFechaStr() + " al " + dias[6].getFechaStr();
    }
}
