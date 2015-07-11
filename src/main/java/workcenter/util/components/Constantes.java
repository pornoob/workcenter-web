package workcenter.util.components;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import workcenter.entidades.TipoUnidad;
import workcenter.negocio.personal.LogicaVariables;
import workcenter.util.dto.Horario;
import workcenter.util.dto.Mes;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.*;

/**
 * @author colivares
 */
@Component
@Scope("application")
public class Constantes implements Serializable {
    // Zona de usuarios
    private Map<Object, Object> accesos;

    // Zona de remuneraciones
    private int filtroEmpleador = 1;
    private int filtroConductor = 2;
    private Double aportePorcentajeTrabajador=0.6;
    private Double aportePorcentajeEmpleador=2.4;
    private Double aporteContradoNoIdefinido=3.0;
    private String diasAnticipo="15";
    private Integer diasTrabajados=30;
    private int conceptoAnticipo = 9;
    private Boolean genericaAdministrativo = true;
    private Boolean genericaConductores = false;

    // Zona de personal
    private int cargoConductor = 3;
    private int cargoMecanico = 4;
    private List<String> estadosCiviles;
    private int fonasa = 0;
    private int isapre = 1;
    private int unidadPesos = 1;
    private int unidadUf = 2;
    private int unidadPorcentaje = 3;
    private List<TipoUnidad> tiposUnidad;

    // Zona equipos
    private int equipoTipoTracto = 1;
    private int equipoTipoBatea = 2;
    private int alarmaProximaMantencion = 2000;

    // Zona registros
    private int registroR112 = 1;
    private int registroInspeccionAvanzada = 2;

