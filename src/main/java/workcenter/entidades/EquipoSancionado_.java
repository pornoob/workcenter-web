package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EquipoSancionado.class)
public abstract class EquipoSancionado_ {

	public static volatile SingularAttribute<EquipoSancionado, Date> fecha;
	public static volatile SingularAttribute<EquipoSancionado, String> motivo;
	public static volatile SingularAttribute<EquipoSancionado, Equipo> sancionado;
	public static volatile SingularAttribute<EquipoSancionado, Integer> id;
	public static volatile SingularAttribute<EquipoSancionado, Integer> nivel;

}

