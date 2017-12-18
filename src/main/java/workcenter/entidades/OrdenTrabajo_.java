package workcenter.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrdenTrabajo.class)
public abstract class OrdenTrabajo_ {

	public static volatile SingularAttribute<OrdenTrabajo, String> descripcion;
	public static volatile SetAttribute<OrdenTrabajo, TrazabilidadOt> trazabilidad;
	public static volatile SetAttribute<OrdenTrabajo, RepuestoOt> repuestos;
	public static volatile SingularAttribute<OrdenTrabajo, MmeMantencionMaquina> mantencionMaquina;
	public static volatile SingularAttribute<OrdenTrabajo, SolicitanteOt> solicitante;
	public static volatile SingularAttribute<OrdenTrabajo, Integer> id;
	public static volatile SingularAttribute<OrdenTrabajo, MmeMantencionTracto> mantencionTracto;
	public static volatile SingularAttribute<OrdenTrabajo, String> tipoTrabajo;
	public static volatile SingularAttribute<OrdenTrabajo, MmeMantencionSemirremolque> mantencionSemirremolque;
	public static volatile SetAttribute<OrdenTrabajo, AsistenteOt> asistentes;

}

