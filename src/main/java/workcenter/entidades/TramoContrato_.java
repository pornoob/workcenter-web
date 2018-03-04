package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TramoContrato.class)
public abstract class TramoContrato_ {

	public static volatile SingularAttribute<TramoContrato, TipoProducto> tipoProducto;
	public static volatile SetAttribute<TramoContrato, TarifaTramo> tarifas;
	public static volatile SingularAttribute<TramoContrato, Integer> origenId;
	public static volatile SingularAttribute<TramoContrato, Integer> productoId;
	public static volatile SingularAttribute<TramoContrato, ContratoEmpresa> contrato;
	public static volatile SingularAttribute<TramoContrato, Integer> id;
	public static volatile SingularAttribute<TramoContrato, OrigenDestino> origen;
	public static volatile SingularAttribute<TramoContrato, OrigenDestino> destino;
	public static volatile SingularAttribute<TramoContrato, Integer> destinoId;

}

