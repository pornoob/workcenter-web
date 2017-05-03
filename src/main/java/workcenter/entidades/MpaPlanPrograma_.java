package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MpaPlanPrograma.class)
public abstract class MpaPlanPrograma_ {

	public static volatile SingularAttribute<MpaPlanPrograma, Date> fecha;
	public static volatile SingularAttribute<MpaPlanPrograma, Personal> rutCreador;
	public static volatile SingularAttribute<MpaPlanPrograma, MpaPrograma> idPrograma;
	public static volatile SingularAttribute<MpaPlanPrograma, MpaActividad> idActividad;
	public static volatile SingularAttribute<MpaPlanPrograma, Personal> rutResponsable;
	public static volatile CollectionAttribute<MpaPlanPrograma, MpaValorPlanPrograma> mpaValorPlanProgramaCollection;
	public static volatile SingularAttribute<MpaPlanPrograma, Integer> anioVigencia;
	public static volatile SingularAttribute<MpaPlanPrograma, MpaContrato> contrato;
	public static volatile SingularAttribute<MpaPlanPrograma, Integer> id;

}

