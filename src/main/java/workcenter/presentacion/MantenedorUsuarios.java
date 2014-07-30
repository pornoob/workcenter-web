package workcenter.presentacion;

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
import workcenter.util.services.Idioma;

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
    
    @Autowired
    Idioma idioma;

    private List<Personal> listaPersonal;
    private List<Personal> listaFiltradaPersonal;
    private List<Proyecto> proyectosDisponibles;
    private List<Permiso> permisosUsuario;

    private Personal personalSeleccionado;
    private Proyecto proyectoSeleccionado;
    private Integer permisoSeleccionado;

    private boolean habilitado;

    public String inicio() {
        listaPersonal = logicaPersonal.obtenerTodosConUsuario();
        return "flowListarPersonal";
    }

    public void guardarUsuario() {
        personalSeleccionado.getUsuario().setPermisosCollection(permisosUsuario);
        logicaPersonal.guardarUsuario(personalSeleccionado);
        
        FacesUtil.mostrarMensajeInformativo(idioma.obtenerMensaje("editarUsuario.operacionExitosa"), null);
    }

    public String administrarAccesos(Personal p) {
        personalSeleccionado = p;
        personalSeleccionado.setUsuario(personalSeleccionado.getUsuario() != null ? personalSeleccionado.getUsuario() : new Usuario());
        proyectosDisponibles = logicaProyecto.obtenerTodos();
        permisosUsuario = (List<Permiso>) personalSeleccionado.getUsuario().getPermisosCollection();
        permisoSeleccionado = 0;
        return "flowEditarAccesos";
    }

    public void agregarPermiso() {
        Permiso permiso = new Permiso();
        Proyecto proyecto = new Proyecto();

        proyecto.setDescripcion(proyectoSeleccionado.getDescripcion());
        proyecto.setId(proyectoSeleccionado.getId());
        proyecto.setTitulo(proyectoSeleccionado.getTitulo());
        proyecto.setTipo(proyectoSeleccionado.getTipo());

        permiso.setNivel(permisoSeleccionado);
        permiso.setProyecto(proyectoSeleccionado);
        permiso.setUsuario(personalSeleccionado.getUsuario());

        permisosUsuario = permisosUsuario == null ? new ArrayList<Permiso>() : permisosUsuario;
        if (permiso.getId() != null && permisosUsuario.contains(permiso)) {
            return;
        }
        if (permiso.getId() == null) {
            for (Permiso p : permisosUsuario) {
                if (p.getProyecto().getTitulo().equals(permiso.getProyecto().getTitulo())) {
                    return;
                }
            }
        }
        permisosUsuario.add(permiso);
        limpiarAgrPermiso();
    }

    public void quitarPermiso(Permiso permiso) {
        if (permiso.getId() != null) {
            permisosUsuario.remove(permiso);
            return;
        }

        for (int i = 0; i < permisosUsuario.size(); i++) {
            if (permisosUsuario.get(i).getProyecto().equals(permiso.getProyecto())) {
                permisosUsuario.remove(i);
                break;
            }
        }
    }

    public void limpiarAgrPermiso() {
        permisoSeleccionado = 0;
        proyectoSeleccionado = null;
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

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
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
}
