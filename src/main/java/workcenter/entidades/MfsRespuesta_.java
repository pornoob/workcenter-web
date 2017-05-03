package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MfsRespuesta.class)
public abstract class MfsRespuesta_ {

	public static volatile SingularAttribute<MfsRespuesta, Integer> valorRespuesta;
	public static volatile SingularAttribute<MfsRespuesta, MfsEncuesta> encuesta;
	public static volatile SingularAttribute<MfsRespuesta, Integer> id;
	public static volatile SingularAttribute<MfsRespuesta, MfsPregunta> idPregunta;

}

