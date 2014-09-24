package workcenter.util.components;

import org.springframework.context.annotation.Scope;
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
}
