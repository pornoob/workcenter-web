package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Mensaje.class)
public abstract class Mensaje_ {

	public static volatile SingularAttribute<Mensaje, Date> fecha;
	public static volatile SingularAttribute<Mensaje, Integer> receptor;
	public static volatile SingularAttribute<Mensaje, Integer> vinculo;
	public static volatile SingularAttribute<Mensaje, String> tabla;
	public static volatile SingularAttribute<Mensaje, Integer> id;
	public static volatile SingularAttribute<Mensaje, String> detalle;
	public static volatile SingularAttribute<Mensaje, Integer> emisor;

}

