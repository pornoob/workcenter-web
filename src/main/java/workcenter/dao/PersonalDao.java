package workcenter.dao;

import org.primefaces.model.SortMeta;
import org.springframework.stereotype.Repository;
import workcenter.entidades.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author colivares
 */
@Repository
public class PersonalDao extends MyDao {

    @PersistenceContext
    private EntityManager em;

    public List<Personal> obtenerTodos() {
        Query q = em.createNamedQuery("Personal.findAll" );
        return q.getResultList();
    }

    public Personal obtenerConAccesos(Long rut) {
        Query q = em.createNamedQuery("Personal.findByRutWithAccess" );
        q.setParameter("rut", rut);
        return (Personal) q.getSingleResult();
    }

    public List<Personal> obtenerTodosConUsuario() {
        Query q = em.createNamedQuery("Personal.findAllWithUser" );
        return q.getResultList();
    }

    public void actualizarUsuario(Usuario usuario) {
        em.merge(usuario);
    }

    public void crearUsuario(Usuario usuario) {
        em.persist(usuario);
    }

    public List<Personal> obtenerSegunCargo(Integer cargo) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        sb.append("    p.* ");
        sb.append("from personal p ");
        sb.append("inner join ( ");
        sb.append("    select max(fecha) as fecha, rut from contratospersonal cpi ");
        sb.append("    where cpi.vencimiento is null or cpi.vencimiento >= CURRENT_DATE ");
        sb.append("    GROUP BY rut ");
        sb.append(") maxContrato on maxContrato.rut = p.rut ");
        sb.append("inner join contratospersonal cp on (cp.rut = p.rut and cp.fecha = maxContrato.fecha) and cp.cargo=:cargo ");
        sb.append("order by p.nombres ASC, p.apellidos ASC ");

