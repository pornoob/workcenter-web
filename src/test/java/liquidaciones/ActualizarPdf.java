package liquidaciones;

import core.InitialContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import workcenter.entidades.Remuneracion;
import workcenter.negocio.personal.LogicaLiquidaciones;
import workcenter.util.others.RenderPdf;

/**
 * Created by claudio on 16-04-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ActualizarPdf extends InitialContext {
    @Autowired
    private RenderPdf renderPdf;

    @Autowired
    private LogicaLiquidaciones logicaLiquidaciones;

    @Test
    public void actualizarLiquidacion() {
        Long[] ids = {
            4948l,
            4961l,
            4974l,
            4987l,
            5001l
        };

        for (Long id : ids) {
            Remuneracion remuneracion = logicaLiquidaciones.obtenerLiquidacion(id);
            int cargas = 0;
            renderPdf.generarLiquidacion(remuneracion, cargas);
            logicaLiquidaciones.guardarDatosLiquidacion(remuneracion);
        }
    }
}
