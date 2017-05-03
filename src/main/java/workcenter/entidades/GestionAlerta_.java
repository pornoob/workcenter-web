package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GestionAlerta.class)
public abstract class GestionAlerta_ {

	public static volatile SingularAttribute<GestionAlerta, Date> fecha;
	public static volatile SingularAttribute<GestionAlerta, String> tipo;
	public static volatile SingularAttribute<GestionAlerta, String> ubicacion;
	public static volatile SingularAttribute<GestionAlerta, String> ruta;
	public static volatile SingularAttribute<GestionAlerta, Integer> id;
	public static volatile SingularAttribute<GestionAlerta, Integer> velocidad;
	public static volatile SingularAttribute<GestionAlerta, String> patente;
	public static volatile SingularAttribute<GestionAlerta, String> gestion;

}

