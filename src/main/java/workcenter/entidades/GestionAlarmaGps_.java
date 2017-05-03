package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GestionAlarmaGps.class)
public abstract class GestionAlarmaGps_ {

	public static volatile SingularAttribute<GestionAlarmaGps, Date> fecha;
	public static volatile SingularAttribute<GestionAlarmaGps, Usuario> idUsuario;
	public static volatile CollectionAttribute<GestionAlarmaGps, DocGestionAlarmaGps> docGestionAlarmaGpsCollection;
	public static volatile SingularAttribute<GestionAlarmaGps, Integer> idGestion;
	public static volatile SingularAttribute<GestionAlarmaGps, AlarmaGps> idAlarma;
	public static volatile SingularAttribute<GestionAlarmaGps, String> detalle;

}

