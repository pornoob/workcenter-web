package workcenter.entidades.metamodels;

import workcenter.entidades.Personal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Created by claudio on 12-10-15.
 */
@StaticMetamodel(Personal.class)
public class Personal_ {
    public static volatile SingularAttribute<Personal, Integer> rut;
    public static volatile SingularAttribute<Personal, String> nombres;
    public static volatile SingularAttribute<Personal, String> apellidos;
    public static volatile SingularAttribute<Personal, String> celular;
}
