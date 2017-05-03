package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Factura.class)
public abstract class Factura_ {

	public static volatile SingularAttribute<Factura, Integer> neto;
	public static volatile SingularAttribute<Factura, Integer> numerofactura;
	public static volatile SingularAttribute<Factura, Date> mescontable;
	public static volatile SingularAttribute<Factura, Integer> impespecifico;
	public static volatile SingularAttribute<Factura, Date> fecha;
	public static volatile SingularAttribute<Factura, Integer> receptor;
	public static volatile SingularAttribute<Factura, Integer> otrosimp;
	public static volatile SingularAttribute<Factura, Integer> iva;
	public static volatile SingularAttribute<Factura, Double> litros;
	public static volatile SingularAttribute<Factura, Integer> bruto;
	public static volatile SingularAttribute<Factura, Integer> id;
	public static volatile SingularAttribute<Factura, Integer> clasificacion;
	public static volatile SingularAttribute<Factura, Integer> emisor;

}

