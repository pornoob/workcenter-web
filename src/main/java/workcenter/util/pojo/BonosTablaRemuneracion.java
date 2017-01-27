package workcenter.util.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import workcenter.entidades.BonoDescuentoRemuneracion;
import workcenter.entidades.Remuneracion;

/**
 * @author colivares
 */
public class BonosTablaRemuneracion implements Serializable {
    private List<String> tipos;

    public BonosTablaRemuneracion(List<Remuneracion> remuneraciones) {
        tipos = new ArrayList<>();
        for (Remuneracion r : remuneraciones) {
            for (BonoDescuentoRemuneracion b : r.getRemuneracionBonoDescuentoList()) {
                if (Boolean.TRUE.equals(b.getImponible()) && Boolean.TRUE.equals(b.getBono()) && !tipos.contains(b.getDescripcion())) {
                    tipos.add(b.getDescripcion());
                }
            }
        }
//        System.out.println("AGREGAMOS BONOS");
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
