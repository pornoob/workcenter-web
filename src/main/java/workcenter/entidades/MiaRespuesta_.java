package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MiaRespuesta.class)
public abstract class MiaRespuesta_ {

	public static volatile SingularAttribute<MiaRespuesta, MiaInspeccionAvanzada> miaInspeccionAvanzadaByIdInspeccion;
	public static volatile SingularAttribute<MiaRespuesta, MiaPregunta> miaPreguntasByIdPregunta;
	public static volatile SingularAttribute<MiaRespuesta, Integer> id;
	public static volatile SingularAttribute<MiaRespuesta, Boolean> cumple;

}

