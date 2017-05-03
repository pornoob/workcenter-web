package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Consumo.class)
public abstract class Consumo_ {

	public static volatile SingularAttribute<Consumo, Integer> existencia;
	public static volatile SingularAttribute<Consumo, Integer> id;
	public static volatile SingularAttribute<Consumo, Integer> cantidad;
	public static volatile SingularAttribute<Consumo, Integer> permisoespecial;
	public static volatile SingularAttribute<Consumo, String> entregadoa;
	public static volatile SingularAttribute<Consumo, Date> salida;
	public static volatile SingularAttribute<Consumo, Integer> entregadopor;

}

