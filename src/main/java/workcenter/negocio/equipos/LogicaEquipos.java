package workcenter.negocio.equipos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.EquipoDao;
import workcenter.entidades.*;

import java.util.List;

/**
 * Created by colivares on 19-08-14.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaEquipos {
    @Autowired
    EquipoDao equipoDao;

    @Transactional(readOnly = true)
    public List<Equipo> obtenerTodos() {
        return equipoDao.obtenerTodos();
    }

    @Transactional(readOnly = true)
    public List<Equipo> obtenerTractos() {
        return equipoDao.obtenerTractos();
    }

    @Transactional(readOnly = true)
    public List<Equipo> obtenerBateas() {
        return equipoDao.obtenerBateas();
    }

    @Transactional(readOnly = true)
    public EquipoSancionado obtenerEquipoSancionado(Equipo e) {
        return equipoDao.obtenerEquipoSancionado(e);
    }

    @Transactional(readOnly = true)
    public List<TipoEquipo> obtenerTipos() {
        return equipoDao.obtenerTipos();
    }

    @Transactional(readOnly = true)
    public List<SubtipoEquipo> obtenerSubtipos() {
        return equipoDao.obtenerSubtiposEquipos();
    }

    @Transactional(readOnly = true)
    public List<MarcaEquipo> obtenerMarcas() {
        return equipoDao.obtenerMarcas();
    }

    @Transactional(readOnly = true)
    public List<ModeloEquipo> obtenerModelos() {
        return equipoDao.obtenerModelos();
    }

    @Transactional(readOnly = true)
    public List<FotoEquipo> obtenerFotos(Equipo e) {
        return equipoDao.obtenerFotos(e);
    }

    @Transactional(readOnly = true)
    public SeguroEquipo obtenerUltimoSeguro(Equipo equipo) {
        return equipoDao.obtenerUltimoSeguro(equipo);
    }

    @Transactional(readOnly = true)
    public List<SeguroEquipo> obtenerSeguros(Equipo equipo) {
        return equipoDao.obtenerSeguros(equipo);
    }

    @Transactional(readOnly = true)
    public Equipo obtenerEquipo(Equipo e) {
        return equipoDao.obtenerEquipo(e);
    }

    @Transactional(readOnly = false)
    public void guardarSeguro(SeguroEquipo seguro) {
        equipoDao.guardarSeguro(seguro);
    }

    @Transactional(readOnly = true)
    public List<DocumentoEquipo> obtenerDocumentosActualizados(Equipo e) {
        return equipoDao.obtenerDocumentosActualizados(e);
    }

    @Transactional(readOnly = false)
    public void guardar(Equipo equipo) {
        equipoDao.guardar(equipo);
    }

    @Transactional(readOnly = true)
    public List<TipoDocumentoEquipo> obtenerTiposPendientes(Equipo equipo) {
        return equipoDao.obtenerTiposPendientes(equipo);
    }

    @Transactional(readOnly = false)
    public void guardarHistorialDocumento(HistorialDocumentoEquipo respaldo) {
        equipoDao.guardarHistorialDocumento(respaldo);
    }

    @Transactional(readOnly = false)
    public void guardarDocumento(DocumentoEquipo doc) {
        equipoDao.guardarDocumento(doc);
    }

    @Transactional(readOnly = false)
    public void guardarFoto(FotoEquipo foto) {
        equipoDao.guardarFoto(foto);
    }

    @Transactional(readOnly = true)
    public Vuelta obtenerUltimaVuelta(Equipo e) {
        return equipoDao.obtenerUltimaVuelta(e);
    }
}
