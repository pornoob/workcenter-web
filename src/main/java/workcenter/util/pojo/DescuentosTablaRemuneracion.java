package workcenter.util.pojo;

import workcenter.entidades.BonoDescuentoRemuneracion;
import workcenter.entidades.Remuneracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author colivares
 */
public class DescuentosTablaRemuneracion implements Serializable {
    private List<String> tipos;

    public DescuentosTablaRemuneracion(List<Remuneracion> remuneraciones) {
        tipos = new ArrayList<String>();
        for (Remuneracion r : remuneraciones) {
            for (BonoDescuentoRemuneracion b : r.getRemuneracionBonoDescuentoList()) {
                if (Boolean.FALSE.equals(b.getBono()) && !tipos.contains(b.getDescripcion())) {
                    tipos.add(b.getDescripcion());
                }
            }
        }
        System.out.println("AGREGAMOS DESCUENTOS");
        for (String s : tipos) {
            System.out.println(s);
        }
    }
    
    public int getCantidadColumnas() {
        return this.tipos.size();
    }

    public List<String> getTipos() {
        return tipos;
    }

    public void setTipos(List<String> tipos) {
        this.tipos = tipos;
    }
    
}
