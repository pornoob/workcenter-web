package workcenter.presentacion.equipos;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.LogicaEmpresas;
import workcenter.negocio.equipos.LogicaEquipos;
import workcenter.util.components.Constantes;
import workcenter.util.components.FacesUtil;
import workcenter.util.components.SesionCliente;
import workcenter.util.pojo.FilterOption;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.springframework.util.FileCopyUtils.BUFFER_SIZE;

/**
 * Created by colivares on 28-08-14.
 */
@Component
@Scope("flow")
public class MantenedorEquipos implements Serializable {
    @Autowired
    private LogicaEquipos logicaEquipos;

    @Autowired
    private LogicaEmpresas logicaEmpresas;

    @Autowired
    private Constantes constantes;

    @Autowired
    private SesionCliente sesionCliente;

    private List<Equipo> equipos;
    private List<Equipo> equiposFiltrados;
    private List<TipoEquipo> tiposEquipos;
    private List<SubtipoEquipo> subtiposEquipos;
    private List<MarcaEquipo> marcasEquipos;
    private List<ModeloEquipo> modelos;
    private List<FilterOption> colores;
    private List<FilterOption> posicionesFoto;
    private List<Empresa> empresas;
    private Equipo equipo;
    private String posicionFoto;
    private transient UploadedFile foto;
    private FotoEquipo fotoEquipo;
    private SancionRetiradaEquipo sancionRetiradaEquipo;

    //cache
    private EquipoSancionado equipoSancionado;

    public void inicio() {
        equipos = logicaEquipos.obtenerTodosParaMantenedor();
        tiposEquipos = logicaEquipos.obtenerTipos();
        subtiposEquipos = logicaEquipos.obtenerSubtipos();
        marcasEquipos = logicaEquipos.obtenerMarcas();
        modelos = logicaEquipos.obtenerModelos();
        colores = new ArrayList<FilterOption>();
        colores.add(new FilterOption(5, "Amarillo"));
        colores.add(new FilterOption(3, "Azul"));
        colores.add(new FilterOption(1, "Blanco"));
        colores.add(new FilterOption(7, "Naranjo"));
        colores.add(new FilterOption(2, "Negro"));
        colores.add(new FilterOption(4, "Rojo"));
        colores.add(new FilterOption(6, "Verde"));
        posicionesFoto = new ArrayList<FilterOption>();
        posicionesFoto.add(new FilterOption(1, "Frontal"));
        posicionesFoto.add(new FilterOption(2, "Posterior"));
        posicionesFoto.add(new FilterOption(3, "Izquierda"));
        posicionesFoto.add(new FilterOption(4, "Derecha"));
    }

    public String irListar() {
        equipos = logicaEquipos.obtenerTodos();
        tiposEquipos = logicaEquipos.obtenerTipos();
        subtiposEquipos = logicaEquipos.obtenerSubtipos();
        marcasEquipos = logicaEquipos.obtenerMarcas();
        modelos = logicaEquipos.obtenerModelos();
        return "flowListar";
    }

    public String irAgregarEquipo() {
        equipo = new Equipo();
        equipo.setFotos(new ArrayList<FotoEquipo>());
        empresas = logicaEmpresas.obtenerEmpresas();
        return "flowEditar";
    }

    public String irEditar(Equipo e) {
        equipo = e;
        equipo.setFotos(logicaEquipos.obtenerFotos(e));
        empresas = logicaEmpresas.obtenerEmpresas();
        return "flowEditar";
    }

    public String irPolizas() {
        return "flowPoliza";
    }

    public String irDocs() {
        return "flowDocs";
    }

    public String irAgregarFoto() {
        fotoEquipo = new FotoEquipo();
        return "flowNuevaFoto";
    }

    public String irBloquear(Equipo e) {
        equipo = e;
        equipoSancionado = logicaEquipos.obtenerEquipoSancionado(equipo);
        equipoSancionado = new EquipoSancionado();
        equipoSancionado.setSancionado(e);
        return "flowBloquear";
    }

    public String irDesbloquear(Equipo e) {
        equipo = e;
        sancionRetiradaEquipo = new SancionRetiradaEquipo();
        equipoSancionado = logicaEquipos.obtenerEquipoSancionado(equipo);
        if (equipoSancionado == null) {
            FacesUtil.mostrarMensajeError("Operación fallida", "El equipo no posee un bloqueo");
            return null;
        }
        sancionRetiradaEquipo.setSancionado(equipo);
        sancionRetiradaEquipo.setFechasancion(equipoSancionado.getFecha());
        sancionRetiradaEquipo.setMotivo(equipoSancionado.getMotivo());
        return "flowDesbloquear";
    }

    public void guardar() {
        logicaEquipos.guardar(equipo);
    }

    public void bloquear() {
        equipoSancionado.setFecha(new Date());
        equipoSancionado.setNivel(0); // nunca implementaron niveles
        logicaEquipos.sancionar(equipoSancionado);
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Se ha bloqueado el equipo");
    }

