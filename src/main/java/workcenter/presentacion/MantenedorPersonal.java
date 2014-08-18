package workcenter.presentacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Cargo;
import workcenter.entidades.ContratoPersonal;
import workcenter.entidades.DocumentoPersonal;
import workcenter.entidades.Empresa;
import workcenter.entidades.Personal;
import workcenter.entidades.Prevision;
import workcenter.entidades.PrevisionContrato;
import workcenter.entidades.SancionRetiradaPersonal;
import workcenter.entidades.Sancionado;
import workcenter.entidades.TipoUnidad;
import workcenter.entidades.ValorPrevisionPersonal;
import workcenter.entidades.Variable;
import workcenter.negocio.LogicaEmpresas;
import workcenter.negocio.LogicaPersonal;
import workcenter.negocio.LogicaPrevisiones;
import workcenter.negocio.LogicaVariables;
import workcenter.util.components.Constantes;
import workcenter.util.components.SesionCliente;
import workcenter.util.pojo.Descargable;
import workcenter.util.pojo.FacesUtil;
import workcenter.util.pojo.FilterOption;

/**
 *
 * @author colivares
 */
@Component
@Scope("flow")
public class MantenedorPersonal implements Serializable {

    private List<Personal> listaPersonal;
    private List<Personal> listaFiltradaPersonal;
    private Personal personalSeleccionado;
    private String filtroEmpleador;
    private Integer filtroEstado;
    private List<FilterOption> opcionesFiltroEstado;
    private List<FilterOption> opcionesSalud;
    private Integer tipoSalud;
    private String rutIngresado;
    private String paginaPrevia;
    private Integer gratificacionCalculada;
    private ContratoPersonal contratoSeleccionado;
    private List<Empresa> empleadores;
    private Empresa empleadorSeleccionado;
    private List<Cargo> cargos;
    private Cargo cargoSeleccionado;
    private List<Prevision> isapres;
    private List<Prevision> afps;
    private Prevision isapreSeleccionada;
    private Prevision afpSeleccionada;
    private ValorPrevisionPersonal valorSalud;
    private Integer hrsEspera;
    private Integer semanaCorrida;
    private SancionRetiradaPersonal retiroSancion;
    private Sancionado sancion;
    private DocumentoPersonal docSeleccionado;

    @Autowired
    LogicaPersonal logicaPersonal;

    @Autowired
    LogicaVariables logicaVariables;

    @Autowired
    LogicaEmpresas logicaEmpresas;

    @Autowired
    LogicaPrevisiones logicaPrevisiones;

    @Autowired
    Constantes constantes;

    @Autowired
    SesionCliente sesionCliente;

    public String inicio() {
        opcionesFiltroEstado = new ArrayList<FilterOption>();
        opcionesFiltroEstado.add(new FilterOption(0, "-- Todos --"));
        opcionesFiltroEstado.add(new FilterOption(1, "Habilitados"));
        opcionesFiltroEstado.add(new FilterOption(2, "Bloqueados"));

        opcionesSalud = new ArrayList<FilterOption>();
        opcionesSalud.add(new FilterOption(constantes.getFonasa(), "FONASA"));
        opcionesSalud.add(new FilterOption(constantes.getIsapre(), "ISAPRE"));
        return irListaPersonal();
    }

    public void sancionar() {
        sancion.setFecha(new Date());
        sancion.setNivel(0); // no hay niveles implementandos
        sancion.setSancionado(personalSeleccionado);
        personalSeleccionado.setSancion(sancion);
        logicaPersonal.guardar(personalSeleccionado);
        FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Se ha sancionado al personal "+personalSeleccionado.getNombreCompleto()+" por "+sancion.getMotivo());
    }

    public void quitarSancion() {
        retiroSancion.setFecha(new Date());
        retiroSancion.setPerdonadapor(logicaPersonal.obtener(sesionCliente.getUsuario().getRut()));
        personalSeleccionado.setSancion(null);
        logicaPersonal.guardar(personalSeleccionado);
        FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Se ha quitado la sanción al personal "+personalSeleccionado.getNombreCompleto()+" por "+retiroSancion.getMotivosancion()+" debido a "+retiroSancion.getMotivolevantada());
    }

