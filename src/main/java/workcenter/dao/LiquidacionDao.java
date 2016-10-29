package workcenter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import workcenter.entidades.*;
import workcenter.util.components.Constantes;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class LiquidacionDao {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private Constantes c;

    public ContratoPersonal obtenerDatosContrato(Personal p) {
        StringBuilder sb = new StringBuilder();
        sb.append("select cp.* from contratospersonal cp ");
        sb.append("where rut=:rut ");
        sb.append("ORDER BY fecha DESC ,numero DESC ");
        sb.append("LIMIT 1");
        Query q = em.createNativeQuery(sb.toString(), ContratoPersonal.class);
        q.setParameter("rut", p.getRut());
        try {
            return (ContratoPersonal) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Variable obtenerValorUtm(Integer mes, Integer anio) {
        Query q = em.createNamedQuery("Variable.findByLlaveMesAnio");
        q.setParameter("mes", mes);
        q.setParameter("anio", anio);
        q.setParameter("llave", "utm");

        try {
            return (Variable) q.getSingleResult();
        } catch (Exception e) {
            Variable v = new Variable();
            v.setValor("0");
            return v;
        }
    }

    public Variable obtenerValorUf(Integer mes, Integer anio) {
        Query q = em.createNamedQuery("Variable.findByLlaveMesAnio");
        q.setParameter("mes", mes);
        q.setParameter("anio", anio);
        q.setParameter("llave", "utm");

        try {
            return (Variable) q.getSingleResult();
        } catch (Exception e) {
            Variable v = new Variable();
            v.setValor("0");
            return v;
        }
    }

    public List<ValorImpuestoUnico> obtenerValoresVigentesImpUnico() {
        return em.createNamedQuery("ValorImpuestoUnico.findVigentes").getResultList();
    }

    public void guardarDatosLiquidacion(Remuneracion liquidacion) {
        System.err.println("Entro a actualizar");
        em.merge(liquidacion);
        System.err.println("ya actualizó");
    }

    public List<BonoDescuentoPersonal> obtenerBonosDescuentos() {
        return em.createNamedQuery("BonoDescuentoPersonal.findAll").getResultList();
    }

    public Integer obtenerAnticipoSueldo(Personal idPers, String mes,
            Integer anio) {

        Query q = em.createNamedQuery("Dinero.findByConceptoFecha");
        q.setParameter("mes", Integer.parseInt(mes));
        q.setParameter("anio", anio);
        q.setParameter("receptor", idPers);
        /**
         * se modifica el ciclo for ya que la persona puede tener mas de un
         * anticipo en el mes registrado (caso especial), //por ende se debera
         * acumular el monto para luego retornarlo*
         */
        Integer monto = 0;
        for (Dinero listaDineros : (List<Dinero>) q.getResultList()) {
            if (listaDineros.getConcepto().getId() == c.getConceptoAnticipo()) {
                monto = monto + listaDineros.getMonto();
            }
        }
        return monto;
    }

    public Integer obtenerAnticipoViatico(Personal idPers, String mes, Integer anio) {
        if (true) return 0;

        Query q = em.createNamedQuery("Dinero.findByConceptoFecha");
        q.setParameter("mes", Integer.parseInt(mes));
        q.setParameter("anio", anio);
        q.setParameter("receptor", idPers);
        /**
         * se modifica el ciclo for ya que la persona puede tener mas de un
         * anticipo en el mes registrado (caso especial), //por ende se debera
         * acumular el monto para luego retornarlo*
         */
        Integer monto = 0;
        for (Dinero listaDineros : (List<Dinero>) q.getResultList()) {
            if (listaDineros.getConcepto().getId() == c.getConceptoAnticipo()) {
                monto = monto + listaDineros.getMonto();
            }
        }
        return monto;
    }

    public List<Remuneracion> obtenerListaRemuneraciones() {
        Query q = em.createNamedQuery("Remuneracion.findAllByGenerica");
        q.setParameter("esGenerica", c.getGenericaAdministrativo());
        return q.getResultList();
    }

    public List<BonoDescuentoPersonal> obtenerBonosFaltantes(Personal p) {
        Query q = em.createNamedQuery("BonoDescuento.findFaltantesByPersonal");
        q.setParameter("personal", p);
        List<BonoDescuento> bonosDescuentos = q.getResultList();

        List<BonoDescuentoPersonal> retorno = new ArrayList<BonoDescuentoPersonal>();
        for (BonoDescuento b : bonosDescuentos) {
            BonoDescuentoPersonal bdp = new BonoDescuentoPersonal();
            bdp.setIdPersonal(p);
            bdp.setIdBonodescuento(b);
            bdp.setMonto(BigInteger.valueOf(b.getMonto()));
            retorno.add(bdp);
        }
        return retorno;
    }

    public void eliminarBonosPersonal(List<BonoDescuentoPersonal> bdp) {

        for (BonoDescuentoPersonal bonoDescuentoPersonal : bdp) {
            BonoDescuentoPersonal b = em.find(BonoDescuentoPersonal.class, bonoDescuentoPersonal.getId());
            em.remove(b);
        }
    }

    public Remuneracion obtenerLiquidacion(Long id) {
        return em.find(Remuneracion.class, id);
    }

    public Remuneracion obtenerIngresoPrevio(Remuneracion liquidacion) {
        try {
            StringBuilder jql = new StringBuilder();
            jql.append("SELECT m FROM Remuneracion m WHERE m.fechaLiquidacion = :fecha and m.idPersonal = :personal ORDER BY m.idMaestro DESC");
            Query q = em.createQuery(jql.toString());
            
            q.setParameter("fecha", liquidacion.getFechaLiquidacion());
            q.setParameter("personal", liquidacion.getIdPersonal());
            
            return (Remuneracion) q.setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Remuneracion> obtRemuDesdeMesAnterior() {
        StringBuilder jql = new StringBuilder();
        jql.append("SELECT r FROM Remuneracion r WHERE r.fechaLiquidacion >= :fecha ")
                .append("AND r.esGenerica = :generica");
        Query q = em.createQuery(jql.toString(), Remuneracion.class);
        q.setParameter("generica", c.getGenericaAdministrativo());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01");
        String mesActual = sdf.format(new Date());
        sdf.applyPattern("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(mesActual));
            c.add(Calendar.MONTH, -1);
            q.setParameter("fecha", c.getTime());

            return q.getResultList();
        } catch (ParseException e) {
            return new ArrayList<>();
        }
    }
}
