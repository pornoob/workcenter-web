package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ActividadDiaria.class)
public abstract class ActividadDiaria_ {

	public static volatile SingularAttribute<ActividadDiaria, Date> fecha;
	public static volatile SingularAttribute<ActividadDiaria, Personal> persona;
	public static volatile SingularAttribute<ActividadDiaria, Integer> hora;
	public static volatile SingularAttribute<ActividadDiaria, Integer> idUsuario;
	public static volatile SingularAttribute<ActividadDiaria, TipoActividadDiaria> idTipoActividad;
	public static volatile SingularAttribute<ActividadDiaria, MpaContrato> contrato;
	public static volatile SingularAttribute<ActividadDiaria, Servicio> idServicio;
	public static volatile SingularAttribute<ActividadDiaria, Integer> id;
	public static volatile SingularAttribute<ActividadDiaria, String> detalle;

}