    public void desbloquear() {
        sancionRetiradaEquipo.setFecha(new Date());
        sancionRetiradaEquipo.setNivel(0);
        sancionRetiradaEquipo.setPerdonadopor(sesionCliente.getUsuario().getRut());
        logicaEquipos.quitarSAncion(equipoSancionado, sancionRetiradaEquipo);
        FacesUtil.mostrarMensajeInformativo("Operación Exitosa", "Se ha desbloqueado el equipo");
    }

    public boolean filtroEstado(Object valor, Object filtro, Locale idioma) {
        if (filtro == null) return true;
        equipoSancionado = logicaEquipos.obtenerEquipoSancionado((Equipo) valor);
        return (filtro.equals("bloqueados") && equipoSancionado != null) ||
                (filtro.equals("desbloqueados") && equipoSancionado == null);
    }

    public void subirFoto() {
        try {
            String path = constantes.getPathArchivos() + "Imagenes/equipos/" + equipo.getPatente();
            new File(path).mkdirs();
            path += "/" + posicionFoto + foto.getFileName().substring(foto.getFileName().lastIndexOf('.'));

            List<FotoEquipo> existentes = obtenerFotosSegunPosicion();
            for (FotoEquipo fe : existentes) {
                try {
                    Files.delete(Paths.get(constantes.getPathArchivos() + fe.getFoto()));
                } catch (NoSuchFileException nsfe) {
                    System.err.println("IMAGEN PREVIA MAL SUBIDA, NO EXISTE " + equipo.getPatente());
                }
                fotoEquipo = fe;
                break;
            }

            fotoEquipo.setEquipo(equipo);
            fotoEquipo.setEtiqueta(posicionFoto);
            fotoEquipo.setFoto(path.substring(constantes.getPathArchivos().length()));
            if (fotoEquipo.getId() == null) equipo.getFotos().add(fotoEquipo);
            logicaEquipos.guardarFoto(fotoEquipo);

            new File(path).createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bulk;
            InputStream inputStream = foto.getInputstream();
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
            fotoEquipo = new FotoEquipo();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Operación fallida", "Ha ocurrido un error interno");
            e.printStackTrace();
        }
    }

    public List<FotoEquipo> obtenerFotosSegunPosicion() {
        List<FotoEquipo> retorno = new ArrayList<FotoEquipo>();
        if (posicionFoto == null) posicionFoto = "frontal";
        for (FotoEquipo fe : equipo.getFotos()) {
            if (fe.getEtiqueta().equals(posicionFoto)) {
                retorno.add(fe);
            }
        }
        return retorno;
    }

    public boolean estaHabilitado(Equipo e) {
        equipoSancionado = logicaEquipos.obtenerEquipoSancionado(e);
        return equipoSancionado == null;
    }

    public String getRazonBloqueo() {
        return equipoSancionado.getMotivo();
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public List<Equipo> getEquiposFiltrados() {
        return equiposFiltrados;
    }

    public void setEquiposFiltrados(List<Equipo> equiposFiltrados) {
        this.equiposFiltrados = equiposFiltrados;
    }

    public List<TipoEquipo> getTiposEquipos() {
        return tiposEquipos;
    }

    public void setTiposEquipos(List<TipoEquipo> tiposEquipos) {
        this.tiposEquipos = tiposEquipos;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public List<SubtipoEquipo> getSubtiposEquipos() {
        return subtiposEquipos;
    }

    public void setSubtiposEquipos(List<SubtipoEquipo> subtiposEquipos) {
        this.subtiposEquipos = subtiposEquipos;
    }

    public List<MarcaEquipo> getMarcasEquipos() {
        return marcasEquipos;
    }

    public void setMarcasEquipos(List<MarcaEquipo> marcasEquipos) {
        this.marcasEquipos = marcasEquipos;
    }

    public List<ModeloEquipo> getModelos() {
        return modelos;
    }

    public List<FilterOption> getColores() {
        return colores;
    }

    public void setColores(List<FilterOption> colores) {
        this.colores = colores;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public void setModelos(List<ModeloEquipo> modelos) {
        this.modelos = modelos;
    }

    public List<FilterOption> getPosicionesFoto() {
        return posicionesFoto;
    }

    public void setPosicionesFoto(List<FilterOption> posicionesFoto) {
        this.posicionesFoto = posicionesFoto;
    }

    public String getPosicionFoto() {
        return posicionFoto;
    }

    public void setPosicionFoto(String posicionFoto) {
        this.posicionFoto = posicionFoto.toLowerCase();
    }

    public UploadedFile getFoto() {
        return foto;
    }

    public void setFoto(UploadedFile foto) {
        this.foto = foto;
    }

    public FotoEquipo getFotoEquipo() {
        return fotoEquipo;
    }

    public void setFotoEquipo(FotoEquipo fotoEquipo) {
        this.fotoEquipo = fotoEquipo;
    }

    public EquipoSancionado getEquipoSancionado() {
        return equipoSancionado;
    }

    public void setEquipoSancionado(EquipoSancionado equipoSancionado) {
        this.equipoSancionado = equipoSancionado;
    }

    public SancionRetiradaEquipo getSancionRetiradaEquipo() {
        return sancionRetiradaEquipo;
    }

    public void setSancionRetiradaEquipo(SancionRetiradaEquipo sancionRetiradaEquipo) {
        this.sancionRetiradaEquipo = sancionRetiradaEquipo;
    }
}