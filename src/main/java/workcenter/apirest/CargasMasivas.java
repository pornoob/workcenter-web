package workcenter.apirest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import workcenter.entidades.*;
import workcenter.negocio.LogicaDocumentos;
import workcenter.negocio.LogicaTipoDocPersonal;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.util.components.Constantes;
import workcenter.util.components.Formato;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by claudio on 14-09-15.
 */
@RestController
@RequestMapping("restapi")
public class CargasMasivas {

    @Autowired
    private Formato formato;

    @Autowired
    private Constantes constantes;

    @Autowired
    private LogicaTipoDocPersonal logicaTipoDocPersonal;

    @Autowired
    private LogicaPersonal logicaPersonal;

    @Autowired
    private LogicaDocumentos logicaDocumentos;

    @RequestMapping(method = RequestMethod.GET, value = "cargaMasivaDocsPersonal/rut/{rut}/idTipoDoc/{idTipoDoc}/vencimiento/{vencimiento}/numero/{numero}", produces = "application/json")
    public String cargaMasivaDocsPersonal(
            @PathVariable String rut,
            @PathVariable Integer idTipoDoc,
            @PathVariable String vencimiento,
            @PathVariable Integer numero
    ) throws ParseException {
        if (rut == null) return "{\"resultado\": \"Rut inválido\"}";

        boolean rutValido = Pattern.matches("\\d{1,2}\\.\\d{3}.\\d{3}-[kK0-9]", rut);

        if (!rutValido) return "{\"resultado\": \"Rut inválido\"}";

        String path = constantes.getPathArchivos() + "Documentos/personal/" + rut + "/";
        new File(path).mkdirs();

        TipoDocPersonal tdp = logicaTipoDocPersonal.obtenerPorID(idTipoDoc);

        path += tdp.getEtiqueta() + ".pdf";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = sdf.parse(vencimiento);

        Personal personal = logicaPersonal.obtener(Integer.parseInt(rut.split("-")[0].replaceAll("\\.", "")));
        personal.setDocumentos(logicaPersonal.obtenerDocumentos(personal));

        DocumentoPersonal existente = null;
        for (DocumentoPersonal dp : personal.getDocumentos()) {
            if (dp.getTipo().equals(tdp)) {
                existente = dp;
                break;
            }
        }
        String resultado = "{ \"resultado\": \"Sin documento Prevevio\" }";
        if (existente != null) {
            Documento d = new Documento();
            d.setFecha(new Date());
            System.err.println("EXISTENTE: " + existente);
            d.setNombreOriginal(existente.getArchivo().substring(existente.getArchivo().lastIndexOf('/') + 1));
            logicaDocumentos.guardarDocumento(d);

            HistorialDocumentosPersonal respaldo = new HistorialDocumentosPersonal();
            respaldo.setNumero(existente.getNumero());
            respaldo.setPersonal(Integer.parseInt(rut.split("-")[0].replaceAll("\\.", "")));
            respaldo.setTipo(tdp.getId());
            respaldo.setVencimiento(existente.getVencimiento());
            logicaPersonal.guardarHistorialDocumento(respaldo);

            new File(constantes.getPathArchivos()).mkdirs();

            try {
                Files.move(Paths.get(constantes.getPathArchivos() + existente.getArchivo()), Paths.get(constantes.getPathArchivos() + d.getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            logicaDocumentos.asociarDocumento(d, respaldo);
            resultado = "{ \"resultado\": \"Documento respaldado\" }";
        }

        return resultado;
    }
}
