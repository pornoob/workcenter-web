package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MpaEjecucionPlan.class)
public abstract class MpaEjecucionPlan_ {

	public static volatile SingularAttribute<MpaEjecucionPlan, MpaPrograma> idPrograma;
	public static volatile SingularAttribute<MpaEjecucionPlan, MpaActividad> idActividad;
	public static volatile SingularAttribute<MpaEjecucionPlan, Personal> rutResponsable;
	public static volatile SingularAttribute<MpaEjecucionPlan, Integer> idMes;
	public static volatile SingularAttribute<MpaEjecucionPlan, Integer> id;

}

