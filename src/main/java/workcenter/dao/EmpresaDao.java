package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.Empresa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

    public Empresa obtenerEmpresaConContactos(Integer id) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT DISTINCT e FROM Empresa e ")
                .append("LEFT JOIN FETCH e.contactos ce ")
                .append("LEFT JOIN FETCH ce.contacto ")
                .append("WHERE e.id = :id ");
        Query q = em.createQuery(jpql.toString(), Empresa.class);
        q.setParameter("id", id);
        try {
            return (Empresa) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(Empresa empresa) {
        em.merge(empresa);
    }

    public Empresa obtenerEmpresaConContratos(Integer id) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT DISTINCT e FROM Empresa e ")
                .append("LEFT JOIN FETCH e.contratos ce ")
                .append("LEFT JOIN FETCH ce.contactos c ")
                .append("LEFT JOIN FETCH c.contactoEmpresa ")
                .append("LEFT JOIN FETCH ce.tramos t ")
                .append("LEFT JOIN FETCH t.origen ")
                .append("LEFT JOIN FETCH t.destino ")
                .append("LEFT JOIN FETCH t.tarifas ")
                .append("LEFT JOIN FETCH t.tipoProducto ")
                .append("WHERE e.id = :id ");
        Query q = em.createQuery(jpql.toString(), Empresa.class);
        q.setParameter("id", id);
        try {
            return (Empresa) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
