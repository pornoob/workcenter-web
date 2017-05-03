package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Cargo.class)
public abstract class Cargo_ {

	public static volatile SingularAttribute<Cargo, String> formacion;
	public static volatile SingularAttribute<Cargo, String> nombreCargo;
	public static volatile SingularAttribute<Cargo, Integer> exposicion;
	public static volatile SingularAttribute<Cargo, String> experiencia;
	public static volatile SingularAttribute<Cargo, String> educacion;
	public static volatile SingularAttribute<Cargo, Integer> id;
	public static volatile SingularAttribute<Cargo, String> responsabilidades;
	public static volatile SingularAttribute<Cargo, String> dependencia;
	public static volatile CollectionAttribute<Cargo, ContratoPersonal> contratospersonalCollection;

}

