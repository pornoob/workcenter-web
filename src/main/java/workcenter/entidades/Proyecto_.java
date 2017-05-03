package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Proyecto.class)
public abstract class Proyecto_ {

	public static volatile SingularAttribute<Proyecto, String> descripcion;
	public static volatile SingularAttribute<Proyecto, String> tipo;
	public static volatile SingularAttribute<Proyecto, String> titulo;
	public static volatile CollectionAttribute<Proyecto, Permiso> permisosCollection;
	public static volatile SingularAttribute<Proyecto, Integer> id;

}

