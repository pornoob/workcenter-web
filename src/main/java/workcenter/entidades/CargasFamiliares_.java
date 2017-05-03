package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CargasFamiliares.class)
public abstract class CargasFamiliares_ {

	public static volatile SingularAttribute<CargasFamiliares, String> apellidos;
	public static volatile SingularAttribute<CargasFamiliares, Integer> rutCarga;
	public static volatile SingularAttribute<CargasFamiliares, String> dvVerificadorCarga;
	public static volatile SingularAttribute<CargasFamiliares, Personal> rutPersonal;
	public static volatile SingularAttribute<CargasFamiliares, Date> nacimiento;
	public static volatile SingularAttribute<CargasFamiliares, String> nombres;

}

