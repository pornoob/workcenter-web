package workcenter.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import workcenter.entidades.BonoDescuento;

/**
 *
 * @author Claudio Olivares
 */
@Repository
public class TestDao {
    private final String bonoStr = "Incentivo TVP";
    private final String periodo = "01-02-2016";
    private final String[][] trabajadores = {
        {"5516309" ,"0"},
{"6034858" ,"0"},
{"7064941" ,"0"},
{"7387833" ,"0"},
{"7476153" ,"0"},
{"7660739" ,"0"},
{"7819688" ,"100000"},
{"7880271" ,"0"},
{"8091435" ,"0"},
{"8244432" ,"0"},
{"8302301" ,"0"},
{"8359263" ,"0"},
{"8551905" ,"0"},
{"8580801" ,"0"},
{"8632682" ,"0"},
{"9015132" ,"0"},
{"9042208" ,"0"},
{"9149133" ,"0"},
{"9494865" ,"0"},
{"9545871" ,"0"},
{"10030178" ,"0"},
{"10080102" ,"0"},
{"10141733" ,"0"},
{"10218140" ,"80000"},
{"10250249" ,"0"},
{"10280275" ,"40000"},
{"10288116" ,"0"},
{"10336245" ,"0"},
{"10748903" ,"0"},
{"10751013" ,"0"},
{"10884025" ,"0"},
{"10913132" ,"50000"},
{"10980938" ,"0"},
{"10997822" ,"0"},
{"11277513" ,"0"},
{"11364834" ,"0"},
{"11388484" ,"0"},
{"11388578" ,"0"},
{"11398067" ,"0"},
{"11433353" ,"0"},
{"11516005" ,"0"},
{"11519342" ,"0"},
{"11601419" ,"40000"},
{"11670162" ,"0"},
{"11732451" ,"0"},
{"11733579" ,"0"},
{"11993645" ,"0"},
{"12123353" ,"0"},
{"12173994" ,"0"},
{"12206311" ,"0"},
{"12227772" ,"0"},
{"12352032" ,"0"},
{"12711203" ,"0"},
{"12818818" ,"0"},
{"12819612" ,"0"},
{"12819809" ,"0"},
{"12950992" ,"0"},
{"12952197" ,"0"},
{"12952286" ,"0"},
{"13151162" ,"0"},
{"13189457" ,"0"},
{"13209157" ,"0"},
{"13328079" ,"0"},
{"13365885" ,"0"},
{"13370935" ,"0"},
{"13519504" ,"0"},
{"13540176" ,"0"},
{"13541491" ,"0"},
{"13541599" ,"0"},
{"13718458" ,"0"},
{"13753592" ,"0"},
{"13753882" ,"0"},
{"13753968" ,"150000"},
{"13754394" ,"0"},
{"13802636" ,"0"},
{"13976842" ,"0"},
{"13984320" ,"0"},
{"13987133" ,"0"},
{"13994747" ,"0"},
{"14241403" ,"0"},
{"14253826" ,"0"},
{"14357023" ,"0"},
{"14447411" ,"0"},
{"14556934" ,"0"},
{"14613181" ,"0"},
{"14619420" ,"0"},
{"15065828" ,"0"},
{"15090719" ,"0"},
{"15096649" ,"0"},
{"15425106" ,"0"},
{"15525124" ,"0"},
{"15659551" ,"0"},
{"15706970" ,"0"},
{"15835864" ,"0"},
{"15836217" ,"0"},
{"15851357" ,"0"},
{"15999985" ,"0"},
{"16083890" ,"0"},
{"16402048" ,"0"},
{"16402084" ,"0"},
{"16402235" ,"0"},
{"16702206" ,"0"},
{"16956500" ,"100000"},
{"17628388" ,"0"},
{"18369636" ,"0"}

    };
    
    @PersistenceContext
    private EntityManager em;
    
    public void cargar() {
        BonoDescuento bonoDescuento;
        List<String> ruts = new ArrayList<>();
        List<String> montos = new ArrayList<>();
        List<Integer> idBonoRemuneracion;
        
        int filasProcesadas;
        
        for (String[] fila : trabajadores) {
            ruts.add(fila[0]);
            montos.add(fila[1]);
        }
                
        Query q = em.createNativeQuery("select * from bonosdescuentos where descripcion = :bono order by id desc", BonoDescuento.class);
        q.setParameter("bono", bonoStr);
        q.setMaxResults(1);
        
        bonoDescuento = (BonoDescuento) q.getResultList().get(0);
        
        q = em.createNativeQuery("insert into maestroGuiasBonosDescuentos (idMaestroGuia, descripcion, imponible, bono) " +
                "select idmaestro,:bono as descripcion,:imponible as imponible,:tipoBono as bono " +
                "from maestroGuias " +
                "where " +
                "fechaLiquidacion = STR_TO_DATE(:periodo, '%d-%m-%Y') " +
                "and idPersonal in :listaPersonal");
        
        q.setParameter("bono", bonoDescuento.getDescripcion());
        q.setParameter("imponible", bonoDescuento.getImponible() ? 1 : 0);
        q.setParameter("tipoBono", bonoDescuento.getIdTipoBonodescuento().getId() == 1 ? 1 : 0);
        q.setParameter("periodo", periodo);
        q.setParameter("listaPersonal", ruts);
        
        filasProcesadas = q.executeUpdate();
        System.out.println("Insertados: "+filasProcesadas);
        
        q = em.createNativeQuery("delete b from maestroGuiasBonosDescuentos b "
                + "inner join maestroGuias r on b.idMaestroGuia = r.idMaestro "
                + "where "
                + "r.idPersonal in :listaPersonal "
                + "and b.descripcion = :bono "
                + "and r.fechaLiquidacion = STR_TO_DATE(:periodo, '%d-%m-%Y') "
                + "and b.monto is not null");
        
        q.setParameter("periodo", periodo);
        q.setParameter("listaPersonal", ruts);
        q.setParameter("bono", bonoDescuento.getDescripcion());
        filasProcesadas = q.executeUpdate();
        
        System.out.println("Eliminados: "+filasProcesadas);
        
        q = em.createNativeQuery("select b.idBonoDescuento from maestroGuiasBonosDescuentos b " +
                "inner join maestroGuias r on b.idMaestroGuia = r.idMaestro " +
                "where " +
                "r.fechaLiquidacion = STR_TO_DATE(:periodo, '%d-%m-%Y') " +
                "and descripcion = :bono " +
                "and r.idPersonal in :listaPersonal " +
                "order by r.idPersonal ");
        
        q.setParameter("periodo", periodo);
        q.setParameter("listaPersonal", ruts);
        q.setParameter("bono", bonoDescuento.getDescripcion());
        
        idBonoRemuneracion = q.getResultList();
        
        for (int i = 0; i < montos.size(); i++) {
            String monto = montos.get(i);
            q = em.createNativeQuery("update maestroGuiasBonosDescuentos set monto = :monto where idBonoDescuento = :idBonoRemuneracion");
            q.setParameter("monto", Integer.valueOf(monto));
            q.setParameter("idBonoRemuneracion", idBonoRemuneracion.get(i));
            
            q.executeUpdate();
        }
    }
}
