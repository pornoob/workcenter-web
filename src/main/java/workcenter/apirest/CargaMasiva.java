package workcenter.apirest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by claudio on 12-09-15.
 */
@RestController
public class CargaMasiva {

    @RequestMapping(
            value = "/cargaMasivaDocsPersonal/rut/{rut}/idTipoDoc/{idTipoDoc}/vencimiento/{vencimiento}/numero/{numero}",
            method = RequestMethod.GET
    )
    public String cargaMasivaDocsPersonal(@PathVariable("rut") String rut,
                                        @PathVariable("idTipoDoc") Integer idTipoDoc,
                                        @PathVariable("vencimiento") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) Date vencimiento,
                                        @PathVariable("numero") String numero) {
        return "como estamos";
    }
}