    // Zona PIIR
    private int piirEstadoInicial = 1;
    private int piirEstadoCerradaPorSistema = 3;
    private String pirrMensajeCorreo = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n" +
            "<html>\n" +
            "<head>\n" +
            "\t<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">\n" +
            "\t<title></title>\n" +
            "\t<meta name=\"generator\" content=\"LibreOffice 4.2.6.3 (Linux)\">\n" +
            "\t<meta name=\"created\" content=\"20141104;0\">\n" +
            "\t<meta name=\"changed\" content=\"20141105;112620377867073\">\n" +
            "\t<style type=\"text/css\">\n" +
            "\t<!--\n" +
            "\t\t@page { margin-left: 2cm; margin-right: 2cm; margin-top: 0.07cm; margin-bottom: 2cm }\n" +
            "\t\tp { margin-bottom: 0.25cm; color: #000000; line-height: 120% }\n" +
            "\t\ttd p { margin-bottom: 0cm; color: #000000; line-height: 120% }\n" +
            "\t\ttd p.western { font-size: 10pt }\n" +
            "\t\ttd p.cjk { font-size: 10pt }\n" +
            "\t-->\n" +
            "\t</style>\n" +
            "</head>\n" +
            "<body lang=\"es-CL\" text=\"#000000\" dir=\"ltr\" style=\"background: transparent\">\n" +
            "<p>La siguiente incidencia ha sido <b>$tipoCambio</b><span style=\"font-weight: normal\">,\n" +
            "ahora tiene el estado </span><b>$estadoTransicion</b> con el\n" +
            "siguiente detalle: <b>$detalleTransicion</b></p>\n" +
            "<table width=\"100%\" cellpadding=\"2\" cellspacing=\"0\" style=\"page-break-before: auto; page-break-after: auto\">\n" +
            "\t<col width=\"44*\">\n" +
            "\t<col width=\"21*\">\n" +
            "\t<col width=\"56*\">\n" +
            "\t<col width=\"54*\">\n" +
            "\t<col width=\"0*\">\n" +
            "\t<col width=\"20*\">\n" +
            "\t<col width=\"3*\">\n" +
            "\t<col width=\"59*\">\n" +
            "\t<tbody>\n" +
            "\t\t<tr>\n" +
            "\t\t\t<td colspan=\"8\" width=\"100%\" height=\"48\" valign=\"top\" style=\"border: 1px solid #000000; padding: 0.1cm\">\n" +
            "\t\t\t\t<p class=\"western\" style=\"margin-bottom: 0.5cm\"><img src=\"data:image/png;base64,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\" name=\"Imagen1\" align=\"left\" width=\"91\" height=\"78\" border=\"0\"><br><br>\n" +
            "\t\t\t\t</p>\n" +
            "\t\t\t\t<p class=\"western\" align=\"center\"><font size=\"3\" style=\"font-size: 12pt\"><b>Transportes\n" +
            "\t\t\t\tlas Ventanas/Puchuncaví</b></font></p>\n" +
            "\t\t\t</td>\n" +
            "\t\t</tr>\n" +
            "\t</tbody>\n" +
            "\t<tbody>\n" +
            "\t\t<tr>\n" +
            "\t\t\t<td width=\"17%\" style=\"border-top: none; border-bottom: none; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.05cm; padding-right: 0cm\">\n" +
            "\t\t\t\t<p class=\"western\" style=\"margin-left: 0.5cm\"><b>Cod. Incidencia</b></p>\n" +
            "\t\t\t</td>\n" +
            "\t\t\t<td width=\"8%\" style=\"border: none; padding: 0cm\">\n" +
            "\t\t\t\t<p class=\"western\">:</p>\n" +
            "\t\t\t</td>\n" +
            "\t\t\t<td colspan=\"6\" width=\"75%\" style=\"border-top: none; border-bottom: none; border-left: none; border-right: 1px solid #000000; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0cm; padding-right: 0.05cm\">\n" +
            "\t\t\t\t<p class=\"western\">$codIncidencia</p>\n" +
            "\t\t\t</td>\n" +
            "\t\t</tr>\n" +
            "\t\t<tr>\n" +
            "\t\t\t<td width=\"17%\" style=\"border-top: none; border-bottom: none; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.05cm; padding-right: 0cm\">\n" +
            "\t\t\t\t<p class=\"western\" style=\"margin-left: 0.5cm\"><b>Informador</b></p>\n" +
            "\t\t\t</td>\n" +
            "\t\t\t<td width=\"8%\" style=\"border: none; padding: 0cm\">\n" +
            "\t\t\t\t<p class=\"western\">:</p>\n" +
            "\t\t\t</td>\n" +
            "\t\t\t<td colspan=\"6\" width=\"75%\" style=\"border-top: none; border-bottom: none; border-left: none; border-right: 1px solid #000000; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0cm; padding-right: 0.05cm\">\n" +
            "\t\t\t\t<p class=\"western\">$informador</p>\n" +
            "\t\t\t</td>\n" +
            "\t\t</tr>\n" +
            "\t\t<tr>\n" +
            "\t\t\t<td width=\"17%\" style=\"border-top: none; border-bottom: none; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.05cm; padding-right: 0cm\">\n" +
            "\t\t\t\t<p class=\"western\" style=\"margin-left: 0.5cm\"><b>Apoyo</b></p>\n" +
            "\t\t\t</td>\n" +
            "\t\t\t<td width=\"8%\" style=\"border: none; padding: 0cm\">\n" +
            "\t\t\t\t<p class=\"western\">:</p>\n" +
            "\t\t\t</td>\n" +
            "\t\t\t<td colspan=\"6\" width=\"75%\" style=\"border-top: none; border-bottom: none; border-left: none; border-right: 1px solid #000000; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0cm; padding-right: 0.05cm\">\n" +
            "\t\t\t\t<p class=\"western\">$apoyo</p>\n" +
            "\t\t\t</td>\n" +
            "\t\t</tr>\n" +
            "        <tr>\n" +
            "            <td width=\"17%\" style=\"border-top: none; border-bottom: none; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.05cm; padding-right: 0cm\">\n" +
            "                <p class=\"western\" style=\"margin-left: 0.5cm\"><b>Severidad</b></p>\n" +
            "            </td>\n" +
            "            <td width=\"8%\" style=\"border: none; padding: 0cm\">\n" +
            "                <p class=\"western\">:</p>\n" +
            "            </td>\n" +
            "            <td width=\"22%\" style=\"border: none; padding: 0cm\">\n" +
            "                <p class=\"western\">$severidad</p>\n" +
            "            </td>\n" +
            "            <td colspan=\"2\" width=\"21%\" style=\"border: none; padding: 0cm\">\n" +
            "                <p class=\"western\"><b>Prioridad</b></p>\n" +
            "            </td>\n" +
            "            <td colspan=\"2\" width=\"9%\" style=\"border: none; padding: 0cm\">\n" +
            "                <p class=\"western\">:</p>\n" +
            "            </td>\n" +
            "            <td width=\"23%\" style=\"border-top: none; border-bottom: none; border-left: none; border-right: 1px solid #000000; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0cm; padding-right: 0.05cm\">\n" +
            "                <p class=\"western\">$prioridad</p>\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "\t\t<tr>\n" +
            "\t\t\t<td width=\"17%\" style=\"border-top: none; border-bottom: none; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.05cm; padding-right: 0cm\">\n" +
            "\t\t\t\t<p class=\"western\" style=\"margin-left: 0.5cm\"><b>Fecha</b></p>\n" +
            "\t\t\t</td>\n" +
            "\t\t\t<td width=\"8%\" style=\"border: none; padding: 0cm\">\n" +
            "\t\t\t\t<p class=\"western\">:</p>\n" +
            "\t\t\t</td>\n" +
            "\t\t\t<td width=\"22%\" style=\"border: none; padding: 0cm\">\n" +
            "\t\t\t\t<p class=\"western\">$fecha</p>\n" +
            "\t\t\t</td>\n" +
            "\t\t\t<td width=\"21%\" style=\"border: none; padding: 0cm\">\n" +
            "\t\t\t\t<p class=\"western\"><b>Resolución Programada</b></p>\n" +
            "\t\t\t</td>\n" +
            "\t\t\t<td colspan=\"2\" width=\"8%\" style=\"border: none; padding: 0cm\">\n" +
            "\t\t\t\t<p class=\"western\">:</p>\n" +
            "\t\t\t</td>\n" +
            "\t\t\t<td colspan=\"2\" width=\"24%\" style=\"border-top: none; border-bottom: none; border-left: none; border-right: 1px solid #000000; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0cm; padding-right: 0.05cm\">\n" +
            "\t\t\t\t<p class=\"western\">$fResolucion</p>\n" +
            "\t\t\t</td>\n" +
            "\t\t</tr>\n" +
            "\t\t<tr>\n" +
            "\t\t\t<td width=\"17%\" height=\"24\" style=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">\n" +
            "\t\t\t\t<p class=\"western\" style=\"margin-left: 0.5cm\"><b>Detalle</b></p>\n" +
            "\t\t\t</td>\n" +
            "\t\t\t<td width=\"8%\" style=\"border-top: none; border-bottom: 1px solid #000000; border-left: none; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0cm; padding-right: 0cm\">\n" +
            "\t\t\t\t<p class=\"western\">:</p>\n" +
            "\t\t\t</td>\n" +
            "\t\t\t<td colspan=\"6\" width=\"75%\" style=\"border-top: none; border-bottom: 1px solid #000000; border-left: none; border-right: 1px solid #000000; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0cm; padding-right: 0.05cm\">\n" +
            "\t\t\t\t<p class=\"western\">$detalle</p>\n" +
            "\t\t\t</td>\n" +
            "\t\t</tr>\n" +
            "\t</tbody>\n" +
            "</table>\n" +
            "<p style=\"margin-bottom: 0cm; line-height: 100%\"><br>\n" +
            "</p>\n" +
            "</body>\n" +
            "</html>";

