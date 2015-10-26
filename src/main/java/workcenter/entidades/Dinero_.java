package workcenter.entidades;

        import javax.persistence.metamodel.SingularAttribute;
        import javax.persistence.metamodel.StaticMetamodel;
        import java.util.Date;

/**
 * Created by renholders on 24-10-2015.
 */
@StaticMetamodel(Dinero.class)
public class Dinero_ {
    public static volatile SingularAttribute<Dinero,Integer> id;
    public static volatile SingularAttribute<Dinero,Personal> receptor;
    public static volatile SingularAttribute<Dinero,Concepto> concepto;
    public static volatile SingularAttribute<Dinero,Date> fechaactivo;
}
