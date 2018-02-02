package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.ContratoEmpresa;
import workcenter.entidades.ContratoEmpresa_;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by renholders on 25-11-2015.
 */
@Repository
public class ContratoEmpresaDao extends MyDao {

    @PersistenceContext
    private EntityManager em;

    public List<ContratoEmpresa> obtenerContratoEmpresas() {
        return em.createNamedQuery("ContratoEmpresa.findAll", ContratoEmpresa.class).getResultList();
    }

    public List<ContratoEmpresa> obtenerContratoEmpresasConEmpresas() {
        Query q = em.createNamedQuery("ContratoEmpresa.findAll", ContratoEmpresa.class);
        EntityGraph<ContratoEmpresa> graph = em.createEntityGraph(ContratoEmpresa.class);
        graph.addAttributeNodes(ContratoEmpresa_.empresa);

        q.setHint(ENTITY_GRAPH_OVERRIDE_HINT, graph);
        return q.getResultList();
    }
}
