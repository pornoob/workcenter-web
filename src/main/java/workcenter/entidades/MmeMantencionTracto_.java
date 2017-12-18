package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MmeMantencionTracto.class)
public abstract class MmeMantencionTracto_ {

	public static volatile SingularAttribute<MmeMantencionTracto, Date> fecha;
	public static volatile SingularAttribute<MmeMantencionTracto, MmeTipoMantencion> tipo;
	public static volatile SingularAttribute<MmeMantencionTracto, Integer> ciclo;
	public static volatile SingularAttribute<MmeMantencionTracto, Integer> kilometraje;
	public static volatile SingularAttribute<MmeMantencionTracto, OrdenTrabajo> ot;
	public static volatile SingularAttribute<MmeMantencionTracto, Personal> mecanicoResponsable;
	public static volatile SingularAttribute<MmeMantencionTracto, Integer> id;
	public static volatile SingularAttribute<MmeMantencionTracto, Equipo> tracto;
	public static volatile SingularAttribute<MmeMantencionTracto, String> patente;

}

