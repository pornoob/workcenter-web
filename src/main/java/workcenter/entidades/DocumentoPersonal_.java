package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DocumentoPersonal.class)
public abstract class DocumentoPersonal_ {

	public static volatile SingularAttribute<DocumentoPersonal, Date> vencimiento;
	public static volatile SingularAttribute<DocumentoPersonal, TipoDocPersonal> tipo;
	public static volatile SingularAttribute<DocumentoPersonal, String> numero;
	public static volatile SingularAttribute<DocumentoPersonal, String> archivo;
	public static volatile SingularAttribute<DocumentoPersonal, Personal> personal;
	public static volatile SingularAttribute<DocumentoPersonal, Integer> id;

}

