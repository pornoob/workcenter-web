package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Personal.class)
public abstract class Personal_ {

	public static volatile CollectionAttribute<Personal, MpaPlanPrograma> mpaPlanProgramaCollection;
	public static volatile SingularAttribute<Personal, String> mail;
	public static volatile ListAttribute<Personal, BonoDescuentoPersonal> bonosDescuentos;
	public static volatile SingularAttribute<Personal, String> contactoe;
	public static volatile SetAttribute<Personal, CargasFamiliares> lstCargasFamiliares;
	public static volatile SingularAttribute<Personal, Sancionado> sancion;
	public static volatile SingularAttribute<Personal, String> nombres;
	public static volatile SingularAttribute<Personal, String> edomicilio;
	public static volatile SingularAttribute<Personal, Integer> rut;
	public static volatile SingularAttribute<Personal, String> ecivil;
	public static volatile ListAttribute<Personal, DocumentoPersonal> documentos;
	public static volatile SingularAttribute<Personal, String> domicilio;
	public static volatile SingularAttribute<Personal, String> celular;
	public static volatile ListAttribute<Personal, Remuneracion> maestroGuiaList;
	public static volatile SingularAttribute<Personal, String> telefono;
	public static volatile SingularAttribute<Personal, String> apellidos;
	public static volatile SingularAttribute<Personal, Character> digitoverificador;
	public static volatile ListAttribute<Personal, ContratoPersonal> contratos;
	public static volatile SingularAttribute<Personal, Date> nacimiento;
	public static volatile ListAttribute<Personal, Servicio> servicios;
	public static volatile ListAttribute<Personal, SancionRetiradaPersonal> sancionesRetiradas;
	public static volatile SingularAttribute<Personal, String> foto;
	public static volatile CollectionAttribute<Personal, MpaPlanPrograma> mpaPlanProgramaCollection1;
	public static volatile SingularAttribute<Personal, Usuario> usuario;
	public static volatile CollectionAttribute<Personal, MpaEjecucionPlan> mpaEjecucionPlanCollection;

}