    // Correo de la aplicación
    private String correo = "piir@transportesventanas.cl";
    private String claveCorreo = "piir12345";
    private String servidorCorreo = "smtp.googlemail.com";
    private int puertoCorreo = 465;

    // Genericos
    private List<Mes> meses;
    private List<Horario> horarios;
    private int cantidadFilasTablas = 15;
    private String filasPorPaginaTemplate = "10, 50, 100, 1000";
    private String paginadorTemplate = "{FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}";
    private String pathArchivos;
    private Random random;

    // cliente de correo, alarmas GPS
    private String marUsuarioCorreo = "colivares@transportesventanas.cl";
    private String marContrasennaCorreo = "colivares1";
    private String marServidorCorreo = "imap.googlemail.com";
    private Integer marPuertoCorreo = 993;
    private String marProtocoloCorreo = "imaps";

    // modulos
    private String moduloAlarmasGPS = "Modulo Alarmas GPS";
    private String moduloProgramaActividades = "Modulo Programa Actividades";
    private String moduloPersonal = "Mantenedor Personal App";
    private String moduloAdministradorRegistros = "Administrador de Registros";
    private String moduloEquipos = "Mantenedor Tractos";
    private String moduloMantenciones = "Modulo Mantenciones";

    @Autowired
    LogicaVariables logicaVariables;

