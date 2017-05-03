package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DocumentoEquipo.class)
public abstract class DocumentoEquipo_ {

	public static volatile SingularAttribute<DocumentoEquipo, Date> vencimiento;
	public static volatile SingularAttribute<DocumentoEquipo, TipoDocumentoEquipo> tipo;
	public static volatile SingularAttribute<DocumentoEquipo, String> numero;
	public static volatile SingularAttribute<DocumentoEquipo, String> archivo;
	public static volatile SingularAttribute<DocumentoEquipo, Integer> id;
	public static volatile SingularAttribute<DocumentoEquipo, String> patente;

}

