package workcenter.presentacion.usuarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Permiso;
import workcenter.entidades.Personal;
import workcenter.entidades.Proyecto;
import workcenter.entidades.Usuario;
import workcenter.negocio.LogicaPersonal;
import workcenter.negocio.LogicaProyecto;
import workcenter.negocio.LogicaPermiso;
import workcenter.util.components.Constantes;
import workcenter.util.pojo.FacesUtil;

/**
 * @author colivares
 */
@Component
@Scope("flow")
public class MantenedorUsuarios implements Serializable {

    @Autowired
    LogicaPersonal logicaPersonal;

    @Autowired
    LogicaProyecto logicaProyecto;

    @Autowired
    LogicaPermiso logicaPermiso;

    @Autowired
    Constantes constantes;

    private List<Personal> listaPersonal;
    private List<Personal> listaFiltradaPersonal;
    private List<Proyecto> proyectosDisponibles;
    private List<Permiso> permisosUsuario;

    private Personal personalSeleccionado;
    private Proyecto proyectoSeleccionado;
    private Integer permisoSeleccionado;
    private String acceso;

    public String inicio() {
        listaPersonal = logicaPersonal.obtenerTodosConUsuario();
        return "flowListarPersonal";
    }

    public String administrarAccesos(Personal p) {
        personalSeleccionado = p;
        personalSeleccionado.setUsuario(personalSeleccionado.getUsuario() != null ? personalSeleccionado.getUsuario() : new Usuario());
        permisosUsuario = (List<Permiso>) personalSeleccionado.getUsuario().getPermisosCollection();
        proyectosDisponibles = logicaProyecto.obtenerTodos();
        return irEditarUsuario();
    }

    public String irCrearPermiso() {
        permisoSeleccionado = null;
        System.err.println("HICE PERMISO NULL");
        for (Permiso permiso : permisosUsuario) {
            if (proyectosDisponibles.contains(permiso.getProyecto())) {
                proyectosDisponibles.remove(permiso.getProyecto());
            }
        }
        acceso = null;
        return "flowEditarPermiso";
    }

    public String irEditarPermiso(Permiso permiso) {
        permisoSeleccionado = permiso.getId();
        System.err.println("HICE PERMISO "+permisoSeleccionado);
        proyectoSeleccionado = permiso.getProyecto();
        acceso = constantes.getAccesos().get(permiso.getNivel()).toString();
        return "flowEditarPermiso";
    }

    public String irEditarUsuario() {
        return "flowEditarAccesos";
    }

    public String irPerfil() {
        return "flowVerPerfil";
    }

    public void guardarPermiso() {
        Integer nivel = (Integer) constantes.getAccesos().get(acceso);
        System.err.println("RECIBO PERMISO "+permisoSeleccionado);

        Permiso permiso = null;
        if (permisoSeleccionado != null) {
            for (Permiso p : permisosUsuario)
                if (p.getId().intValue() == permisoSeleccionado.intValue()) {
                    permiso = p;
                    break;
                }
        } else
            permiso = new Permiso();

        permiso.setProyecto(proyectoSeleccionado);
        permiso.setUsuario(personalSeleccionado.getUsuario());
        permiso.setNivel(nivel);
        if (permiso.getId() == null)
            personalSeleccionado.getUsuario().getPermisosCollection().add(permiso);
        else
            personalSeleccionado.getUsuario().setPermisosCollection(permisosUsuario);

        logicaPersonal.guardar(personalSeleccionado);
        proyectoSeleccionado = null;
        permisoSeleccionado = null;
        FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Se ha guardado correctamente el permiso");
    }

    public void quitarPermiso() {
        Permiso permiso = null;
        for (Permiso p : permisosUsuario)
            if (p.getProyecto().equals(proyectoSeleccionado)) {
                permiso = p;
                break;
            }
        permisosUsuario.remove(permiso);
        personalSeleccionado.getUsuario().setPermisosCollection(permisosUsuario);
        proyectosDisponibles.add(permiso.getProyecto());
        logicaPersonal.guardar(personalSeleccionado);
        proyectoSeleccionado = null;
        permisoSeleccionado = null;
        FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Se ha eliminado correctamente el permiso");
    }

    public List<Personal> getListaPersonal() {
        return listaPersonal;
    }

    public void setListaPersonal(List<Personal> listaPersonal) {
        this.listaPersonal = listaPersonal;
    }

    public List<Personal> getListaFiltradaPersonal() {
        return listaFiltradaPersonal;
    }

    public void setListaFiltradaPersonal(List<Personal> listaFiltradaPersonal) {
        this.listaFiltradaPersonal = listaFiltradaPersonal;
    }

    public Personal getPersonalSeleccionado() {
        return personalSeleccionado;
    }

    public void setPersonalSeleccionado(Personal personalSeleccionado) {
        this.personalSeleccionado = personalSeleccionado;
    }

    public List<Proyecto> getProyectosDisponibles() {
        return proyectosDisponibles;
    }

    public void setProyectosDisponibles(List<Proyecto> proyectosDisponibles) {
        this.proyectosDisponibles = proyectosDisponibles;
    }

    public Proyecto getProyectoSeleccionado() {
        return proyectoSeleccionado;
    }

    public void setProyectoSeleccionado(Proyecto proyectoSeleccionado) {
        this.proyectoSeleccionado = proyectoSeleccionado;
    }

    public Integer getPermisoSeleccionado() {
        return permisoSeleccionado;
    }

    public void setPermisoSeleccionado(Integer permisoSeleccionado) {
        this.permisoSeleccionado = permisoSeleccionado;
    }

    public List<Permiso> getPermisosUsuario() {
        return permisosUsuario;
    }

    public void setPermisosUsuario(List<Permiso> permisosUsuario) {
        this.permisosUsuario = permisosUsuario;
    }

    public List<String> getAccesos() {
        List<String> accesos = new ArrayList<String>();
        for (Entry<Object, Object> e : constantes.getAccesos().entrySet()) {
            if (e.getKey().getClass().getName().contains("Integer")) {
                accesos.add(e.getValue().toString());
            }
        }
        return accesos;
    }

    public String getAcceso() {
        return acceso;
    }

    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }
}
