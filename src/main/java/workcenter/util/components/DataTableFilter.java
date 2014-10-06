package workcenter.util.components;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by colivares on 28-08-14.
 */
@Component
@Scope("application")
public class DataTableFilter implements Serializable {
    public boolean filtroInsensitivo(Object valor, Object filtro, Locale idioma) {
        if (valor == null || filtro == null) return false;
        return valor.toString().toUpperCase().contains(filtro.toString().toUpperCase());
    }
}
