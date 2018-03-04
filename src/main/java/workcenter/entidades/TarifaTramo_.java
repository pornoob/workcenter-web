package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TarifaTramo.class)
public abstract class TarifaTramo_ {

	public static volatile SingularAttribute<TarifaTramo, TipoTarifa> tipoTarifa;
	public static volatile SingularAttribute<TarifaTramo, Date> fechaVigencia;
	public static volatile SingularAttribute<TarifaTramo, TramoContrato> tramo;
	public static volatile SingularAttribute<TarifaTramo, Integer> id;
	public static volatile SingularAttribute<TarifaTramo, Integer> tarifaCobro;
	public static volatile SingularAttribute<TarifaTramo, Integer> tarifaPago;

}

