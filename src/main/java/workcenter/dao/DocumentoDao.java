package workcenter.dao;

import org.springframework.stereotype.Repository;
import workcenter.entidades.AsociacionDocumento;
import workcenter.entidades.Documento;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 * @author colivares
 */
@Repository
public class DocumentoDao {

    @PersistenceContext
    private EntityManager em;

    public void guardar(Documento d) {
        if (d.getId() == null) {
            em.persist(d);
        } else {
            em.merge(d);
        }
    }

    public Documento obtenerDocumentoPorCodigo(String filtroArchivo) {
        try {
            return (Documento) em.createNamedQuery("Documento.findById").setParameter("id", Integer.valueOf(filtroArchivo)).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void guardarAsociacion(AsociacionDocumento ad) {
        if (ad.getId() == null) {
            em.persist(ad);
        } else {
            em.merge(ad);
        }
    }

    public List<Documento> obtenerDocumentos(String tabla, String valor) {
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct d.* from documentos d ");
        sql.append("inner join asociacion_documento ad on (ad.nombre_tabla=:tabla and ad.id_en_tabla=:valor and ad.id_documento=d.id) ");
        Query q = em.createNativeQuery(sql.toString(), Documento.class);
        q.setParameter("tabla", tabla);
        q.setParameter("valor", valor);
        return q.getResultList();
    }
}
