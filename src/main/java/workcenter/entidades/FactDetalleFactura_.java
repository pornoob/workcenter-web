package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FactDetalleFactura.class)
public abstract class FactDetalleFactura_ {

	public static volatile SingularAttribute<FactDetalleFactura, FactFactura> factura;
	public static volatile SingularAttribute<FactDetalleFactura, Integer> precioUnitario;
	public static volatile SingularAttribute<FactDetalleFactura, FactProducto> producto;
	public static volatile SingularAttribute<FactDetalleFactura, Integer> cantidad;
	public static volatile SingularAttribute<FactDetalleFactura, Integer> precioTotal;
	public static volatile SingularAttribute<FactDetalleFactura, Long> detalleId;

}

