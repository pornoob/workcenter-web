package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ContratoPersonal.class)
public abstract class ContratoPersonal_ {

	public static volatile SingularAttribute<ContratoPersonal, Date> vencimiento;
	public static volatile SingularAttribute<ContratoPersonal, Integer> numero;
	public static volatile SingularAttribute<ContratoPersonal, Empresa> empleador;
	public static volatile SingularAttribute<ContratoPersonal, Boolean> sinTope;
	public static volatile SingularAttribute<ContratoPersonal, Integer> subcontrato;
	public static volatile SingularAttribute<ContratoPersonal, Boolean> vigente;
	public static volatile SingularAttribute<ContratoPersonal, Date> inicio;
	public static volatile SingularAttribute<ContratoPersonal, Integer> bonopactado;
	public static volatile SingularAttribute<ContratoPersonal, Integer> locomocion;
	public static volatile ListAttribute<ContratoPersonal, PrevisionContrato> previsiones;
	public static volatile ListAttribute<ContratoPersonal, ValorPrevisionPersonal> valoresPrevisiones;
	public static volatile SingularAttribute<ContratoPersonal, Personal> rut;
	public static volatile SingularAttribute<ContratoPersonal, Date> fecha;
	public static volatile SingularAttribute<ContratoPersonal, Integer> sueldoBase;
	public static volatile SingularAttribute<ContratoPersonal, Float> porcentaje;
	public static volatile SingularAttribute<ContratoPersonal, Cargo> cargo;
	public static volatile SingularAttribute<ContratoPersonal, Integer> colacion;

}

