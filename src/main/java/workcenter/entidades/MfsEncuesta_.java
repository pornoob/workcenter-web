package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MfsEncuesta.class)
public abstract class MfsEncuesta_ {

	public static volatile SingularAttribute<MfsEncuesta, Date> fecha;
	public static volatile ListAttribute<MfsEncuesta, MfsRespuesta> respuestas;
	public static volatile SingularAttribute<MfsEncuesta, Personal> encuestado;
	public static volatile ListAttribute<MfsEncuesta, MfsTest> tests;
	public static volatile SingularAttribute<MfsEncuesta, Personal> encargado;
	public static volatile SingularAttribute<MfsEncuesta, Integer> id;

}

