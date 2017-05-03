package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MmeMantencionSemiremolque.class)
public abstract class MmeMantencionSemiremolque_ {

	public static volatile SingularAttribute<MmeMantencionSemiremolque, Date> fecha;
	public static volatile SingularAttribute<MmeMantencionSemiremolque, Integer> criterioSiguiente;
	public static volatile SingularAttribute<MmeMantencionSemiremolque, Personal> mecanicoResponsable;
	public static volatile SingularAttribute<MmeMantencionSemiremolque, Integer> id;
	public static volatile SingularAttribute<MmeMantencionSemiremolque, Equipo> semiRemolque;

}

