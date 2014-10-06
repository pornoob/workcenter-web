package workcenter.scheduled;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import workcenter.dao.TestQueryDao;
import workcenter.entidades.AlarmaGps;
import workcenter.negocio.LogicaAlarmasGps;
import workcenter.presentacion.includes.AlarmasUploader;
import workcenter.util.components.Constantes;

/**
 *
 * @author colivares
 */
@Component()
public class CronService {

    @Autowired
    TestQueryDao testQueryDao;

    @Autowired
    LogicaAlarmasGps logicaAlarmasGps;

    @Autowired
    Constantes constantes;

    @Scheduled(fixedRate = 30000)
    public void mantenerActivoMySQL() {
        testQueryDao.run();
    }

    // todos los días a las importar datos desde la copec
    @Scheduled(cron = "0 0 0 * * *")
    public void descargarRendimientosDiarios() {
    }

    // todos los días a las 7 de la mañana descargar correo SAMTECH
    @Scheduled(cron = "0 0 7 * * *")
    public void descargarFicheroAlarmaGPS() {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", constantes.getProtocoloCorreo());
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect(constantes.getServidorCorreo(), constantes.getUsuarioCorreo(), constantes.getContrasennaCorreo());
            Folder inbox = store.getFolder("SAMTECH");
            inbox.open(Folder.READ_ONLY);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date diaActual = sdf.parse(sdf.format(new Date()));
            Calendar c = Calendar.getInstance();
            c.setTime(diaActual);
            c.add(Calendar.MINUTE, 60 * -24);
            Date diaAnterior = c.getTime();
            
            Message msg = inbox.getMessage(inbox.getMessageCount());

            if (msg.getSentDate().before(diaAnterior)) {
                return;
            }

            Multipart mp = (Multipart) msg.getContent();
            BodyPart bp = mp.getBodyPart(0);
            MimeBodyPart mbp = (MimeBodyPart) mp.getBodyPart(1);

            InputStream is = null;
            try {
                is = mbp.getInputStream();
                XSSFWorkbook libro = new XSSFWorkbook(is);
                XSSFSheet hoja = libro.getSheetAt(0);
                XSSFRow fila;
                Iterator it = hoja.iterator();
                for (int i = 0; i < 2; i++) {
                    // nos saltamos las filas sin datos
                    it.next();
                }
                sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
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
                    a.setRuta(fila.getCell(6).getStringCellValue().replaceAll("[^a-zA-Z-]", ""));
                    a.setUbicacion(fila.getCell(7).getStringCellValue());
                    a.setVelocidad(Integer.parseInt(fila.getCell(5).getStringCellValue().replaceAll("[^0-9]", "").trim()));
                    logicaAlarmasGps.guardarAlarma(a);
                }
            } catch (IOException ex) {
                Logger.getLogger(AlarmasUploader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(AlarmasUploader.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(AlarmasUploader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }

}
