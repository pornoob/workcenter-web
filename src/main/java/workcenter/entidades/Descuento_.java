package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Descuento.class)
public abstract class Descuento_ {

	public static volatile SingularAttribute<Descuento, String> descripcion;
	public static volatile SingularAttribute<Descuento, Date> fecha;
	public static volatile SingularAttribute<Descuento, Dinero> motivo;
	public static volatile SingularAttribute<Descuento, Long> persona;
	public static volatile SingularAttribute<Descuento, Integer> monto;
	public static volatile SingularAttribute<Descuento, Integer> id;
	public static volatile SingularAttribute<Descuento, String> nombre;

}

