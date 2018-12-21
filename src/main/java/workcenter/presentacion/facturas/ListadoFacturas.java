package workcenter.presentacion.facturas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.dao.FacturaDao;
import workcenter.entidades.FactFactura;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

@Component
@Scope("view")
public class ListadoFacturas implements Serializable {
    private List<FactFactura> facturaList;

    @Autowired
    private FacturaDao facturaDao;

    @PostConstruct
    public void init() {}
}
