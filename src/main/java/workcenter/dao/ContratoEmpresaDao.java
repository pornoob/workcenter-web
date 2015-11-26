package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.ContratoEmpresa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by renholders on 25-11-2015.
 */
@Repository
public class ContratoEmpresaDao {

    @PersistenceContext
    private EntityManager em;

    public List<ContratoEmpresa> obtenerContratoEmpresas() {
        return em.createNamedQuery("ContratoEmpresa.findAll").getResultList();
    }
}
