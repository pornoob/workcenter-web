package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Existencia.class)
public abstract class Existencia_ {

	public static volatile SingularAttribute<Existencia, Integer> tipo;
	public static volatile SingularAttribute<Existencia, Integer> factura;
	public static volatile SingularAttribute<Existencia, Date> ingreso;
	public static volatile SingularAttribute<Existencia, Double> valorunitario;
	public static volatile SingularAttribute<Existencia, Integer> id;
	public static volatile SingularAttribute<Existencia, Integer> cantidad;

}

