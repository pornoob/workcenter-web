package workcenter.presentacion.includes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import static org.springframework.util.FileCopyUtils.BUFFER_SIZE;
import workcenter.entidades.Documento;
import workcenter.negocio.LogicaDocumentos;
import workcenter.util.components.SesionCliente;
import workcenter.util.pojo.FacesUtil;

/**
 *
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FicheroUploader {
    @Autowired
    LogicaDocumentos logicaDocumentos;

    public Documento subir(FileUploadEvent fue) {
        try {
            String ruta = System.getProperty("catalina.base") + "/static/workcenter/";
            Documento d = new Documento();
            d.setFecha(new Date());
            d.setNombreOriginal(fue.getFile().getFileName());
            logicaDocumentos.guardarDocumento(d);
            FacesUtil.mostrarMensajeInformativo("Fichero guardado", "ID Generado " + d.getId());

            File result = new File(ruta);
            result.mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(result.getAbsolutePath() + "/" + d.getId());
            byte[] buffer = new byte[BUFFER_SIZE];
            int bulk;
            InputStream inputStream = fue.getFile().getInputstream();
            while (true) {
                bulk = inputStream.read(buffer);
                if (bulk < 0) {
                    break;
                }
                fileOutputStream.write(buffer, 0, bulk);
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            inputStream.close();
            return d;
        } catch (IOException e) {
            e.printStackTrace();
            FacesUtil.mostrarMensajeError("No se ha subido el fichero", "Error interno, consulte con el administrador");
            return null;
        }
    }
}