    public String volverDesdeDesbloqueo() {
        if (retiroSancion.getId() == null) {
            int indiceEliminado = 0;
            for (int i = 0; personalSeleccionado.getSancionesRetiradas() != null && i < personalSeleccionado.getSancionesRetiradas().size(); i++) {
                if (personalSeleccionado.getSancionesRetiradas().get(i).getId() == null
                        && personalSeleccionado.getSancionesRetiradas().get(i).getFechasancion().equals(retiroSancion.getFechasancion())
                        && personalSeleccionado.getSancionesRetiradas().get(i).getMotivosancion().equals(retiroSancion.getMotivosancion())
                        && personalSeleccionado.getSancionesRetiradas().get(i).getSancionado().equals(retiroSancion.getSancionado())) {
                    indiceEliminado = i;
                    break;
                }
            }
            personalSeleccionado.getSancionesRetiradas().remove(indiceEliminado);
        }
        retiroSancion = null;
        return irListaPersonal();
    }

    public String volverDesdeBloqueo() {
        if (sancion.getId() == null) {
            personalSeleccionado.setSancion(null);
        }
        return irListaPersonal();
    }

    public String irVerFicha(Personal p) {
        personalSeleccionado = p;
        personalSeleccionado.setDocumentos(logicaPersonal.obtenerDocumentos(personalSeleccionado));
        rutIngresado = p.getRut() + "-" + p.getDigitoverificador();
        return "flowMostrarFicha";
    }

    public String irBloqueoPersonal(Personal p) {
        personalSeleccionado = p;
        sancion = new Sancionado();
        return "flowBloquear";
    }

    public String irDesbloqueoPersonal(Personal p) {
        personalSeleccionado = p;
        if (personalSeleccionado.getSancion() == null) {
            FacesUtil.mostrarMensajeInformativo("Error", "El personal seleccionado no tiene sanciones activas");
            return null;
        }
        retiroSancion = new SancionRetiradaPersonal();
        retiroSancion.setMotivosancion(personalSeleccionado.getSancion().getMotivo());
        retiroSancion.setSancionado(personalSeleccionado);
        retiroSancion.setFechasancion(personalSeleccionado.getSancion().getFecha());
        retiroSancion.setNivel(personalSeleccionado.getSancion().getNivel());
        personalSeleccionado.setSancionesRetiradas(logicaPersonal.obtenerSancionesRetiradas(personalSeleccionado));
        personalSeleccionado.getSancionesRetiradas().add(retiroSancion);
        return "flowDesbloquear";
    }

    public String irActualizarDocCarpeta(DocumentoPersonal dp) {
        docSeleccionado = dp;
        return "flowActualizarDocumento";
    }

    public String irListaPersonal() {
        listaPersonal = logicaPersonal.obtenerTodos();
        filtroEmpleador = null;
        filtroEstado = null;
        return "flowListarPersonal";
    }

    public String irContratosPersonal(Personal p) {
        personalSeleccionado = p;
        personalSeleccionado.setContratospersonalCollection(logicaPersonal.obtenerContratos(personalSeleccionado));
        paginaPrevia = "flowListarPersonal";
        return "flowVerContratos";
    }

    public String irContratosPersonal() {
        String retorno = irContratosPersonal(personalSeleccionado);
        paginaPrevia = "flowMostrarFicha";
        return retorno;
    }

    public String irEditarContrato(ContratoPersonal cp) {
        contratoSeleccionado = cp;

        setearValoresCalculados();

        empleadores = logicaEmpresas.obtenerEmpleadores();
        empleadorSeleccionado = logicaPersonal.obtenerEmpleador(personalSeleccionado);

        cargos = logicaPersonal.obtenerCargos();
        cargoSeleccionado = cp.getCargo();

        isapres = logicaPrevisiones.obtenerIsapres();
        afps = logicaPrevisiones.obtenerAfps();
        cp.setPrevisiones(logicaPrevisiones.obtenerPrevisionesContrato(cp));
        for (PrevisionContrato pc : cp.getPrevisiones()) {
            if (pc.getPrevision().getTipo().equals("salud")) {
                isapreSeleccionada = pc.getPrevision();
                if (isapreSeleccionada.getNombre().equalsIgnoreCase("fonasa")) {
                    tipoSalud = constantes.getFonasa();
                } else {
                    tipoSalud = constantes.getIsapre();
                }
            } else {
                afpSeleccionada = pc.getPrevision();
            }
        }
        valorSalud = logicaPersonal.obtenerValorPrevisionSaludActual(cp);
        if (valorSalud == null) {
            valorSalud = new ValorPrevisionPersonal();
            valorSalud.setContrato(cp);
            valorSalud.setValor(0);
            for (TipoUnidad tu : constantes.getTiposUnidad()) {
                if (tu.getId().intValue() == constantes.getUnidadPesos()) {
                    valorSalud.setUnidad(tu);
                    break;
                }
            }
        }
        return "flowEditarContrato";
    }

