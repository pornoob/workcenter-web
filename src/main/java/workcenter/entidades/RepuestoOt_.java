package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RepuestoOt.class)
public abstract class RepuestoOt_ {

	public static volatile SingularAttribute<RepuestoOt, Long> otId;
	public static volatile SingularAttribute<RepuestoOt, OrdenTrabajo> ot;
	public static volatile SingularAttribute<RepuestoOt, Long> productoId;
	public static volatile SingularAttribute<RepuestoOt, FactProducto> producto;
	public static volatile SingularAttribute<RepuestoOt, Integer> cantidad;

}

