package workcenter.presentacion.personal.reportes_sii;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Remuneracion;
import workcenter.negocio.personal.LogicaLibroRemuneraciones;
import workcenter.util.components.Constantes;
import workcenter.util.dto.Mes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by colivares on 09-02-15.
 */
@Component
@Scope("flow")
public class MantenedorReportesSII implements Serializable {

    public void inicio() {
    }
}
