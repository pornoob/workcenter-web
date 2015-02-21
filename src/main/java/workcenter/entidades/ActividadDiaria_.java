package workcenter.entidades;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

/**
 * Created by claudio on 09-01-15.
 */
@StaticMetamodel(ActividadDiaria.class)
public class ActividadDiaria_ {
    public static volatile SingularAttribute<ActividadDiaria, Integer> id;
    public static volatile SingularAttribute<ActividadDiaria, Servicio> idServicio;
    public static volatile SingularAttribute<ActividadDiaria, MpaContrato> contrato;
    public static volatile SingularAttribute<ActividadDiaria, Integer> idUsuario;
    public static volatile SingularAttribute<ActividadDiaria, Integer> hora;
    public static volatile SingularAttribute<ActividadDiaria, TipoActividadDiaria> idTipoActividad;
    public static volatile SingularAttribute<ActividadDiaria, Date> fecha;
    public static volatile SingularAttribute<ActividadDiaria, String> detalle;
    public static volatile SingularAttribute<ActividadDiaria, Personal> persona;
}
