package workcenter.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import workcenter.entidades.Empresa;

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

        // empezamos el hardcodeo para traernos las excepciones
        sb.append("or e.id=128 "); // luis gabriel silva peña
        sb.append("or e.nombre like '%banco%' "); // bancos

        sb.append("order by e.nombre asc");
        Query q = em.createNativeQuery(sb.toString(), Empresa.class);
        return q.getResultList();
    }
}
