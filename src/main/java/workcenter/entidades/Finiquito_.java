package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Finiquito.class)
public abstract class Finiquito_ {

	public static volatile SingularAttribute<Finiquito, Personal> trabajador;
	public static volatile SingularAttribute<Finiquito, Date> fechaIngreso;
	public static volatile SingularAttribute<Finiquito, Integer> monto;
	public static volatile SingularAttribute<Finiquito, Empresa> empleador;
	public static volatile SingularAttribute<Finiquito, Integer> id;
	public static volatile SingularAttribute<Finiquito, Date> fechaFiniquito;

}

