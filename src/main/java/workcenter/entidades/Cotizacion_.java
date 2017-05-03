package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Cotizacion.class)
public abstract class Cotizacion_ {

	public static volatile SingularAttribute<Cotizacion, Date> fecha;
	public static volatile SingularAttribute<Cotizacion, Integer> cliente;
	public static volatile SingularAttribute<Cotizacion, Integer> condventa;
	public static volatile SingularAttribute<Cotizacion, String> representante;
	public static volatile SingularAttribute<Cotizacion, String> duracion;
	public static volatile SingularAttribute<Cotizacion, Integer> id;
	public static volatile SingularAttribute<Cotizacion, String> origen;
	public static volatile SingularAttribute<Cotizacion, String> destino;
	public static volatile SingularAttribute<Cotizacion, Integer> empresa;
	public static volatile SingularAttribute<Cotizacion, Integer> emitidopor;

}

