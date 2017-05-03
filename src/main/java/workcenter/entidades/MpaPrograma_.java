package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MpaPrograma.class)
public abstract class MpaPrograma_ {

	public static volatile CollectionAttribute<MpaPrograma, MpaPlanPrograma> mpaPlanProgramaCollection;
	public static volatile CollectionAttribute<MpaPrograma, MpaEjecucionPlan> mpaEjecucionPlanCollection;
	public static volatile SingularAttribute<MpaPrograma, Integer> id;
	public static volatile SingularAttribute<MpaPrograma, String> nombre;
	public static volatile CollectionAttribute<MpaPrograma, MpaActividad> mpaActividadesCollection;

}

