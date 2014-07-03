package workcenter.util.components;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import workcenter.dao.UsuarioDao;
import workcenter.entidades.Permiso;
import workcenter.util.dto.UsuarioDto;
import workcenter.util.pojo.FacesUtil;

/**
 * @author colivares
 */
@Component
@Scope("session")
public class SesionCliente implements Serializable {

    @Autowired
    UsuarioDao usuarioDao;
    
    @Autowired
    Constantes constantes;

    private UsuarioDto usuario;

    public SesionCliente() {
        usuario = null;
    }

    public UsuarioDto getUsuario() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();

        if (a == null || "anonymousUser".equals(a.getName())) {
            usuario = null;
        } else if (!a.getName().equals("anonymousUser") && (usuario == null || !usuario.getRut().equals(Integer.valueOf(a.getName())))) {
            usuario = usuarioDao.obtenerUsuario(Integer.valueOf(a.getName()));
        }
        return usuario;
    }
    
    public boolean esEditor(String modulo) {
        if (tieneNivel((Integer)constantes.getAccesos().get("Editor"), constantes.getModuloAlarmasGPS())
                || tieneNivel((Integer)constantes.getAccesos().get("Administrador"), constantes.getModuloAlarmasGPS())) {
            return true;
        }
        return false;
    }

    public boolean tienePermiso(String permiso) {
        HttpServletRequest request = (HttpServletRequest) FacesUtil.obtenerHttpServletRequest();
        return request.isUserInRole(permiso);
    }
    
    public boolean tieneNivel(Integer nivel, String permiso) {
        Permiso p = usuarioDao.obtenerPermiso(this.getUsuario().getRut(), permiso, nivel);
        return p != null;
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
        String string = usuario.getNombres() + " " + usuario.getApellidos();
        StringBuilder sb = new StringBuilder();
        for (String s : string.split(" ")) {
            sb.append(StringUtils.capitalize(s));
            sb.append(' ');
        }
        return sb.toString().trim();
    }
}
