package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Equipo.class)
public abstract class Equipo_ {

	public static volatile SingularAttribute<Equipo, String> motor;
	public static volatile SingularAttribute<Equipo, TipoEquipo> tipo;
	public static volatile SingularAttribute<Equipo, Empresa> duenio;
	public static volatile SingularAttribute<Equipo, Integer> numero;
	public static volatile SingularAttribute<Equipo, String> color;
	public static volatile SingularAttribute<Equipo, ModeloEquipo> modelo;
	public static volatile SingularAttribute<Equipo, String> patente;
	public static volatile SingularAttribute<Equipo, MarcaEquipo> marca;
	public static volatile SingularAttribute<Equipo, Integer> kilometraje;
	public static volatile SingularAttribute<Equipo, String> chasis;
	public static volatile SingularAttribute<Equipo, SubtipoEquipo> subtipo;
	public static volatile SingularAttribute<Equipo, Boolean> bollo;
	public static volatile SingularAttribute<Equipo, Integer> anio;
	public static volatile ListAttribute<Equipo, FotoEquipo> fotos;

}

