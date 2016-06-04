package liquidaciones;

import core.InitialContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import workcenter.entidades.BonoDescuentoRemuneracion;
import workcenter.entidades.Remuneracion;
import workcenter.negocio.personal.LogicaLiquidaciones;
import workcenter.util.others.RenderPdf;

import java.math.BigInteger;

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
                4110l,
                4949l,
                4960l,
                4973l,
                4986l,
                5000l,
                5016l,
                5031l,
                5046l,
                5071l,
                5096l
        };

        for (Long id : ids) {
            Remuneracion remuneracion = logicaLiquidaciones.obtenerLiquidacion(id);
            for (BonoDescuentoRemuneracion bonoDescuentoRemuneracion : remuneracion.getRemuneracionBonoDescuentoList()) {
                // incentivo tvp
                if (bonoDescuentoRemuneracion.getIdBonoDescuento() == 72l) {
                    bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf(150000));
                    continue;
                }

                // incentivo seguridad
                if (bonoDescuentoRemuneracion.getIdBonoDescuento() == 40l) {
                    bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf(160000));
                    continue;
                }

                // responsabilidad tvp
                if (bonoDescuentoRemuneracion.getIdBonoDescuento() == 68l) {
                    bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf(0));
                }
            }

            int cargas = 0;
            renderPdf.generarLiquidacion(remuneracion, cargas);
            logicaLiquidaciones.guardarDatosLiquidacion(remuneracion);
        }
    }
}
