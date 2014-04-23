package workcenter.util.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.util.pojo.Mes;

/**
 * @author colivares
 */
@Component
@Scope("application")
public class Constantes implements Serializable {
    // Zona de usuarios
    // Estos funcionan inversos el nivel mínimo es el menos restrictivo
    private int nivelMinimoPermisos=0;
    private int nivelMaximoPermisos=3;
    
    // Zona de remuneraciones
    private int filtroEmpleador=1;
    private int filtroConductor=2;
    
    // Zona de personal
    private int cargoConductor=3;
    
    // Genericos
    private List<Mes> meses;
    private int cantidadFilasTablas = 15;
    private String filasPorPaginaTemplate = "10, 15, 20, 30, 40, 50, 100";
    private String paginadorTemplate = "{FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}";
    private String contextoEstatico = "/static";

    public Constantes() {
        ProxyFactory pf = new ProxyFactory(this);
        pf.addAdvice(new MethodInterceptor() {

            public Object invoke(MethodInvocation mi) throws Throwable {
                if (mi.getMethod().getName().startsWith("set"))
                    throw new UnsupportedOperationException("Constantes: Estás intentando modificar un valor constante");
                return null;
            }
        });
        meses = new ArrayList<Mes>();
        meses.add(new Mes(1, "Enero"));
        meses.add(new Mes(2, "Febrero"));
        meses.add(new Mes(3, "Marzo"));
        meses.add(new Mes(4, "Abril"));
        meses.add(new Mes(5, "Mayo"));
        meses.add(new Mes(6, "Junio"));
        meses.add(new Mes(7, "Julio"));
        meses.add(new Mes(8, "Agosto"));
        meses.add(new Mes(9, "Septiembre"));
        meses.add(new Mes(10, "Octubre"));
        meses.add(new Mes(11, "Noviembre"));
        meses.add(new Mes(12, "Diciembre"));
    }

    public int getCargoConductor() {
        return cargoConductor;
    }

    public int getNivelMinimoPermisos() {
        return nivelMinimoPermisos;
    }

    public int getNivelMaximoPermisos() {
        return nivelMaximoPermisos;
    }

    public int getFiltroEmpleador() {
        return filtroEmpleador;
    }

    public int getFiltroConductor() {
        return filtroConductor;
    }

    public List<Mes> getMeses() {
        return meses;
    }

    public int getCantidadFilasTablas() {
        return cantidadFilasTablas;
    }

    public String getFilasPorPaginaTemplate() {
        return filasPorPaginaTemplate;
    }

    public String getPaginadorTemplate() {
        return paginadorTemplate;
    }

    public String getContextoEstatico() {
        return contextoEstatico;
    }
}