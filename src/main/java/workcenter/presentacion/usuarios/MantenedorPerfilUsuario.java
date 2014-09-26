package workcenter.presentacion.usuarios;

import org.omg.CORBA.StringValueHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.MuePermisoUsuario;
import workcenter.entidades.Permiso;
import workcenter.entidades.Usuario;
import workcenter.negocio.usuarios.LogicaPermiso;
import workcenter.negocio.usuarios.LogicaUsuario;
import workcenter.util.components.Constantes;
import workcenter.util.components.FacesUtil;
import workcenter.util.components.SesionCliente;
import workcenter.util.dto.PermisoDto;
import workcenter.util.pojo.Md5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by colivares on 28-08-14.
 */
@Component
@Scope("flow")
public class MantenedorPerfilUsuario implements Serializable {
    private List<PermisoDto> permisos;
    private String claveActual;
    private String claveNueva;
    private String claveConfirmada;

    @Autowired
    private LogicaPermiso logicaPermiso;

    @Autowired
    private LogicaUsuario logicaUsuario;

    @Autowired
    private SesionCliente sesionCliente;

    @Autowired
    private Constantes constantes;

    public void inicio() {
        permisos = new ArrayList<PermisoDto>();
        if (sesionCliente.getUsuario().getExterno())
            for (MuePermisoUsuario pe : logicaPermiso.obtPermisosUsuarioExterno(sesionCliente.getUsuario().getUsuario())) {
                PermisoDto p = new PermisoDto();
                p.setModulo(pe.getModulo().getTitulo());
                p.setAcceso((String) constantes.getAccesos().get(pe.getNivel()));
                permisos.add(p);
            }
        else
            for (Permiso pi : logicaPermiso.obtPermisosUsuarioInterno(sesionCliente.getUsuario().getRut())) {
                PermisoDto p = new PermisoDto();
                p.setModulo(pi.getProyecto().getTitulo());
                p.setAcceso((String) constantes.getAccesos().get(pi.getNivel()));
                permisos.add(p);
            }
    }

    public char obtenerDv(Integer parteNumerica) {
        String rut = String.valueOf(parteNumerica);
        int suma = 0, factor = 2;
        for (int i = rut.length() - 1; i >= 0; i--) {
            suma += factor * (rut.charAt(i) - '0');
            factor = factor == 7 ? 2 : factor + 1;
        }
        suma = (suma * 10) % 11;
        if (suma == 10) return 'K';
        else return (char) (suma + '0');
    }

    public void cambiarClave() {
        if (!claveConfirmada.equals(claveNueva)) {
            FacesUtil.mostrarMensajeError("Operación fallida", "La nueva contraseña no coincide con lo confirmado");
            return;
        }
        if (sesionCliente.getUsuario().getExterno()) {
            if (logicaUsuario.logInExterno(sesionCliente.getUsuario().getUsuario(), claveActual) == null) {
                FacesUtil.mostrarMensajeError("Operación fallida", "La nueva contraseña actual no coincide");
                return;
            }
            logicaUsuario.cambiarClave(sesionCliente.getUsuario().getUsuario(), Md5.hash(claveConfirmada));
        } else {
            if (logicaUsuario.logIn(sesionCliente.getUsuario().getRut().intValue() + "-" + obtenerDv(sesionCliente.getUsuario().getRut()), claveActual) == null) {
                FacesUtil.mostrarMensajeError("Operación fallida", "La nueva contraseña actual no coincide");
                return;
            }
            logicaUsuario.cambiarClave(sesionCliente.getUsuario().getRut(), Md5.hash(claveConfirmada));
        }
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Se ha cambiado correctamente la contraseña");
    }

    public String irVerPerfil() {
        return "flowVerPerfil";
    }

    public String irCambiarClave() {
        return "flowCambiarClave";
    }

    public List<PermisoDto> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<PermisoDto> permisos) {
        this.permisos = permisos;
    }

    public String getClaveActual() {
        return claveActual;
    }

    public void setClaveActual(String claveActual) {
        this.claveActual = claveActual;
    }

    public String getClaveNueva() {
        return claveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        this.claveNueva = claveNueva;
    }

    public String getClaveConfirmada() {
        return claveConfirmada;
    }

    public void setClaveConfirmada(String claveConfirmada) {
        this.claveConfirmada = claveConfirmada;
    }
}
