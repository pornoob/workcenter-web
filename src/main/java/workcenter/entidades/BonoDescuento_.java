package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BonoDescuento.class)
public abstract class BonoDescuento_ {

	public static volatile SingularAttribute<BonoDescuento, String> descripcion;
	public static volatile SingularAttribute<BonoDescuento, Long> monto;
	public static volatile SingularAttribute<BonoDescuento, Boolean> indefinido;
	public static volatile SingularAttribute<BonoDescuento, Boolean> porPersona;
	public static volatile SingularAttribute<BonoDescuento, TipoUnidad> idTipoUnidad;
	public static volatile SingularAttribute<BonoDescuento, TipoBonoDescuento> idTipoBonodescuento;
	public static volatile SingularAttribute<BonoDescuento, Integer> duracion;
	public static volatile SingularAttribute<BonoDescuento, Boolean> imponible;
	public static volatile SingularAttribute<BonoDescuento, Long> id;

}

