package workcenter.util.components;

import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import workcenter.negocio.usuarios.LogicaUsuario;
import workcenter.util.dto.UsuarioDto;

/**
 * @author colivares
 */
@Component
@Scope("session")
public class SesionCliente implements Serializable {

    @Autowired
    LogicaUsuario logicaUsuario;

    @Autowired
    Constantes constantes;

    private UsuarioDto usuario;
    private boolean inicioSesion;

    public SesionCliente() {
        usuario = null;
    }

    public UsuarioDto getUsuario() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();

        if (a == null || "anonymousUser".equals(a.getName())) {
            usuario = null;
        } else if (!a.getName().equals("anonymousUser")) {
            try {
                if (usuario == null || !usuario.getRut().equals(Integer.valueOf(a.getName())))
                    usuario = logicaUsuario.obtenerUsuario(Integer.valueOf(a.getName()));
            } catch (NumberFormatException nfe) {
                usuario = logicaUsuario.obtenerUsuario(a.getName());
            }
        }
        return usuario;
    }

    public boolean esEditor(String modulo) {
        if (tieneNivel((Integer) constantes.getAccesos().get("Editor"), modulo)
                || tieneNivel((Integer) constantes.getAccesos().get("Administrador"), modulo)) {
            return true;
        }
        return false;
    }

    public boolean tieneAccesoEspecial(String modulo) {
        if (tieneNivel((Integer) constantes.getAccesos().get("Privilegios Especiales"), modulo)
                || tieneNivel((Integer) constantes.getAccesos().get("Administrador"), modulo)) {
            return true;
        }
        return false;
    }

    public boolean esAdministrador(String modulo) {
        if (tieneNivel((Integer) constantes.getAccesos().get("Administrador"), modulo)) {
            return true;
        }
        return false;
    }

    public boolean tienePermiso(String permiso) {
        if (!this.estaAutentificado()) {
            return false;
        }
        HttpServletRequest request = (HttpServletRequest) FacesUtil.obtenerHttpServletRequest();
        return request.isUserInRole(permiso);
    }

    public boolean tieneNivel(Integer nivel, String permiso) {
        if (this.getUsuario() == null) return false;
        SecurityContext context = SecurityContextHolder.getContext();
        Collection<? extends GrantedAuthority> authorities = context.getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(permiso + "_" + nivel)) return true;
        }
        return false;
    }

    public void setUsuario(UsuarioDto usuario) {
        this.usuario = usuario;
    }

    public boolean estaAutentificado() {
        return getUsuario() != null;
    }

    public String getNombreCompleto() {
        if (!estaAutentificado()) {
            return null;
        }
        String string = null;
        if (!usuario.isExterno())
            string = usuario.getNombres() + " " + usuario.getApellidos();
        else
            string = usuario.getNombres();

        StringBuilder sb = new StringBuilder();
        for (String s : string.split(" ")) {
            sb.append(StringUtils.capitalize(s));
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    public boolean isInicioSesion() {
        return inicioSesion;
    }

    public void setInicioSesion(boolean inicioSesion) {
        this.inicioSesion = inicioSesion;
    }
}
