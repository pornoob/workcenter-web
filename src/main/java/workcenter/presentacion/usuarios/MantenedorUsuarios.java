package workcenter.presentacion.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Permiso;
import workcenter.entidades.Personal;
import workcenter.entidades.Proyecto;
import workcenter.entidades.Usuario;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.negocio.usuarios.LogicaPermiso;
import workcenter.negocio.usuarios.LogicaProyecto;
import workcenter.util.components.Constantes;
import workcenter.util.components.FacesUtil;
import workcenter.util.pojo.Md5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

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
        if (personalSeleccionado.getUsuario() == null) {
            Usuario u = new Usuario();
            u.setRut(personalSeleccionado.getRut());
            u.setPassword(Md5.hash(String.valueOf(personalSeleccionado.getRut())));
            u.setPermisos(new HashSet<Permiso>());
            u.setHabilitado(false);
            personalSeleccionado.setUsuario(u);
            logicaPersonal.guardar(personalSeleccionado);
        }
        proyectosDisponibles = logicaProyecto.obtenerTodos();
        return irEditarUsuario();
    }

    public String irCrearPermiso() {
        permisoSeleccionado = null;
        if (permisosUsuario != null)
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
        proyectoSeleccionado = permiso.getProyecto();
        acceso = constantes.getAccesos().get(permiso.getNivel()).toString();
        return "flowEditarPermiso";
    }

    public String irEditarUsuario() {
        personalSeleccionado = logicaPersonal.obtenerConAccesos(personalSeleccionado.getRut());
        if (personalSeleccionado.getUsuario() != null)
            permisosUsuario = new ArrayList<>(personalSeleccionado.getUsuario().getPermisos());
        return "flowEditarAccesos";
    }

    public String irPerfil() {
        return "flowVerPerfil";
    }

    public void guardarPermiso() {
         Integer nivel = (Integer) constantes.getAccesos().get(acceso);

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
        if (permiso.getId() == null) {
            if (personalSeleccionado.getUsuario().getPermisos() == null)
                personalSeleccionado.getUsuario().setPermisos(new HashSet<Permiso>());
           // if (!personalSeleccionado.getUsuario().getPermisosCollection().contains(permiso.getProyecto()))
            personalSeleccionado.getUsuario().getPermisos().add(permiso);
        }

        logicaPersonal.guardar(personalSeleccionado);
        personalSeleccionado = logicaPersonal.obtener(personalSeleccionado.getRut());
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
        proyectosDisponibles.add(permiso.getProyecto());
        personalSeleccionado = logicaPersonal.obtener(personalSeleccionado.getRut());
        logicaPersonal.guardar(personalSeleccionado);
        proyectoSeleccionado = null;
        permisoSeleccionado = null;
        FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Se ha eliminado correctamente el permiso");
    }

    public void guardarUsuario() {
        logicaPersonal.guardar(personalSeleccionado);
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
