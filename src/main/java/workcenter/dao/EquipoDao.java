package workcenter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import workcenter.entidades.*;
import workcenter.util.components.Constantes;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
        return em.createNamedQuery("Equipo.findAll", Equipo.class).getResultList();
    }
    
    public List<Equipo> obtenerTodosParaMantenedor() {
        Query q = em.createNamedQuery("Equipo.findAll");
        EntityGraph<Equipo> graph = em.createEntityGraph(Equipo.class);
        
        graph.addAttributeNodes(
                "tipo",
                "subtipo",
                "duenio",
                "marca",
                "modelo"
        );
        
        q.setHint(ENTITY_GRAPH_OVERRIDE_HINT, graph);
        return q.getResultList();
    }

    public List<Equipo> obtenerTractos() {
        return em.createNamedQuery("Equipo.findByTipo", Equipo.class)
                .setParameter("tipo", new TipoEquipo(constantes.getEquipoTipoTracto())).getResultList();
    }
    
    public List<Equipo> obtenerMaquinas() {
        return em.createNamedQuery("Equipo.findByTipo", Equipo.class)
                .setParameter("tipo", new TipoEquipo(constantes.getEquipoTipoMaquina())).getResultList();
    }

    public List<Equipo> obtenerBateas() {
        return em.createNamedQuery("Equipo.findByTipo", Equipo.class)
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
        return em.createNamedQuery("TipoEquipo.findAll", TipoEquipo.class).getResultList();
    }

    public List<SubtipoEquipo> obtenerSubtiposEquipos() {
        return em.createNamedQuery("SubtipoEquipo.findAll", SubtipoEquipo.class).getResultList();
    }

    public List<MarcaEquipo> obtenerMarcas() {
        return em.createNamedQuery("MarcaEquipo.findAll", MarcaEquipo.class).getResultList();
    }

    public List<ModeloEquipo> obtenerModelos() {
        return em.createNamedQuery("ModeloEquipo.findAll", ModeloEquipo.class).getResultList();
    }

    public List<FotoEquipo> obtenerFotos(Equipo e) {
        return em.createNamedQuery("FotoEquipo.findByEquipo", FotoEquipo.class).setParameter("equipo", e).getResultList();
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
        return em.createNamedQuery("SeguroEquipo.findByEquipo", SeguroEquipo.class).setParameter("equipo", equipo).getResultList();
    }

    public Equipo obtenerEquipo(Equipo e) {
        return em.find(Equipo.class, e.getPatente());
    }

    public void guardarSeguro(SeguroEquipo seguro) {
        if (seguro.getId() == null) em.persist(seguro);
        else em.merge(seguro);
    }

    public List<DocumentoEquipo> obtenerDocumentosActualizados(Equipo e) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT de FROM DocumentoEquipo de INNER JOIN FETCH de.tipo ")
                .append("WHERE de.patente = :patente AND EXISTS (")
                .append("    SELECT MAX(deMax.vencimiento), deMax.tipo FROM DocumentoEquipo deMax ")
                .append("    WHERE deMax.tipo = de.tipo AND deMax.patente = de.patente ")
                .append("    GROUP BY de.tipo ")
                .append("    HAVING (de.vencimiento = MAX(deMax.vencimiento) OR MAX(deMax.vencimiento) IS NULL)")
                .append(") ORDER BY de.tipo DESC ");
        return em.createQuery(jpql.toString(), DocumentoEquipo.class)
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
        graph.addAttributeNodes(Equipo_.modelo);
        q.setHint(ENTITY_GRAPH_OVERRIDE_HINT, graph);
        return q.getResultList();
    }

    public List<Equipo> obtenerTractosConMantenimientos() {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT DISTINCT e FROM Equipo e ")
                .append("LEFT JOIN FETCH e.tipo ")
                .append("LEFT JOIN FETCH e.mantenimientos m ")
                .append("LEFT JOIN FETCH m.tipo ")
                .append("WHERE e.tipo = :tipo");

        Query q = em.createQuery(jpql.toString(), Equipo.class);

        q.setParameter("tipo", new TipoEquipo(constantes.getEquipoTipoTracto()));
        return q.getResultList();
    }

    public List<Equipo> obtenerTractosConMantenimientos(Integer mes, Integer anio) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT DISTINCT e FROM Equipo e ")
                .append("INNER JOIN FETCH e.tipo te ")
                .append("LEFT JOIN FETCH e.mantenimientos m ")
                .append("LEFT JOIN FETCH m.tipo ")
                .append("WHERE e.tipo = :tipo AND YEAR(m.fecha) = :anio AND MONTH(m.fecha) = :mes ");
        Query q = em.createQuery(jpql.toString(), Equipo.class);
        q.setParameter("anio", anio);
        q.setParameter("mes", mes);
        q.setParameter("tipo", new TipoEquipo(constantes.getEquipoTipoTracto()));

        return q.getResultList();
    }

    public List<Vuelta> obtenerUltimasVueltasTracto() {
        StringBuilder sql = new StringBuilder("SELECT v.* FROM vueltas v, (")
                .append("SELECT GREATEST(max(v2.kminicial), max(v2.kmfinal)) AS kms, v2.tracto ")
                .append("FROM vueltas v2 ")
                .append("GROUP BY v2.tracto ")
                .append("HAVING GREATEST(max(v2.kminicial), max(v2.kmfinal)) > 0")
                .append(") mv ")
                .append("WHERE v.tracto = mv.tracto AND GREATEST(v.kmInicial, v.kmFinal) = mv.kms");
        Query q = em.createNativeQuery(sql.toString(), Vuelta.class);
        List<Vuelta> vueltasTmp = q.getResultList();

        q = em.createQuery("SELECT v FROM Vuelta v JOIN FETCH v.tracto WHERE v IN :vueltas");
        q.setParameter("vueltas", vueltasTmp);
        return q.getResultList();
    }

    public List<RendimientoCopec> obtenerUltimosRendimientosCopec() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT r.* FROM rendimientos_copec r, ")
                .append("( SELECT max(odometro) AS odometro, max(fecha) AS fecha, patente ")
                .append("FROM rendimientos_copec WHERE odometro > 0 ")
                .append("GROUP BY patente) rm ")
                .append("WHERE r.patente = rm.patente AND r.odometro = rm.odometro AND r.fecha = rm.fecha ");
        Query q = em.createNativeQuery(sql.toString(), RendimientoCopec.class);

        return q.getResultList();
    }
}