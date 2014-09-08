package workcenter.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import workcenter.entidades.ContratoPersonal;
import workcenter.entidades.DocumentoPersonal;
import workcenter.entidades.Empresa;
import workcenter.entidades.Personal;
import workcenter.entidades.SancionRetiradaPersonal;
import workcenter.entidades.Sancionado;
import workcenter.entidades.Usuario;
import workcenter.entidades.ValorPrevisionPersonal;

/**
 * @author colivares
 */
@Repository
public class PersonalDao {

    @PersistenceContext
    private EntityManager em;

    public List<Personal> obtenerTodos() {
        Query q = em.createNamedQuery("Personal.findAll");
        return q.getResultList();
    }

    public Personal obtenerConAccesos(Integer rut) {
        Query q = em.createNamedQuery("Personal.findByRutWithAccess");
        q.setParameter("rut", rut);
        return (Personal) q.getSingleResult();
    }

    public List<Personal> obtenerTodosConUsuario() {
        Query q = em.createNamedQuery("Personal.findAllWithUser");
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
        sb.append("select distinct p.* from personal p ");
        sb.append("inner join contratospersonal cp on (p.rut=cp.rut and cp.cargo=:cargo) ");
        sb.append("order by p.apellidos asc");
        Query q = em.createNativeQuery(sb.toString(), Personal.class);
        q.setParameter("cargo", cargo);
        return q.getResultList();
    }

    public List<Personal> obtenerPorPermiso(String modulo) {
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct p.* from personal p ");
        sb.append("inner join permisos per on (p.rut=per.usuario) ");
        sb.append("inner join proyectos pro on (per.proyecto=pro.id) ");
        sb.append("where upper(pro.titulo) = upper(:modulo) ");
        Query q = em.createNativeQuery(sb.toString(), Personal.class);
        q.setParameter("modulo", modulo);
        return q.getResultList();
    }

    public Personal obtener(Integer rut) {
        return em.find(Personal.class, rut);
    }

    public Empresa obtenerEmpleador(Personal p) {
        StringBuilder sb = new StringBuilder();
        sb.append("select e.* from empresas e ");
        sb.append("inner join contratospersonal cp on (cp.empleador = e.id) ");
        sb.append("where cp.rut=:rut ");
        sb.append("order by cp.numero desc limit 1");
        Query q = em.createNativeQuery(sb.toString(), Empresa.class);
        q.setParameter("rut", p.getRut());
        try {
            return (Empresa) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<DocumentoPersonal> obtenerDocumentos(Personal personal) {
        return em.createNamedQuery("DocumentoPersonal.findByPersonal").setParameter("personal", personal).getResultList();
    }

    public List<ContratoPersonal> obtenerContratos(Personal personal) {
        return em.createNamedQuery("ContratoPersonal.findByRut").setParameter("rut", personal).getResultList();
    }

    public ValorPrevisionPersonal obtenerValorPrevisionSaludActual(ContratoPersonal cp) {
        StringBuilder sb = new StringBuilder();
        sb.append("select vpp.* from valoresprevisionpersonal vpp ");
        sb.append("inner join prevision p on (vpp.prevision = p.id and p.tipo='salud') ");
        sb.append("inner join previsionescontratos pc on (vpp.contrato= pc.contrato and vpp.prevision=pc.prevision) ");
        sb.append("where vpp.contrato=:contrato and pc.fechatermino is null ");
        sb.append("order by vpp.fechavigencia desc ");
        sb.append("limit 1 ");
        try {
            return (ValorPrevisionPersonal) em.createNativeQuery(sb.toString(), ValorPrevisionPersonal.class).setParameter("contrato", cp.getNumero()).getSingleResult();
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
        return em.createNamedQuery("SancionRetiradaPersonal.findBySancionado").setParameter("sancionado", p).getResultList();
    }

    public Sancionado obtenerSancion(Personal p) {
        try {
            return (Sancionado) em.createNamedQuery("Sancionado.findBySancionado").setParameter("sancionado", p).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<DocumentoPersonal> obtenerDocumentosActualizados(Personal p) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from (");
        sql.append("select max(dp.id) as id, dp.vencimiento, dp.numero, dp.personal, dp.tipo, dp.archivo from tiposdocpersonal tdp ");
        sql.append("inner join documentospersonal dp on (tdp.id=dp.tipo) ");
        sql.append("inner join (select max(vencimiento) as vencimiento, tipo from documentospersonal where personal=:personal group by tipo) dp2 ");
        sql.append("on (dp2.tipo=dp.tipo and (dp2.vencimiento=dp.vencimiento or dp2.vencimiento is null)) ");
        sql.append("where personal=:personal group by dp.tipo order by dp.id desc");
        sql.append(") dp group by dp.tipo order by dp.tipo");
        return em.createNativeQuery(sql.toString(), DocumentoPersonal.class)
                .setParameter("personal", p.getRut())
                .getResultList();
    }
}
