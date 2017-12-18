package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MmeMantencionMaquina.class)
public abstract class MmeMantencionMaquina_ {

	public static volatile SingularAttribute<MmeMantencionMaquina, Date> fecha;
	public static volatile SingularAttribute<MmeMantencionMaquina, OrdenTrabajo> ot;
	public static volatile SingularAttribute<MmeMantencionMaquina, Equipo> maquina;
	public static volatile SingularAttribute<MmeMantencionMaquina, Personal> mecanicoResponsable;
	public static volatile SingularAttribute<MmeMantencionMaquina, Integer> hrasDiff;
	public static volatile SingularAttribute<MmeMantencionMaquina, Integer> id;
	public static volatile SingularAttribute<MmeMantencionMaquina, Integer> hrasAnotadas;
	public static volatile SetAttribute<MmeMantencionMaquina, MmeCheckMaquina> checkeoRealizado;

}

