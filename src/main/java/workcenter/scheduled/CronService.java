package workcenter.scheduled;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
import workcenter.entidades.MirEstadoIncidencia;
import workcenter.entidades.MirIncidencia;
import workcenter.entidades.MirTrazabilidadIncidencia;
import workcenter.negocio.LogicaAlarmasGps;
import workcenter.negocio.incidencias.LogicaIncidencias;
import workcenter.presentacion.includes.AlarmasUploader;
import workcenter.util.components.Constantes;
import workcenter.util.services.MailSender;

/**
 * @author colivares
 */
@Component()
public class CronService {

    @Autowired
    private TestQueryDao testQueryDao;

    @Autowired
    private LogicaAlarmasGps logicaAlarmasGps;

    @Autowired
    private LogicaIncidencias logicaIncidencias;

    @Autowired
    private Constantes constantes;

    @Autowired
    private MailSender mailSender;

    @Scheduled(fixedRate = 30000)
    public void mantenerActivoMySQL() {
        testQueryDao.run();
    }

    // cada 1 hra notificar o cerrar estados incidencias
    @Scheduled(cron = "0 0 */1 * * *")
    public void cerrarIncidencias() {
        List<MirIncidencia> incidencias = logicaIncidencias.obtenerIncidenciasPorEstado(constantes.getPiirEstadoInicial());
        long actual = new Date().getTime();
        long programada = 0;
        long diferencia = 0;
        for (MirIncidencia i : incidencias) {
            programada = i.getResolucionProgramada().getTime();
            diferencia = (programada - actual) / 3600000; // 1 hr 3600 segundos y * 1000 para pasarlo a segundos
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            if (diferencia <= 5 && diferencia > 4) {
                MirEstadoIncidencia estado = logicaIncidencias.obtEstadoActual(i);

                String html = new String(constantes.getPirrMensajeCorreo());
                html = html.replaceAll("\\$tipoCambio", "Marcada para ser Cerrada");
                html = html.replaceAll("\\$estadoTransicion", estado.getNombre());
                html = html.replaceAll("\\$detalleTransicion", "Inactividad por parte de Informador y/o Apoyo");
                html = html.replaceAll("\\$codIncidencia", i.getId().toString());
                html = html.replaceAll("\\$informador", i.getRutInformador().getNombreCompleto());
                html = html.replaceAll("\\$apoyo", i.getIdApoyo().getIdSocio().getNombreCompleto());
                html = html.replaceAll("\\$fecha", sdf.format(i.getFecha()));
                html = html.replaceAll("\\$fResolucion", sdf.format(i.getResolucionProgramada()));
                html = html.replaceAll("\\$detalle", logicaIncidencias.obtDetalleInicial(i));
                html = html.replaceAll("\\$severidad", i.getSeveridad().getNombre());
                html = html.replaceAll("\\$prioridad", i.getSeveridad().getNombre());

                String[] destinos = new String[2];
                destinos[0] = i.getRutInformador().getMail();
                destinos[1] = i.getIdApoyo().getIdSocio().getMail();
                mailSender.send(destinos, "[PIIR #" + i.getId() + "] Incidencia Marcada por Inactividad", html);
            } else if (diferencia <= 0) {
                MirEstadoIncidencia estado = logicaIncidencias.obtenerEstadoIncidencia(constantes.getPiirEstadoCerradaPorSistema());
                MirTrazabilidadIncidencia t = new MirTrazabilidadIncidencia();
                t.setIdIncidencia(i);
                t.setFecha(new Date());
                t.setIdEstado(estado);
                t.setDetalle("El plazo de resolución ha expirado");

                String html = new String(constantes.getPirrMensajeCorreo());
                html = html.replaceAll("\\$tipoCambio", "Cerrada");
                html = html.replaceAll("\\$estadoTransicion", estado.getNombre());
                html = html.replaceAll("\\$detalleTransicion", t.getDetalle());
                html = html.replaceAll("\\$codIncidencia", i.getId().toString());
                html = html.replaceAll("\\$informador", i.getRutInformador().getNombreCompleto());
                html = html.replaceAll("\\$apoyo", i.getIdApoyo().getIdSocio().getNombreCompleto());
                html = html.replaceAll("\\$fecha", sdf.format(i.getFecha()));
                html = html.replaceAll("\\$fResolucion", sdf.format(i.getResolucionProgramada()));
                html = html.replaceAll("\\$detalle", logicaIncidencias.obtDetalleInicial(i));
                html = html.replaceAll("\\$severidad", i.getSeveridad().getNombre());
                html = html.replaceAll("\\$prioridad", i.getSeveridad().getNombre());

                // cerrar incidencia
                logicaIncidencias.guardarIncidencia(i, t);
                String[] destinos = new String[2];
                destinos[0] = i.getRutInformador().getMail();
                destinos[1] = i.getIdApoyo().getIdSocio().getMail();
                mailSender.send(destinos, "[PIIR #" + i.getId() + "] Incidencia Cerrada por Sistema", html);
            }
        }
    }

    // todos los días a las 7 de la mañana descargar correo SAMTECH
    @Scheduled(cron = "0 0 7 * * *")
    public void descargarFicheroAlarmaGPS() {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", constantes.getMarProtocoloCorreo());
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect(constantes.getMarServidorCorreo(), constantes.getMarUsuarioCorreo(), constantes.getMarContrasennaCorreo());
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
