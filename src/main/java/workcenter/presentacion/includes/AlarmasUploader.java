package workcenter.presentacion.includes;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.AlarmaGps;
import workcenter.negocio.LogicaAlarmasGps;
import workcenter.util.pojo.FacesUtil;

/**
 *
 * @author colivares
 */
@Component
@Scope("view")
public class AlarmasUploader {

    @Autowired
    LogicaAlarmasGps logicaAlarmasGps;

    public void subir(FileUploadEvent fue) {
        System.out.println("LLEGO");
//        LogicaAlarmasGps logicaAlarmasGps = new LogicaAlarmasGps();
        InputStream is = null;
        try {
            is = fue.getFile().getInputstream();
            XSSFWorkbook libro = new XSSFWorkbook(is);
            XSSFSheet hoja = libro.getSheetAt(0);
            XSSFRow fila;
            Iterator it = hoja.iterator();
            for (int i = 0; i < 2; i++) {
                // nos saltamos las filas sin datos
                it.next();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String fecha;
            while (it.hasNext()) {
                fila = (XSSFRow) it.next();
                AlarmaGps a = new AlarmaGps();
                a.setAlarma(fila.getCell(4).getStringCellValue());
                a.setChofer(fila.getCell(2).getStringCellValue());
                a.setCliente(fila.getCell(8).getStringCellValue());
                fecha = fila.getCell(3).getStringCellValue().length() < 19 ? "0" + fila.getCell(3).getStringCellValue() : fila.getCell(3).getStringCellValue();
                fecha = fecha.replaceAll("[^0-9/ :]", "").trim();
                a.setFecha(sdf.parse(fecha));
                a.setNumero(Integer.parseInt(fila.getCell(0).getStringCellValue().replaceAll("[^0-9]", "").trim()));
                a.setPatente(fila.getCell(1).getStringCellValue());
                a.setRuta(fila.getCell(6).getStringCellValue());
                a.setUbicacion(fila.getCell(7).getStringCellValue());
                a.setVelocidad(Integer.parseInt(fila.getCell(5).getStringCellValue().replaceAll("[^0-9]", "").trim()));
                logicaAlarmasGps.guardarAlarma(a);
            }
        } catch (IOException ex) {
            Logger.getLogger(AlarmasUploader.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.mostrarMensajeError("Carga fallida", "Problema con el fichero de entrada");
        } catch (ParseException ex) {
            Logger.getLogger(AlarmasUploader.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.mostrarMensajeError("Carga fallida", "Hay una fecha que no cumple con el formato dd/MM/yyyy hh:mm:ss");
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(AlarmasUploader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
