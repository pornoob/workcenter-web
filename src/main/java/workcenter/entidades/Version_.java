package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Version.class)
public abstract class Version_ {

	public static volatile SingularAttribute<Version, String> modificacion;
	public static volatile SingularAttribute<Version, Integer> proyecto;
	public static volatile SingularAttribute<Version, Integer> id;
	public static volatile SingularAttribute<Version, Double> version;
	public static volatile SingularAttribute<Version, String> nota;
	public static volatile SingularAttribute<Version, String> url;

}

