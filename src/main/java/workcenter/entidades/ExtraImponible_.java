package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ExtraImponible.class)
public abstract class ExtraImponible_ {

	public static volatile SingularAttribute<ExtraImponible, String> descripcion;
	public static volatile SingularAttribute<ExtraImponible, String> etiqueta;
	public static volatile SingularAttribute<ExtraImponible, Date> fecha;
	public static volatile SingularAttribute<ExtraImponible, Integer> persona;
	public static volatile SingularAttribute<ExtraImponible, Integer> monto;
	public static volatile SingularAttribute<ExtraImponible, Boolean> pagado;
	public static volatile SingularAttribute<ExtraImponible, Integer> id;

}

