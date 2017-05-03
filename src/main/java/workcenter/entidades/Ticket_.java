package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Ticket.class)
public abstract class Ticket_ {

	public static volatile SingularAttribute<Ticket, Date> fecha;
	public static volatile SingularAttribute<Ticket, Integer> estado;
	public static volatile SingularAttribute<Ticket, Date> fechalocal;
	public static volatile SingularAttribute<Ticket, String> titulo;
	public static volatile SingularAttribute<Ticket, Integer> ticketvinculado;
	public static volatile SingularAttribute<Ticket, Integer> id;
	public static volatile SingularAttribute<Ticket, Integer> emisor;

}

