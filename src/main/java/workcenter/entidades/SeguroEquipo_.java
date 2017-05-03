package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SeguroEquipo.class)
public abstract class SeguroEquipo_ {

	public static volatile SingularAttribute<SeguroEquipo, Date> vencimiento;
	public static volatile SingularAttribute<SeguroEquipo, Double> cobertura;
	public static volatile SingularAttribute<SeguroEquipo, String> numero;
	public static volatile SingularAttribute<SeguroEquipo, String> archivo;
	public static volatile SingularAttribute<SeguroEquipo, Empresa> tenedor;
	public static volatile SingularAttribute<SeguroEquipo, Equipo> equipo;
	public static volatile SingularAttribute<SeguroEquipo, Empresa> contratante;
	public static volatile SingularAttribute<SeguroEquipo, Integer> id;
	public static volatile SingularAttribute<SeguroEquipo, Empresa> aseguradora;

}

