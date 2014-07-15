package workcenter.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author colivares
 */
@Repository
public class TestQueryDao {
    @PersistenceContext
    EntityManager em;
    
    @Transactional
    public void run() {
        Session s = em.unwrap(Session.class);
        try {
            org.hibernate.Query q = s.createSQLQuery("select 1");
            q.list();
        } catch (Exception e) {
            s.reconnect(s.close());
        }
    }
}
