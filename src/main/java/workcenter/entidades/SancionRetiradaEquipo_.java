package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SancionRetiradaEquipo.class)
public abstract class SancionRetiradaEquipo_ {

	public static volatile SingularAttribute<SancionRetiradaEquipo, Integer> perdonadopor;
	public static volatile SingularAttribute<SancionRetiradaEquipo, String> motivoretirosancion;
	public static volatile SingularAttribute<SancionRetiradaEquipo, Date> fecha;
	public static volatile SingularAttribute<SancionRetiradaEquipo, String> motivo;
	public static volatile SingularAttribute<SancionRetiradaEquipo, Equipo> sancionado;
	public static volatile SingularAttribute<SancionRetiradaEquipo, Integer> id;
	public static volatile SingularAttribute<SancionRetiradaEquipo, Integer> nivel;
	public static volatile SingularAttribute<SancionRetiradaEquipo, Date> fechasancion;

}

