package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SancionRetiradaPersonal.class)
public abstract class SancionRetiradaPersonal_ {

	public static volatile SingularAttribute<SancionRetiradaPersonal, String> motivolevantada;
	public static volatile SingularAttribute<SancionRetiradaPersonal, Date> fecha;
	public static volatile SingularAttribute<SancionRetiradaPersonal, Personal> sancionado;
	public static volatile SingularAttribute<SancionRetiradaPersonal, String> motivosancion;
	public static volatile SingularAttribute<SancionRetiradaPersonal, Integer> id;
	public static volatile SingularAttribute<SancionRetiradaPersonal, Personal> perdonadapor;
	public static volatile SingularAttribute<SancionRetiradaPersonal, Integer> nivel;
	public static volatile SingularAttribute<SancionRetiradaPersonal, Date> fechasancion;

}

