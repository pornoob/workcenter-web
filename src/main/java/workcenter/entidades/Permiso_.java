package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Permiso.class)
public abstract class Permiso_ {

	public static volatile SingularAttribute<Permiso, Proyecto> proyecto;
	public static volatile SingularAttribute<Permiso, Usuario> usuario;
	public static volatile SingularAttribute<Permiso, Integer> id;
	public static volatile SingularAttribute<Permiso, Integer> nivel;

}

