package workcenter.util.lazymodels;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import workcenter.entidades.Personal;
import workcenter.negocio.personal.LogicaPersonal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PersonalLazyModel extends LazyDataModel<Personal> {
    private LogicaPersonal logicaPersonal;
    private int rowCount;

    public PersonalLazyModel(LogicaPersonal logicaPersonal) {
        super();
        this.logicaPersonal = logicaPersonal;
        rowCount = logicaPersonal.obtenerConteoPersonal();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public void setRowCount(int rowCount) {
        System.err.println("LLEGO CON "+rowCount);
        this.rowCount = rowCount;
    }

    @Override
    public List<Personal> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
        int end = first + pageSize;
        List<Personal> retorno = logicaPersonal.obtenerTodosLazy(first, end, multiSortMeta, filters);
        setRowCount(logicaPersonal.obtenerConteoLazy(filters));
        return retorno;
    }

    @Override
    public List<Personal> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        int end = first + pageSize;
        List<SortMeta> multiSortMeta = new ArrayList<SortMeta>();
        SortMeta sm = new SortMeta();
        sm.setSortField(sortField);
        sm.setSortOrder(sortOrder);
        return logicaPersonal.obtenerTodosLazy(first, end, multiSortMeta, filters);
    }
    
}