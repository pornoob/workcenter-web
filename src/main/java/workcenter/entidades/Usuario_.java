package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Usuario.class)
public abstract class Usuario_ {

	public static volatile SingularAttribute<Usuario, Integer> rut;
	public static volatile SingularAttribute<Usuario, String> password;
	public static volatile CollectionAttribute<Usuario, GestionAlarmaGps> gestionAlarmaGpsCollection;
	public static volatile SingularAttribute<Usuario, Personal> personal;
	public static volatile SingularAttribute<Usuario, Boolean> habilitado;
	public static volatile SetAttribute<Usuario, Permiso> permisos;

}

