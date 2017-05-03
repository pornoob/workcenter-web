package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Concepto.class)
public abstract class Concepto_ {

	public static volatile SingularAttribute<Concepto, String> descripcion;
	public static volatile SingularAttribute<Concepto, String> etiqueta;
	public static volatile CollectionAttribute<Concepto, Dinero> dinerosCollection;
	public static volatile SingularAttribute<Concepto, Integer> id;
	public static volatile SingularAttribute<Concepto, Boolean> salida;

}

