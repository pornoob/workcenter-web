package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MfsPregunta.class)
public abstract class MfsPregunta_ {

	public static volatile SingularAttribute<MfsPregunta, Integer> seccion;
	public static volatile SingularAttribute<MfsPregunta, Integer> numero;
	public static volatile SingularAttribute<MfsPregunta, MfsTipoPregunta> tipoPregunta;
	public static volatile SingularAttribute<MfsPregunta, Integer> id;
	public static volatile SingularAttribute<MfsPregunta, Integer> respuesta;
	public static volatile SingularAttribute<MfsPregunta, String> pregunta;

}

