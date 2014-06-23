package workcenter.negocio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import workcenter.dao.UsuarioDao;
import workcenter.entidades.Proyecto;
import workcenter.util.dto.UsuarioDto;
import workcenter.util.pojo.Md5;

/**
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaUsuario {
    
    @Autowired
    UsuarioDao usuarioDao;
    
    public UsuarioDto logIn(String rut, String pass) {
        try {
            return usuarioDao.obtenerUsuario(Integer.valueOf(rut.split("-")[0]), Md5.hash(pass));
        } catch(Exception e) {
            return null;
        }
//        return usuarioDao.obtenerUsuario(Integer.valueOf(rut.split("-")[0]), pass);
    }

    public List<Proyecto> obtenerPermisos(Integer rut) {
        return usuarioDao.obtenerPermisos(rut);
    }
}