    public Constantes() {
        iniciaConstantes();
    }

    @Transactional(readOnly = true)
    private void iniciaConstantes() {
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

        estadosCiviles = new ArrayList<String>();
        estadosCiviles.add("Soltero");
        estadosCiviles.add("Casado");
        estadosCiviles.add("Viudo");
        estadosCiviles.add("Separado");

        tiposUnidad = null;
        random = new SecureRandom();
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

    public String getMarUsuarioCorreo() {
        return marUsuarioCorreo;
    }

    public String getMarContrasennaCorreo() {
        return marContrasennaCorreo;
    }

    public String getMarServidorCorreo() {
        return marServidorCorreo;
    }

    public Integer getMarPuertoCorreo() {
        return marPuertoCorreo;
    }

    public String getMarProtocoloCorreo() {
        return marProtocoloCorreo;
    }

    public List<String> getEstadosCiviles() {
        return estadosCiviles;
    }

    public String getModuloPersonal() {
        return moduloPersonal;
    }

    public int getFonasa() {
        return fonasa;
    }

    public int getIsapre() {
        return isapre;
    }

    public int getUnidadPesos() {
        return unidadPesos;
    }

    public int getUnidadUf() {
        return unidadUf;
    }

    public int getUnidadPorcentaje() {
        return unidadPorcentaje;
    }

    public List<TipoUnidad> getTiposUnidad() {
        if (tiposUnidad == null) {
            tiposUnidad = logicaVariables.obtenerTiposUnidad();
        }
        return tiposUnidad;
    }

    public String getModuloAdministradorRegistros() {
        return moduloAdministradorRegistros;
    }

    public int getRegistroR112() {
        return registroR112;
    }

    public String getPathArchivos() {
        if (pathArchivos == null)
            pathArchivos = System.getProperty("catalina.home") + "/static/workcenter/";
        return pathArchivos;
    }

    public int getEquipoTipoTracto() {
        return equipoTipoTracto;
    }

    public int getEquipoTipoBatea() {
        return equipoTipoBatea;
    }

    public Integer getRegistroInspeccionAvanzada() {
        return registroInspeccionAvanzada;
    }

    public String getModuloEquipos() {
        return moduloEquipos;
    }

    public int getCargoMecanico() {
        return cargoMecanico;
    }

    public int getAlarmaProximaMantencion() {
        return alarmaProximaMantencion;
    }

    public String getModuloMantenciones() {
        return moduloMantenciones;
    }

    public int getPiirEstadoInicial() {
        return piirEstadoInicial;
    }

    public String getCorreo() {
        return correo;
    }

    public String getClaveCorreo() {
        return claveCorreo;
    }

    public String getServidorCorreo() {
        return servidorCorreo;
    }

    public int getPuertoCorreo() {
        return puertoCorreo;
    }

    public int getPiirEstadoCerradaPorSistema() {
        return piirEstadoCerradaPorSistema;
    }

    public String getPirrMensajeCorreo() {
        return pirrMensajeCorreo;
    }

    public Random getRandom() {
        return random;
    }

	public Double getAportePorcentajeTrabajador() {
		return aportePorcentajeTrabajador;
	}

	public void setAportePorcentajeTrabajador(Double aportePorcentajeTrabajador) {
		this.aportePorcentajeTrabajador = aportePorcentajeTrabajador;
	}

	public Double getAportePorcentajeEmpleador() {
		return aportePorcentajeEmpleador;
	}

	public void setAportePorcentajeEmpleador(Double aportePorcentajeEmpleador) {
		this.aportePorcentajeEmpleador = aportePorcentajeEmpleador;
	}
	
	public int getConceptoAnticipo(){
		return conceptoAnticipo;
	}
	
	public String getDiasAnticipo(){
		return diasAnticipo;
	}

	public Boolean getGenericaAdministrativo() {
		return genericaAdministrativo;
	}

	public Boolean getGenericaConductores() {
		return genericaConductores;
	}

	public Integer getDiasTrabajados() {
		return diasTrabajados;
	}

	public Double getAporteContradoNoIdefinido() {
		return aporteContradoNoIdefinido;
	}
}