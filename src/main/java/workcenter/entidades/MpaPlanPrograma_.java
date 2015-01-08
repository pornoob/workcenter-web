package workcenter.entidades;

import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

/**
 * Created by claudio on 26-12-14.
 */
@StaticMetamodel(MpaPlanPrograma.class)
public class MpaPlanPrograma_ {
    public static volatile SingularAttribute<MpaPlanPrograma, Integer> id;
    public static volatile SingularAttribute<MpaPlanPrograma, Date> fecha;
    public static volatile SingularAttribute<MpaPlanPrograma, Personal> rutCreador;
    public static volatile SingularAttribute<MpaPlanPrograma, Personal> rutResponsable;
    public static volatile SingularAttribute<MpaPlanPrograma, MpaActividad> idActividad;
    public static volatile SingularAttribute<MpaPlanPrograma, MpaPrograma> idPrograma;
    public static volatile CollectionAttribute<MpaPlanPrograma, MpaValorPlanPrograma> mpaValorPlanProgramaCollection;
    public static volatile SingularAttribute<MpaPlanPrograma, Integer> anioVigencia;
    public static volatile SingularAttribute<MpaPlanPrograma, MpaContrato> contrato;
}
