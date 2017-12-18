package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.PrestamoCancelado;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by renholders on 07-11-2015.
 */
@Repository
public class PrestamoCanceladoDao {
    @PersistenceContext
    EntityManager em;

    public Boolean guardarPrestamoCancelado(PrestamoCancelado pCancelado){

        if (pCancelado.getId() == null){
            try {
                em.persist(pCancelado);
                return true;
            }catch (Exception ex){
                ex.printStackTrace();
                return false;
            }
        }else{
            try {
                em.merge(pCancelado);
                return true;
            }catch (Exception ex){
                ex.printStackTrace();
                return false;
            }
        }
    }
}

