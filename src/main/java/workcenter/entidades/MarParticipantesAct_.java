package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MarParticipantesAct.class)
public abstract class MarParticipantesAct_ {

	public static volatile SingularAttribute<MarParticipantesAct, MarActividad> marActividadByIdActividad;
	public static volatile SingularAttribute<MarParticipantesAct, Integer> id;
	public static volatile SingularAttribute<MarParticipantesAct, Personal> participante;
	public static volatile SingularAttribute<MarParticipantesAct, Float> nota;

}

