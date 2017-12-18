package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.Empresa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author colivares
 */
@Repository
public class EmpresaDao {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<Empresa> obtenerEmpleadores() {
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct e.* from empresas e ");
        sb.append("inner join contratospersonal cp on (e.id = cp.empleador) ");
        sb.append("where (cp.vencimiento > current_date or cp.vencimiento is null) ");

        sb.append("order by e.nombre asc");
        Query q = em.createNativeQuery(sb.toString(), Empresa.class);
        return q.getResultList();
    }

    public List<Empresa> obtenerEmpresas() {
        return em.createNamedQuery("Empresa.findAll").getResultList();
    }

    public Empresa obtenerEmpresa(Long rut) {
        try {
            return (Empresa) em.createNamedQuery("Empresa.findByRut").setParameter("rut", rut).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
