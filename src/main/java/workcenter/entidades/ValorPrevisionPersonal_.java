package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ValorPrevisionPersonal.class)
public abstract class ValorPrevisionPersonal_ {

	public static volatile SingularAttribute<ValorPrevisionPersonal, TipoUnidad> unidad;
	public static volatile SingularAttribute<ValorPrevisionPersonal, Date> fechaVigencia;
	public static volatile SingularAttribute<ValorPrevisionPersonal, Double> valor;
	public static volatile SingularAttribute<ValorPrevisionPersonal, ContratoPersonal> contrato;
	public static volatile SingularAttribute<ValorPrevisionPersonal, Integer> id;
	public static volatile SingularAttribute<ValorPrevisionPersonal, Prevision> prevision;

}

