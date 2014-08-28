package workcenter.presentacion.equipos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Equipo;
import workcenter.entidades.TipoEquipo;
import workcenter.negocio.LogicaEquipos;

import java.io.Serializable;
import java.util.List;

/**
 * Created by colivares on 28-08-14.
 */
@Component
@Scope("flow")
public class MantenedorEquipos implements Serializable {
    @Autowired
    private LogicaEquipos logicaEquipos;

    private List<Equipo> equipos;
    private List<Equipo> equiposFiltrados;
    private List<TipoEquipo> tiposEquipos;
    private Equipo equipo;

    public void inicio() {
        equipos = logicaEquipos.obtenerTodos();
        tiposEquipos = logicaEquipos.obtenerTipos();
    }

    public String irEditar(Equipo e) {
        equipo = e;
        return "flowEditar";
    }

    public boolean estaHabilitado(Equipo e) {
        return logicaEquipos.obtenerEquipoSancionado(e) == null;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public List<Equipo> getEquiposFiltrados() {
        return equiposFiltrados;
    }

    public void setEquiposFiltrados(List<Equipo> equiposFiltrados) {
        this.equiposFiltrados = equiposFiltrados;
    }

    public List<TipoEquipo> getTiposEquipos() {
        return tiposEquipos;
    }

    public void setTiposEquipos(List<TipoEquipo> tiposEquipos) {
        this.tiposEquipos = tiposEquipos;
    }
}