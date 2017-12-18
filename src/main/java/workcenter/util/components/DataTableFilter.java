package workcenter.util.components;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by colivares on 28-08-14.
 */
@Component
@Scope("application")
public class DataTableFilter implements Serializable {
    public boolean filtroInsensitivo(Object valor, Object filtro, Locale idioma) {
        if (valor == null || filtro == null) return false;

        String v = valor.toString().toLowerCase();
        String f = filtro.toString().toLowerCase();

        v = v.replaceAll("á","a");
        v = v.replaceAll("é","e");
        v = v.replaceAll("í","i");
        v = v.replaceAll("ó","o");
        v = v.replaceAll("ú","u");
        v = v.replaceAll("ü","u");

        f = f.replaceAll("á", "a");
        f = f.replaceAll("é", "e");
        f = f.replaceAll("í", "i");
        f = f.replaceAll("ó", "o");
        f = f.replaceAll("ú", "u");
        f = f.replaceAll("ü", "u");
        return v.contains(f);
    }

    public boolean filtroFechas(Object valor, Object filtro, Locale idioma) {
        if (valor == null) return false;
        if (filtro == null) return true;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        return sdf.format(valor).equals(sdf.format(filtro));
    }
}
