package workcenter.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import workcenter.entidades.*;
import workcenter.util.components.Constantes;

/**
 * Created by colivares on 19-08-14.
 */
@Repository
public class EquipoDao extends MyDao{
    @PersistenceContext
    EntityManager em;

    @Autowired
    Constantes constantes;

    public List<Equipo> obtenerTodos() {
        return em.createNamedQuery("Equipo.findAll").getResultList();
    }

    public List<Equipo> obtenerTractos() {
        return em.createNamedQuery("Equipo.findByTipo")
                .setParameter("tipo", new TipoEquipo(constantes.getEquipoTipoTracto())).getResultList();
    }
    
    public List<Equipo> obtenerMaquinas() {
        return em.createNamedQuery("Equipo.findByTipo")
                .setParameter("tipo", new TipoEquipo(constantes.getEquipoTipoMaquina())).getResultList();
    }

    public List<Equipo> obtenerBateas() {
        return em.createNamedQuery("Equipo.findByTipo")
                .setParameter("tipo", new TipoEquipo(constantes.getEquipoTipoBatea())).getResultList();
    }

    public EquipoSancionado obtenerEquipoSancionado(Equipo e) {
        try {
            return (EquipoSancionado) em.createNamedQuery("EquipoSancionado.findByEquipo").setParameter("equipo", e).setMaxResults(1).getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<TipoEquipo> obtenerTipos() {
        return em.createNamedQuery("TipoEquipo.findAll").getResultList();
    }

    public List<SubtipoEquipo> obtenerSubtiposEquipos() {
        return em.createNamedQuery("SubtipoEquipo.findAll").getResultList();
    }

    public List<MarcaEquipo> obtenerMarcas() {
        return em.createNamedQuery("MarcaEquipo.findAll").getResultList();
    }

    public List<ModeloEquipo> obtenerModelos() {
        return em.createNamedQuery("ModeloEquipo.findAll").getResultList();
    }

    public List<FotoEquipo> obtenerFotos(Equipo e) {
        return em.createNamedQuery("FotoEquipo.findByEquipo").setParameter("equipo", e).getResultList();
    }

    public SeguroEquipo obtenerUltimoSeguro(Equipo equipo) {
        try {
            String sql = "select se.* from segurosequipos se where se.equipo = :equipo order by se.id desc limit 1";
            Query q = em.createNativeQuery(sql, SeguroEquipo.class);
            q.setParameter("equipo", equipo.getPatente());
            return (SeguroEquipo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<SeguroEquipo> obtenerSeguros(Equipo equipo) {
        return em.createNamedQuery("SeguroEquipo.findByEquipo").setParameter("equipo", equipo).getResultList();
    }

    public Equipo obtenerEquipo(Equipo e) {
        return em.find(Equipo.class, e.getPatente());
    }

    public void guardarSeguro(SeguroEquipo seguro) {
        if (seguro.getId() == null) em.persist(seguro);
        else em.merge(seguro);
    }

    public List<DocumentoEquipo> obtenerDocumentosActualizados(Equipo e) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ( ");
        sql.append("select d.* from tiposdocumentosequipos tde ");
        sql.append("inner join documentosequipo d on (tde.id=d.tipo) ");
        sql.append("inner join (select max(vencimiento) as fecha, tipo from documentosequipo where patente=:patente group by tipo) d2 ");
        sql.append("on (d.tipo=d2.tipo and (d.vencimiento=d2.fecha or d2.fecha is null)) ");
        sql.append("where d.patente=:patente ");
        sql.append("group by d.tipo order by d.id desc ");
        sql.append(") d group by d.tipo order by d.tipo ");
        return em.createNativeQuery(sql.toString(), DocumentoEquipo.class)
                .setParameter("patente", e.getPatente())
                .getResultList();
    }

    public void guardar(Equipo equipo) {
        if (equipo.getPatente() == null)
            em.persist(equipo);
        else
            em.merge(equipo);
    }

    public List<TipoDocumentoEquipo> obtenerTiposPendientes(Equipo equipo) {
        StringBuilder sql = new StringBuilder();
        sql.append("select t.* from tiposdocumentosequipos t where t.id not in ( ");
        sql.append("select distinct de.tipo from documentosequipo de where de.patente=:patente ");
        sql.append(')');
        return em.createNativeQuery(sql.toString(), TipoDocumentoEquipo.class)
                .setParameter("patente", equipo.getPatente())
                .getResultList();
    }

    public void guardarHistorialDocumento(HistorialDocumentoEquipo respaldo) {
        if (respaldo.getId() == null)
            em.persist(respaldo);
        else
            em.merge(respaldo);
    }

    public void guardarDocumento(DocumentoEquipo doc) {
        if (doc.getPatente() == null)
            em.persist(doc);
        else
            em.merge(doc);
    }

    public void guardarFoto(FotoEquipo foto) {
        if (foto.getId() == null)
            em.persist(foto);
        else
            em.merge(foto);
    }

    public Vuelta obtenerUltimaVuelta(Equipo e) {
        String sql = "select * from vueltas where tracto=:equipo and (kmfinal > 0 or kminicial > 0) order by fecha desc limit 1";
        try {
            return (Vuelta) em.createNativeQuery(sql, Vuelta.class).setParameter("equipo", e.getPatente()).getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public void guardarRendimientoDiario(RendimientoCopec rc) throws PersistenceException {
        if (rc.getId() == null)
            em.persist(rc);
        else
            em.merge(rc);
    }

    public Date obtenerFechaUltimoRendimientoDiario() {
        String sql = "select max(fecha) from rendimientos_copec";
        return (Date) em.createNativeQuery(sql).getSingleResult();
    }

    public Integer obtenerUltimoKmProveedor(Equipo e) {
        String sql = "select max(odometro) from rendimientos_copec where patente = :patente";
        return (Integer) em.createNativeQuery(sql).setParameter("patente", e.getPatente().replace(' ', '-')).getSingleResult();
    }

    public void guardar(EquipoSancionado e) {
        if (e.getId() == null)
            em.persist(e);
        else
            em.merge(e);
    }

    public void eliminar(EquipoSancionado sancion) {
        EquipoSancionado es = em.find(EquipoSancionado.class, sancion.getId());
        em.remove(es);
    }

    public void guardar(SancionRetiradaEquipo retiro) {
        if (retiro.getId() == null)
            em.persist(retiro);
        else
            em.merge(retiro);
    }

	public List<Equipo> obtenerEquipoTipo(TipoEquipo tipo) {
		return em.createNamedQuery("Equipo.findByTipo")
                .setParameter("tipo", tipo).getResultList();
	}

    public List<Equipo> obtenerMaquinasConModelo() {
        Query q = em.createNamedQuery("Equipo.findByTipo");
        q.setParameter("tipo", new TipoEquipo(constantes.getEquipoTipoMaquina()));
        
        EntityGraph<Equipo> graph = em.createEntityGraph(Equipo.class);
        graph.addAttributeNodes("modelo");
        q.setHint(ENTITY_GRAPH_OVERRIDE_HINT, graph);
        return q.getResultList();
    }
}