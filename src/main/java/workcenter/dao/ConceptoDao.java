package workcenter.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import workcenter.entidades.Concepto;

@Repository
public class ConceptoDao {
    @PersistenceContext
    private EntityManager em;

    public List<Concepto> obtenerTodos() {
        return em.createNamedQuery("Concepto.findAll").getResultList();
    }
}