package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Stock.class)
public abstract class Stock_ {

	public static volatile SingularAttribute<Stock, Long> stockId;
	public static volatile SingularAttribute<Stock, FactProducto> producto;
	public static volatile SingularAttribute<Stock, Integer> cantidad;
	public static volatile SingularAttribute<Stock, FactDetalleFactura> detalle;

}

