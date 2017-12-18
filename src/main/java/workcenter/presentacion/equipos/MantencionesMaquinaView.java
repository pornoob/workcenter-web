package workcenter.presentacion.equipos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Equipo;
import workcenter.entidades.MmeCheckMaquina;
import workcenter.entidades.MmeMantencionMaquina;
import workcenter.negocio.equipos.LogicaEquipos;
import workcenter.negocio.equipos.LogicaMantenciones;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Claudio Olivares
 */
@Component
@Scope("view")
public class MantencionesMaquinaView implements Serializable {

    private static final long serialVersionUID = -7397597162103384913L;

    private List<Equipo> maquinas;
    private Map<Equipo, Boolean> checkeos;

    @Autowired
    private LogicaEquipos logicaEquipos;

    @Autowired
    private LogicaMantenciones logicaMantenciones;

    @PostConstruct
    private void init() {
        maquinas = logicaEquipos.obtenerMaquinasConModelo();
        maquinas.remove(new Equipo("CBFP 86"));
        maquinas.remove(new Equipo("WH87 40"));
        checkeos = new HashMap<>();
        for (Equipo m : maquinas) {
            checkeos.put(m, todosLosCheckRealizados(m));
        }
    }

    public boolean todosLosCheckRealizados(Equipo maquina) {
        MmeMantencionMaquina mantencion = logicaMantenciones.obtenerUltimaMantencionMaquina(maquina);
        if (mantencion == null) {
            return false;
        }
        for (MmeCheckMaquina mmeCheckMaquina : mantencion.getCheckeoRealizado()) {
            if (!mantencion.getHrasAnotadas().equals(mmeCheckMaquina.getHrasAnotadas()) && mantencion.getHrasAnotadas() > mmeCheckMaquina.getHrasAnotadas() + mmeCheckMaquina.getTareaMaquina().getCantHoras()) {
                return false;
            }
        }
        return true;
    }

    public List<Equipo> getMaquinas() {
        return maquinas;
    }

    public void setMaquinas(List<Equipo> maquinas) {
        this.maquinas = maquinas;
    }

    public Map<Equipo, Boolean> getCheckeos() {
        return checkeos;
    }

    public void setCheckeos(Map<Equipo, Boolean> checkeos) {
        this.checkeos = checkeos;
    }
}
