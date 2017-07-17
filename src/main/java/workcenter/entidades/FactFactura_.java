package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FactFactura.class)
public abstract class FactFactura_ {

	public static volatile SingularAttribute<FactFactura, Date> fecha;
	public static volatile SingularAttribute<FactFactura, Empresa> receptor;
	public static volatile SingularAttribute<FactFactura, Long> facturaId;
	public static volatile SingularAttribute<FactFactura, Integer> numero;
	public static volatile SetAttribute<FactFactura, FactDetalleFactura> items;
	public static volatile SingularAttribute<FactFactura, Empresa> emisor;

}

