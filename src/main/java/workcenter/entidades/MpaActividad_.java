package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MpaActividad.class)
public abstract class MpaActividad_ {

	public static volatile CollectionAttribute<MpaActividad, MpaPlanPrograma> mpaPlanProgramaCollection;
	public static volatile SingularAttribute<MpaActividad, MpaPrograma> idPrograma;
	public static volatile CollectionAttribute<MpaActividad, MpaEjecucionPlan> mpaEjecucionPlanCollection;
	public static volatile SingularAttribute<MpaActividad, Integer> id;
	public static volatile SingularAttribute<MpaActividad, String> nombre;

}

