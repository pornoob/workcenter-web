package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Sancionado.class)
public abstract class Sancionado_ {

	public static volatile SingularAttribute<Sancionado, Date> fecha;
	public static volatile SingularAttribute<Sancionado, String> motivo;
	public static volatile SingularAttribute<Sancionado, Personal> sancionado;
	public static volatile SingularAttribute<Sancionado, Integer> id;
	public static volatile SingularAttribute<Sancionado, Integer> nivel;

}

