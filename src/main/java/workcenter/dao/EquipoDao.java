package workcenter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import workcenter.entidades.*;
import workcenter.util.components.Constantes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by colivares on 19-08-14.
 */
@Repository
public class EquipoDao {
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

    public List<Equipo> obtenerBateas() {
        return em.createNamedQuery("Equipo.findByTipo")
                .setParameter("tipo", new TipoEquipo(constantes.getEquipoTipoBatea())).getResultList();
    }

    public EquipoSancionado obtenerEquipoSancionado(Equipo e) {
        try {
            return (EquipoSancionado) em.createNamedQuery("EquipoSancionado.findByEquipo").setParameter("equipo", e).getSingleResult();
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
        sql.append("select d.* from tiposdocumentosequipos tde ");
        sql.append("inner join documentosequipo d on (tde.id=d.tipo) ");
        sql.append("inner join (select max(vencimiento) as fecha, tipo from documentosequipo where patente=:patente group by tipo) d2 ");
        sql.append("on (d.tipo=d2.tipo and d.vencimiento=d2.fecha) ");
        sql.append("where d.patente=:patente ");
        return em.createNativeQuery(sql.toString(), DocumentoEquipo.class)
                .setParameter("patente", e.getPatente())
                .getResultList();
    }
}