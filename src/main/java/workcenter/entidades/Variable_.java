package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Variable.class)
public abstract class Variable_ {

	public static volatile SingularAttribute<Variable, Integer> rut;
	public static volatile SingularAttribute<Variable, Date> fecha;
	public static volatile SingularAttribute<Variable, String> llave;
	public static volatile SingularAttribute<Variable, String> valor;
	public static volatile SingularAttribute<Variable, String> ambito;
	public static volatile SingularAttribute<Variable, Integer> id;

}

