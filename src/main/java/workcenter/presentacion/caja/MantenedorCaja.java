package workcenter.presentacion.caja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Dinero;
import workcenter.negocio.caja.LogicaCaja;

import java.io.Serializable;
import java.util.List;

/**
 * Created by renholders on 09-10-2015.
 */
@Component
@Scope("flow")
public class MantenedorCaja implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Dinero> lstDineros;
    @Autowired LogicaCaja logicaCaja;

    public void inicio(){
//        lstDineros = logicaCaja.obtenerDineros();
        System.out.println("Termino de cargar el listado");
    }

    public List<Dinero> getLstDineros() {
        return lstDineros;
    }

    public void setLstDineros(List<Dinero> lstDineros) {
        this.lstDineros = lstDineros;
    }
}
