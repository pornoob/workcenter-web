package workcenter.presentacion.maestro_guias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Equipo;
import workcenter.entidades.Personal;
import workcenter.entidades.Vuelta;
import workcenter.negocio.equipos.LogicaEquipos;
import workcenter.negocio.maestro_guias.LogicaMaestroGuias;
import workcenter.negocio.personal.LogicaPersonal;

import java.io.Serializable;
import java.util.List;

/**
 * Created by renholders on 23-11-2015.
 */
@Component
@Scope("flow")
public class MantenedorMaestroGuias implements Serializable{

    private Integer ordenConsulta;
    private Vuelta ordenDeCarga;
    private List<Equipo> lstEquipos;
    private List<Equipo> lstBateas;
    private Equipo equipoSeleccionado;
    private List<Personal> lstConductores;
    @Autowired
    LogicaMaestroGuias logicaMaestroGuias;
    @Autowired
    LogicaEquipos logicaEquipos;
    @Autowired
    LogicaPersonal logicaPersonal;

    public void inicio(){
        ordenDeCarga = new Vuelta();
        lstEquipos = logicaEquipos.obtenerTractos();
        lstConductores = logicaPersonal.obtenerConductores();
        lstBateas = logicaEquipos.obtenerBateas();
        System.out.print("CANTIDAD DE TRACTOS: "+lstEquipos.size());
        System.out.print("CANTIDAD DE CONDUCTORES: "+lstConductores.size());
        System.out.print("CANTTIDA DE BATEAS: " + lstBateas.size());
    }

    public void consultarOrdenDeCarga(){
        System.out.println("ORDE DE CARGA SELECCIONADA VA CON : " + ordenConsulta);
        ordenDeCarga = logicaMaestroGuias.obtenerOrdendeCarga(ordenConsulta);
        System.out.println("OBJETO VUELTA TIENE VALOR:  "+ordenDeCarga.getFecha());
    }

    public Integer getOrdenConsulta() {
        return ordenConsulta;
    }

    public void setOrdenConsulta(Integer ordenConsulta) {
        this.ordenConsulta = ordenConsulta;
    }

    public Vuelta getOrdenDeCarga() {
        return ordenDeCarga;
    }

    public void setOrdenDeCarga(Vuelta ordenDeCarga) {
        this.ordenDeCarga = ordenDeCarga;
    }

    public List<Equipo> getLstEquipos() {
        return lstEquipos;
    }

    public void setLstEquipos(List<Equipo> lstEquipos) {
        this.lstEquipos = lstEquipos;
    }

    public Equipo getEquipoSeleccionado() {
        return equipoSeleccionado;
    }

    public void setEquipoSeleccionado(Equipo equipoSeleccionado) {
        this.equipoSeleccionado = equipoSeleccionado;
    }

    public List<Personal> getLstConductores() {
        return lstConductores;
    }

    public void setLstConductores(List<Personal> lstConductores) {
        this.lstConductores = lstConductores;
    }

    public List<Equipo> getLstBateas() {
        return lstBateas;
    }

    public void setLstBateas(List<Equipo> lstBateas) {
        this.lstBateas = lstBateas;
    }
}