    public void cambiaTipoUnidad() {
        Integer tipoUnidad = Integer.valueOf(FacesUtil.obtenerHttpServletRequest().getParameter("frmPersonal:sTipoUnidad"));
        for (TipoUnidad tu : constantes.getTiposUnidad()) {
            if (tu.getId().intValue() == tipoUnidad.intValue()) {
                valorSalud.setUnidad(tu);
                break;
            }
        }
    }

    public void cambiaTipoSalud() {
        tipoSalud = Integer.valueOf(FacesUtil.obtenerHttpServletRequest().getParameter("frmPersonal:sTipoSalud"));
    }

    public boolean esFonasa() {
        return tipoSalud != null && tipoSalud.intValue() == constantes.getFonasa();
    }

    private void setearValoresCalculados() {
        Variable sueldoMinimo = logicaVariables.obtenerSueldoMinimoActual();
        int im = sueldoMinimo != null ? Integer.parseInt(sueldoMinimo.getValor()) : 0;
        gratificacionCalculada = contratoSeleccionado.getSueldoBase() / 4 < (int) (4.75 * im / 12) ? (contratoSeleccionado.getSueldoBase() / 4) : (int) (4.75 * im / 12);
        if (contratoSeleccionado.getCargo().getNombrecargo().equalsIgnoreCase("conductor")) {
            hrsEspera = ((int) (im * 1.5 / 180 * 88));
            semanaCorrida = 41200;
        } else {
            hrsEspera = null;
            semanaCorrida = null;
        }
    }

    public String irAtras() {
        return paginaPrevia;
    }

    public Empresa obtenerEmpleador(Personal p) {
        return logicaPersonal.obtenerEmpleador(p);
    }

    public void filtraPorEmpleadores() {
        listaFiltradaPersonal = new ArrayList<Personal>();
        if (filtroEmpleador == null || "".equals(filtroEmpleador)) {
            listaFiltradaPersonal = listaPersonal;
            return;
        }
        Empresa e;
        for (Personal p : listaPersonal) {
            e = logicaPersonal.obtenerEmpleador(p);
            if (e == null) {
                continue;
            }
            if (e.getNombre().toLowerCase().contains(filtroEmpleador.toLowerCase())) {
                listaFiltradaPersonal.add(p);
            }
        }
    }

    public void filtrarPorEstado() {
        listaFiltradaPersonal = new ArrayList<Personal>();
        if (filtroEstado == null || filtroEstado.intValue() == 0) {
            listaFiltradaPersonal = listaPersonal;
            return;
        }
        for (Personal p : listaPersonal) {
            if (filtroEstado.intValue() == 2 && p.getSancion() == null) {
                continue;
            } else if (filtroEstado.intValue() == 1 && p.getSancion() != null) {
                continue;
            }
            listaFiltradaPersonal.add(p);
        }
    }

