package workcenter.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import workcenter.dao.TestQueryDao;

/**
 *
 * @author colivares
 */
@Component
public class KeepConnection {
    @Autowired
    TestQueryDao testQueryDao;
    
    @Scheduled(fixedRate = 30000)
    public void run() {
        System.out.println("TEST QUERY SELECT 1");
        testQueryDao.run();
    }
}
