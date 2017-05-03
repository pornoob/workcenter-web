package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MarActividad.class)
public abstract class MarActividad_ {

	public static volatile SingularAttribute<MarActividad, String> descripcion;
	public static volatile SingularAttribute<MarActividad, String> horaFin;
	public static volatile SingularAttribute<MarActividad, Date> fecha;
	public static volatile SingularAttribute<MarActividad, Personal> encargado;
	public static volatile SingularAttribute<MarActividad, Integer> id;
	public static volatile SingularAttribute<MarActividad, String> horaInicio;
	public static volatile SingularAttribute<MarActividad, MarTipoActividad> tipoActividad;
	public static volatile ListAttribute<MarActividad, MarParticipantesAct> participantes;

}

