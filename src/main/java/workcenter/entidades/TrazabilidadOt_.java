package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TrazabilidadOt.class)
public abstract class TrazabilidadOt_ {

	public static volatile SingularAttribute<TrazabilidadOt, Date> fecha;
	public static volatile SingularAttribute<TrazabilidadOt, OrdenTrabajo> ot;
	public static volatile SingularAttribute<TrazabilidadOt, Integer> estadoId;
	public static volatile SingularAttribute<TrazabilidadOt, Integer> id;
	public static volatile SingularAttribute<TrazabilidadOt, Personal> ejecutor;
	public static volatile SingularAttribute<TrazabilidadOt, Long> autor;

}

