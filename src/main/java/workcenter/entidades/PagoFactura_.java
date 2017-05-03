package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PagoFactura.class)
public abstract class PagoFactura_ {

	public static volatile SingularAttribute<PagoFactura, Date> fechapago;
	public static volatile SingularAttribute<PagoFactura, Integer> factura;
	public static volatile SingularAttribute<PagoFactura, Integer> monto;
	public static volatile SingularAttribute<PagoFactura, Boolean> pagado;
	public static volatile SingularAttribute<PagoFactura, Integer> id;
	public static volatile SingularAttribute<PagoFactura, String> detalle;

}

