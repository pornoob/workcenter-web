package workcenter.util.pojo;

import workcenter.entidades.BonoDescuentoRemuneracion;
import workcenter.entidades.Remuneracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BonoDescuentoTablaRemuneracion implements Serializable {
    private Boolean esDescuento;
    private Boolean esImponible;
    private List<String> tipos;

    public BonoDescuentoTablaRemuneracion(Boolean esDescuento, Boolean esImponible) {
        this.esDescuento = esDescuento;
        this.esImponible = esImponible;
    }

    public void init(List<Remuneracion> remuneraciones) {
        tipos = new ArrayList<>();
        for (Remuneracion r : remuneraciones) {
            for (BonoDescuentoRemuneracion b : r.getRemuneracionBonoDescuentoList()) {
                if (esImponible.equals(b.getImponible()) && !esDescuento.equals(b.getBono()) && !tipos.contains(b.getDescripcion())) {
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
