package workcenter.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GuiaPetroleo.class)
public abstract class GuiaPetroleo_ {

	public static volatile SingularAttribute<GuiaPetroleo, Date> fecha;
	public static volatile SingularAttribute<GuiaPetroleo, Integer> numeroguia;
	public static volatile SingularAttribute<GuiaPetroleo, Float> litros;
	public static volatile SingularAttribute<GuiaPetroleo, String> equipo;
	public static volatile SingularAttribute<GuiaPetroleo, Integer> ordendecarga;
	public static volatile SingularAttribute<GuiaPetroleo, Integer> id;
	public static volatile SingularAttribute<GuiaPetroleo, Integer> preciolitro;
	public static volatile SingularAttribute<GuiaPetroleo, EstacionServicio> estaciondeservicio;
	public static volatile SingularAttribute<GuiaPetroleo, Integer> conductor;

}

