package workcenter.util.lazymodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import workcenter.entidades.Personal;
import workcenter.negocio.personal.LogicaPersonal;

public class PersonalLazyModel extends LazyDataModel<Personal> {
    private LogicaPersonal logicaPersonal;

    public PersonalLazyModel(LogicaPersonal logicaPersonal) {
        super();
        this.logicaPersonal = logicaPersonal;
    }

    @Override
    public List<Personal> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
        return logicaPersonal.obtenerTodosLazy(first, first, multiSortMeta, filters);
    }

    @Override
    public List<Personal> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<SortMeta> multiSortMeta = new ArrayList<SortMeta>();
        SortMeta sm = new SortMeta();
        sm.setSortField(sortField);
        sm.setSortOrder(sortOrder);
        return logicaPersonal.obtenerTodosLazy(first, first, multiSortMeta, filters);
    }
    
}