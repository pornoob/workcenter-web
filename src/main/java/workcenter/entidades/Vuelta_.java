package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Vuelta.class)
public abstract class Vuelta_ {

	public static volatile SingularAttribute<Vuelta, Integer> ingresadoPor;
	public static volatile ListAttribute<Vuelta, GuiaPetroleo> guiasPetroleo;
	public static volatile SingularAttribute<Vuelta, Equipo> batea;
	public static volatile SingularAttribute<Vuelta, Integer> totalCombustible;
	public static volatile SingularAttribute<Vuelta, Integer> dineroEntregado;
	public static volatile ListAttribute<Vuelta, Producto> productosList;
	public static volatile SingularAttribute<Vuelta, Personal> conductor;
	public static volatile SingularAttribute<Vuelta, Date> fecha;
	public static volatile SingularAttribute<Vuelta, Integer> kmFinal;
	public static volatile SingularAttribute<Vuelta, Integer> viatico;
	public static volatile SingularAttribute<Vuelta, Integer> id;
	public static volatile SingularAttribute<Vuelta, Equipo> tracto;
	public static volatile SingularAttribute<Vuelta, Integer> peaje;
	public static volatile SingularAttribute<Vuelta, Integer> otrosGastos;
	public static volatile SingularAttribute<Vuelta, Integer> totalLitros;
	public static volatile SingularAttribute<Vuelta, Integer> kmInicial;

}