        Query q = em.createNativeQuery(sb.toString(), Personal.class);
        q.setParameter("cargo", cargo);
        return q.getResultList();
    }

    public List<Personal> obtenerSegunCargoServicio(Cargo cargo, Servicio servicio) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        sb.append("    p.* ");
        sb.append("from personal p ");
        sb.append("inner join ( ");
        sb.append("    select max(fecha) as fecha, rut from contratospersonal cpi ");
        sb.append("    where cpi.vencimiento is null or cpi.vencimiento >= CURRENT_DATE ");
        sb.append("    GROUP BY rut ");
        sb.append(") maxContrato on maxContrato.rut = p.rut ");
        sb.append("inner join contratospersonal cp on (cp.rut = p.rut and cp.fecha = maxContrato.fecha) and cp.cargo=:cargo ");
        if (servicio != null){
        sb.append("inner join usuario_servicio_ruta us on (us.rut = p.rut and us.id_servicio = :servicio) ");
        }
        Query q = em.createNativeQuery(sb.toString(), Personal.class);
        q.setParameter("cargo", cargo.getId());
        if (servicio != null){
        q.setParameter("servicio", servicio.getId());
        }
        return q.getResultList();
    }

    public List<Personal> obtenerPorPermiso(String modulo) {
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct p.* from personal p " );
        sb.append("inner join permisos per on (p.rut=per.usuario) " );
        sb.append("inner join proyectos pro on (per.proyecto=pro.id) " );
        sb.append("where upper(pro.titulo) = upper(:modulo) " );
        Query q = em.createNativeQuery(sb.toString(), Personal.class);
        q.setParameter("modulo", modulo);
        return q.getResultList();
    }

    public Personal obtener(Long rut) {
        return em.find(Personal.class, rut);
    }

    public List<Personal> obtener(List<Long> rut) {
        Query q = em.createNamedQuery("Personal.findByRuts", Personal.class);
        q.setParameter("ruts", rut);
        return q.getResultList();
    }

    public Empresa obtenerEmpleador(Personal p) {
        StringBuilder sb = new StringBuilder();
        sb.append("select e.* from empresas e " );
        sb.append("inner join contratospersonal cp on (cp.empleador = e.id) " );
        sb.append("where cp.rut=:rut " );
        sb.append("order by cp.numero desc limit 1" );
        Query q = em.createNativeQuery(sb.toString(), Empresa.class);
        q.setParameter("rut", p.getRut());
        try {
            return (Empresa) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<DocumentoPersonal> obtenerDocumentos(Personal personal) {
        return em.createNamedQuery("DocumentoPersonal.findByPersonal" ).setParameter("personal", personal).getResultList();
    }

    public List<DocumentoPersonal> obtenerDocumentos(){
    	return em.createNamedQuery("DocumentoPersonal.findAll").getResultList();
    }

    public List<ContratoPersonal> obtenerContratos(Personal personal) {
        Query q = em.createQuery("SELECT cp FROM ContratoPersonal cp INNER JOIN FETCH cp.cargo INNER JOIN FETCH cp.empleador WHERE cp.rut = :personal", ContratoPersonal.class);
        q.setParameter("personal", personal);
        return q.getResultList();
    }

    public ValorPrevisionPersonal obtenerValorPrevisionAfpActual(ContratoPersonal cp) {
        StringBuilder sb = new StringBuilder();
        sb.append("select vpp from ValorPrevisionPersonal vpp ")
                .append("inner join fetch vpp.unidad u ")
                .append("inner join fetch vpp.prevision p ")
                .append("inner join fetch vpp.contrato c ")
                .append("inner join fetch c.previsiones pc ")
                .append("where c = :contrato and pc.fechatermino is null and p.tipo = 'afp' ")
                .append("order by vpp.fechaVigencia desc ");

        try {
            Query q = em.createQuery(sb.toString(), ValorPrevisionPersonal.class);
            q.setParameter("contrato", cp);
            q.setMaxResults(1);
            return (ValorPrevisionPersonal) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    public ValorPrevisionPersonal obtenerValorPrevisionSaludActual(ContratoPersonal cp) {
        StringBuilder sb = new StringBuilder();
        sb.append("select vpp from ValorPrevisionPersonal vpp ")
                .append("inner join fetch vpp.unidad u ")
                .append("inner join fetch vpp.prevision p ")
                .append("inner join fetch vpp.contrato c ")
                .append("inner join fetch c.previsiones pc ")
                .append("where c = :contrato and pc.fechatermino is null and p.tipo = 'salud' ")
                .append("order by vpp.fechaVigencia desc ");
        try {
            Query q = em.createQuery(sb.toString(), ValorPrevisionPersonal.class);
            q.setParameter("contrato", cp);
            q.setMaxResults(1);
            return (ValorPrevisionPersonal) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void guardar(Personal personal) {
        if (personal.getRut() == null) {
            em.persist(personal);
        } else {
            em.merge(personal);
        }
    }

    public List<SancionRetiradaPersonal> obtenerSancionesRetiradas(Personal p) {
        return em.createNamedQuery("SancionRetiradaPersonal.findBySancionado" ).setParameter("sancionado", p).getResultList();
    }

    public Sancionado obtenerSancion(Personal p) {
        try {
            return (Sancionado) em.createNamedQuery("Sancionado.findBySancionado" ).setParameter("sancionado", p).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void guardarDocumento(DocumentoPersonal doc) {
        if (doc.getId() == null)
            em.persist(doc);
        else
            em.merge(doc);
    }

    public void guardarHistorialDocumento(HistorialDocumentosPersonal respaldo) {
        if (respaldo.getId() == null)
            em.persist(respaldo);
        else
            em.merge(respaldo);
    }

    public List<TipoDocPersonal> obtenerTiposDocPorCargo(Personal p) {
        StringBuilder sb = new StringBuilder();
        sb.append("select dp.* from tiposdocpersonal dp " );
        sb.append("inner join tipodocporcargo dpc on (dp.id = dpc.documento) " );
        sb.append("inner join ( " );
        sb.append("select cp.cargo from contratospersonal cp where cp.rut=:rut order by cp.fecha desc, cp.numero desc limit 1 " );
        sb.append(") cp on (dpc.cargo=cp.cargo) " );

        return em.createNativeQuery(sb.toString(), TipoDocPersonal.class)
                .setParameter("rut", p.getRut())
                .getResultList();
    }

    public List<TipoDocPersonal> obtenerTiposDocPersonal() {
        StringBuilder sb = new StringBuilder();
        sb.append("select dp.* from tiposdocpersonal dp " );

        return em.createNativeQuery(sb.toString(), TipoDocPersonal.class).getResultList();
    }

    public void guardarContrato(ContratoPersonal contrato) {
        if (contrato.getNumero() == null)
            em.persist(contrato);
        else
            em.merge(contrato);
    }

	public Personal obtenerPersonal(Personal p) {
		Query q = em.createNamedQuery("Personal.findByRut" );
        q.setParameter("rut", p.getRut());
        return (Personal) q.getSingleResult();
	}

    public Personal obtenerConDatosLiquidacion(Personal p) {
        Query q = em.createNamedQuery("Personal.findByRutWithLiquidacion" );
        q.setParameter("personal", p);
        return (Personal) q.getSingleResult();
    }

    public ContratoPersonal obtenerContratoActual(Personal personal) {
        Query q = em.createNamedQuery("Personal.findContratoActual");
        q.setParameter("personal", personal);
        q.setMaxResults(1);
        try {
            return (ContratoPersonal) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public int obtenerConteoPersonal() {
        Query q = em.createQuery("select count(p) from Personal p");
        return ((Long) q.getSingleResult()).intValue();
    }

    public int obtenerConteoLazy(Map<String, Object> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Personal> personal = cq.from(Personal.class);

        for (Map.Entry<String, Object> entry : filters.entrySet()) {

            if ("rut".equals(entry.getKey()))
                cq.where(cb.like(personal.get(Personal_.rut).as(String.class), entry.getValue().toString()+"%"));
            else if ("nombres".equals(entry.getKey()))
                cq.where(cb.like(personal.get(Personal_.nombres).as(String.class), "%"+entry.getValue().toString()+"%"));
            else if ("apellidos".equals(entry.getKey()))
                cq.where(cb.like(personal.get(Personal_.apellidos).as(String.class), "%"+entry.getValue().toString()+"%"));
            else if ("celular".equals(entry.getKey()))
                cq.where(cb.like(personal.get(Personal_.celular).as(String.class), "%"+entry.getValue().toString()+"%"));
        }

        cq.select(cb.count(personal));
        TypedQuery tq = em.createQuery(cq);

        return ((Long)tq.getSingleResult()).intValue();
    }

    public List<Personal> obtenerTodosLazy(int first, int end, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Personal> cq = cb.createQuery(Personal.class);
        Root<Personal> personal = cq.from(Personal.class);

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            System.err.println("FILTRO " + entry.getKey());

            if ("rut".equals(entry.getKey()))
                cq.where(cb.like(personal.get(Personal_.rut).as(String.class), entry.getValue().toString()+"%"));
            else if ("nombres".equals(entry.getKey()))
                cq.where(cb.like(personal.get(Personal_.nombres).as(String.class), "%"+entry.getValue().toString()+"%"));
            else if ("apellidos".equals(entry.getKey()))
                cq.where(cb.like(personal.get(Personal_.apellidos).as(String.class), "%"+entry.getValue().toString()+"%"));
            else if ("celular".equals(entry.getKey()))
                cq.where(cb.like(personal.get(Personal_.celular).as(String.class), "%"+entry.getValue().toString()+"%"));
        }

        cq.select(personal);
        TypedQuery tq = em.createQuery(cq);


        tq.setMaxResults(end - first);
        tq.setFirstResult(first);

        return tq.getResultList();
    }

    public List<ValorPrevisionPersonal> obtenerValoresPrevisionContrato(ContratoPersonal contrato) {
        StringBuilder jql = new StringBuilder("select v from ValorPrevisionPersonal v ")
                .append("where v.contrato = :contrato");
        Query q = em.createQuery(jql.toString());
        q.setParameter("contrato", contrato);
        return q.getResultList();
    }

    public List<Personal> findAllWithService() {
        Query q = em.createNamedQuery("Personal.findAll", Personal.class);
        EntityGraph<Personal> personalGraph = em.createEntityGraph(Personal.class);
        personalGraph.addAttributeNodes("servicios");
        personalGraph.addAttributeNodes("lstCargasFamiliares");
        personalGraph.addAttributeNodes("sancion");
        personalGraph.addAttributeNodes("contratos");

        Subgraph<Object> contratoGraph = personalGraph.addSubgraph("contratos");
        contratoGraph.addAttributeNodes("empleador");

        q.setHint(ENTITY_GRAPH_OVERRIDE_HINT, personalGraph);
        return q.getResultList();
    }

    public List<Personal> obtenerConductoresConSanciones() {
        Date currentDate = Calendar.getInstance().getTime();

        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT c FROM Personal c LEFT JOIN FETCH c.sancion ")
                .append("WHERE EXISTS (")
                .append("    SELECT MAX(cp.fecha), cp.rut FROM ContratoPersonal cp ")
                .append("    WHERE (cp.vencimiento is null or cp.vencimiento >= :currentDate) AND ")
                .append("    cp.rut = c ")
                .append("    GROUP BY cp.rut ")
                .append(")");

        Query q = em.createQuery(jpql.toString(), Personal.class);
        q.setParameter("currentDate", currentDate);
        return q.getResultList();
    }

    public void eliminarDocPersonal(DocumentoPersonal doc) {
        DocumentoPersonal attached = em.find(DocumentoPersonal.class, doc.getId());
        em.remove(attached);
    }
}
