package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ContratoEmpresa.class)
public abstract class ContratoEmpresa_ {

	public static volatile SingularAttribute<ContratoEmpresa, String> nombrecontrato;
	public static volatile SingularAttribute<ContratoEmpresa, Date> fechainicio;
	public static volatile SingularAttribute<ContratoEmpresa, Boolean> indefinido;
	public static volatile SingularAttribute<ContratoEmpresa, Integer> ordendecompra;
	public static volatile SetAttribute<ContratoEmpresa, TramoContrato> tramos;
	public static volatile SingularAttribute<ContratoEmpresa, String> numerocontrato;
	public static volatile SetAttribute<ContratoEmpresa, Contacto> contactos;
	public static volatile SingularAttribute<ContratoEmpresa, Integer> id;
	public static volatile SingularAttribute<ContratoEmpresa, Empresa> empresa;
	public static volatile SingularAttribute<ContratoEmpresa, Boolean> escliente;
	public static volatile SingularAttribute<ContratoEmpresa, Date> fechatermino;
	public static volatile SingularAttribute<ContratoEmpresa, String> detalle;

}

