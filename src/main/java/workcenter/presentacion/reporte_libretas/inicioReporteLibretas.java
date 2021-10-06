package workcenter.presentacion.reporte_libretas;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Dinero;
import workcenter.entidades.Personal;
import workcenter.entidades.Vuelta;
import workcenter.negocio.caja.LogicaCaja;
import workcenter.negocio.maestro_guias.LogicaMaestroGuias;
import workcenter.negocio.personal.LogicaPersonal;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Component
@Scope("flow")
public class inicioReporteLibretas implements Serializable {
    private final LogicaCaja logicaCaja;
    private final LogicaPersonal logicaPersonal;
    private final LogicaMaestroGuias logicaMaestroGuias;

    @Getter @Setter
    private Date fechaInicio;
    @Getter @Setter
    private Date fechaTermino;
    @Getter @Setter
    private List<Personal> conductores;
    @Getter @Setter
    private Personal conductorSeleccionado;
    @Getter @Setter
    private List<Vuelta> vueltas;
    private List<Dinero> dineros;

    @Autowired
    public inicioReporteLibretas(LogicaCaja logicaCaja, LogicaPersonal logicaPersonal, LogicaMaestroGuias logicaMaestroGuias) {
        this.logicaCaja = logicaCaja;
        this.logicaPersonal = logicaPersonal;
        this.logicaMaestroGuias = logicaMaestroGuias;
    }

    @PostConstruct
    public void inicio() {
        conductorSeleccionado = null;
        conductores = logicaPersonal.obtenerConductores();
    }

    public void buscar() {
        vueltas = logicaMaestroGuias.buscar(fechaInicio, fechaTermino, conductorSeleccionado);
        dineros = logicaCaja.obtenerDinerosSegunVueltas(vueltas);
    }

    public Integer calcularTotalGastos(Vuelta v) {
        int gastos = 0;
        gastos += v.getViatico() != null ? v.getViatico() : 0;
        gastos += v.getPeaje() != null ? v.getPeaje() : 0;
        gastos += v.getOtrosGastos() != null ? v.getOtrosGastos() : 0;
        return gastos;
    }

    public Integer dineroEntregadoSegunCaja(Vuelta v) {
        int entregado = 0;
        for (Dinero d : dineros) {
            entregado += d.getOrdendecarga().equals(v) ? d.getMonto() : 0;
        }
        return entregado;
    }
}
