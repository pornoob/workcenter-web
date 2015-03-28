package workcenter.presentacion.personal;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.LogicaDocumentos;
import workcenter.negocio.LogicaEmpresas;
import workcenter.negocio.faena.LogicaFaena;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.negocio.personal.LogicaPrevisiones;
import workcenter.negocio.personal.LogicaVariables;
import workcenter.util.components.Constantes;
import workcenter.util.components.Formato;
import workcenter.util.components.SesionCliente;
import workcenter.util.pojo.Descargable;
import workcenter.util.components.FacesUtil;
import workcenter.util.pojo.FilterOption;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.springframework.util.FileCopyUtils.BUFFER_SIZE;

/**
 * @author colivares
 */
@Component
@Scope("flow")
public class MantenedorPersonal implements Serializable {

    private List<Personal> listaPersonal;
    private List<Personal> listaFiltradaPersonal;
    private Personal personalSeleccionado;
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
    private List<DocumentoPersonal> documentos;
    private List<TipoDocPersonal> tiposDocumentos;
    private transient UploadedFile archivo;
    private List<FilterOption> listaFaenas;

    private enum TipoOperacion {
        EDITAR,
        ACTUALIZAR,
        INGRESAR
    }

    ;

    private TipoOperacion operacion;

    @Autowired
    private LogicaPersonal logicaPersonal;

    @Autowired
    private LogicaVariables logicaVariables;

    @Autowired
    private LogicaEmpresas logicaEmpresas;

    @Autowired
    private LogicaPrevisiones logicaPrevisiones;

    @Autowired
    private Constantes constantes;

    @Autowired
    private SesionCliente sesionCliente;

    @Autowired
    private Formato formato;

    @Autowired
    private LogicaDocumentos logicaDocumentos;
    
    @Autowired
    private LogicaFaena logicaFaena;

    public String inicio() {
        opcionesFiltroEstado = new ArrayList<FilterOption>();
        opcionesFiltroEstado.add(new FilterOption(0, "-- Todos --"));
        opcionesFiltroEstado.add(new FilterOption(1, "Habilitados"));
        opcionesFiltroEstado.add(new FilterOption(2, "Bloqueados"));

        opcionesSalud = new ArrayList<FilterOption>();
        opcionesSalud.add(new FilterOption(constantes.getFonasa(), "FONASA"));
        opcionesSalud.add(new FilterOption(constantes.getIsapre(), "ISAPRE"));
        
        listaFaenas = new ArrayList<FilterOption>();
        listaFaenas.add(new FilterOption(1, "Chagres"));
        listaFaenas.add(new FilterOption(2, "Tortolas"));
        listaFaenas.add(new FilterOption(3, "El Soldado"));
        
//        listaFaenas = logicaFaena.obtenerTodasFaenas();
        return irListaPersonal();
    }
    
	public void sancionar() {
        sancion.setFecha(new Date());
        sancion.setNivel(0); // no hay niveles implementandos
        sancion.setSancionado(personalSeleccionado);
        personalSeleccionado.setSancion(sancion);
        logicaPersonal.guardar(personalSeleccionado);
        FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Se ha sancionado al personal " + personalSeleccionado.getNombreCompleto() + " por " + sancion.getMotivo());
    }

