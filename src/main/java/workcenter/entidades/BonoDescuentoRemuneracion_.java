package workcenter.entidades;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BonoDescuentoRemuneracion.class)
public abstract class BonoDescuentoRemuneracion_ {

	public static volatile SingularAttribute<BonoDescuentoRemuneracion, String> descripcion;
	public static volatile SingularAttribute<BonoDescuentoRemuneracion, Boolean> bono;
	public static volatile SingularAttribute<BonoDescuentoRemuneracion, Long> idBonoDescuento;
	public static volatile SingularAttribute<BonoDescuentoRemuneracion, BigInteger> monto;
	public static volatile SingularAttribute<BonoDescuentoRemuneracion, Boolean> imponible;
	public static volatile SingularAttribute<BonoDescuentoRemuneracion, Remuneracion> idMaestroGuia;

}

