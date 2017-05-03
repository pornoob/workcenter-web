package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Documento.class)
public abstract class Documento_ {

	public static volatile SingularAttribute<Documento, Date> fecha;
	public static volatile SingularAttribute<Documento, String> nombreOriginal;
	public static volatile SingularAttribute<Documento, Integer> id;
	public static volatile CollectionAttribute<Documento, AsociacionDocumento> asociacionDocumentoCollection;

}

