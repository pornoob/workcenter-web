package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PrevisionContrato.class)
public abstract class PrevisionContrato_ {

	public static volatile SingularAttribute<PrevisionContrato, Date> fechainicio;
	public static volatile SingularAttribute<PrevisionContrato, ContratoPersonal> contrato;
	public static volatile SingularAttribute<PrevisionContrato, Integer> id;
	public static volatile SingularAttribute<PrevisionContrato, Prevision> prevision;
	public static volatile SingularAttribute<PrevisionContrato, Date> fechatermino;

}

