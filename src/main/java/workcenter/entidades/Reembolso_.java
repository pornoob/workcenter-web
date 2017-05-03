package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Reembolso.class)
public abstract class Reembolso_ {

	public static volatile SingularAttribute<Reembolso, String> descripcion;
	public static volatile SingularAttribute<Reembolso, String> fecha;
	public static volatile SingularAttribute<Reembolso, Integer> motivo;
	public static volatile SingularAttribute<Reembolso, Integer> persona;
	public static volatile SingularAttribute<Reembolso, Integer> monto;
	public static volatile SingularAttribute<Reembolso, Boolean> pagado;
	public static volatile SingularAttribute<Reembolso, Boolean> imponible;
	public static volatile SingularAttribute<Reembolso, Integer> id;
	public static volatile SingularAttribute<Reembolso, String> nombre;

}

