package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Empresa.class)
public abstract class Empresa_ {

	public static volatile SingularAttribute<Empresa, Long> rut;
	public static volatile SingularAttribute<Empresa, String> direccion;
	public static volatile SingularAttribute<Empresa, String> logo;
	public static volatile SetAttribute<Empresa, ContactoEmpresa> contactos;
	public static volatile SetAttribute<Empresa, ContratoEmpresa> contratos;
	public static volatile SingularAttribute<Empresa, Integer> id;
	public static volatile SingularAttribute<Empresa, Character> digitoverificador;
	public static volatile SingularAttribute<Empresa, String> telefono;
	public static volatile SingularAttribute<Empresa, String> nombre;
	public static volatile SingularAttribute<Empresa, String> giro;

}

