package workcenter.entidades;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BonoDescuentoPersonal.class)
public abstract class BonoDescuentoPersonal_ {

	public static volatile SingularAttribute<BonoDescuentoPersonal, Date> fechadesde;
	public static volatile SingularAttribute<BonoDescuentoPersonal, Date> fecha;
	public static volatile SingularAttribute<BonoDescuentoPersonal, BigInteger> monto;
	public static volatile SingularAttribute<BonoDescuentoPersonal, BonoDescuento> idBonodescuento;
	public static volatile SingularAttribute<BonoDescuentoPersonal, Personal> idPersonal;
	public static volatile SingularAttribute<BonoDescuentoPersonal, Date> fechahasta;
	public static volatile SingularAttribute<BonoDescuentoPersonal, Long> id;

}

