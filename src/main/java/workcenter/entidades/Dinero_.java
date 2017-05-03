package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Dinero.class)
public abstract class Dinero_ {

	public static volatile SingularAttribute<Dinero, Integer> monto;
	public static volatile SingularAttribute<Dinero, Date> fechareal;
	public static volatile SingularAttribute<Dinero, Personal> receptor;
	public static volatile SingularAttribute<Dinero, Concepto> concepto;
	public static volatile SingularAttribute<Dinero, Date> fechaactivo;
	public static volatile ListAttribute<Dinero, Descuento> lstDescuentos;
	public static volatile SingularAttribute<Dinero, Vuelta> ordendecarga;
	public static volatile SingularAttribute<Dinero, Integer> id;
	public static volatile SingularAttribute<Dinero, String> comentario;

}

