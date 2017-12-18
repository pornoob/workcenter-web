package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AsistenteOt.class)
public abstract class AsistenteOt_ {

	public static volatile SingularAttribute<AsistenteOt, Long> personalId;
	public static volatile SingularAttribute<AsistenteOt, Long> otId;
	public static volatile SingularAttribute<AsistenteOt, OrdenTrabajo> ot;
	public static volatile SingularAttribute<AsistenteOt, Float> horasHombre;
	public static volatile SingularAttribute<AsistenteOt, Personal> personal;

}

