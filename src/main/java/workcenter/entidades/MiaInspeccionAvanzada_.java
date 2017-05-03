package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MiaInspeccionAvanzada.class)
public abstract class MiaInspeccionAvanzada_ {

	public static volatile SingularAttribute<MiaInspeccionAvanzada, Date> fecha;
	public static volatile SingularAttribute<MiaInspeccionAvanzada, Equipo> batea;
	public static volatile SingularAttribute<MiaInspeccionAvanzada, Integer> kilometraje;
	public static volatile SingularAttribute<MiaInspeccionAvanzada, Integer> piir;
	public static volatile SingularAttribute<MiaInspeccionAvanzada, Integer> id;
	public static volatile SingularAttribute<MiaInspeccionAvanzada, Personal> ejecutor;
	public static volatile SingularAttribute<MiaInspeccionAvanzada, Equipo> tracto;
	public static volatile SingularAttribute<MiaInspeccionAvanzada, Personal> conductor;
	public static volatile SingularAttribute<MiaInspeccionAvanzada, String> observacion;

}

