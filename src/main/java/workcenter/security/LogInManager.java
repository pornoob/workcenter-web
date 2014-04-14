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
import workcenter.entidades.Permiso;
import workcenter.entidades.Proyecto;
import workcenter.negocio.LogicaUsuario;
import workcenter.util.dto.UsuarioDto;
import workcenter.util.services.Idioma;

/**
 * @author colivares
 */
@Component
public class LogInManager implements AuthenticationProvider, Serializable {
    @Autowired
    LogicaUsuario logicaUsuario;
    
    @Autowired
    Idioma idioma;

    public Authentication authenticate(Authentication a) throws AuthenticationException {
        String rut = a.getName();
        String password = a.getCredentials().toString();
        UsuarioDto usuario = logicaUsuario.logIn(rut, password);
        if (usuario == null) {
            throw new BadCredentialsException(idioma.obtenerMensaje("validador.usuarioInvalido"));
        }
        return new UsernamePasswordAuthenticationToken(usuario.getRut(), a.getCredentials(), getAcceso(usuario));
    }

    private List<GrantedAuthority> getAcceso(UsuarioDto u) {
        List<GrantedAuthority> listaRoles = new ArrayList<GrantedAuthority>();
        List<Proyecto> permisos = logicaUsuario.obtenerPermisos(u.getRut());
//        System.out.println("PERMISOS PARA "+u.getNombres());
        for (Proyecto p : permisos) {
//            System.out.println("P: "+p.getTitulo().toUpperCase().replaceAll(" ", "_"));
            listaRoles.add(new SimpleGrantedAuthority(p.getTitulo().toUpperCase().replaceAll(" ", "_")));
        }
        listaRoles.add(new SimpleGrantedAuthority("USUARIO_WEB"));
        return listaRoles;
    }

    public boolean supports(Class<? extends Object> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
