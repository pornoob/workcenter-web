package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MmeCheckMaquina.class)
public abstract class MmeCheckMaquina_ {

	public static volatile SingularAttribute<MmeCheckMaquina, MmeTareaMaquina> tareaMaquina;
	public static volatile SingularAttribute<MmeCheckMaquina, MmeMantencionMaquina> mantencionMaquina;
	public static volatile SingularAttribute<MmeCheckMaquina, Integer> tareaMaquinaId;
	public static volatile SingularAttribute<MmeCheckMaquina, Integer> mantencionMaquinaId;
	public static volatile SingularAttribute<MmeCheckMaquina, Integer> hrasAnotadas;

}