    public void quitarSancion() {
        retiroSancion.setFecha(new Date());
        retiroSancion.setPerdonadapor(logicaPersonal.obtener(sesionCliente.getUsuario().getRut()));
        personalSeleccionado.setSancion(null);
        logicaPersonal.guardar(personalSeleccionado);
        FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Se ha quitado la sanción al personal " + personalSeleccionado.getNombreCompleto() + " por " + retiroSancion.getMotivosancion() + " debido a " + retiroSancion.getMotivolevantada());
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

    public boolean filtroEmpleador(Object valor, Object filtro, Locale idioma) {
        if (valor == null) return false;
        Empresa e = logicaPersonal.obtenerEmpleador((Personal) valor);
        if (e == null) return false;
        return e.getNombre().toLowerCase().contains(filtro.toString().toLowerCase());
    }

    public boolean filtroEstado(Object valor, Object filtro, Locale idioma) {
        if (valor == null) return false;
        Personal p = (Personal) valor;
        Integer opcion = Integer.parseInt(filtro.toString());
        if (opcion.intValue() == 2 && p.getSancion() == null) {
            return false;
        } else if (opcion.intValue() == 1 && p.getSancion() != null) {
            return false;
        } else {
            return true;
        }
    }
    
    // se agrega filtro faena
    
    public boolean filtroFaena(Object valor, Object filtro, Locale idioma) {
    
    	 if (valor == null) return false;
         Personal p = logicaPersonal.obtenerPersonal((Personal) valor);
         if (p.getFaena() == null) return false;
         boolean tieneFaena = false;
         for (Faena f : p.getFaena()){	
     		if (f.getId() == Integer.parseInt(filtro.toString())){
     		 tieneFaena = true;
     		}
   	}
        if  (tieneFaena == true){return true;}else return false;       
         
        }
//  	if ( filtro.toString().length() > 0 ){   
//    	
//    	Personal e = (Personal)valor;
//    	boolean tiene = false;
//    	for (Faena f : e.getFaena()){	
//    		if (f.getNombre().toLowerCase().contains(filtro.toString().toLowerCase())){
//    		 tiene = true;
//    		}
//    	}
//        if  (tiene == true){return true;}else return false;
//    	}else {return false;}
    
//    if (valor == null) return false;
//    Faena f = logicaFaena.obtenerFaena(filtro.toString());
//    if (f == null) return false;
//    return f.getNombre().toLowerCase().contains(filtro.toString().toLowerCase());
    	
    
    
    //-----------------------
    
    //  metodo para buscar personas
    public Personal obtenerPersonal(Personal p) {
        return logicaPersonal.obtenerPersonal(p);
    }
    
    // metodo para filtrar combobox
    
    public void filtrarComboFaena(Personal p){
    	
    	for (Faena f : p.getFaena()){
    		for (FilterOption o : listaFaenas){
    			if (f.getNombre().toString() == o.getLabel()){
    				listaFaenas.remove(o);
    			}
    		}
    	}
    }


    public String irCambiarFoto() {
        return "flowCambiarFoto";
    }

    public String irVerFicha(Personal p) {
        personalSeleccionado = p;
        personalSeleccionado.setDocumentos(logicaPersonal.obtenerDocumentos(personalSeleccionado));
        documentos = new ArrayList<DocumentoPersonal>(personalSeleccionado.getDocumentos());
        tiposDocumentos = new ArrayList<TipoDocPersonal>();
        for (TipoDocPersonal tdp : logicaPersonal.obtenerTiposDocPorCargo(personalSeleccionado)) {
            boolean encontrado = false;
            for (DocumentoPersonal dp : documentos) {
                if (dp.getTipo().equals(tdp)) {
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) tiposDocumentos.add(tdp);
        }
        rutIngresado = p.getRut() + "-" + p.getDigitoverificador();
        return "flowMostrarFicha";
    }

    public String irBloqueoPersonal(Personal p) {
        personalSeleccionado = p;
        sancion = new Sancionado();
        return "flowBloquear";
    }

    public String irDocumentos() {
        return null;
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
        operacion = TipoOperacion.ACTUALIZAR;
        return "flowActualizarDocumento";
    }

    public String irEditarDocCarpeta(DocumentoPersonal dp) {
        docSeleccionado = dp;
        operacion = TipoOperacion.EDITAR;
        return "flowActualizarDocumento";
    }

    public String irIngresarDocCarpeta() {
        docSeleccionado = new DocumentoPersonal();
        operacion = TipoOperacion.INGRESAR;
        return "flowActualizarDocumento";
    }

    public String irListaPersonal() {
        listaPersonal = logicaPersonal.obtenerTodos();      
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

    public void subirArchivo() {
        String path = constantes.getPathArchivos() + "Documentos/personal/"
                + formato.numeroAgrupado(personalSeleccionado.getRut())
                + "-" + personalSeleccionado.getDigitoverificador() + "/";
        new File(path).mkdirs();
        path += docSeleccionado.getTipo().getEtiqueta() + archivo.getFileName().substring(archivo.getFileName().lastIndexOf('.'));
        if (docSeleccionado.getId() == null) {
            docSeleccionado.setPersonal(personalSeleccionado);
            personalSeleccionado.getDocumentos().add(docSeleccionado);
        }
        DocumentoPersonal existente = null;
        if (operacion == TipoOperacion.ACTUALIZAR) {
            // respaldamos existente antes de subir
            for (DocumentoPersonal de : documentos) {
                if (de.getTipo().equals(docSeleccionado.getTipo())) {
                    existente = de;
                    break;
                }
            }
            Documento d = new Documento();
            d.setFecha(new Date());
            System.err.println("EXISTENTE: " + existente);
            d.setNombreOriginal(existente.getArchivo().substring(existente.getArchivo().lastIndexOf('/') + 1));
            logicaDocumentos.guardarDocumento(d);


            HistorialDocumentosPersonal respaldo = new HistorialDocumentosPersonal();
            respaldo.setNumero(existente.getNumero());
            respaldo.setPersonal(personalSeleccionado.getRut());
            respaldo.setTipo(docSeleccionado.getTipo().getId());
            respaldo.setVencimiento(existente.getVencimiento());
            logicaPersonal.guardarHistorialDocumento(respaldo);

            File result = new File(constantes.getPathArchivos());
            result.mkdirs();
            try {
                Files.move(Paths.get(constantes.getPathArchivos() + existente.getArchivo()), Paths.get(constantes.getPathArchivos() + d.getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            logicaDocumentos.asociarDocumento(d, respaldo);
        } else if (operacion == TipoOperacion.INGRESAR) {
            tiposDocumentos.remove(docSeleccionado.getTipo());
        }
        docSeleccionado.setArchivo(path.substring(constantes.getPathArchivos().length()));
        logicaPersonal.guardarDocumento(docSeleccionado);
        try {
            new File(path).createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bulk;
            InputStream inputStream = archivo.getInputstream();
            while (true) {
                bulk = inputStream.read(buffer);
                if (bulk < 0) {
                    break;
                }
                fileOutputStream.write(buffer, 0, bulk);
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            inputStream.close();
            FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Se ha agregado la nueva imagen");
        } catch (Exception e) {
            FacesUtil.mostrarMensajeInformativo("Operación fallida", "Ha ocurrido un error interno");
        }
    }

    public String irCrearContrato() {
        return irEditarContrato(new ContratoPersonal());
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
        if (cp.getNumero()!= null)
            cp.setPrevisiones(logicaPrevisiones.obtenerPrevisionesContrato(cp));
        else
            cp.setPrevisiones(new ArrayList<PrevisionContrato>());
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

    public void guardarContrato() {
        logicaPersonal.guardarContrato(contratoSeleccionado);
    }

    public List<DocumentoPersonal> obtenerDocumentosActualizados() {
        return logicaPersonal.obtenerDocumentosActualizados(personalSeleccionado);
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
        if (contratoSeleccionado.getNumero() != null) {
            gratificacionCalculada = contratoSeleccionado.getSueldoBase() / 4 < (int) (4.75 * im / 12) ? (contratoSeleccionado.getSueldoBase() / 4) : (int) (4.75 * im / 12);
            if (contratoSeleccionado.getCargo().getNombrecargo().equalsIgnoreCase("conductor")) {
                hrsEspera = ((int) (im * 1.5 / 180 * 88));
                semanaCorrida = 41200;
            } else {
                hrsEspera = null;
                semanaCorrida = null;
            }
        }
    }

    public String irAtras() {
        return paginaPrevia;
    }

    public Empresa obtenerEmpleador(Personal p) {
        return logicaPersonal.obtenerEmpleador(p);
    }

    public boolean estaBloqueado(Personal p) {
        p.setSancion(logicaPersonal.obtenerSancion(p));
        if (p.getSancion() == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean esActualizarDoc() {
        return operacion == TipoOperacion.ACTUALIZAR;
    }

    public boolean esEditarDoc() {
        return operacion == TipoOperacion.EDITAR;
    }

    public boolean esIngresarDoc() {
        return operacion == TipoOperacion.INGRESAR;
    }

    public StreamedContent generaDescargable(DocumentoPersonal dp) {
        Descargable d = new Descargable(new File(constantes.getPathArchivos() + "/" + dp.getArchivo()));
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

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public List<TipoDocPersonal> getTiposDocumentos() {
        return tiposDocumentos;
    }

    public void setTiposDocumentos(List<TipoDocPersonal> tiposDocumentos) {
        this.tiposDocumentos = tiposDocumentos;
    }
    
    public List<FilterOption> getListaFaenas() {
		return listaFaenas;
	}

	public void setListaFaenas(List<FilterOption> listaFaenas) {
		this.listaFaenas = listaFaenas;
	}
}
