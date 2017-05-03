package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Tarea.class)
public abstract class Tarea_ {

	public static volatile SingularAttribute<Tarea, Date> entregalocal;
	public static volatile SingularAttribute<Tarea, Integer> ticket;
	public static volatile SingularAttribute<Tarea, Double> duracion;
	public static volatile SingularAttribute<Tarea, Date> inicio;
	public static volatile SingularAttribute<Tarea, Date> iniciolocal;
	public static volatile SingularAttribute<Tarea, Date> entrega;
	public static volatile SingularAttribute<Tarea, Integer> id;
	public static volatile SingularAttribute<Tarea, Integer> tareade;
	public static volatile SingularAttribute<Tarea, Integer> ticketrecargado;
	public static volatile SingularAttribute<Tarea, String> nombre;
	public static volatile SingularAttribute<Tarea, Integer> prioridad;
	public static volatile SingularAttribute<Tarea, String> detalle;

}

