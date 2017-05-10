package workcenter.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import workcenter.entidades.Proyecto;
import workcenter.negocio.usuarios.LogicaUsuario;
import workcenter.util.dto.UsuarioDto;

/**
 * @author colivares
 */
@Component
public class LogInManager implements AuthenticationProvider, Serializable {

    private static final long serialVersionUID = -5117201650243282098L;
    @Autowired
    LogicaUsuario logicaUsuario;

    public Authentication authenticate(Authentication a) throws AuthenticationException {
        String rut = a.getName();
        String password = "";
        try {
            password = a.getCredentials().toString();
        } catch (NullPointerException ne) {
        }
        UsuarioDto usuario = logicaUsuario.logIn(rut, password);
        if (usuario == null) {
            usuario = logicaUsuario.logInExterno(rut, password);
        }
        if (usuario == null) {
            throw new BadCredentialsException("Usuario y/o Contraseña Inválidos");
        }
        return new UsernamePasswordAuthenticationToken(usuario.getIdentificador(), a.getCredentials(), getAcceso(usuario));
    }

    private List<GrantedAuthority> getAcceso(UsuarioDto u) {
        List<GrantedAuthority> listaRoles = new ArrayList<GrantedAuthority>();
        List<Proyecto> permisos = null;

        if (!u.isExterno())
            permisos = logicaUsuario.obtenerPermisos(u.getRut());
        else
            permisos = logicaUsuario.obtenerPermisos(u.getUsuario());
        for (Proyecto p : permisos) {
            listaRoles.add(new SimpleGrantedAuthority(p.getTitulo().toUpperCase().replaceAll(" ", "_")));
        }
        listaRoles.add(new SimpleGrantedAuthority("USUARIO_WEB"));
        return listaRoles;
    }

    public boolean supports(Class<? extends Object> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
