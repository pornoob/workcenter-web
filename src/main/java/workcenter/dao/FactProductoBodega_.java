package workcenter.dao;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import workcenter.entidades.FactProducto;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FactProductoBodega.class)
public abstract class FactProductoBodega_ {

	public static volatile SingularAttribute<FactProductoBodega, String> proveedores;
	public static volatile SingularAttribute<FactProductoBodega, Long> precioUnitario;
	public static volatile SingularAttribute<FactProductoBodega, FactProducto> producto;
	public static volatile SingularAttribute<FactProductoBodega, Integer> cantidad;

}

