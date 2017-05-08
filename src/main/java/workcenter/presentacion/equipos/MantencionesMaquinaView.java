package workcenter.presentacion.equipos;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Equipo;
import workcenter.negocio.equipos.LogicaEquipos;

/**
 *
 * @author Claudio Olivares
 */
@Component
@Scope("view")
public class MantencionesMaquinaView implements Serializable {
    
    private static final long serialVersionUID = -7397597162103384913L;

    private List<Equipo> maquinas;
    
    @Autowired
    private LogicaEquipos logicaEquipos;
    
    @PostConstruct
    private void init() {
        maquinas = logicaEquipos.obtenerMaquinas();
    }

    public List<Equipo> getMaquinas() {
        return maquinas;
    }

    public void setMaquinas(List<Equipo> maquinas) {
        this.maquinas = maquinas;
    }
}
