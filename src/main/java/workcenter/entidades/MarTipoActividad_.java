package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MarTipoActividad.class)
public abstract class MarTipoActividad_ {

	public static volatile SingularAttribute<MarTipoActividad, Boolean> requiereNota;
	public static volatile SingularAttribute<MarTipoActividad, String> duracion;
	public static volatile SingularAttribute<MarTipoActividad, MarTipoActividad> marTipoActividadByIdTipoPadre;
	public static volatile SingularAttribute<MarTipoActividad, Integer> id;
	public static volatile SingularAttribute<MarTipoActividad, String> nombre;
	public static volatile SingularAttribute<MarTipoActividad, MarRegistro> marRegistrosByIdRegistro;

}

