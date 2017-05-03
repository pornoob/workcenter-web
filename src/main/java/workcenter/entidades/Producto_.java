package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Producto.class)
public abstract class Producto_ {

	public static volatile SingularAttribute<Producto, Date> fechadescarga;
	public static volatile SingularAttribute<Producto, Integer> numeroguia;
	public static volatile SingularAttribute<Producto, Double> tonsalida;
	public static volatile SingularAttribute<Producto, Double> tonentrega;
	public static volatile SingularAttribute<Producto, TramoContrato> tramo;
	public static volatile SingularAttribute<Producto, Vuelta> ordencarga;
	public static volatile SingularAttribute<Producto, Date> fechacarga;
	public static volatile SingularAttribute<Producto, Integer> id;

}

