package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.Concepto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ConceptoDao {
    @PersistenceContext
    private EntityManager em;

    public List<Concepto> obtenerTodos() {
        return em.createNamedQuery("Concepto.findAll").getResultList();
    }
}