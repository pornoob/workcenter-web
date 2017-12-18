package workcenter.presentacion.includes;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import workcenter.entidades.Documento;
import workcenter.negocio.LogicaDocumentos;
import workcenter.util.components.Constantes;
import workcenter.util.components.FacesUtil;

import java.io.*;
import java.util.Date;

import static org.springframework.util.FileCopyUtils.BUFFER_SIZE;

/**
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FicheroUploader {
    @Autowired
    LogicaDocumentos logicaDocumentos;

    @Autowired
    Constantes constantes;

    public Documento subir(FileUploadEvent fue) {
        try {
            String ruta = constantes.getPathArchivos();
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

    public Documento subir(UploadedFile uf) {
        String ruta = constantes.getPathArchivos();
        Documento d = new Documento();
        d.setFecha(new Date());
        d.setNombreOriginal(uf.getFileName());
        logicaDocumentos.guardarDocumento(d);
        FacesUtil.mostrarMensajeInformativo("Fichero guardado", "ID Generado " + d.getId());

        File result = new File(ruta);
        result.mkdirs();
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(result.getAbsolutePath() + "/" + d.getId());
            byte[] buffer = new byte[BUFFER_SIZE];
            int bulk;
            InputStream inputStream = uf.getInputstream();
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
