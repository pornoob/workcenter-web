package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AlarmaGps.class)
public abstract class AlarmaGps_ {

	public static volatile SingularAttribute<AlarmaGps, Date> fecha;
	public static volatile SingularAttribute<AlarmaGps, String> cliente;
	public static volatile SingularAttribute<AlarmaGps, String> ubicacion;
	public static volatile SingularAttribute<AlarmaGps, Integer> numero;
	public static volatile SingularAttribute<AlarmaGps, String> chofer;
	public static volatile SingularAttribute<AlarmaGps, String> alarma;
	public static volatile SingularAttribute<AlarmaGps, String> ruta;
	public static volatile CollectionAttribute<AlarmaGps, GestionAlarmaGps> gestionAlarmasGpsCollection;
	public static volatile SingularAttribute<AlarmaGps, Integer> id;
	public static volatile SingularAttribute<AlarmaGps, Integer> velocidad;
	public static volatile SingularAttribute<AlarmaGps, String> patente;

}

