package workcenter.dao;

        import org.springframework.stereotype.Repository;
        import workcenter.entidades.Vuelta;

        import javax.persistence.EntityManager;
        import javax.persistence.PersistenceContext;

/**
 * Created by renholders on 23-11-2015.
 */
@Repository
public class MaestroDeGuiasDAO {

    @PersistenceContext
    private EntityManager em;

    public Vuelta obtenerOrdendeCarga(Integer ordenConsulta){
        return (Vuelta)em.createNamedQuery("Vuelta.findByOrdenDeCarga")
                .setParameter("ordenDeCarga", ordenConsulta)
                .getSingleResult();
    }
}
