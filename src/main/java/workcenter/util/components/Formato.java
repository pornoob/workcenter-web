package workcenter.util.components;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by claudio on 17-09-14.
 */
@Component
public class Formato implements Serializable {
    public String numeroAgrupado(Integer numero) {
        StringBuilder sb = new StringBuilder();
        String tmp = String.valueOf(numero);
        int cont = 1;
        for (int i = tmp.length() - 1; i >= 0; i--) {
            sb.insert(0, tmp.charAt(i));
            if (cont % 3 == 0 && i > 0) {
                sb.insert(0, '.');
            }
            cont = (cont + 1) % 3;
        }
        return sb.toString();
    }

    public String numeroAgrupado(Double numero) {
        StringBuilder sb = new StringBuilder();
        String tmp = String.valueOf(numero).split(".")[0];
        int cont = 1;
        for (int i = tmp.length() - 1; i >= 0; i--) {
            sb.insert(0, tmp.charAt(i));
            if (cont % 3 == 0 && i > 0) {
                sb.insert(0, '.');
            }
            cont = (cont + 1) % 3;
        }
        return sb.toString();
    }
}
