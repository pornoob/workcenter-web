package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MmeMantencionSemirremolque.class)
public abstract class MmeMantencionSemirremolque_ {

	public static volatile SingularAttribute<MmeMantencionSemirremolque, Date> fecha;
	public static volatile SingularAttribute<MmeMantencionSemirremolque, Integer> criterioSiguiente;
	public static volatile SingularAttribute<MmeMantencionSemirremolque, OrdenTrabajo> ot;
	public static volatile SingularAttribute<MmeMantencionSemirremolque, Personal> mecanicoResponsable;
	public static volatile SingularAttribute<MmeMantencionSemirremolque, Equipo> semiRremolque;
	public static volatile SingularAttribute<MmeMantencionSemirremolque, Integer> id;

}

