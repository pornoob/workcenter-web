package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MirIncidencia.class)
public abstract class MirIncidencia_ {

	public static volatile SingularAttribute<MirIncidencia, MirApoyo> idApoyo;
	public static volatile SingularAttribute<MirIncidencia, Date> fecha;
	public static volatile SingularAttribute<MirIncidencia, Personal> rutInformador;
	public static volatile SingularAttribute<MirIncidencia, MirSeveridad> severidad;
	public static volatile SingularAttribute<MirIncidencia, Integer> id;
	public static volatile SingularAttribute<MirIncidencia, Date> resolucionProgramada;
	public static volatile SingularAttribute<MirIncidencia, MirPrioridad> prioridad;

}

