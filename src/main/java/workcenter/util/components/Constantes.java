package workcenter.util.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Servicio;
import workcenter.util.dto.Horario;
import workcenter.util.dto.Mes;

/**
 * @author colivares
 */
@Component
@Scope("application")
public class Constantes implements Serializable {
    // Zona de usuarios
    private Map<Object, Object> accesos;
    
    // Zona de remuneraciones
    private int filtroEmpleador=1;
    private int filtroConductor=2;
    
    // Zona de personal
    private int cargoConductor=3;
    
    // Genericos
    private List<Mes> meses;
    private List<Horario> horarios;
    private int cantidadFilasTablas = 15;
    private String filasPorPaginaTemplate = "10, 15, 20, 30, 40, 50, 100";
    private String paginadorTemplate = "{FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}";
    
    // cliente de correo
    private String usuarioCorreo = "colivares@transportesventanas.cl";
    private String contrasennaCorreo = "colivares1";
    private String servidorCorreo = "imap.googlemail.com";
    private Integer puertoCorreo = 993;
    private String protocoloCorreo = "imaps";
    
    // modulos
    private String moduloAlarmasGPS = "Modulo Alarmas GPS";
    private String moduloProgramaActividades = "Modulo Programa Actividades";

    public Constantes() {
        ProxyFactory pf = new ProxyFactory(this);
        pf.addAdvice(new MethodInterceptor() {

            public Object invoke(MethodInvocation mi) throws Throwable {
                if (mi.getMethod().getName().startsWith("set"))
                    throw new UnsupportedOperationException("Constantes: Estás intentando modificar un valor constante");
                return null;
            }
        });
        meses = new ArrayList<Mes>();
        meses.add(new Mes("01", "Enero"));
        meses.add(new Mes("02", "Febrero"));
        meses.add(new Mes("03", "Marzo"));
        meses.add(new Mes("04", "Abril"));
        meses.add(new Mes("05", "Mayo"));
        meses.add(new Mes("06", "Junio"));
        meses.add(new Mes("07", "Julio"));
        meses.add(new Mes("08", "Agosto"));
        meses.add(new Mes("09", "Septiembre"));
        meses.add(new Mes("10", "Octubre"));
        meses.add(new Mes("11", "Noviembre"));
        meses.add(new Mes("12", "Diciembre"));
        
        horarios = new ArrayList<Horario>();
        horarios.add(new Horario(0, "00:00"));
        horarios.add(new Horario(1, "01:00"));
        horarios.add(new Horario(2, "02:00"));
        horarios.add(new Horario(3, "03:00"));
        horarios.add(new Horario(4, "04:00"));
        horarios.add(new Horario(5, "05:00"));
        horarios.add(new Horario(6, "06:00"));
        horarios.add(new Horario(7, "07:00"));
        horarios.add(new Horario(8, "08:00"));
        horarios.add(new Horario(9, "09:00"));
        horarios.add(new Horario(10, "10:00"));
        horarios.add(new Horario(11, "11:00"));
        horarios.add(new Horario(12, "12:00"));
        horarios.add(new Horario(13, "13:00"));
        horarios.add(new Horario(14, "14:00"));
        horarios.add(new Horario(15, "15:00"));
        horarios.add(new Horario(16, "16:00"));
        horarios.add(new Horario(17, "17:00"));
        horarios.add(new Horario(18, "18:00"));
        horarios.add(new Horario(19, "19:00"));
        horarios.add(new Horario(20, "20:00"));
        horarios.add(new Horario(21, "21:00"));
        horarios.add(new Horario(22, "22:00"));
        horarios.add(new Horario(23, "23:00"));
        
        accesos = new HashMap<Object, Object>();
        accesos.put(new Integer(0), "Administrador");
        accesos.put(new Integer(2), "Consultor");
        accesos.put(new Integer(1), "Editor");
        accesos.put(new Integer(3), "Privilegios Especiales");
        
        accesos.put("Administrador", new Integer(0));
        accesos.put("Consultor", new Integer(2));
        accesos.put("Editor", new Integer(1));
        accesos.put("Privilegios Especiales", new Integer(3));
    }

    public int getCargoConductor() {
        return cargoConductor;
    }

    public int getFiltroEmpleador() {
        return filtroEmpleador;
    }

    public int getFiltroConductor() {
        return filtroConductor;
    }

    public List<Mes> getMeses() {
        return meses;
    }

    public int getCantidadFilasTablas() {
        return cantidadFilasTablas;
    }

    public String getFilasPorPaginaTemplate() {
        return filasPorPaginaTemplate;
    }

    public String getPaginadorTemplate() {
        return paginadorTemplate;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public Map<Object, Object> getAccesos() {
        return accesos;
    }

    public String getModuloAlarmasGPS() {
        return moduloAlarmasGPS;
    }

    public String getModuloProgramaActividades() {
        return moduloProgramaActividades;
    }

    public String getUsuarioCorreo() {
        return usuarioCorreo;
    }

    public String getContrasennaCorreo() {
        return contrasennaCorreo;
    }

    public String getServidorCorreo() {
        return servidorCorreo;
    }

    public Integer getPuertoCorreo() {
        return puertoCorreo;
    }

    public String getProtocoloCorreo() {
        return protocoloCorreo;
    }
}