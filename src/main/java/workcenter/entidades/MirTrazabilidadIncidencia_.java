package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MirTrazabilidadIncidencia.class)
public abstract class MirTrazabilidadIncidencia_ {

	public static volatile SingularAttribute<MirTrazabilidadIncidencia, MirIncidencia> idIncidencia;
	public static volatile SingularAttribute<MirTrazabilidadIncidencia, Date> fecha;
	public static volatile SingularAttribute<MirTrazabilidadIncidencia, MirEstadoIncidencia> idEstado;
	public static volatile SingularAttribute<MirTrazabilidadIncidencia, Personal> creador;
	public static volatile SingularAttribute<MirTrazabilidadIncidencia, Integer> id;
	public static volatile SingularAttribute<MirTrazabilidadIncidencia, String> detalle;

}