    public boolean estaBloqueado(Personal p) {
        p.setSancion(logicaPersonal.obtenerSancion(p));
        if (p.getSancion() == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean esUF(TipoUnidad unidad) {
        return unidad.getId().intValue() == constantes.getUnidadUf();
    }

    public StreamedContent generaDescargable(DocumentoPersonal dp) {
        Descargable d = new Descargable(new File(System.getProperty("catalina.base") + "/static" + FacesUtil.obtenerContextPath() + "/" + dp.getArchivo()));
        return d.getStreamedContent();
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

    public String getFiltroEmpleador() {
        return filtroEmpleador;
    }

    public void setFiltroEmpleador(String filtroEmpleador) {
        this.filtroEmpleador = filtroEmpleador;
    }

    public Integer getFiltroEstado() {
        return filtroEstado;
    }

    public void setFiltroEstado(Integer filtroEstado) {
        this.filtroEstado = filtroEstado;
    }

    public List<FilterOption> getOpcionesFiltroEstado() {
        return opcionesFiltroEstado;
    }

    public void setOpcionesFiltroEstado(List<FilterOption> opcionesFiltroEstado) {
        this.opcionesFiltroEstado = opcionesFiltroEstado;
    }

    public String getRutIngresado() {
        return rutIngresado;
    }

    public void setRutIngresado(String rutIngresado) {
        this.rutIngresado = rutIngresado;
    }

    public ContratoPersonal getContratoSeleccionado() {
        return contratoSeleccionado;
    }

    public void setContratoSeleccionado(ContratoPersonal contratoSeleccionado) {
        this.contratoSeleccionado = contratoSeleccionado;
    }

    public List<FilterOption> getOpcionesSalud() {
        return opcionesSalud;
    }

    public void setOpcionesSalud(List<FilterOption> opcionesSalud) {
        this.opcionesSalud = opcionesSalud;
    }

    public Integer getGratificacionCalculada() {
        return gratificacionCalculada;
    }

    public void setGratificacionCalculada(Integer gratificacionCalculada) {
        this.gratificacionCalculada = gratificacionCalculada;
    }

    public List<Empresa> getEmpleadores() {
        return empleadores;
    }

    public void setEmpleadores(List<Empresa> empleadores) {
        this.empleadores = empleadores;
    }

    public Empresa getEmpleadorSeleccionado() {
        return empleadorSeleccionado;
    }

    public void setEmpleadorSeleccionado(Empresa empleadorSeleccionado) {
        this.empleadorSeleccionado = empleadorSeleccionado;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public Cargo getCargoSeleccionado() {
        return cargoSeleccionado;
    }

    public void setCargoSeleccionado(Cargo cargoSeleccionado) {
        this.cargoSeleccionado = cargoSeleccionado;
    }

    public List<Prevision> getIsapres() {
        return isapres;
    }

    public void setIsapres(List<Prevision> isapres) {
        this.isapres = isapres;
    }

    public List<Prevision> getAfps() {
        return afps;
    }

    public void setAfps(List<Prevision> afps) {
        this.afps = afps;
    }

    public Prevision getIsapreSeleccionada() {
        return isapreSeleccionada;
    }

    public void setIsapreSeleccionada(Prevision isapreSeleccionada) {
        this.isapreSeleccionada = isapreSeleccionada;
    }

    public Prevision getAfpSeleccionada() {
        return afpSeleccionada;
    }

    public void setAfpSeleccionada(Prevision afpSeleccionada) {
        this.afpSeleccionada = afpSeleccionada;
    }

    public Integer getTipoSalud() {
        return tipoSalud;
    }

    public void setTipoSalud(Integer tipoSalud) {
        this.tipoSalud = tipoSalud;
    }

    public ValorPrevisionPersonal getValorSalud() {
        return valorSalud;
    }

    public void setValorSalud(ValorPrevisionPersonal valorSalud) {
        this.valorSalud = valorSalud;
    }

    public Integer getHrsEspera() {
        return hrsEspera;
    }

    public void setHrsEspera(Integer hrsEspera) {
        this.hrsEspera = hrsEspera;
    }

    public Integer getSemanaCorrida() {
        return semanaCorrida;
    }

    public void setSemanaCorrida(Integer semanaCorrida) {
        this.semanaCorrida = semanaCorrida;
    }

    public SancionRetiradaPersonal getRetiroSancion() {
        return retiroSancion;
    }

    public void setRetiroSancion(SancionRetiradaPersonal retiroSancion) {
        this.retiroSancion = retiroSancion;
    }

    public Sancionado getSancion() {
        return sancion;
    }

    public void setSancion(Sancionado sancion) {
        this.sancion = sancion;
    }

    public DocumentoPersonal getDocSeleccionado() {
        return docSeleccionado;
    }

    public void setDocSeleccionado(DocumentoPersonal docSeleccionado) {
        this.docSeleccionado = docSeleccionado;
    }
}
